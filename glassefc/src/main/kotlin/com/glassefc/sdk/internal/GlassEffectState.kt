package com.glassefc.sdk.internal

import android.content.Context
import java.time.LocalDate

internal object GlassEffectState {

    private var config: GlassefcConfig? = null
    private var storage: SecureStorage? = null
    private var licenseManager: LicenseManager? = null

    fun initialize(
        context: Context,
        licenseKey: String,
        freelancer: String,
        project: String,
        amount: String,
        deadline: LocalDate,
        enableAntiTamper: Boolean,
        licenseServerUrl: String?
    ) {
        val cfg = GlassefcConfig(
            licenseKey = licenseKey,
            freelancer = freelancer,
            project = project,
            amount = amount,
            deadline = deadline,
            enableAntiTamper = enableAntiTamper,
            licenseServerUrl = licenseServerUrl
        )
        config = cfg
        storage = SecureStorage(context)
        licenseManager = LicenseManager(storage!!)

        if (enableAntiTamper && AntiTamper.isTampered(context, true)) {
            licenseManager!!.deactivate()
        }

        if (!licenseManager!!.isActivated() && licenseManager!!.validateFormat(licenseKey)) {
            licenseManager!!.activate(licenseKey)
        }
    }

    fun getConfig(): GlassefcConfig? = config

    fun activate(licenseKey: String): Boolean {
        val lm = licenseManager ?: return false
        return lm.activate(licenseKey)
    }

    fun isActivated(): Boolean {
        val lm = licenseManager ?: return false
        return lm.isActivated()
    }

    fun isLicensed(): Boolean {
        val lm = licenseManager ?: return false
        val cfg = config ?: return false
        return lm.isLicensed(cfg.licenseKey)
    }
}
