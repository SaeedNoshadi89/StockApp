package com.sn.stockapp.data.csv

import com.opencsv.CSVReader
import com.sn.stockapp.data.mapper.toIntradayInfoModel
import com.sn.stockapp.data.remote.dto.IntradayInfoDto
import com.sn.stockapp.domain.model.IntradayInfoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntradayParser @Inject constructor() : CSVParser<IntradayInfoModel> {
    override suspend fun parser(stream: InputStream): List<IntradayInfoModel> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO){
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    line.let {
                        val timestamp = it.getOrNull(0) ?: return@mapNotNull null
                        val close = it.getOrNull(1) ?: return@mapNotNull null
                        val dto = IntradayInfoDto(timestamp, close.toDouble())
                        dto.toIntradayInfoModel()
                    }
                }
                .filter {
                    it.date.dayOfMonth == LocalDateTime.now().minusDays(4).dayOfMonth
                }
                .sortedBy {
                    it.date.hour
                }
                .also {
                    csvReader.close()
                }
        }
    }
}