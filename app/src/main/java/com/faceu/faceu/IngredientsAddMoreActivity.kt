package com.faceu.faceu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.faceu.faceu.databinding.ActivityIngredientsAddMoreBinding

class IngredientsAddMoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIngredientsAddMoreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIngredientsAddMoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.icBack.setOnClickListener {
            startActivity(Intent(this, IngredientsActionActivity::class.java))
        }

        binding.btnAnalyze.setOnClickListener {
            startActivity(Intent(this, IngredientsResultActivity::class.java))
        }
    }
}