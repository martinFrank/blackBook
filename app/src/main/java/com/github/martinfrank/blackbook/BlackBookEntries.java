package com.github.martinfrank.blackbook;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class BlackBookEntries {

    private final String LOG_TAG = "BlackBookEntries";
    private ZipFile zipFile;
    private Map<Integer, String> contentMap = new HashMap<>();

    public BlackBookEntries(ZipFile zipFile) {
        this.zipFile = zipFile;
        readContent();
    }

    private void readContent() {
        Enumeration<? extends ZipEntry> zipEnumerator = zipFile.entries();
        int current = 0;
        while (zipEnumerator.hasMoreElements()) {
            ZipEntry zipEntry = zipEnumerator.nextElement();
            contentMap.put(current, zipEntry.getName());
            Log.d(LOG_TAG, "putting "+current+" / "+ zipEntry.getName());
            current = current + 1;
        }
    }


    public void writeFile(int index, File outputFile){

    }

    public InputStream getZipEntryInputStream(int index) throws IOException {
        if(index >= contentMap.size() ){
            return null;
        }
        return zipFile.getInputStream(zipFile.getEntry(contentMap.get(index)));
    }

    public int size() {
        return contentMap.size();
    }

    public File createFile(int index, Context context) {
        try {
            File outputDir = context.getCacheDir(); // context being the Activity pointer
            File outputFile = File.createTempFile("currentImage", ".jpg", outputDir);

            ZipEntry entry = zipFile.getEntry(contentMap.get(index));
            FileGrabber.writeFile(outputFile, zipFile.getInputStream(entry));
            Log.d(LOG_TAG, "temp file="+outputFile+" size="+ Files.size(outputFile.toPath()));
            return outputFile;
        }catch (IOException e){
            return null;
        }
    }
}
