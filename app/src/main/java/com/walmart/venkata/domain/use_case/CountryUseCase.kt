package com.walmart.venkata.domain.use_case

import com.walmart.venkata.data.common.ApiResponseState
import com.walmart.venkata.domain.model.Country
import com.walmart.venkata.domain.repository.ICountryRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case to fetch the list of countries from the repository.
 * This encapsulates the business logic for retrieving country data,
 * and returns a Flow emitting loading, success, or error states.
 */
class CountryUseCase(private val repository: ICountryRepository) {

    operator fun invoke(): Flow<ApiResponseState<List<Country>>> = repository.getCountriesInfo()

}