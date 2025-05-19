package com.walmart.venkata.presentation.viewmodel

import app.cash.turbine.test
import com.walmart.venkata.data.common.ApiResponseState
import com.walmart.venkata.domain.model.Country
import com.walmart.venkata.domain.use_case.CountryUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CountryViewModelTest{

  private lateinit var countryViewModel: CountryViewModel
  private val countryUseCase = mockk<CountryUseCase>()

  @Before
  fun setup(){
      Dispatchers.setMain(StandardTestDispatcher())
  }

 @After
 fun tearDown() {
     Dispatchers.resetMain()
 }

 @Test
 fun `test fetchCountriesInfo to get list of countries from use case`() = runTest {
     val expectedCountries = getCountriesFake()

     coEvery { countryUseCase() } returns flowOf(
         ApiResponseState.Loading,
         ApiResponseState.Success(expectedCountries)
     )

     countryViewModel = CountryViewModel(countryUseCase)

     countryViewModel.uiState.test {
         val loadingState = awaitItem()
         assertTrue(loadingState is ApiResponseState.Loading)

         val successState = awaitItem()
         assertTrue(successState is ApiResponseState.Success)
         assertEquals(expectedCountries, (successState as ApiResponseState.Success).data)

         cancelAndIgnoreRemainingEvents()
     }
 }

 @Test
 fun `test fetchCountriesInfo emits Error state when use case returns error`() = runTest {
     coEvery { countryUseCase() } returns flowOf(
      ApiResponseState.Loading,
      ApiResponseState.Error("Network failure")
      )

      countryViewModel = CountryViewModel(countryUseCase)

      countryViewModel.uiState.test {
          assertEquals(ApiResponseState.Loading, awaitItem())

          val errorState = awaitItem()
          assertTrue(errorState is ApiResponseState.Error)
          assertEquals("Network failure", (errorState as ApiResponseState.Error).message)

          cancelAndIgnoreRemainingEvents()
      }
 }


 @Test
 fun `test fetchCountriesInfo emits Error state when exception is thrown`() = runTest {
      coEvery { countryUseCase() } throws RuntimeException("Something went wrong")

      countryViewModel = CountryViewModel(countryUseCase)

      countryViewModel.uiState.test {
          val errorState = awaitItem()
          assertTrue(errorState is ApiResponseState.Error)
          assertEquals("Error: Something went wrong", (errorState as ApiResponseState.Error).message)

          cancelAndIgnoreRemainingEvents()
      }
 }


 fun getCountriesFake(): List<Country> {
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