package com.retail.kbv.retailapp.ui.activities

import android.app.Activity
import android.graphics.Matrix
import android.graphics.RectF
import android.hardware.Camera
import android.media.CamcorderProfile
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.view.*
import com.retail.kbv.retailapp.R
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.File

class CameraActivity: Activity(), SurfaceHolder.Callback {

    private val CAMERA_ID = Camera.CameraInfo.CAMERA_FACING_FRONT

    private var holder: SurfaceHolder? = null
    private var camera: Camera? = null
    private var surfaceView: SurfaceView? = null

    private var mediarecorder: MediaRecorder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        surfaceView = cameraAct_surface

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        holder = surfaceView?.holder
        holder?.addCallback(this)

        start.setOnClickListener {
            if (prepareVideoRecorder()) {
                mediarecorder?.start()
            } else {
                releaseMediaRecorder()
            }
        }

        stop.setOnClickListener {
            if (mediarecorder != null) {
                mediarecorder!!.stop()
                releaseMediaRecorder()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        camera = Camera.open(CAMERA_ID)
//        setPreviewSize(true)
    }

    override fun onPause() {
        super.onPause()
        camera?.release()
        camera = null
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        camera?.setPreviewDisplay(holder)
        camera?.startPreview()
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        camera?.stopPreview()
        setCameraDisplayOrientation(CAMERA_ID)

        camera?.setPreviewDisplay(holder)
        camera?.startPreview()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {

    }

    private fun setPreviewSize(full: Boolean) {
        val display = windowManager.defaultDisplay
        val landscape = display.width > display.height
        val cameraSize = camera?.parameters?.previewSize

        val rectDisplay = RectF()
        val rectPreview = RectF()

        rectDisplay.set(0f, 0f, display.width.toFloat(), display.height.toFloat())
        if (landscape) {
            rectPreview.set(0f, 0f, cameraSize!!.width.toFloat(), cameraSize.height.toFloat())
        } else {
            rectPreview.set(0f, 0f, cameraSize!!.height.toFloat(), cameraSize.width.toFloat())
        }

        val matrix = Matrix()
        if (!full) {
            matrix.setRectToRect(rectPreview, rectDisplay, Matrix.ScaleToFit.START)
        } else {
            matrix.setRectToRect(rectDisplay, rectPreview, Matrix.ScaleToFit.START)
            matrix.invert(matrix)
        }
        matrix.mapRect(rectPreview)

        surfaceView?.layoutParams?.height = rectPreview.bottom.toInt()
        surfaceView?.layoutParams?.width = rectPreview.right.toInt()
    }

    private fun setCameraDisplayOrientation(cameraId: Int) {
        val rotation = windowManager.defaultDisplay.rotation
        var degrees = 0
        when (rotation) {
            Surface.ROTATION_0 -> degrees = 0
            Surface.ROTATION_90 -> degrees = 90
            Surface.ROTATION_180 -> degrees = 180
            Surface.ROTATION_270 -> degrees = 270
        }

        var result = 0
        val info = Camera.CameraInfo()
        Camera.getCameraInfo(cameraId, info)
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
            result = ((360 - degrees) + info.orientation)
        } else if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = ((360 - degrees) - info.orientation)
            result += 360
        }
        result %= 360
        camera?.setDisplayOrientation(result)

    }

    private fun prepareVideoRecorder(): Boolean {
        mediarecorder = MediaRecorder()
        camera?.unlock()

        mediarecorder?.setCamera(camera)
        mediarecorder?.setAudioSource(MediaRecorder.AudioSource.CAMCORDER)
        mediarecorder?.setVideoSource(MediaRecorder.VideoSource.CAMERA)
        mediarecorder?.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_LOW))
        mediarecorder?.setOutputFile(getOutputVideoFile().toString())
        mediarecorder?.setPreviewDisplay(surfaceView?.holder?.surface)

        try {
            mediarecorder?.prepare()
        } catch (e: Exception) {
            e.printStackTrace()
            releaseMediaRecorder()
            return false
        }
        return true
    }

    private fun releaseMediaRecorder() {
        mediarecorder?.reset()
        mediarecorder?.release()
        mediarecorder = null
        camera?.lock()
    }

    private fun getOutputVideoFile(): File? {
        val mediaStorageDir = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "HereVideos")
        if (mediaStorageDir.exists()
                || mediaStorageDir.mkdir()) {
            val timeStamp = System.currentTimeMillis().toString()
            return File(mediaStorageDir.path + File.separator + "VID_" + timeStamp + ".mp4")
        }
        return null
    }
}