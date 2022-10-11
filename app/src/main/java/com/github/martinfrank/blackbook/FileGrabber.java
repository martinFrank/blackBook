package com.github.martinfrank.blackbook;

import android.content.Context;
import android.net.Uri;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FileGrabber {
    public static File grab(Context context, Uri uri) {
        try {
            File outputDir = context.getCacheDir(); // context being the Activity pointer
            File outputFile = File.createTempFile("mytempfile", ".zip", outputDir);
            writeFile(outputFile, context.getContentResolver().openInputStream(uri));
            return outputFile;
        } catch (IOException e) {
            //file cannot be grabbed
        }
        return null;
    }

    public static boolean writeFile(File target, InputStream inputStream) {
        try {
            Files.copy(inputStream,
                    target.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            //file cannot be grabbed
            return false;
        }

    }
}
