package com.faceu.faceu.ui

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.faceu.faceu.BuildConfig
import com.faceu.faceu.R
import com.faceu.faceu.databinding.ActivityFaceCameraBinding
import com.faceu.faceu.ui.vm.FaceViewModel
import java.io.*
import java.util.Objects

class FaceCameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFaceCameraBinding

    private lateinit var currentFileImg: File
    private lateinit var tempCameraImg: String

    private val intentGalleryResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val resultImg = uriToFile(it.data?.data as Uri, this)
                currentFileImg = resultImg

                Glide.with(this)
                    .load(resultImg)
                    .placeholder(R.drawable.baseline_image_24)
                    .error(R.drawable.baseline_image_24)
                    .into(binding.ivPreview)
            }
        }

    private val intentCameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val resultImg = File(tempCameraImg)
                currentFileImg = resultImg

                Glide.with(this)
                    .load(resultImg)
                    .placeholder(R.drawable.baseline_image_24)
                    .error(R.drawable.baseline_image_24)
                    .into(binding.ivPreview)
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivityFaceCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backToMethod.setOnClickListener {
            finish()
        }


        binding.btnSubmit.setOnClickListener {
            val intent = Intent(this, FaceAnalyzeActivity::class.java)
            intent.putExtra(FaceAnalyzeActivity.EXTRA_IMAGE, currentFileImg)
            startActivity(intent)
        }

        binding.btnCamera.setOnClickListener {
            val tmpFile = createImageTempFile(this)

            tempCameraImg = tmpFile.absolutePath
            val tempFileUri: Uri = FileProvider.getUriForFile(
                Objects.requireNonNull(this@FaceCameraActivity),
                BuildConfig.APPLICATION_ID + ".provider",
                tmpFile
            )

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                resolveActivity(packageManager)
                putExtra(MediaStore.EXTRA_OUTPUT, tempFileUri)
            }

            intentCameraResult.launch(intent)
        }

        binding.btnGallery.setOnClickListener {
            intentGalleryResult.launch(
                Intent.createChooser(
                    Intent(Intent.ACTION_GET_CONTENT).apply { type = "image/*" },
                    getString(R.string.choose_a_photo)
                )
            )
        }
    }

    private fun createImageTempFile(context: Context): File {
        return File.createTempFile(
            System.currentTimeMillis().toString() + "_" + (Math.random() * 1000).toInt().toString(),
            ".jpg",
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        )
    }

    private fun createCustomTempFile(context: Context): File {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("file", ".jpg", storageDir)
    }

    private fun uriToFile(selectedImg: Uri, context: Context): File {
        val contentResolver: ContentResolver = context.contentResolver
        val myFile = createCustomTempFile(context)
        val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
        val outputStream: OutputStream = FileOutputStream(myFile)
        val buf = ByteArray(1024)
        var len: Int
        while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
        outputStream.close()
        inputStream.close()
        return myFile
    }

}