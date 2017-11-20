package com.retail.kbv.retailapp.ui.activities;

import android.app.Activity;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.retail.kbv.retailapp.R;
import com.retail.kbv.retailapp.ui.custom.CameraPreview;

import java.io.File;
import java.io.IOException;

public class CameraJavaActivity extends Activity {

    private Camera        camera;
    private CameraPreview cameraPreview;
    private MediaRecorder mediaRecorder;
    private boolean isRecording = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_camera);

        camera = getCameraInstance();
        cameraPreview = new CameraPreview(this, camera);

        ((FrameLayout)findViewById(R.id.cameraAct_surface)).addView(cameraPreview);


        findViewById(R.id.start).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isRecording) {
                            if (CameraJavaActivity.this.prepareVideoRecorder()) {
                                mediaRecorder.start();
                                isRecording = true;
                            } else {
                                CameraJavaActivity.this.releaseMediaRecorder();
                            }
                        }
                    }
                });
        findViewById(R.id.stop).setOnClickListener(
                v -> {
                    if (isRecording) {
                        mediaRecorder.stop();  // stop the recording
                        releaseMediaRecorder(); // release the MediaRecorder object
                        camera.lock();         // take camera access back from MediaRecorder
                        isRecording = false;
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
//        releaseMediaRecorder();
//        releaseCamera();
    }

    private void releaseCamera(){
        if (camera != null){
            camera.release();
            camera = null;
        }
    }

    private void releaseMediaRecorder(){
        if (mediaRecorder != null) {
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;
            camera.lock();
        }
    }

    public Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
        } catch (Exception e) {
            Log.d("myLog", "Camera == null");
        }
        return c;
    }

    private boolean prepareVideoRecorder(){
        releaseCamera();
        camera = getCameraInstance();
        mediaRecorder = new MediaRecorder();

        // Step 1: Unlock and set camera to MediaRecorder
        camera.unlock();
        mediaRecorder.setCamera(camera);

        // Step 2: Set sources
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        // Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));

        // Step 4: Set output file
        mediaRecorder.setOutputFile(getOutputMediaFile().toString());

        // Step 5: Set the preview output
        mediaRecorder.setPreviewDisplay(cameraPreview.getHolder().getSurface());

        // Step 6: Prepare configured MediaRecorder
        try {
            mediaRecorder.prepare();
        } catch (IllegalStateException e) {
            releaseMediaRecorder();
            return false;
        } catch (IOException e) {
            releaseMediaRecorder();
            return false;
        }
        return true;
    }

    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        String timeStamp = String.valueOf(System.currentTimeMillis());
        return new File(mediaStorageDir.getPath() + File.separator +
                        "VID_" + timeStamp + ".mp4");
    }
}
