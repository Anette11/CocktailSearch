package com.example.cocktailsearch.util

import android.app.Application
import com.example.cocktailsearch.di.adapterModule
import com.example.cocktailsearch.di.apiModule
import com.example.cocktailsearch.di.repositoryModule
import com.example.cocktailsearch.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CocktailApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CocktailApplication)
            modules(listOf(apiModule, repositoryModule, viewModelModule, adapterModule))
        }
    }
}