package com.github.martinfrank.blackbook;

import android.annotation.SuppressLint;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    @SuppressLint("ClickableViewAccessibility") //this is an app for me - and i can see clearly
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BlackBookView blackBookView = findViewById(R.id.view_blackbook);
        blackBookView.setOnTouchListener(new SwipeZoomTouchListener(MainActivity.this) {
            public void onSwipeRight() {
                super.onSwipeRight();
                Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                super.onSwipeLeft();
                Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onScale(float factor) {
                super.onScale(factor);
            }
        });

    }
}