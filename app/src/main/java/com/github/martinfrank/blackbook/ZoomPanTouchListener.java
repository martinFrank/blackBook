package com.github.martinfrank.blackbook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import androidx.annotation.NonNull;

public class ZoomPanTouchListener implements View.OnTouchListener {

    private final GestureDetector gestureDetector;
    private final ScaleGestureDetector mScaleDetector;


    public ZoomPanTouchListener(Context ctx) {
        gestureDetector = new GestureDetector(ctx, new MyGestureListener());
        mScaleDetector = new ScaleGestureDetector(ctx, new MyScaleGestureDetector());
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mScaleDetector.onTouchEvent(event);
        gestureDetector.onTouchEvent(event);
        return true;
    }

    private final class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
            onPan(distanceX, distanceY);
            return super.onScroll(e1, e2, distanceX, distanceY);

        }

    }

    private final class MyScaleGestureDetector implements ScaleGestureDetector.OnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            ZoomPanTouchListener.this.onScale(scaleGestureDetector.getScaleFactor());
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {

        }


    }

    public void onScale(float factor) {
    }

    public void onPan(float distanceX, float distanceY) {
    }

}
