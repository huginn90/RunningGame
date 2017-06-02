package com.iot.surfaceviewtest;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.SurfaceHolder;

import java.util.Vector;


/**
 * Created by hdj on 2017-05-30.
 */

public class GameThread extends Thread {
    private long setTime = 0;

    private Character character;
    private Vector<BackGround> bglist = new Vector<>();
    private Vector<Tile> tilelist = new Vector<>();
    private boolean haveToStop = false;
    private SurfaceHolder surfaceHolder = null;
    private Rect rectFrame = new Rect();
    private Resources resource;

    public GameThread(SurfaceHolder surfaceHolder, Resources res) {

        this.resource = res;
        this.surfaceHolder = surfaceHolder;

        character = new Character();
    }

    @Override
    public void run() {
        super.run();
        while (!haveToStop) {
            // 화면 잠그기
            Canvas canvas = surfaceHolder.lockCanvas();

            // 화면 그리기
            draw(canvas);

            // 화면 풀기
            if (canvas != null)
                surfaceHolder.unlockCanvasAndPost(canvas);

            // 게임 진행
            update();
        }
    }

    private void update() {

        //배경 이동
        for (int i = 0; i < bglist.size(); i++) {
            bglist.get(i).move(rectFrame);

            Rect bgrect = bglist.get(i).getRect();
            if (bgrect.right < 0) {
                // 앞쪽 배경 삭제. 뒤쪽 배경 추가.
                bglist.removeElementAt(i);
                BackGround newbg = new BackGround();
                newbg.setImage(resource, R.drawable.gamebg);
                newbg.setSpeed(-5, 0);
                newbg.setPosition(rectFrame.right, 0);
                newbg.setSize(rectFrame.width(), rectFrame.height());
                bglist.add(newbg);
            }
        }

        //  타일 이동
        for (int i = 0; i < tilelist.size(); i++) {
            // 화면에서 사라진 타일 삭제.
            Tile tile = tilelist.get(i);
            if (tile.getRect().right < 0) {
                tilelist.removeElementAt(0);
            }

            tile.move(rectFrame);

            // 타일이 화면에 다 그려지면 다음 타일 생성.
            Rect lasttile = tilelist.lastElement().getRect();
            if (lasttile.right < rectFrame.right) {

                Tile newtile = new Tile();
                newtile.setImage(resource, R.drawable.block1);
                int tileheight = rectFrame.height() - (rectFrame.height() / 5);
                newtile.setPosition(rectFrame.right + (rectFrame.width() / 4), tileheight);
                newtile.setSize(Random.get(rectFrame.width() / 10, rectFrame.width() / 4), rectFrame.height() / 20);
                tilelist.add(newtile);

            }
        }

        // 캐릭터 이동
        character.move(rectFrame);
        // 타일위가 아니면 밑으로 떨어짐.
        if(!character.isOverTile(tilelist)) {
            character.setSpeed(0, 5);
        }
        else
            character.setSpeed(0, 0);

    }

    private void draw(Canvas canvas) {
        for(int i=0; i<bglist.size(); i++) {
            bglist.get(i).draw(canvas);
        }

        character.draw(canvas);

        for (int i=0; i<tilelist.size(); i++) {
            tilelist.get(i).draw(canvas);
        }
    }

    public void onSizeChanged(int width, int height) {
        rectFrame.set(0, 0, width, height);

        // 배경화면 추가.
        for (int i = 0; i < 3; i++) {
            BackGround bg = new BackGround();
            bg.setImage(resource, R.drawable.gamebg);
            bg.setPosition(rectFrame.right * i, 0);
            bg.setSize(rectFrame.width(), rectFrame.height());
            bglist.add(bg);
        }

        // 초기 타일 생성.
        Tile tile = new Tile();
        tile.setImage(resource, R.drawable.block1);
        int tileheight = rectFrame.height() - (rectFrame.height() / 5);
        tile.setPosition(0, tileheight);
        tile.setSize(rectFrame.width(), rectFrame.height() / 20);
        tilelist.add(tile);

        // 캐릭터 추가.
        Bitmap bitmap = BitmapFactory.decodeResource(resource, R.drawable.runcharacter1);
        bitmap = removeColor(bitmap, Color.WHITE);
        int widthCharacter = width / 10;
        character.setImage(bitmap);
        character.setPosition(width / 5, tileheight - widthCharacter);
        character.setSize(widthCharacter, widthCharacter);
    }

    public void on() {
        this.start();
    }

    public void off() {
        this.haveToStop = true;
    }

    // 색깔 제거
    private Bitmap removeColor(Bitmap bitmap, int color) {
        int size = bitmap.getWidth() * bitmap.getHeight();
        int[] array = new int[size];
        bitmap.getPixels(array, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        for (int i = 0; i < array.length; i++) {
            if (array[i] == color) {
                array[i] = Color.TRANSPARENT;
            }
        }

        return Bitmap.createBitmap(array, 0, bitmap.getWidth(), bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

    }

    public void gameTouched() {
        if(character.isOverTile(tilelist)) {
            character.jump();
        }
    }
}
