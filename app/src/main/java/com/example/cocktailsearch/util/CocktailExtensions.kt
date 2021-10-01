package com.example.cocktailsearch.util

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.cocktailsearch.R

@BindingAdapter("loadImageFromUrl")
fun ImageView.loadImageFromUrl(imageUrl: String?) {
    Log.d("CocktailExtensions", "loadImageFromUrl()")
    this.load(imageUrl) {
        crossfade(true)
        placeholder(R.drawable.ic_cup)
        error(R.drawable.ic_cup)
    }
}