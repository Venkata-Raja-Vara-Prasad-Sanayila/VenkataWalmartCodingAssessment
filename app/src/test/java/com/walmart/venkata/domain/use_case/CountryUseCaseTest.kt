package com.walmart.venkata.domain.use_case

import com.walmart.venkata.data.common.ApiResponseState
import com.walmart.venkata.domain.model.Country
import com.walmart.venkata.domain.repository.ICountryRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


class CountryUseCaseTest{

  private lateinit var countryUseCase: CountryUseCase
  private val repository = mockk<ICountryRepository>()

  @Before
  fun setup(){
      countryUseCase = CountryUseCase(repository)
  }


 @Test
 fun `getCountries from repository returns success state`() = runTest {

      val expected = getCountriesFake()

      coEvery { repository.getCountriesInfo() } returns flow{
       emit(ApiResponseState.Loading)
       emit(ApiResponseState.Success(expected))
      }

      val result = countryUseCase().toList()

      assertTrue(result[0] is ApiResponseState.Loading)
      assertTrue(result[1] is ApiResponseState.Success)
      assertEquals(expected, (result[1] as ApiResponseState.Success).data)

 }

 @Test
 fun `getCountries from repository error state`() = runTest {

      coEvery { repository.getCountriesInfo() } returns flow {
        emit(ApiResponseState.Error("Network Error: Forbidden resource"))
      }

      countryUseCase().collect{state->
          assertTrue(state is ApiResponseState.Error)
          assertEquals("Network Error: Forbidden resource", (state as ApiResponseState.Error).message)
      }

 }

 private fun getCountriesFake(): List<Country> {
      return listOf(
          Country(
              name = "United Arab Emirates",
              capital = "Abu Dhabi",
              code = "AE",
              region = "AS"
          ),
          Country(
              name = "United Kingdom of Great Britain and Northern Ireland",
              capital = "London",
              code = "GB",
              region = "EU"
          )
      )
 }

}