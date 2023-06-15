package com.faceu.faceu.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.faceu.faceu.R
import com.faceu.faceu.databinding.ActivityFaceResultBinding
import com.faceu.faceu.ui.vm.FaceViewModel
import java.io.File

class FaceResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFaceResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivityFaceResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mFile = intent.getSerializableExtra(EXTRA_IMAGE) as File
        val label = intent.getStringExtra(EXTRA_LABEL)

        Glide.with(this)
            .load(mFile)
            .into(binding.ivResult)

        binding.btnCamera.setOnClickListener {
            startActivity(Intent(this, FaceCameraActivity::class.java))
        }

        binding.ivHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        when(label) {
            "Acne" -> {
                binding.tvResult.setText(R.string.acne)
            }
            "Dark Spot" -> {
                binding.tvResult.setText(R.string.dark_spot)
            }
            "Normal" -> {
                binding.tvResult.setText(R.string.normal)
            }
            "Redness" -> {
                binding.tvResult.setText(R.string.redness)
            }
            "Wrinkles" -> {
                binding.tvResult.setText(R.string.wrinkle)
            }
            else -> {
                binding.tvResult.setText(R.string.default_result)
            }
        }

    }
    companion object {
        const val EXTRA_IMAGE = "extra_image"
        const val EXTRA_LABEL = "extra_label"
    }
}