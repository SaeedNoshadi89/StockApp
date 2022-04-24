package com.sn.stockapp.data.repository

import com.sn.stockapp.data.csv.CSVParser
import com.sn.stockapp.data.local.StockDatabase
import com.sn.stockapp.data.mapper.toCompanyListingEntity
import com.sn.stockapp.data.mapper.toCompanyListingModel
import com.sn.stockapp.data.remote.dto.StockApi
import com.sn.stockapp.domain.model.CompanyListingModel
import com.sn.stockapp.domain.repository.StockRepository
import com.sn.stockapp.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val stockApi: StockApi,
    private val stockDatabase: StockDatabase,
    private val companyListingParser: CSVParser<CompanyListingModel>
) : StockRepository {
    private val dao = stockDatabase.stockDao
    override suspend fun companyListing(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Result<List<CompanyListingModel>>> {
        return flow {
            emit(Result.Loading(true))
            val localCompanyListing = dao.searchCompanyListing(query)
            emit(Result.Success(localCompanyListing.map { it.toCompanyListingModel() }))

            val isDbEmpty = localCompanyListing.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Result.Loading(false))
                return@flow
            }

            val remoteCompanyListing = try {
                val response = stockApi.getCompanyListing()
                companyListingParser.parser(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(e))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(e))
                null
            }

            remoteCompanyListing?.let { companyListing ->
                dao.clearCompanyListing()
                dao.insertCompanyListings(companyListing.map { it.toCompanyListingEntity() })
                emit(
                    Result.Success(
                        data = dao.searchCompanyListing("").map { it.toCompanyListingModel() })
                )
                emit(Result.Loading(false))
            }
        }
    }
}