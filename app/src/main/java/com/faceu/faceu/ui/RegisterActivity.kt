package com.faceu.faceu.ui

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.faceu.faceu.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        // Initialize Firebase Auth
        auth = Firebase.auth

        binding.btnRegister.setOnClickListener {
            //startActivity(Intent(this, LoginActivity::class.java))
            if (binding.tvName.text.isNotEmpty() && binding.tvEmail.text.isNotEmpty() && binding.tvPassword.text.isNotEmpty()){
                register(binding.tvName.text.toString(), binding.tvEmail.text.toString(), binding.tvPassword.text.toString())
                finish()
            } else {
                Toast.makeText(this@RegisterActivity, "Please input username and/or email and/or password first", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvRegisterToLogin.setOnClickListener {
            finish()
        }
    }

    private fun register(username: String, email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful && task.result != null) {
                val user = task.result.user
                user?.updateProfile(userProfileChangeRequest {
                    setDisplayName(username).build()
                })
                // Sign in success, update UI with the signed-in user's information
                Toast.makeText(this, "Successfully Registered", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(baseContext, "Fail to register.", Toast.LENGTH_SHORT).show()
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
        startActivity(Intent(this@RegisterActivity, HomeActivity::class.java))
    }
}