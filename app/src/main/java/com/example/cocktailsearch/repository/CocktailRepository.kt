package com.example.cocktailsearch.repository

import com.example.cocktailsearch.api.CocktailWebservice

class CocktailRepository(
    private val cocktailWebservice: CocktailWebservice
) {
    fun getCocktailsByFilter(filter: String) =
        cocktailWebservice.getCocktailsByFilter(filter)

    fun getCocktailCategories(filter: String) =
        cocktailWebservice.getCocktailCategories(filter)
}