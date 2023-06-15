package com.faceu.faceu.data.network

import com.faceu.faceu.data.response.ListResultResponse
import com.faceu.faceu.data.response.PredictionsItem
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @Multipart
    @POST("/predict_image")
    fun uploadImage(
        @Part uploaded_file: MultipartBody.Part
    ):Call<PredictionsItem>

    @GET("/show_prediction")
    fun getPrediction(): Call<PredictionsItem>

    @GET("/show_predictions")
    fun getPredictions(): Call<ListResultResponse>
}