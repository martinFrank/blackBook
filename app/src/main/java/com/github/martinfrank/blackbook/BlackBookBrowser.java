package com.github.martinfrank.blackbook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;

import java.io.*;
import java.util.zip.ZipFile;

public class BlackBookBrowser {

    private static final String LOG_TAG = "BlackBookBrowser";

    private int index;
    private int size;

    private Drawable current;
    private Drawable next;
    private Drawable previous;
    //    private ZipFile zipFile;
    private BlackBookEntries blackBookEntries;

    public BlackBookBrowser() {
    }

    public boolean loadBlackBook(File blackBookFile) {
        if (blackBookFile != null) {
            try {
                blackBookEntries = new BlackBookEntries(new ZipFile(blackBookFile));
                index = 0;
                return true;
            } catch (IOException e) {
                //error must be handled with return value (true/false)
            }
        }
        return false;
    }

    public File getCurrentAsFile(Context context){
        if(blackBookEntries == null){
            return null;
        }
        return blackBookEntries.createFile(index, context);
    }

    public void indexToNextImage() {
        if(blackBookEntries == null){
            return;
        }
        index = index + 1;
        if(index >= blackBookEntries.size() ){
            index = 0;
        }
    }

    public void indexToPreviousImage() {
        if(blackBookEntries == null){
            return;
        }
        index = index - 1;
        if(index < 0){
            index = blackBookEntries.size()-1;
        }
    }

    public void setIndex(int index){
        this.index = index;
    }

    public int getIndex(){
        return index;
    }
}
