package com.jc.practice.utils;

import android.os.Handler;
import android.os.Looper;

import com.jc.practice.listener.UploadCallbacks;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * Created by jcskh on 06-06-2016.
 */
public class ProgressRequestBody extends RequestBody {
    private File file;
    private UploadCallbacks uploadCallbacks;

    public ProgressRequestBody(File file, UploadCallbacks uploadCallbacks) {
        this.file = file;
        this.uploadCallbacks = uploadCallbacks;
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse("multipart/form-data");
    }

    @Override
    public long contentLength() throws IOException {
        return file.length();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        byte[] buffer = new byte[2048];
        FileInputStream in = new FileInputStream(file);
        long uploaded = 0;

        try {
            int read;
            Handler handler = new Handler(Looper.getMainLooper());
            while ((read = in.read(buffer)) != -1) {

                uploaded += read;
                sink.write(buffer, 0, read);
                handler.post(new ProgressUpdater(uploaded, contentLength()));
            }
        } finally {
            in.close();
        }
    }

    private class ProgressUpdater implements Runnable {
        private long mUploaded;
        private long mTotal;

        public ProgressUpdater(long mUploaded, long mTotal) {
            this.mUploaded = mUploaded;
            this.mTotal = mTotal;
        }

        @Override
        public void run() {
            int progress_percentage = (int)(100 * mUploaded / mTotal);
            uploadCallbacks.onProgressUpdate(progress_percentage);
        }
    }
}
