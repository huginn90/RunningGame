package com.iot.surfaceviewtest;

import android.graphics.Rect;
import android.util.Log;

import java.util.Vector;

/**
 * Created by hdj on 2017-06-02.
 */

public class Character extends Entity {

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

    public void jump() {
        if(!jumpflag) {
            jumpflag = true;
            setSpeed(0, -5);
        }
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

    public void charactermove(Vector<Tile> tilelist, Rect rectFrame) {
        super.move();
        if(this.isOverTile(tilelist)) {
            if(!this.isJumpflag()) {
                // 타일위이면서 점프중이 아니면 그대로 유지.
                this.setSpeed(0, 0);
            }
        }
        else {
            Rect characterRect = this.getRect();
            if(characterRect.top < rectFrame.height()/5) {
                // 점프로 일정 높이에 도달했을때.
                this.setJumpflag(false);
            }
            if(!this.isJumpflag()) {
                // 타일위가 아니고, 점프중이 아니면 떨어짐.
                this.setSpeed(0, 5);
            }
        }
    }
}
