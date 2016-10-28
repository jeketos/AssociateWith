package com.jeketos.associatewith;

public class Point {
    public float x;
    public float y;
    public int motionEvent;

    public Point(float x, float y, int motionEvent) {
        this.x = x;
        this.y = y;
        this.motionEvent = motionEvent;
    }

    @Override
    public String toString() {
        return "x = " + x +
                "\ny = " + y +
                "\nevent = " + motionEvent;
    }
}
