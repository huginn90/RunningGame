package com.iot.surfaceviewtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        GameView gameView = new GameView(this);
        setContentView(gameView);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
