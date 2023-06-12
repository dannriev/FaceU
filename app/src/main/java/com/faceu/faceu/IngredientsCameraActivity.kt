package com.faceu.faceu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.faceu.faceu.databinding.ActivityIngredientsCameraBinding

class IngredientsCameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIngredientsCameraBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIngredientsCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backToIngredientsAct.setOnClickListener {
            startActivity(Intent(this, IngredientsActivity::class.java))
        }

        binding.ingredientsClick.setOnClickListener {
            startActivity(Intent(this, IngredientsActionActivity::class.java))
        }

        binding.btnLibrary.setOnClickListener {
            startActivity(Intent(this, IngredientsHistoryActivity::class.java))
        }
    }
}