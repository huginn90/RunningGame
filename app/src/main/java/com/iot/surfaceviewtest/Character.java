package com.iot.surfaceviewtest;

import android.graphics.Rect;
import android.util.Log;

import java.util.Vector;

/**
 * Created by hdj on 2017-06-02.
 */

public class Character extends Entity {
    private Rect rectFrame = null;

    public void setJumpflag(boolean jumpflag) {
        this.jumpflag = jumpflag;
    }

    private boolean jumpflag = false;

    public Character() {
        super(255);
        setSpeed(0, 0);
    }

    public boolean isJumpflag() {
        return jumpflag;
    }

    public void setRectFrame(Rect rectFrame) {
        this.rectFrame = rectFrame;
    }

    public void jump() {
        jumpflag = true;
        setSpeed(0, -5);
    }

    public boolean isOverTile(Vector<Tile> tilelist) {
        for (int i = 0; i < tilelist.size(); i++) {
            Tile tile = tilelist.get(i);
            if (Rect.intersects(getRect(), tile.getRect())) {
                Log.i("Character", "isovertile");
                return true;
            }
        }
        Log.i("Character", "notovertile");
        return false;
    }

}
