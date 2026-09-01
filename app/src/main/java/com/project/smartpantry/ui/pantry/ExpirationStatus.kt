package com.project.smartpantry.ui.pantry

import java.time.LocalDate

enum class ExpirationStatus {
    NONE,
    FRESH,
    EXPIRING_SOON,
    EXPIRED
}

fun expirationStatus(
    expirationDateEpochDay: Long?,
    today: LocalDate = LocalDate.now()
): ExpirationStatus {
    if (expirationDateEpochDay == null) {
        return ExpirationStatus.NONE
    }
    val expirationDate = LocalDate.ofEpochDay(expirationDateEpochDay)

    return when {
        expirationDate.isBefore(today) -> {
            ExpirationStatus.EXPIRED
        }

        !expirationDate.isAfter(today.plusDays(3)) -> {
            ExpirationStatus.EXPIRING_SOON
        }

        else -> {
            ExpirationStatus.FRESH
        }
    }
}