package com.retail.kbv.retailapp.ui.activities

import android.app.Activity
import android.hardware.Camera
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Window
import android.view.WindowManager
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.Facing
import com.otaliastudios.cameraview.SessionType
import com.retail.kbv.retailapp.R
import kotlinx.android.synthetic.main.activity_camera_square.*
import java.io.File

class CameraSquareActivity: Activity() {

    private val CAMERA_ID = Camera.CameraInfo.CAMERA_FACING_FRONT

    private var cameraView: CameraView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_square)

        cameraView = cameraAct_surface_square
        cameraView?.addCameraListener(object : CameraListener() {
            override fun onVideoTaken(video: File?) {
                Log.d("myLogs", "file")
            }
        })

        cameraView?.sessionType = SessionType.VIDEO
        cameraView?.facing = Facing.FRONT
        cameraView?.cropOutput = true


        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)



        start.setOnClickListener {
            val file = getOutputVideoFile()
            cameraView?.startCapturingVideo(file, 3500)
        }

        stop.setOnClickListener {}
    }

    override fun onResume() {
        super.onResume()
        cameraView?.start()

    }

    override fun onPause() {
        super.onPause()
        cameraView?.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraView?.destroy()
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