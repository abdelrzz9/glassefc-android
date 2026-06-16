package com.glassefc.sdk.internal

import java.time.LocalDate

internal data class GlassefcConfig(
    val licenseKey: String,
    val freelancer: String,
    val project: String,
    val amount: String,
    val deadline: LocalDate,
    val enableAntiTamper: Boolean = true,
    val licenseServerUrl: String? = null
) {
    init {
        require(licenseKey.isNotBlank()) { "licenseKey must not be blank" }
        require(freelancer.isNotBlank()) { "freelancer must not be blank" }
        require(project.isNotBlank()) { "project must not be blank" }
        require(amount.isNotBlank()) { "amount must not be blank" }
    }
}
