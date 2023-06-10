package com.faceu.faceu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.faceu.faceu.databinding.ActivityFaceCameraBinding

class FaceCameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFaceCameraBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFaceCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backToMethod.setOnClickListener {
            startActivity(Intent(this, CreateMethodActivity::class.java))
        }

        binding.ivClickCamera.setOnClickListener {
            startActivity(Intent(this, FaceAnalyzeActivity::class.java))
        }
    }
}