package com.walmart.venkata.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.walmart.venkata.domain.use_case.CountryUseCase

/**
 * Factory class for creating instances of [CountryViewModel] with constructor parameters.
 *
 * This factory is needed because [CountryViewModel] requires a [CountryUseCase] dependency,
 * which cannot be provided by the default ViewModelProvider.
 */
class CountryViewModelFactory(private val countryUseCase: CountryUseCase): ViewModelProvider.NewInstanceFactory() {

    /**
     * @return a new instance of [CountryViewModel].
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CountryViewModel(countryUseCase) as T
    }
}