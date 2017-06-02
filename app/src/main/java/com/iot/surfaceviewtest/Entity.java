package com.iot.surfaceviewtest;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by hdj on 2017-05-30.
 */

class Entity {
    private Point position = new Point(0, 0);
    private Point speed = new Point(0, 0);
    private Point size = new Point(0, 0);
    private Bitmap image = null;

    private Rect rectSrc = new Rect();
    private Rect rectDst = new Rect();
    private Paint paint = new Paint();

    public Entity(int alpha) {
        //투명도 50%
        paint.setAlpha(alpha);
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void setPosition(int x, int y) {
        this.position.set(x, y);
    }

    public Point getSpeed() {
        return speed;
    }

    public void setSpeed(Point speed) {
        this.speed = speed;
    }

    public void setSpeed(int x, int y) {
        this.speed.set(x, y);
    }

    public Point getSize() {
        return size;
    }

    public void setSize(Point size) {
        this.size = size;
    }

    public void setSize(int x, int y) {
        this.size.set(x, y);
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setImage(Resources res, int image) {

        Bitmap bitmap = BitmapFactory.decodeResource(res, image);
        this.image = bitmap;
    }

    public void draw(Canvas canvas) {
        // 원본에서 가져오기
        rectSrc.set(0, 0, image.getWidth(), image.getHeight());

        // 그릴 영역
        rectDst.set(position.x, position.y, position.x+size.x  ,position.y+size.y);

        canvas.drawBitmap(image, rectSrc, rectDst, paint);
    }

    public void move(Rect rectFrame) {
        position.x+=speed.x;
        position.y+=speed.y;
    }

    public Rect getRect() {
        rectDst.set(position.x, position.y, position.x+size.x, position.y+size.y);

        return rectDst;
    }
}
