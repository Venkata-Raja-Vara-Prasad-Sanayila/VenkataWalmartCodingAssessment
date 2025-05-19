package com.walmart.venkata.domain.repository

import com.walmart.venkata.data.common.ApiResponseState
import com.walmart.venkata.domain.model.Country
import kotlinx.coroutines.flow.Flow

interface ICountryRepository {

    fun getCountriesInfo(): Flow<ApiResponseState<List<Country>>>
}