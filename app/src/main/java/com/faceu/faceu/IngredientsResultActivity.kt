package com.faceu.faceu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.faceu.faceu.databinding.ActivityIngredientsResultBinding

class IngredientsResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIngredientsResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIngredientsResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backToIngredientsMore.setOnClickListener {
            startActivity(Intent(this, IngredientsAddMoreActivity::class.java))
        }

        binding.backToHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}