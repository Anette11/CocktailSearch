package com.example.cocktailsearch.api

import com.example.cocktailsearch.model.datacategories.CocktailCategories
import com.example.cocktailsearch.model.datafiltered.CocktailsFiltered
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Url

interface CocktailWebservice {

    @GET
    fun getCocktailsByFilter(
        @Url filter: String
    ): Observable<CocktailsFiltered>

    @GET
    fun getCocktailCategories(
        @Url filter: String
    ): Observable<CocktailCategories>
}