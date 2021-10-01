package com.example.cocktailsearch.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cocktailsearch.R
import com.example.cocktailsearch.adapters.CocktailListAdapter
import com.example.cocktailsearch.databinding.FragmentHomeBinding
import com.example.cocktailsearch.util.CocktailConstants
import com.example.cocktailsearch.viewmodel.CocktailViewModel
import com.google.android.material.chip.Chip
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val cocktailListAdapter: CocktailListAdapter by inject()
    private val viewModel: CocktailViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )
        getCocktailsByFilter()
        observeProgressBarToChangeVisibility()
        setOnCheckedChangeListenerForChipGroup()
        observeChipsCheckedStatusToSwitchFilteredListInRecyclerView()
        initializeRecyclerView()
        return binding.root
    }

    private fun getCocktailsByFilter() =
        viewModel.getCocktailsByFilter(CocktailConstants.filterAlcoholic)

    private fun setOnCheckedChangeListenerForChipGroup() {
        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip: Chip? = group.findViewById(checkedId)
            if (chip?.text == resources.getString(R.string.alcoholic)) {
                viewModel.getCocktailsByFilter(CocktailConstants.filterAlcoholic)
                viewModel.checkedChip.postValue(resources.getString(R.string.alcoholic))
            } else if (chip?.text == resources.getString(R.string.non_alcoholic)) {
                viewModel.getCocktailsByFilter(CocktailConstants.filterNonAlcoholic)
                viewModel.checkedChip.postValue(resources.getString(R.string.non_alcoholic))
            }
        }
    }

    private fun observeChipsCheckedStatusToSwitchFilteredListInRecyclerView() {
        viewModel.checkedChip.observe(viewLifecycleOwner, { chipText ->
            if (chipText == resources.getString(R.string.alcoholic)) {
                cocktailListAdapter.submitList(viewModel.cocktailsFilterAlcoholic.value?.drinks)
            } else if (chipText == resources.getString(R.string.non_alcoholic)) {
                cocktailListAdapter.submitList(viewModel.cocktailsFilterNonAlcoholic.value?.drinks)
            }
        })
    }

    private fun observeProgressBarToChangeVisibility() {
        viewModel.progressBarVisibilityHomeFragment.observe(viewLifecycleOwner, {
            if (it) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        })
    }

    private fun initializeRecyclerView() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = cocktailListAdapter
        }
    }
}