@file:JvmName("Glassefc")
package com.glassefc.sdk

import android.content.Context
import androidx.compose.runtime.Composable
import com.glassefc.sdk.internal.GlassEffectState
import com.glassefc.sdk.internal.ui.GlassEffectOverlay
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

public object Glassefc {

    private var initialized = false

    @JvmStatic
    public fun init(
        context: Context,
        licenseKey: String,
        freelancer: String,
        project: String,
        amount: String,
        deadline: String,
        enableAntiTamper: Boolean = true,
        licenseServerUrl: String? = null
    ) {
        val parsedDeadline = try {
            LocalDate.parse(deadline, DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH))
        } catch (e: Exception) {
            throw IllegalArgumentException(
                "deadline must be in yyyy-MM-dd format, but was: '$deadline'"
            )
        }

        GlassEffectState.initialize(
            context = context,
            licenseKey = licenseKey,
            freelancer = freelancer,
            project = project,
            amount = amount,
            deadline = parsedDeadline,
            enableAntiTamper = enableAntiTamper,
            licenseServerUrl = licenseServerUrl
        )
        initialized = true
    }

    @JvmStatic
    public fun activate(licenseKey: String): Boolean {
        check(initialized) { "Glassefc not initialized. Call Glassefc.init() first." }
        return GlassEffectState.activate(licenseKey)
    }

    @JvmStatic
    public fun isActivated(): Boolean {
        return initialized && GlassEffectState.isActivated()
    }

    @JvmStatic
    public fun isLicensed(): Boolean {
        return initialized && GlassEffectState.isLicensed()
    }

    @Composable
    public operator fun invoke(content: @Composable () -> Unit) {
        if (initialized && GlassEffectState.isActivated()) {
            content()
        } else {
            val config = GlassEffectState.getConfig()
            if (config != null) {
                val now = LocalDate.now()
                GlassEffectOverlay(
                    isOverdue = now > config.deadline,
                    daysRemaining = now.until(config.deadline, ChronoUnit.DAYS),
                    formattedDeadline = config.deadline.format(
                        DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH)
                    ),
                    project = config.project,
                    amount = config.amount,
                    freelancer = config.freelancer
                )
            } else {
                GlassEffectOverlay(
                    isOverdue = false,
                    daysRemaining = 0,
                    formattedDeadline = "\u2014",
                    project = "Application Locked",
                    amount = "\u2014",
                    freelancer = "Developer"
                )
            }
        }
    }
}
