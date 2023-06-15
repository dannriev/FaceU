package com.faceu.faceu.data.response

import com.google.gson.annotations.SerializedName

data class PredictionsItem(

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("predicted_class")
	val predictedClass: String
)
