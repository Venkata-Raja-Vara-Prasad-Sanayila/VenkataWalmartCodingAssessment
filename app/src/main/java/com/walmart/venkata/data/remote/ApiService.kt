package com.walmart.venkata.data.remote

import com.walmart.venkata.data.dto.CountryDto
import com.walmart.venkata.data.common.utils.Constants
import retrofit2.Response
import retrofit2.http.GET

/**
 * Retrofit service for fetching country data
 */
interface ApiService {

    @GET(Constants.GET_COUNTRIES_END_POINT)
    suspend fun getCountriesInfo(): Response<List<CountryDto>>
}