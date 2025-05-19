package com.walmart.venkata.presentation.view

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.walmart.venkata.R
import com.walmart.venkata.data.common.ApiResponseState
import com.walmart.venkata.data.remote.ApiClient
import com.walmart.venkata.data.remote.ApiService
import com.walmart.venkata.data.repository.CountryRepository
import com.walmart.venkata.databinding.ActivityMainBinding
import com.walmart.venkata.domain.use_case.CountryUseCase
import com.walmart.venkata.presentation.view.adapter.CountryAdapter
import com.walmart.venkata.presentation.viewmodel.CountryViewModel
import com.walmart.venkata.presentation.viewmodel.CountryViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var countryAdapter: CountryAdapter
    private lateinit var viewModel: CountryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Initialize Country view model object
        setupViewModel()

        //Set up the Recycler view
        setupRecyclerView()

        //Ui state observers for displaying corresponding UI
        setupObservers()
    }

    /**
     * Observes ViewModel UI state and updates the UI accordingly.
     * Uses repeatOnLifecycle to safely collect flows within lifecycle-aware scope.
     */
    private fun setupObservers(){
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { state ->
                    when (state) {
                        //Error on network api response failure or exception
                        is ApiResponseState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.txtErrorMessage.visibility = View.VISIBLE
                            binding.txtErrorMessage.text = state.message
                        }

                        //Show Progresses bar while making network api call
                        ApiResponseState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        //Success Response to display list of views
                        is ApiResponseState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            countryAdapter.refreshData(state.data)
                        }
                    }
                }
            }
        }
    }

    /**
     * Initializes RecyclerView and sets its adapter and layout manager.
     */
    private fun setupRecyclerView(){
        binding.recyclerViewCountries.layoutManager = LinearLayoutManager(this)
        countryAdapter = CountryAdapter(emptyList())
        binding.recyclerViewCountries.adapter = countryAdapter
    }

    /**
     * Sets up the [CountryViewModel] object with dependencies:
     * [ApiService] -> [CountryRepository] -> [CountryUseCase] -> [CountryViewModel].
     */
    private fun setupViewModel() {
        val apiService = ApiClient.retrofit.create(ApiService::class.java)
        val repository = CountryRepository(apiService)
        val countryUseCase = CountryUseCase(repository)
        val factory = CountryViewModelFactory(countryUseCase)
        viewModel = ViewModelProvider(this, factory)[CountryViewModel::class.java]
    }
}