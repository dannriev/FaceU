package com.faceu.faceu.data.response

import com.google.gson.annotations.SerializedName

data class ListResultResponse(

	@field:SerializedName("predictions")
	val predictions: List<PredictionsItem>
)
