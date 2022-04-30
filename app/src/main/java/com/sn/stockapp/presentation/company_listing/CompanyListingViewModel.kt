package com.sn.stockapp.presentation.company_listing

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sn.stockapp.domain.usecase.GetCompanyListingUseCase
import com.sn.stockapp.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListingViewModel @Inject constructor(
    private val getCompanyListingUseCase: GetCompanyListingUseCase
) : ViewModel() {

    var state by mutableStateOf(CompanyListingState())
    private var searchJob: Job? = null

    init {
        getCompanyListing()
    }

    fun CompanyListingEvent.onEvent() {
        when (this) {
            is CompanyListingEvent.Refresh -> {
                getCompanyListing(fetchFromRemote = true)
            }
            is CompanyListingEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getCompanyListing()
                }
            }
        }
    }

    private fun getCompanyListing(
        fetchFromRemote: Boolean = false,
        query: String = state.searchQuery.lowercase()
    ) {
        viewModelScope.launch {
            getCompanyListingUseCase.invoke(fetchFromRemote = fetchFromRemote, query = query)
                .collect { result ->
                    state = when (result) {
                        is Result.Loading -> {
                            state.copy(isLoading = true)
                        }
                        is Result.Success -> {
                            result.data.let {
                                state.copy(isLoading = false, companies = it)
                            }
                        }
                        is Result.Error -> {
                            state.copy(
                                isLoading = false,
                                error = result.exception.message.toString()
                            )
                        }
                    }
                }
        }
    }
}