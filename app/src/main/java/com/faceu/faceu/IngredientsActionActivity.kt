package com.faceu.faceu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.faceu.faceu.databinding.ActivityIngredientsActionBinding

class IngredientsActionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIngredientsActionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIngredientsActionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAnalyze.setOnClickListener {
            startActivity(Intent(this, IngredientsResultActivity::class.java))
        }

        binding.btnAddMore.setOnClickListener {
            startActivity(Intent(this, IngredientsAddMoreActivity::class.java))
        }
        binding.btnScanAgain.setOnClickListener {
            startActivity(Intent(this, IngredientsCameraActivity::class.java))
        }
    }
}