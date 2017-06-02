package com.iot.surfaceviewtest;

/**
 * Created by hdj on 2017-05-30.
 */

public class Random {

    public static int get(int min, int max) {
        if(min>max) return -1;
        int difference = max - min + 1;

        int value = (int) (Math.random()*difference);
        int result = min + value;
        return result;
    }
}
