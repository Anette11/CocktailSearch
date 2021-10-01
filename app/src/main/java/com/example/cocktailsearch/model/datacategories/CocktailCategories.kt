package com.example.cocktailsearch.model.datacategories

import com.google.gson.annotations.SerializedName

data class CocktailCategories(
    @SerializedName("drinks")
    val drinks: List<Drink>
)