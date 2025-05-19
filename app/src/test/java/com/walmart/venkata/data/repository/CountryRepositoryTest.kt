package com.walmart.venkata.data.repository

import com.walmart.venkata.data.common.ApiResponseState
import com.walmart.venkata.data.dto.CountryDto
import com.walmart.venkata.data.dto.Currency
import com.walmart.venkata.data.dto.Language
import com.walmart.venkata.data.mapper.toDomainModel
import com.walmart.venkata.data.remote.ApiService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class CountryRepositoryTest{

  private lateinit var repository: CountryRepository
  private val apiService = mockk<ApiService>()

  @Before
  fun setup(){
      repository = CountryRepository(apiService)
  }

 @Test
 fun `test get countries success response`() =  runTest{

     val expected = getCountriesFake().map { it.toDomainModel() }

     coEvery { apiService.getCountriesInfo() } returns Response.success(getCountriesFake())

     val result = repository.getCountriesInfo().toList()

     assertTrue(result[0] is ApiResponseState.Loading)
     assertTrue(result[1] is ApiResponseState.Success)
     assertEquals(expected, (result[1] as ApiResponseState.Success).data)

 }

    @Test
    fun `test getCountries with network error response`() = runTest {

        coEvery { apiService.getCountriesInfo() } returns Response.error(403, "Forbidden resource".toResponseBody())

        val result = repository.getCountriesInfo().last()
        assertTrue(result is ApiResponseState.Error)
        assertEquals("Network Error: Forbidden resource", (result as ApiResponseState.Error).message)

    }

    @Test
    fun `test getCountries with null response body`() = runTest {

        coEvery { apiService.getCountriesInfo() } returns Response.success(null)

        val result = repository.getCountriesInfo().last()
        assertTrue(result is ApiResponseState.Error)
        assertEquals("No data available", (result as ApiResponseState.Error).message)

    }

    @Test
    fun `test getCountries throws exception`() = runTest {

        coEvery { apiService.getCountriesInfo() } throws RuntimeException("Timeout")

        val result = repository.getCountriesInfo().last()
        assertTrue(result is ApiResponseState.Error)

        assertEquals("Exception: Timeout", (result as ApiResponseState.Error).message)

    }

    private fun getCountriesFake(): List<CountryDto> {
        return listOf(
            CountryDto(
                capital = "Abu Dhabi",
                code = "AE",
                currency = Currency(
                    code = "AED",
                    name = "United Arab Emirates dirham",
                    symbol = "د.إ"
                ),
                flagUrl = "https://restcountries.eu/data/are.svg",
                language = Language(
                    code = "ar",
                    name = "Arabic"
                ),
                name = "United Arab Emirates",
                region = "AS"
            ),
            CountryDto(
                capital = "London",
                code = "GB",
                currency = Currency(
                    code = "GBP",
                    name = "British pound",
                    symbol = "£"
                ),
                flagUrl = "https://restcountries.eu/data/gbr.svg",
                language = Language(
                    code = "en",
                    name = "English"
                ),
                name = "United Kingdom of Great Britain and Northern Ireland",
                region = "EU"
            )
        )
    }



 }