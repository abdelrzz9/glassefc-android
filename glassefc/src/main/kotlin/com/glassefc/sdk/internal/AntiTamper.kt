package com.glassefc.sdk.internal

import android.content.Context
import android.content.pm.ApplicationInfo

internal object AntiTamper {

    fun isDebugBuild(context: Context): Boolean {
        return (context.applicationContext.applicationInfo.flags and
                ApplicationInfo.FLAG_DEBUGGABLE) != 0
    }

    fun isTampered(context: Context, enableCheck: Boolean): Boolean {
        if (!enableCheck) return false
        return isDebugBuild(context)
    }
}
