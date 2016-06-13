package com.jc.practice.listener;

/**
 * Created by jcskh on 06-06-2016.
 */
public interface UploadCallbacks {
    void onProgressUpdate(int percentage);
    void onError();
    void onFinish();
}
