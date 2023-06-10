package com.faceu.faceu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.faceu.faceu.databinding.ActivityFaceAnalyzeBinding

class FaceAnalyzeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFaceAnalyzeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFaceAnalyzeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCamera.setOnClickListener {
            startActivity(Intent(this, FaceCameraActivity::class.java))
        }

        binding.btnStartAnalyze.setOnClickListener {
            startActivity(Intent(this, FaceResultActivity::class.java))
        }
    }
}