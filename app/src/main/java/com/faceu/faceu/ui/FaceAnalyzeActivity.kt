package com.faceu.faceu.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.faceu.faceu.databinding.ActivityFaceAnalyzeBinding
import com.faceu.faceu.ui.vm.FaceViewModel
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class FaceAnalyzeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFaceAnalyzeBinding
    private lateinit var label: String
    private val faceViewModel = FaceViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivityFaceAnalyzeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mFile = intent.getSerializableExtra(EXTRA_IMAGE) as File

        binding.btnCamera.setOnClickListener {
            startActivity(Intent(this, FaceCameraActivity::class.java))
        }

        binding.btnStartAnalyze.setOnClickListener {
            uploadImage(mFile)
            faceViewModel.isLoading.observe(this){ state ->
                showLoading(state)
                faceViewModel.prediction.observe(this){
                    if (it!=null){
                        label = it.predictedClass
                        val intent = Intent(this, FaceResultActivity::class.java)
                        intent.putExtra(FaceResultActivity.EXTRA_IMAGE, mFile)
                        intent.putExtra(FaceResultActivity.EXTRA_LABEL, label)
                        startActivity(intent)
                    }
                }
            }
        }
        Glide.with(this)
            .load(mFile)
            .into(binding.ivFaceRes)
    }

    private fun uploadImage(image: File) {
        faceViewModel.uploadImage(image)
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val EXTRA_IMAGE = "extra_image"
    }
}