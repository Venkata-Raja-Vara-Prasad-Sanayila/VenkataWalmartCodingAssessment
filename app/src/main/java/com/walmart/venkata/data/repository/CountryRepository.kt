package com.walmart.venkata.data.repository

import com.walmart.venkata.data.common.ApiResponseState
import com.walmart.venkata.data.mapper.toDomainModel
import com.walmart.venkata.data.remote.ApiService
import com.walmart.venkata.domain.model.Country
import com.walmart.venkata.domain.repository.ICountryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

/**
 * Repository implementation that handles remote data source operations related to countries.
 *
 * @property ApiService is the Retrofit service used to fetch country data.
 */
class CountryRepository(private val apiService: ApiService): ICountryRepository{

    /**
     * Fetches the list of countries from the API.
     * @return A [Flow] emitting states of the network call result.
     */
    override fun getCountriesInfo(): Flow<ApiResponseState<List<Country>>> = flow{

        emit(ApiResponseState.Loading)
        val response = apiService.getCountriesInfo()
        if (response.isSuccessful){

            val result = response.body()?.map { it.toDomainModel() }

            if (result != null) {
                emit(ApiResponseState.Success(result))
            } else {
                emit(ApiResponseState.Error("No data available"))
            }
        }
        else {
            emit(ApiResponseState.Error("Network Error: ${response.errorBody()?.string()?:"Unknown Error"}"))
        }

    }.catch {
        emit(ApiResponseState.Error("Exception: ${it.localizedMessage}"))
    }

}