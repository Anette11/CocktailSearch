package com.example.cocktailsearch.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailsearch.databinding.OneItemFilteredBinding
import com.example.cocktailsearch.model.datafiltered.Drink

class CocktailListAdapter :
    ListAdapter<Drink, CocktailListAdapter.CocktailViewHolder>(CocktailDiffUtil()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CocktailViewHolder {
        val binding = OneItemFilteredBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CocktailViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CocktailViewHolder,
        position: Int
    ) {
        val drink = getItem(position)
        holder.bind(drink)
    }

    class CocktailViewHolder(
        private val binding: OneItemFilteredBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(drink: Drink) {
            binding.drink = drink
        }
    }

    private class CocktailDiffUtil : DiffUtil.ItemCallback<Drink>() {
        override fun areItemsTheSame(oldItem: Drink, newItem: Drink): Boolean =
            oldItem.strDrink == newItem.strDrink

        override fun areContentsTheSame(oldItem: Drink, newItem: Drink): Boolean =
            oldItem == newItem
    }
}