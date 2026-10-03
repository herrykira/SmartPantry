package com.project.smartpantry

import com.project.smartpantry.ui.pantry.ExpirationStatus
import com.project.smartpantry.ui.pantry.expirationStatus
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class ExpirationStatusTest {
    private val today = LocalDate.of(2026, 10, 2)

    @Test
    fun `no expiration date returns none`() {
        val result = expirationStatus(expirationDateEpochDay = null, today = today)
        assertEquals(ExpirationStatus.NONE, result)
    }

    @Test
    fun `past date is expired`() {
        val expiration = LocalDate.of(2026, 10, 1)
        val result = expirationStatus(expiration.toEpochDay(), today)
        assertEquals(ExpirationStatus.EXPIRED, result)
    }

    @Test
    fun `today is expiring soon`() {
        val result = expirationStatus(today.toEpochDay(), today)
        assertEquals(ExpirationStatus.EXPIRING_SOON, result)
    }

    @Test
    fun `three days away is expiring soon`() {
        val expiration = today.plusDays(3)
        val result = expirationStatus(expiration.toEpochDay(), today)
        assertEquals(ExpirationStatus.EXPIRING_SOON, result)
    }

    @Test
    fun `more than three days away is fresh`() {
        val expiration = today.plusDays(4)
        val result = expirationStatus(expiration.toEpochDay(), today)
        assertEquals(ExpirationStatus.FRESH, result)
    }
}