package com.project.smartpantry.util

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

// Because Material's DatePicker uses date milliseconds representing a calendar date in UTC
fun epochDayToUtcMills(epochDay: Long): Long {
    return LocalDate.ofEpochDay(epochDay).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
}

fun utcMillsToEpochDay(millis: Long): Long {
    return Instant.ofEpochMilli(millis).atZone(ZoneOffset.UTC).toLocalDate().toEpochDay()
}