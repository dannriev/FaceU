package com.faceu.faceu.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.faceu.faceu.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        // Initialize Firebase Auth
        auth = Firebase.auth

        binding.tvCreateAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            if (binding.etEmail.text.isNotEmpty() && binding.etPassword.text.isNotEmpty()){
                login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
            } else {
                Toast.makeText(this@LoginActivity, "Please input email and/or password first", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun login(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {task ->
            if (task.isSuccessful && task.result!=null){
                if (task.result.user != null){
                    reload()
                } else {
                    Toast.makeText(this, "Failed to Login", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Failed to Login", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload()
        }
    }

    private fun reload() {
        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
    }
}