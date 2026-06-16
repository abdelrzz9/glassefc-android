package com.glassefc.sdk.internal.ui

import androidx.compose.ui.graphics.Color

internal object GlassEffectDefaults {
    val bgNormal = Color(0xFF1A1A1A)
    val bgOverdue = Color(0xFF2A0A0A)
    val accentNormal = Color.White
    val accentOverdue = Color(0xFFCC0000)

    const val dimAlpha = 0.5f
    const val veryDimAlpha = 0.4f
    const val cardBgAlpha = 0.08f

    const val labelAmountDue = "AMOUNT DUE"
    const val labelDeadline = "DEADLINE"
    const val labelDeadlineWas = "DEADLINE WAS"
    const val labelDeveloper = "DEVELOPER"
    const val titleLocked = "Payment Required"
    const val titleOverdue = "Payment Overdue"
    const val footerLocked =
        "This app is locked pending payment.\nContact the developer to unlock."
    const val footerOverdue =
        "This app is locked. Payment deadline has passed.\nContact the developer immediately."
    const val overdueSuffix = "Payment is overdue"

    const val iconSizeDp = 60
    const val sectionSpacingDp = 32
    const val innerSectionSpacingDp = 8
    const val cardHorizontalPaddingDp = 40
    const val cardVerticalPaddingDp = 24
    const val horizontalMarginDp = 32
}
