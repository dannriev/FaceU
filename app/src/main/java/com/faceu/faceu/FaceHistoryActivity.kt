package com.faceu.faceu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.faceu.faceu.databinding.ActivityFaceHistoryBinding

class FaceHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFaceHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFaceHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddFace.setOnClickListener {
            startActivity(Intent(this, CreateMethodActivity::class.java))
        }

        binding.icBackToLibrary.setOnClickListener {
            startActivity(Intent(this, LibraryActivity::class.java))
        }

        binding.rvFaceHistory.setOnClickListener {
            startActivity(Intent(this, FaceHistoryActivity::class.java))
        }
    }
}