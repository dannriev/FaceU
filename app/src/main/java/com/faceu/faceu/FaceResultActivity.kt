package com.faceu.faceu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.faceu.faceu.databinding.ActivityFaceResultBinding

class FaceResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFaceResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFaceResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}