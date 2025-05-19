package com.walmart.venkata.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.walmart.venkata.data.common.ApiResponseState
import com.walmart.venkata.domain.model.Country
import com.walmart.venkata.domain.use_case.CountryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for fetching and exposing the list of countries.
 * It communicates with the CountryUseCase and emits UI states for the view to observe.
 */
class CountryViewModel(private val countryUseCase: CountryUseCase): ViewModel() {

    // Holds the current UI state: Loading, Success, or Error
    private val _uiState = MutableStateFlow<ApiResponseState<List<Country>>>(ApiResponseState.Loading)
    val uiState = _uiState.asStateFlow()

    //Fetch countries when ViewModel is initialized
    init {
        fetchCountriesInfo()
    }

    /**
     * Fetches the list of countries using the country use case and emits the UI state.
     * Runs in the IO dispatcher as it performs network I/O operation.
     */
    private fun fetchCountriesInfo(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiState.emit(ApiResponseState.Loading)
                countryUseCase().collectLatest {
                    _uiState.emit(it)
                }
            }
            catch (e: Exception){
                _uiState.emit(ApiResponseState.Error("Error: ${e.localizedMessage}"))
            }
        }
    }

}