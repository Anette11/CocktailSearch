package com.example.cocktailsearch.model.datafiltered

import com.google.gson.annotations.SerializedName

data class CocktailsFiltered(
    @SerializedName("drinks")
    val drinks: List<Drink>
)