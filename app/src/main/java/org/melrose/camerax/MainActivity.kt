package org.melrose.camerax

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Matrix
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.util.Size
import android.view.Surface
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.heyi.support.extend.fullScreen
import org.heyi.support.extend.screenHeight
import org.heyi.support.extend.screenWidth
import org.jetbrains.anko.longToast
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.toast
import org.melrose.camerax.ui.MainActivityUi

class MainActivity : AppCompatActivity() {
    private val TAG:String=this.javaClass.simpleName
    companion object{
        const val CODE_REQUEST_PERMISSIONS = 99
    }

    private val REQUEST_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

    lateinit private var ui: MainActivityUi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreen()
        MainActivityUi().also { ui=it }.setContentView(this)

        ui.texture?.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            updateTransform()
        }
        if (allPermissionsGranted()){
           startCamera()
        }else{
            ActivityCompat.requestPermissions(this,REQUEST_PERMISSIONS,CODE_REQUEST_PERMISSIONS)
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (allPermissionsGranted()){
          startCamera()
        }else{
            toast("Permissions not granted by the user.")
        }
    }


    private fun startCamera(){
       ui.texture?.let {
           texture->
           val previewConfig = PreviewConfig.Builder().apply {
               setTargetResolution(Size(screenWidth, screenHeight))
           }.build()

           val imageCaptureConfig = ImageCaptureConfig.Builder()
               .apply {
                   // We don't set a resolution for image capture; instead, we
                   // select a capture mode which will infer the appropriate
                   // resolution based on aspect ration and requested mode
                   setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
               }.build()

           val imageCapture = ImageCapture(imageCaptureConfig)

           //imageCapture.takePicture()

           val preview = Preview(previewConfig)
           preview.setOnPreviewOutputUpdateListener{
               texture.surfaceTexture = it.surfaceTexture
               updateTransform()
           }

           CameraX.bindToLifecycle(this,preview,imageCapture)
       }
    }


    private fun updateTransform(){
        ui.texture?.let {
            val matrix = Matrix()

            // Compute the center of the view finder
            val centerX = it.width / 2f
            val centerY = it.height / 2f

            if(it.display!=null){
                // Correct preview output to account for display rotation
                val rotationDegrees = when(it.display.rotation) {
                    Surface.ROTATION_0 -> 0
                    Surface.ROTATION_90 -> 90
                    Surface.ROTATION_180 -> 180
                    Surface.ROTATION_270 -> 270
                    else -> return
                }
                matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)

                // Finally, apply transformations to our TextureView
                it.setTransform(matrix)
            }
            }

    }


    private fun allPermissionsGranted() = REQUEST_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
}






