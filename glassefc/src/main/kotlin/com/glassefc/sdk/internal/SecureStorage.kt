package com.glassefc.sdk.internal

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

internal class SecureStorage(context: Context) {

    private val prefs: SharedPreferences = EncryptedSharedPreferences.create(
        FILE_NAME,
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context.applicationContext,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    var isActivated: Boolean
        get() = prefs.getBoolean(KEY_ACTIVATED, false)
        set(value) = prefs.edit().putBoolean(KEY_ACTIVATED, value).apply()

    var licenseHash: String?
        get() = prefs.getString(KEY_LICENSE_HASH, null)
        set(value) = prefs.edit().putString(KEY_LICENSE_HASH, value).apply()

    fun clear() {
        prefs.edit().clear().apply()
    }

    private companion object {
        private const val FILE_NAME = "glassefc_secure_prefs"
        private const val KEY_ACTIVATED = "activated"
        private const val KEY_LICENSE_HASH = "license_hash"
    }
}
