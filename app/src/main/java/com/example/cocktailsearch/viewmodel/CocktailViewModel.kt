package com.example.cocktailsearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cocktailsearch.model.datacategories.CocktailCategories
import com.example.cocktailsearch.model.datafiltered.CocktailsFiltered
import com.example.cocktailsearch.repository.CocktailRepository
import com.example.cocktailsearch.util.CocktailConstants
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class CocktailViewModel(
    private val cocktailRepository: CocktailRepository
) : ViewModel() {
    private val _cocktailsFilterAlcoholic: MutableLiveData<CocktailsFiltered> = MutableLiveData()
    val cocktailsFilterAlcoholic: LiveData<CocktailsFiltered> get() = _cocktailsFilterAlcoholic

    private val _cocktailsFilterNonAlcoholic: MutableLiveData<CocktailsFiltered> = MutableLiveData()
    val cocktailsFilterNonAlcoholic: LiveData<CocktailsFiltered> get() = _cocktailsFilterNonAlcoholic

    private val _cocktailsFilterCategory: MutableLiveData<CocktailsFiltered> = MutableLiveData()
    val cocktailsFilterCategory: LiveData<CocktailsFiltered> get() = _cocktailsFilterCategory

    private val _cocktailICategories: MutableLiveData<ArrayList<String>> = MutableLiveData()
    val cocktailICategories: LiveData<ArrayList<String>> get() = _cocktailICategories

    val chipTextSearchFragment: MutableLiveData<String> = MutableLiveData()

    private val _progressBarVisibilityHomeFragment: MutableLiveData<Boolean> =
        MutableLiveData(false)
    val progressBarVisibilityHomeFragment: LiveData<Boolean> get() = _progressBarVisibilityHomeFragment

    private val _progressBarVisibilitySearchFragment: MutableLiveData<Boolean> =
        MutableLiveData(false)
    val progressBarVisibilitySearchFragment: LiveData<Boolean> get() = _progressBarVisibilitySearchFragment

    val checkedChip: MutableLiveData<String> = MutableLiveData()

    fun getCocktailsByFilter(filter: String) {
        if (checkIfFilteredListIsEmpty(filter)) {
            cocktailRepository.getCocktailsByFilter(filter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<CocktailsFiltered> {

                    override fun onSubscribe(d: Disposable) {
                        postValueForProgressBarVisibilityHomeFragment(true)
                    }

                    override fun onNext(cocktailsFiltered: CocktailsFiltered) {
                        fillFilteredList(filter, cocktailsFiltered)
                    }

                    override fun onError(e: Throwable) {
                        postValueForProgressBarVisibilityHomeFragment(false)
                    }

                    override fun onComplete() {
                        postValueForProgressBarVisibilityHomeFragment(false)
                    }
                })
        }
    }

    fun getCocktailCategories(filter: String) {
        if (checkIfFilteredListIsEmpty(filter)) {
            cocktailRepository.getCocktailCategories(filter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<CocktailCategories> {

                    override fun onSubscribe(d: Disposable) {
                        postValueForProgressBarVisibilitySearchFragment(true)
                    }

                    override fun onNext(cocktailCategories: CocktailCategories) {
                        createArrayListOfCategories(cocktailCategories)
                    }

                    override fun onError(e: Throwable) {
                        postValueForProgressBarVisibilitySearchFragment(false)
                    }

                    override fun onComplete() {
                        postValueForProgressBarVisibilitySearchFragment(false)
                    }
                })
        }
    }

    private fun postValueForProgressBarVisibilityHomeFragment(isVisible: Boolean) =
        _progressBarVisibilityHomeFragment.postValue(isVisible)

    private fun postValueForProgressBarVisibilitySearchFragment(isVisible: Boolean) =
        _progressBarVisibilitySearchFragment.postValue(isVisible)

    private fun createArrayListOfCategories(
        cocktailCategories: CocktailCategories
    ) {
        val arrayList = ArrayList<String>()
        cocktailCategories.drinks.forEach {
            arrayList.add(it.strCategory)
        }
        _cocktailICategories.postValue(arrayList)
    }

    private fun checkIfFilteredListIsEmpty(filter: String): Boolean {
        var isEmpty = true
        when (filter) {
            CocktailConstants.filterAlcoholic -> {
                if (_cocktailsFilterAlcoholic.value != null) isEmpty = false
            }
            CocktailConstants.filterNonAlcoholic -> {
                if (_cocktailsFilterNonAlcoholic.value != null) isEmpty = false
            }
            CocktailConstants.filterCategories -> {
                if (_cocktailICategories.value != null) isEmpty = false
            }
        }
        return isEmpty
    }

    private fun fillFilteredList(
        filter: String,
        cocktailsFiltered: CocktailsFiltered
    ) {
        when (filter) {
            CocktailConstants.filterAlcoholic -> {
                _cocktailsFilterAlcoholic.postValue(cocktailsFiltered)
            }
            CocktailConstants.filterNonAlcoholic -> {
                _cocktailsFilterNonAlcoholic.postValue(cocktailsFiltered)
            }
            else -> {
                _cocktailsFilterCategory.postValue(cocktailsFiltered)
            }
        }
    }
}