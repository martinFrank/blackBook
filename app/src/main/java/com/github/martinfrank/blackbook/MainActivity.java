package com.github.martinfrank.blackbook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;

public class MainActivity extends AppCompatActivity {


    private static final int PICK_PDF_FILE = 122334443;

    @SuppressLint("ClickableViewAccessibility") //this is an app for me - and i can see clearly
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BlackBookView blackBookView = findViewById(R.id.view_blackbook);
        blackBookView.setOnTouchListener(new SwipeZoomTouchListener(MainActivity.this) {
            public void onSwipeRight() {
                super.onSwipeRight();
            }
            public void onSwipeLeft() {
                super.onSwipeLeft();
            }
            @Override
            public void onScale(float factor) {
                super.onScale(factor);
            }
        });

        ImageButton addButton = findViewById(R.id.button_add);
        addButton.setOnClickListener(view -> loadBlackBook());

    }

    private void loadBlackBook() {
//        Toast.makeText(this, "load...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");

        // Optionally, specify a URI for the file that should appear in the
        // system file picker when it loads.
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, "pickerInitialUri");

        startActivityForResult(intent, PICK_PDF_FILE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        // The ACTION_OPEN_DOCUMENT intent was sent with the request code OPEN_DIRECTORY_REQUEST_CODE.
        // If the request code seen here doesn't match, it's the response to some other intent,
        // and the below code shouldn't run at all.
        if (requestCode == PICK_PDF_FILE) {
            if (resultCode == Activity.RESULT_OK) {
                // The document selected by the user won't be returned in the intent.
                // Instead, a URI to that document will be contained in the return intent
                // provided to this method as a parameter.  Pull that uri using "resultData.getData()"
                if (resultData != null && resultData.getData() != null) {
                    File file = new File(resultData.getData().getPath());

                    Toast.makeText(this, ""+file.getName()+" -> "+file.lastModified(), Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d("File uri not found {}");
                }
            } else {
                //Log.d("User cancelled file browsing {}");
            }
        }
    }
}