package com.glassefc

import android.graphics.Color as ArgbColor
import androidx.compose.ui.graphics.Color

internal object GlassEffectDefaults {
    // Overlay backgrounds
    val bgNormal = Color(0xFF1A1A1A)
    val bgOverdue = Color(0xFF2A0A0A)
    val bgNormalArgb = ArgbColor.parseColor("#FF1A1A1A")
    val bgOverdueArgb = ArgbColor.parseColor("#FF2A0A0A")

    // Accent colors
    val accentNormal = Color.White
    val accentOverdue = Color(0xFFCC0000)
    val accentNormalArgb = ArgbColor.parseColor("#FFFFFFFF")
    val accentOverdueArgb = ArgbColor.parseColor("#FFCC0000")

    // Text alpha multipliers
    const val dimAlpha = 0.5f
    const val veryDimAlpha = 0.4f
    const val cardBgAlpha = 0.08f

    // Labels
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

    // Spacing (in dp)
    const val iconSizeDp = 60
    const val sectionSpacingDp = 32
    const val innerSectionSpacingDp = 8
    const val cardHorizontalPaddingDp = 40
    const val cardVerticalPaddingDp = 24
    const val horizontalMarginDp = 32
}
