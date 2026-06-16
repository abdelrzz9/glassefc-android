package com.glassefc

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object GlassEffectConfig {
    var isUnlocked = false
    var freelancer = "Your Name"
    var project    = "Project Name"
    var amount     = "500 USD"
    var deadline   = "2025-01-01"

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    val parsedDeadline: LocalDate
        get() = try {
            LocalDate.parse(deadline, dateFormatter)
        } catch (_: Exception) {
            LocalDate.MAX
        }

    val isOverdue: Boolean
        get() = !isUnlocked && LocalDate.now() > parsedDeadline

    val daysRemaining: Long
        get() = LocalDate.now().until(parsedDeadline, ChronoUnit.DAYS)

    val formattedDeadline: String
        get() = try {
            val date = LocalDate.parse(deadline, dateFormatter)
            val months = arrayOf(
                "jan", "feb", "mar", "apr", "may", "jun",
                "jul", "aug", "sep", "oct", "nov", "dec"
            )
            "${date.dayOfMonth} ${months[date.monthValue - 1]} ${date.year}"
        } catch (_: Exception) {
            deadline
        }
}
