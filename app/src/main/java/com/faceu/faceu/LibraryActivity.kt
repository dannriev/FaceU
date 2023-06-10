package com.faceu.faceu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.faceu.faceu.databinding.ActivityHomeBinding
import com.faceu.faceu.databinding.ActivityLibraryBinding
import com.faceu.faceu.databinding.ActivityLoginBinding

class LibraryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLibraryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLibraryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivFace.setOnClickListener {
            startActivity(Intent(this, FaceHistoryActivity::class.java))
        }

        binding.ivIngredients.setOnClickListener {
            startActivity(Intent(this, IngredientsHistoryActivity::class.java))
        }
    }
}