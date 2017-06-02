package com.iot.surfaceviewtest;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.Vector;


/**
 * Created by hdj on 2017-05-30.
 */

public class GameThread extends Thread {

    private Character character = null;
    private Vector<BackGround> bglist = new Vector<>();
    private Vector<Ground> groundlist = new Vector<>();
    private boolean haveToStop = false;
    private SurfaceHolder surfaceHolder = null;
    private Rect rectFrame = new Rect();
    private Resources resource;

    public GameThread(SurfaceHolder surfaceHolder, Resources res) {

//        Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.run);
//        bitmap = removeColor(bitmap, Color.WHITE);
        this.resource = res;
        this.surfaceHolder = surfaceHolder;

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

        for (int i = 0; i < 3; i++) {
            // 화면 움직임.

            bglist.get(i).move(rectFrame);

            Rect bgrect = bglist.get(i).getRect();
            if (bgrect.right < 0) {
                // 앞쪽 배경 삭제. 뒤쪽 배경 추가.
                Log.i("back delete", "삭제 되나?"+i);
                bglist.removeElementAt(i);
                BackGround newbg = new BackGround();
                newbg.setImage(resource, R.drawable.gamebg);
                newbg.setSpeed(-5, 0);
                newbg.setPosition(rectFrame.right, 0);
                newbg.setSize(rectFrame.width(), rectFrame.height());
                bglist.add(newbg);
            }
        }

    }

    private void draw(Canvas canvas) {
        for (Entity bg : bglist)
            bg.draw(canvas);

        character.draw(canvas);
    }

    public void onSizeChanged(int width, int height) {
        rectFrame.set(0, 0, width, height);

        // 캐릭터 추가.
        character = new Character();
        character.setImage(resource, R.drawable.run);
        character.setSpeed(0, 0);
        character.setPosition(width/5, height/3);
        double widthCharacter = width / 10.0;
        character.setSize((int) widthCharacter, (int) widthCharacter);

        // 배경화면 추가.
        for (int i = 0; i < 3; i++) {
            BackGround bg = new BackGround();
            bg.setImage(resource, R.drawable.gamebg);
            bg.setSpeed(-5, 0);
            bg.setPosition(rectFrame.right * i, 0);
            bg.setSize(rectFrame.width(), rectFrame.height());
            bglist.add(bg);
        }


    }

    public void on() {
        this.start();
    }

    public void off() {
        this.haveToStop = true;
    }
}
