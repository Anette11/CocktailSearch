package com.example.cocktailsearch.model.datafiltered

import com.google.gson.annotations.SerializedName

data class Drink(
    @SerializedName("idDrink")
    val idDrink: String,

    @SerializedName("strDrink")
    val strDrink: String,

    @SerializedName("strDrinkThumb")
    val strDrinkThumb: String,
)