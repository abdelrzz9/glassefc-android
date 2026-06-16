package com.glassefc

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit
import java.util.Locale

object GlassEffectConfig {
    const val freelancer = "Your Name"
    const val project = "Project Name"
    const val amount = "500 USD"
    const val deadline = "2025-01-01"

    private const val UNLOCK_KEY = "glassefc-unlock-2024"
    private val dateFormatter = DateTimeFormatter
        .ofPattern("yyyy-MM-dd", Locale.ENGLISH)

    private val parsedDeadline: LocalDate by lazy {
        try {
            LocalDate.parse(deadline, dateFormatter)
        } catch (e: DateTimeParseException) {
            error(
                "GlassEffectConfig.deadline must be in yyyy-MM-dd format, " +
                    "but was: '$deadline'"
            )
        }
    }

    init {
        parsedDeadline
    }

    @Volatile
    private var _isUnlocked = false

    fun isUnlocked(): Boolean = _isUnlocked

    fun unlock(key: String): Boolean {
        if (key == UNLOCK_KEY) {
            _isUnlocked = true
            return true
        }
        return false
    }

    internal fun forceUnlock() {
        _isUnlocked = true
    }

    internal val isOverdue: Boolean
        get() = !_isUnlocked && LocalDate.now() > parsedDeadline

    internal val daysRemaining: Long
        get() = LocalDate.now().until(parsedDeadline, ChronoUnit.DAYS)

    internal val formattedDeadline: String
        get() = parsedDeadline.format(
            DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH)
        )
}
