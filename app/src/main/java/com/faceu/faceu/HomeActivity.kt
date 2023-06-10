package com.faceu.faceu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.faceu.faceu.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivLibrary.setOnClickListener {
            startActivity(Intent(this, LibraryActivity::class.java))
        }

        binding.btnAddIngredients.setOnClickListener {
            startActivity(Intent(this, IngredientsActivity::class.java))
        }

        binding.btnAddFace.setOnClickListener {
            startActivity(Intent(this, CreateMethodActivity::class.java))
        }

        binding.ivLogout.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        supportActionBar?.hide()
    }
}