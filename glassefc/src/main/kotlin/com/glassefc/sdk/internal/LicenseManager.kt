package com.glassefc.sdk.internal

import java.security.MessageDigest

internal class LicenseManager(private val storage: SecureStorage) {

    fun validateFormat(key: String): Boolean {
        return key.length >= 16 && key.matches(Regex("^[A-Za-z0-9\\-]+$"))
    }

    fun activate(key: String): Boolean {
        val hash = hashLicense(key)
        storage.licenseHash = hash
        storage.isActivated = true
        return true
    }

    fun deactivate() {
        storage.isActivated = false
        storage.licenseHash = null
    }

    fun isActivated(): Boolean {
        return storage.isActivated && storage.licenseHash != null
    }

    fun isLicensed(configLicenseKey: String): Boolean {
        if (!isActivated()) return false
        return storage.licenseHash == hashLicense(configLicenseKey)
    }

    internal fun hashLicense(key: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        return digest.digest(key.toByteArray(Charsets.UTF_8))
            .joinToString("") { "%02x".format(it) }
    }
}
