package com.faceu.faceu.ui.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faceu.faceu.data.network.ApiConfig
import com.faceu.faceu.data.response.ListResultResponse
import com.faceu.faceu.data.response.PredictionsItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class FaceViewModel : ViewModel() {

    private val _prediction = MutableLiveData<PredictionsItem>()
    val prediction get() = _prediction

    private val _listPredictions = MutableLiveData<ArrayList<PredictionsItem>>()
    val listPredictions get() = _listPredictions

    private val _isSuccessful = MutableLiveData<Boolean>()
    val isSuccessful get() = _isSuccessful

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading get() = _isLoading

    fun getPrediction(){
        val client = ApiConfig.getApiService().getPrediction()
        client.enqueue(object : Callback<PredictionsItem>{
            override fun onResponse(
                call: Call<PredictionsItem>,
                response: Response<PredictionsItem>
            ) {
                if (response.code() == 200) {
                    isSuccessful.postValue(true)
                    prediction.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<PredictionsItem>, t: Throwable) {
                Log.e("FaceResultActivity", "onFailure Call: ${t.message}")
            }

        })
    }

    fun getListPredictions(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getPredictions()
        client.enqueue(object : Callback<ListResultResponse>{
            override fun onResponse(
                call: Call<ListResultResponse>,
                response: Response<ListResultResponse>
            ) {
                if (response.code() == 200) {
                    _isLoading.value = false
                    isSuccessful.postValue(true)
                    listPredictions.postValue(response.body()?.predictions?.let { ArrayList(it) })
                }
            }

            override fun onFailure(call: Call<ListResultResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("LibraryActivity", "onFailure Call: ${t.message}")
            }

        })
    }

    fun uploadImage(image: File){
        _isLoading.value = true
        "${image.length() / 1024 / 1024} MB" // manual parse from bytes to Mega Bytes
        val requestImageFile = image.asRequestBody("image/jpeg".toMediaType())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "uploaded_file",
            image.name,
            requestImageFile
        )
        val client = ApiConfig.getApiService().uploadImage(imageMultipart)

        client.enqueue(object : Callback<PredictionsItem> {
            override fun onResponse(call: Call<PredictionsItem>, response: Response<PredictionsItem>) {
                if (response.code() == 200){
                    _isLoading.value = false
                    isSuccessful.postValue(true)
                    prediction.postValue(response.body())
                }
                else {
                    _isLoading.value = false
                    isSuccessful.postValue(false)
                    Log.e("FaceResultActivity", "validation_error")
                }
            }

            override fun onFailure(call: Call<PredictionsItem>, t: Throwable) {
                _isLoading.value = false
                Log.e("FaceResultActivity", "onFailure Call: ${t.message}")
            }
        })
    }
}