package com.sn.stockapp.data.mapper

import com.sn.stockapp.data.remote.dto.IntradayInfoDto
import com.sn.stockapp.domain.model.IntradayInfoModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun IntradayInfoDto.toIntradayInfoModel(): IntradayInfoModel {
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(timestamp, formatter)
    return IntradayInfoModel(
        date = localDateTime,
        close = close
    )
}