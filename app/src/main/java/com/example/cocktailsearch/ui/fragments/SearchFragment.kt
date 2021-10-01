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
import com.example.cocktailsearch.databinding.FragmentSearchBinding
import com.example.cocktailsearch.ui.dialog.CocktailBottomSheetDialogFragment
import com.example.cocktailsearch.util.CocktailConstants
import com.example.cocktailsearch.viewmodel.CocktailViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val cocktailListAdapter: CocktailListAdapter by inject()
    private val viewModel: CocktailViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search,
            container,
            false
        )
        getCocktailCategories()
        observeProgressBarVisibilitySearchFragment()
        observeCocktailsFilterCategory()
        changeChipTextAndVisibility()
        setOnClickListenerForImageViewSearch()
        initializeRecyclerView()
        return binding.root
    }

    private fun observeProgressBarVisibilitySearchFragment() {
        viewModel.progressBarVisibilitySearchFragment.observe(viewLifecycleOwner, {
            if (it) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        })
    }

    private fun observeCocktailsFilterCategory() {
        viewModel.cocktailsFilterCategory.observe(viewLifecycleOwner, {
            cocktailListAdapter.submitList(it.drinks)
        })
    }

    private fun changeChipTextAndVisibility() {
        viewModel.chipTextSearchFragment.observe(viewLifecycleOwner, {
            binding.chip.text = it
            binding.chip.visibility = View.VISIBLE
        })
    }

    private fun setOnClickListenerForImageViewSearch() {
        binding.imageViewSearch.setOnClickListener {
            val cocktailBottomSheetDialogFragment = CocktailBottomSheetDialogFragment()
            cocktailBottomSheetDialogFragment.show(
                requireActivity().supportFragmentManager,
                getString(R.string.cocktail_bottom_sheet_dialog_fragment)
            )
        }
    }

    private fun getCocktailCategories() =
        viewModel.getCocktailCategories(CocktailConstants.filterCategories)

    private fun initializeRecyclerView() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = cocktailListAdapter
        }
    }
}