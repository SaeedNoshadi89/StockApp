package com.sn.stockapp.data.csv

import com.opencsv.CSVReader
import com.sn.stockapp.domain.model.CompanyListingModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanyListingParser @Inject constructor() : CSVParser<CompanyListingModel> {
    override suspend fun parser(stream: InputStream): List<CompanyListingModel> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO){
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    line.let {
                        val symbol = it.getOrNull(0)
                        val name = it.getOrNull(1)
                        val exchange = it.getOrNull(2)
                        val assetType = it.getOrNull(3)
                        val status = it.getOrNull(6)
                        CompanyListingModel(
                            symbol = symbol ?: return@mapNotNull  null,
                            name = name ?: return@mapNotNull  null,
                            exchange = exchange ?: return@mapNotNull  null,
                            assetType = assetType ?: return@mapNotNull  null,
                            status = status ?: return@mapNotNull  null,
                        )
                    }
                }
                .also {
                    csvReader.close()
                }
        }
    }
}