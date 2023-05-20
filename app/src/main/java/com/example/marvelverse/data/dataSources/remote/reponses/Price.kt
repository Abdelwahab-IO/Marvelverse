package com.example.marvelverse.data.dataSources.remote.reponses

import com.google.gson.annotations.SerializedName

data class Price(
	@SerializedName("type")
	val type: String? = null,
	@SerializedName("price")
	val price: Double? = null,
)
