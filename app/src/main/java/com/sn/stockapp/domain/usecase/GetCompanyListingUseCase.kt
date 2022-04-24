package com.sn.stockapp.domain.usecase

import com.sn.stockapp.domain.model.CompanyListingModel
import com.sn.stockapp.domain.repository.StockRepository
import com.sn.stockapp.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCompanyListingUseCase @Inject constructor(
    private val stockRepository: StockRepository,
) {
    operator fun invoke(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Result<List<CompanyListingModel>>> {
        return flow {
            stockRepository.companyListing(fetchFromRemote = fetchFromRemote, query = query).collect{
                when(it){
                    is Result.Loading -> emit(Result.Loading(true))
                    is Result.Success -> emit(Result.Success(it.data))
                    is Result.Error -> emit(Result.Error(it.exception))
                }
            }
        }
    }
}