package com.github.martinfrank.blackbook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.Nullable;

import java.io.File;


public class BlackBookView extends androidx.appcompat.widget.AppCompatImageView {
    private Matrix matrix = new Matrix();

    private float originalWidth;
    private float originalHeight;
    private float xPan;
    private float yPan;

    private float scaleFactor = 1;

    public BlackBookView(Context context) {
        super(context);
        init();
    }

    public BlackBookView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BlackBookView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setScaleType(ScaleType.MATRIX);
    }


    public void setImage(File currentImage) {
        if(currentImage == null){
            return;
        }
        Bitmap bitmap = BitmapFactory.decodeFile(currentImage.getAbsolutePath());
        originalWidth = bitmap.getWidth();
        originalHeight = bitmap.getHeight();
        setImageBitmap(bitmap);
    }

    public void resetGestures() {

        if(originalHeight == 0 || originalWidth == 0){
            xPan = 0;
            yPan = 0;
            scaleFactor = 1;
        }else{
            scaleFactor = Math.min(getHeight()/originalHeight, getWidth()/originalWidth);
            xPan = (getWidth() - originalWidth * scaleFactor)/2f;
            yPan = (getHeight() - originalHeight * scaleFactor)/2f;
        }
        matrix.setTranslate(xPan, yPan);
        matrix.postScale(scaleFactor, scaleFactor);
        setImageMatrix(matrix);
    }

    private void adjustToFitScreen() {
        scaleFactor = Math.min(getHeight()/originalHeight, getWidth()/originalWidth);
        matrix.setTranslate(xPan, yPan);
        matrix.postScale(scaleFactor, scaleFactor);
        xPan = (getHeight() - originalHeight * scaleFactor)/2f;
        yPan = (getWidth() - originalWidth * scaleFactor)/2f;
        matrix.setTranslate(xPan, yPan);
    }

    public void adjustScale(float factor) {
        scaleFactor = scaleFactor * factor;
        matrix.postScale(scaleFactor, scaleFactor);
        setImageMatrix(matrix);
    }

    public void adjustPan(float distanceX, float distanceY) {
        xPan = xPan - distanceX;
        yPan = yPan - distanceY;
        matrix.setTranslate(xPan, yPan);
        matrix.postScale(scaleFactor, scaleFactor);
        setImageMatrix(matrix);
    }


}
