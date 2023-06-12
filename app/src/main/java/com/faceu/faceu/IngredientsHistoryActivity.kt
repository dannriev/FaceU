package com.faceu.faceu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.faceu.faceu.databinding.ActivityIngredientsHistoryBinding

class IngredientsHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIngredientsHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIngredientsHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddIngredients.setOnClickListener {
            startActivity(Intent(this, IngredientsActivity::class.java))
        }

        binding.ivback.setOnClickListener {
            startActivity(Intent(this, LibraryActivity::class.java))
        }

        binding.rvIngredientsHistory.setOnClickListener {
            startActivity(Intent(this, IngredientsResultActivity::class.java))
        }
    }
}