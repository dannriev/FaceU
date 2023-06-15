package com.faceu.faceu.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.faceu.faceu.data.response.PredictionsItem
import com.faceu.faceu.databinding.ActivityFaceHistoryBinding
import com.faceu.faceu.ui.adapter.PredictionsAdapter
import com.faceu.faceu.ui.vm.FaceViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FaceHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFaceHistoryBinding
    private val faceViewModel = FaceViewModel()
    private val adapter = PredictionsAdapter()
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivityFaceHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth
        val user = auth.currentUser
        binding.tvGreetingsName.text = user?.displayName

        adapter.notifyDataSetChanged()

        binding.rvFaceHistory.layoutManager = LinearLayoutManager(this)
        binding.rvFaceHistory.setHasFixedSize(true)
        binding.rvFaceHistory.adapter = adapter

        faceViewModel.getListPredictions()

        faceViewModel.isLoading.observe(this){state ->
            showLoading(state)
        }
        faceViewModel.listPredictions.observe(this){ items ->
            if (items.size != 0){
                binding.rvFaceHistory.visibility = View.VISIBLE
                adapter.setList(items)
            } else {
                binding.rvFaceHistory.visibility = View.GONE
                Toast.makeText(this@FaceHistoryActivity, "Predictions not found!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnAddFace.setOnClickListener {
            startActivity(Intent(this, CreateMethodActivity::class.java))
        }

        binding.icBackToLibrary.setOnClickListener {
            finish()
        }

        binding.rvFaceHistory.setOnClickListener {
            startActivity(Intent(this, FaceHistoryActivity::class.java))
        }
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}