package com.glassefc.sdk.internal.ui

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.glassefc.sdk.internal.GlassefcConfig
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

internal class GlassEffectLayout(context: Context) : FrameLayout(context) {

    private var attached = false

    fun install(config: GlassefcConfig) {
        if (attached) return
        val isOverdue = LocalDate.now() > config.deadline
        val daysRemaining = LocalDate.now().until(config.deadline, ChronoUnit.DAYS)
        val formattedDeadline = config.deadline.format(
            DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH)
        )

        setBackgroundColor(
            if (isOverdue) OVERDUE_BG else NORMAL_BG
        )

        val outerLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
        }

        outerLayout.addView(topSpacer())
        outerLayout.addView(iconView(isOverdue))
        outerLayout.addView(titleSection(isOverdue, config.project))
        outerLayout.addView(amountCard(isOverdue, config.amount))
        outerLayout.addView(deadlineSection(isOverdue, formattedDeadline, daysRemaining))
        outerLayout.addView(freelancerSection(config.freelancer))
        outerLayout.addView(bottomSpacer())
        outerLayout.addView(footerView(isOverdue))

        val scrollView = ScrollView(context)
        scrollView.layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )
        scrollView.addView(outerLayout)
        addView(scrollView)
        attached = true
    }

    private fun topSpacer(): View = View(context).apply {
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            0,
            1f
        )
    }

    private fun bottomSpacer(): View = View(context).apply {
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            0,
            1f
        )
    }

    private fun iconView(isOverdue: Boolean): ImageView {
        return ImageView(context).apply {
            val iconRes = if (isOverdue)
                android.R.drawable.ic_dialog_alert
            else
                android.R.drawable.ic_lock_idle_lock

            setImageDrawable(ResourcesCompat.getDrawable(resources, iconRes, null))
            setColorFilter(
                if (isOverdue) OVERDUE_ACCENT else NORMAL_ACCENT,
                android.graphics.PorterDuff.Mode.SRC_IN
            )
            val size = dpToPx(ICON_SIZE_DP)
            layoutParams = LinearLayout.LayoutParams(size, size).apply {
                bottomMargin = dpToPx(SECTION_SPACING_DP)
            }
            scaleType = ImageView.ScaleType.FIT_CENTER
        }
    }

    private fun titleSection(isOverdue: Boolean, project: String): LinearLayout {
        return LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { bottomMargin = dpToPx(SECTION_SPACING_DP) }

            addView(TextView(context).apply {
                text = if (isOverdue) TITLE_OVERDUE else TITLE_LOCKED
                textSize = 20f
                setTextColor(NORMAL_ACCENT)
                typeface = Typeface.DEFAULT_BOLD
                gravity = Gravity.CENTER
            })

            addView(TextView(context).apply {
                text = project
                textSize = 14f
                setTextColor(DIM_ACCENT)
                gravity = Gravity.CENTER
            })
        }
    }

    private fun amountCard(isOverdue: Boolean, amount: String): LinearLayout {
        return LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { bottomMargin = dpToPx(SECTION_SPACING_DP) }

            val cardBg = GradientDrawable().apply {
                setColor(android.graphics.Color.argb(CARD_BG_ALPHA, 255, 255, 255))
                cornerRadius = dpToPx(20).toFloat()
            }
            setPadding(
                dpToPx(CARD_HORIZONTAL_PADDING_DP),
                dpToPx(CARD_VERTICAL_PADDING_DP),
                dpToPx(CARD_HORIZONTAL_PADDING_DP),
                dpToPx(CARD_VERTICAL_PADDING_DP)
            )
            background = cardBg

            addView(TextView(context).apply {
                text = LABEL_AMOUNT_DUE
                textSize = 12f
                setTextColor(DIM_ACCENT)
                letterSpacing = 0.08f
                gravity = Gravity.CENTER
            })

            addView(TextView(context).apply {
                text = amount
                textSize = 42f
                setTextColor(
                    if (isOverdue) OVERDUE_ACCENT else NORMAL_ACCENT
                )
                typeface = Typeface.DEFAULT_BOLD
                gravity = Gravity.CENTER
            })
        }
    }

    private fun deadlineSection(
        isOverdue: Boolean,
        formattedDeadline: String,
        daysRemaining: Long
    ): LinearLayout {
        return LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { bottomMargin = dpToPx(SECTION_SPACING_DP) }

            addView(TextView(context).apply {
                text = if (isOverdue) LABEL_DEADLINE_WAS else LABEL_DEADLINE
                textSize = 12f
                setTextColor(DIM_ACCENT)
                letterSpacing = 0.08f
                gravity = Gravity.CENTER
            })

            addView(TextView(context).apply {
                text = formattedDeadline
                textSize = 16f
                setTextColor(
                    if (isOverdue) OVERDUE_ACCENT else NORMAL_ACCENT
                )
                gravity = Gravity.CENTER
            })

            addView(TextView(context).apply {
                text = if (isOverdue) OVERDUE_SUFFIX
                else "${daysRemaining} day${if (daysRemaining == 1L) "" else "s"} remaining"
                textSize = 14f
                setTextColor(
                    if (isOverdue)
                        android.graphics.Color.argb(
                            (255 * 0.8f).toInt(),
                            0xCC, 0x00, 0x00
                        )
                    else DIM_ACCENT
                )
                gravity = Gravity.CENTER
            })
        }
    }

    private fun freelancerSection(freelancer: String): LinearLayout {
        return LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { bottomMargin = dpToPx(SECTION_SPACING_DP) }

            addView(TextView(context).apply {
                text = LABEL_DEVELOPER
                textSize = 12f
                setTextColor(DIM_ACCENT)
                letterSpacing = 0.08f
                gravity = Gravity.CENTER
            })

            addView(TextView(context).apply {
                text = freelancer
                textSize = 16f
                setTextColor(NORMAL_ACCENT)
                gravity = Gravity.CENTER
            })
        }
    }

    private fun footerView(isOverdue: Boolean): TextView {
        return TextView(context).apply {
            text = if (isOverdue) FOOTER_OVERDUE else FOOTER_LOCKED
            textSize = 12f
            setTextColor(VERY_DIM_ACCENT)
            gravity = Gravity.CENTER
            textAlignment = View.TEXT_ALIGNMENT_CENTER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = dpToPx(SECTION_SPACING_DP)
                leftMargin = dpToPx(HORIZONTAL_MARGIN_DP)
                rightMargin = dpToPx(HORIZONTAL_MARGIN_DP)
            }
        }
    }

    companion object {
        private val NORMAL_BG = android.graphics.Color.parseColor("#FF1A1A1A")
        private val OVERDUE_BG = android.graphics.Color.parseColor("#FF2A0A0A")
        private val NORMAL_ACCENT = -0x1
        private val OVERDUE_ACCENT = android.graphics.Color.parseColor("#FFCC0000")

        private val DIM_ACCENT = android.graphics.Color.argb(
            (255 * 0.5f).toInt(), 255, 255, 255
        )
        private val VERY_DIM_ACCENT = android.graphics.Color.argb(
            (255 * 0.4f).toInt(), 255, 255, 255
        )

        private const val CARD_BG_ALPHA = 20

        private const val LABEL_AMOUNT_DUE = "AMOUNT DUE"
        private const val LABEL_DEADLINE = "DEADLINE"
        private const val LABEL_DEADLINE_WAS = "DEADLINE WAS"
        private const val LABEL_DEVELOPER = "DEVELOPER"
        private const val TITLE_LOCKED = "Payment Required"
        private const val TITLE_OVERDUE = "Payment Overdue"
        private const val FOOTER_LOCKED =
            "This app is locked pending payment.\nContact the developer to unlock."
        private const val FOOTER_OVERDUE =
            "This app is locked. Payment deadline has passed.\nContact the developer immediately."
        private const val OVERDUE_SUFFIX = "Payment is overdue"

        private const val ICON_SIZE_DP = 60
        private const val SECTION_SPACING_DP = 32
        private const val CARD_HORIZONTAL_PADDING_DP = 40
        private const val CARD_VERTICAL_PADDING_DP = 24
        private const val HORIZONTAL_MARGIN_DP = 32

        fun wrap(activity: Activity, config: GlassefcConfig) {
            val overlay = GlassEffectLayout(activity)
            overlay.install(config)
            activity.addContentView(
                overlay,
                LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT
                )
            )
        }

        fun dpToPx(dp: Int): Int {
            return (dp * android.content.res.Resources.getSystem().displayMetrics.density).toInt()
        }
    }
}
