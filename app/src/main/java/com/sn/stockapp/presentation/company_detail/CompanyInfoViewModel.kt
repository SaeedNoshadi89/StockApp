package com.sn.stockapp.presentation.company_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sn.stockapp.domain.repository.StockRepository
import com.sn.stockapp.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: StockRepository
) : ViewModel() {

    var state by mutableStateOf(CompanyInfoState())

    init {
        viewModelScope.launch {
            val symbol = savedStateHandle.get<String>("symbol") ?: return@launch
            state = state.copy(isLoading = true)
            /*  val companyInfoResult = async { repository.getCompanyInfo(symbol) }
              val intradayInfoResult = async { repository.getIntradayInfo(symbol) }*/

            getCompanyInfo(symbol)
            getIntradayInfo(symbol)
        }
    }

    private fun getIntradayInfo(symbol: String) {
        viewModelScope.launch {
            repository.getIntradayInfo(symbol).collect {
                when (val result = it) {
                    is Result.Success -> {
                        state = state.copy(
                            stockInfo = result.data ?: emptyList(),
                            isLoading = false,
                            error = null
                        )
                    }
                    is Result.Error -> {
                        state = state.copy(
                            stockInfo = null,
                            isLoading = false,
                            error = result.exception.message
                        )
                    }
                    else -> Unit
                }
            }
        }

    }

    private fun getCompanyInfo(symbol: String) {
        viewModelScope.launch {
            repository.getCompanyInfo(symbol).collect {
                when (val result = it) {
                    is Result.Success -> {
                        state = state.copy(company = result.data, isLoading = false, error = null)
                    }
                    is Result.Error -> {
                        state =
                            state.copy(
                                company = null,
                                isLoading = false,
                                error = result.exception.message
                            )
                    }
                    else -> Unit
                }
            }
        }
    }
}