package com.iot.surfaceviewtest;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.SurfaceHolder;

import java.util.Vector;

import static com.iot.surfaceviewtest.R.drawable.ball;

/**
 * Created by hdj on 2017-05-30.
 */

public class GameThread extends Thread {

    private Vector<Entity> listball = new Vector<>();
    private Entity bg = null;
    private boolean haveToStop = false;
    private SurfaceHolder surfaceHolder = null;
    private Rect rectFrame = new Rect();

    public GameThread(SurfaceHolder surfaceHolder, Resources res) {

        Bitmap bitmap = BitmapFactory.decodeResource(res, ball);
        bitmap = removeColor(bitmap, Color.WHITE);

        for(int i=0; i<1; i++) {
            Entity ball = new Entity(200);
            ball.setImage(bitmap);
            ball.setSpeed(Random.get(5,15), Random.get(5,15));
            listball.add(ball);
        }

        this.surfaceHolder = surfaceHolder;

        bg = new Entity(255);
        bg.setImage(res, R.drawable.bg);
        bg.setSpeed(-5, 0);
    }

    private Bitmap removeColor(Bitmap bitmap, int color) {
        int size = bitmap.getWidth()*bitmap.getHeight();
        int[] array = new int[size];
        bitmap.getPixels(array, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        for(int i=0; i<array.length; i++) {
            if(array[i]==color) {
                array[i] = Color.TRANSPARENT;
            }
        }

        return Bitmap.createBitmap(array, 0, bitmap.getWidth(), bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

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
            if(canvas!=null)
                surfaceHolder.unlockCanvasAndPost(canvas);

            // 게임 진행
            update();
        }
    }

    private void update() {
        for(Entity ball : listball)
            ball.move(rectFrame);

        bg.move(rectFrame);
    }

    private void draw(Canvas canvas) {
        bg.draw(canvas);
        for(Entity ball : listball)
            ball.draw(canvas);

    }

    public void onSizeChanged(int width, int height) {
        rectFrame.set(0,0,width,height);

        for(Entity ball : listball) {
            ball.setPosition(0, 0);
            double widthBall = width / 10.0;
            ball.setSize((int) widthBall, (int) widthBall);
        }

        bg.setPosition(width, 0);
        bg.setSize(width, height);
    }

    public void on() {
        this.start();
    }

    public void off() {
        this.haveToStop = true;
    }
}
