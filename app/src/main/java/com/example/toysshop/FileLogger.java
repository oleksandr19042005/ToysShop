package com.example.toysshop;

import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileLogger {

    private static final String TAG = "FileLogger";
    private static final String LOG_FILE_NAME = "app_log.txt";

    public static void log(Context context, String message) {
        String formattedMessage = getFormattedLogMessage(message);
        Log.d(TAG, formattedMessage);
        writeToFile(context, formattedMessage);
    }

    private static String getFormattedLogMessage(String message) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentTime = sdf.format(new Date());
        return currentTime + " - " + message;
    }

    private static void writeToFile(Context context, String message) {
        try {
            File logFile = new File(context.getExternalFilesDir(null), LOG_FILE_NAME);
            BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true));
            writer.write(message);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            Log.e(TAG, "Error writing to log file: " + e.getMessage());
        }
    }
}
