package com.iot.surfaceviewtest;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by hdj on 2017-05-30.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    GameThread gameThread = null;

    public GameView(Context context) {
        super(context);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread = new GameThread(holder, getResources());
        gameThread.on();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        gameThread.onSizeChanged(width, height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        gameThread.off();
    }
}
