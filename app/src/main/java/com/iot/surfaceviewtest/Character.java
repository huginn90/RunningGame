package com.iot.surfaceviewtest;

import android.graphics.Rect;
import android.util.Log;

import java.util.Vector;

/**
 * Created by hdj on 2017-06-02.
 */

public class Character extends Entity {
    public Character() {
        super(255);
        setSpeed(0, 0);
    }

    public void jump() {
        setSpeed(0, -5);
        if(getRect().top < 0) {
            setSpeed(0, 5);
        }
    }

    public boolean isOverTile(Vector<Tile> tilelist) {
        for(int i=0; i<tilelist.size(); i++) {
            Tile tile = tilelist.get(i);
            if(Rect.intersects(getRect(), tile.getRect())) {
                Log.i("Character", "isovertile");
                return true;
            }
        }
        Log.i("Character", "notovertile");
        return false;
    }
}
