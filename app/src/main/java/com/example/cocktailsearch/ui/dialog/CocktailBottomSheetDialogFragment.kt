package com.example.cocktailsearch.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.example.cocktailsearch.R
import com.example.cocktailsearch.util.CocktailConstants
import com.example.cocktailsearch.viewmodel.CocktailViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CocktailBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private val viewModel: CocktailViewModel by sharedViewModel()
    private lateinit var arrayListIngredients: ArrayList<String>
    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private lateinit var arrayAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.bottom_sheet_dialog_fragment,
            container,
            false
        )
        autoCompleteTextView = view.findViewById(R.id.auto_complete_text_view)
        initializeArrayList()
        observeCocktailICategories()
        initializeArrayAdapter()
        setAutoCompleteTextViewListener()
        return view
    }

    private fun observeCocktailICategories() {
        viewModel.cocktailICategories.observe(viewLifecycleOwner, { it ->
            it?.let { arrayList -> arrayList.forEach { arrayListIngredients.add(it) } }
        })
    }

    private fun setAutoCompleteTextViewListener() {
        autoCompleteTextView.setAdapter(arrayAdapter)

        autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            val category = arrayAdapter.getItem(position).toString()
            val filter = CocktailConstants
                .filterCategory
                .replace(getString(R.string.category), category)
            viewModel.getCocktailsByFilter(filter)
            viewModel.chipTextSearchFragment.postValue(category)
            autoCompleteTextView.showDropDown()
            dismiss()
        }
    }

    private fun initializeArrayAdapter() {
        arrayAdapter = ArrayAdapter(
            requireContext(),
            R.layout.drop_down_item,
            arrayListIngredients
        )
    }

    private fun initializeArrayList() {
        arrayListIngredients = ArrayList()
    }
}