package com.example.cocktailsearch.di

import com.example.cocktailsearch.adapters.CocktailListAdapter
import com.example.cocktailsearch.api.CocktailWebservice
import com.example.cocktailsearch.repository.CocktailRepository
import com.example.cocktailsearch.util.CocktailConstants
import com.example.cocktailsearch.viewmodel.CocktailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {
    fun provideRetrofitInstance(): Retrofit =
        Retrofit.Builder()
            .baseUrl(CocktailConstants.baseUrl)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun provideCocktailWebservice(retrofit: Retrofit): CocktailWebservice =
        retrofit.create(CocktailWebservice::class.java)

    single { provideRetrofitInstance() }
    single { provideCocktailWebservice(get()) }
}

val repositoryModule = module {
    single { CocktailRepository(get()) }
}

val viewModelModule = module {
    viewModel { CocktailViewModel(get()) }
}

val adapterModule = module {
    factory { CocktailListAdapter() }
}