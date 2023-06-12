package com.faceu.faceu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.faceu.faceu.databinding.ActivityLoginBinding
import com.faceu.faceu.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.tvRegisterToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}