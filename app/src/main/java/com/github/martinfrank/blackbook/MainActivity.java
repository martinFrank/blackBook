package com.github.martinfrank.blackbook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.*;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";
    private static final int CHOOSE_BLACKBOOK_FILE_CODE = 122334443;

    private static final String BUNDLE_FILENAME = "filename";
    private static final String BUNDLE_INDEX = "index";
    private BlackBookView blackBookView;
    private BlackBookBrowser blackBookBrowser;

    private String currentBlackBookFilename;

    private Handler handler;

    private boolean isPlay = false;

    private Runnable autoPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        blackBookBrowser = new BlackBookBrowser();
        if (savedInstanceState != null)
        {
            String filename = savedInstanceState.getString(BUNDLE_FILENAME);
            File f = new File(filename);
            blackBookBrowser.loadBlackBook(f);
            blackBookBrowser.setIndex(savedInstanceState.getInt(BUNDLE_INDEX));
        }


        handler = new Handler(Looper.getMainLooper());

        blackBookView = findViewById(R.id.view_blackbook);

        setGestureListener();

        findViewById(R.id.button_add).setOnClickListener(view -> loadBlackBook());
        findViewById(R.id.button_next).setOnClickListener(view -> nextImage());
        findViewById(R.id.button_prev).setOnClickListener(view -> previousImage());
        findViewById(R.id.button_play).setOnClickListener(view -> togglePlayPause());
        findViewById(R.id.button_reset).setOnClickListener(view -> resetView());

        autoPlayer = () -> {
            nextImage();
            if (isPlay) {
                handler.postDelayed(autoPlayer, 3000);
            }
        };
    }

    private void togglePlayPause() {
        if (isPlay) {
            //play->pause
            isPlay = false;
            //jetzt ist pause, das braucht einen play knopf
            ((ImageButton) findViewById(R.id.button_play)).setImageResource(R.drawable.play);
            handler.removeCallbacks(autoPlayer);
        } else {
            //pause->play
            isPlay = true;
            //jetzt ist play, das braucht einen pause knopf
            ((ImageButton) findViewById(R.id.button_play)).setImageResource(R.drawable.pause);
            handler.postDelayed(autoPlayer, 3000);
        }

    }

    private void resetView(){
        blackBookView.resetGestures();
    }

    private void previousImage() {
        blackBookBrowser.indexToPreviousImage();
        showIndexedImage();
    }

    private void showIndexedImage() {
        File currentImage = blackBookBrowser.getCurrentAsFile(this);
        blackBookView.setImage(currentImage);
    }

    private void nextImage() {
        blackBookBrowser.indexToNextImage();
        showIndexedImage();
    }

    @SuppressLint("ClickableViewAccessibility") //this is an app for me - and I can see clearly
    private void setGestureListener() {
        blackBookView.setOnTouchListener(new ZoomPanTouchListener(MainActivity.this) {

            @Override
            public void onScale(float factor) {
                super.onScale(factor);
                blackBookView.adjustScale(factor);
            }

            @Override
            public void onPan(float distanceX, float distanceY) {
                super.onPan(distanceX, distanceY);
                blackBookView.adjustPan(distanceX, distanceY);
            }
        });
    }

    @SuppressWarnings("deprecation")
    private void loadBlackBook() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/octet-stream");
        startActivityForResult(intent, CHOOSE_BLACKBOOK_FILE_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == CHOOSE_BLACKBOOK_FILE_CODE && resultCode == Activity.RESULT_OK) {
            if (resultData != null && resultData.getData() != null) {
                File selectedFile = FileGrabber.grab(this, resultData.getData());
                currentBlackBookFilename = selectedFile.getAbsolutePath()+File.separator+selectedFile.getName();
                boolean success = blackBookBrowser.loadBlackBook(selectedFile);
                if (!success) {
                    Toast.makeText(this, "error loading selected content", Toast.LENGTH_SHORT).show();
                }
                showIndexedImage();
                resetView();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(BUNDLE_FILENAME, currentBlackBookFilename);
        savedInstanceState.putInt(BUNDLE_INDEX, blackBookBrowser.getIndex());
    }

}