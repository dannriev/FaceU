package com.faceu.faceu.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.faceu.faceu.databinding.ActivityLibraryBinding

class LibraryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLibraryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLibraryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.backToHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        binding.ivFace.setOnClickListener {
            startActivity(Intent(this, FaceHistoryActivity::class.java))
        }
    }
}