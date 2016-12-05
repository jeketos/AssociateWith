package com.jeketos.associatewith.listener;

import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import com.jeketos.associatewith.Point;

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

public class TouchListener implements View.OnTouchListener {
    private float oldX;
    private float oldY;
    private MoveListener moveListener;

    public TouchListener(MoveListener moveListener){
        this.moveListener = moveListener;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        Point point;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                oldX = event.getRawX() - view.getX();
                oldY = event.getRawY() - view.getY();
                point = getPoint(oldX, oldY, MotionEvent.ACTION_DOWN);
                moveListener.actionDown(point);

                break;
            }
            case MotionEvent.ACTION_MOVE: {
                float x = event.getRawX() - view.getX();
                float y = event.getRawY() - view.getY();
                point = getPoint(x, y, MotionEvent.ACTION_MOVE);
                moveListener.actionMove(oldX,oldY,point);
                oldX = x;
                oldY = y;
                break;
            }
            case MotionEvent.ACTION_UP: {
                float x = event.getRawX() - view.getX();
                float y = event.getRawY() - view.getY();
                point = getPoint(x, y, MotionEvent.ACTION_UP);
                moveListener.actionUp(oldX,oldY,point);
                break;
            }
        }
        return true;
    }

    @NonNull
    private Point getPoint(float x, float y, int actionDown) {
//        float density = Resources.getSystem().getDisplayMetrics().density;
        return new Point(x
//                /density
                , y
//                /density
                , actionDown);
    }

    public interface MoveListener{
        void actionDown(Point point);
        void actionMove(float previousX, float previousY, Point point);
        void actionUp(float previousX, float previousY, Point point);

    }
}
