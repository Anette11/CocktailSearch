package com.example.cocktailsearch.model.datacategories

import com.google.gson.annotations.SerializedName

data class Drink(
    @SerializedName("strCategory")
    val strCategory: String
)