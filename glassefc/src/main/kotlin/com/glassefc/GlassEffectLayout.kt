package com.glassefc

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

class GlassEffectLayout(context: Context) : FrameLayout(context) {

    private var attached = false

    fun install() {
        if (attached || GlassEffectConfig.isUnlocked()) return
        buildLockScreen()
        attached = true
    }

    private fun buildLockScreen() {
        val config = GlassEffectConfig
        val isOverdue = config.isOverdue

        setBackgroundColor(
            if (isOverdue) GlassEffectDefaults.bgOverdueArgb
            else GlassEffectDefaults.bgNormalArgb
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
        outerLayout.addView(deadlineSection(isOverdue, config))
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
                if (isOverdue) GlassEffectDefaults.accentOverdueArgb
                else GlassEffectDefaults.accentNormalArgb,
                android.graphics.PorterDuff.Mode.SRC_IN
            )
            val size = dpToPx(GlassEffectDefaults.iconSizeDp)
            layoutParams = LinearLayout.LayoutParams(size, size).apply {
                bottomMargin = dpToPx(GlassEffectDefaults.sectionSpacingDp)
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
            ).apply { bottomMargin = dpToPx(GlassEffectDefaults.sectionSpacingDp) }

            addView(TextView(context).apply {
                text = if (isOverdue) GlassEffectDefaults.titleOverdue
                else GlassEffectDefaults.titleLocked
                textSize = 20f
                setTextColor(GlassEffectDefaults.accentNormalArgb)
                typeface = Typeface.DEFAULT_BOLD
                gravity = Gravity.CENTER
            })

            addView(TextView(context).apply {
                text = project
                textSize = 14f
                setTextColor(dimArgb())
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
            ).apply { bottomMargin = dpToPx(GlassEffectDefaults.sectionSpacingDp) }

            val cardBg = GradientDrawable().apply {
                setColor(
                    android.graphics.Color.argb(
                        (255 * GlassEffectDefaults.cardBgAlpha).toInt(),
                        255, 255, 255
                    )
                )
                cornerRadius = dpToPx(20).toFloat()
            }
            setPadding(
                dpToPx(GlassEffectDefaults.cardHorizontalPaddingDp),
                dpToPx(GlassEffectDefaults.cardVerticalPaddingDp),
                dpToPx(GlassEffectDefaults.cardHorizontalPaddingDp),
                dpToPx(GlassEffectDefaults.cardVerticalPaddingDp)
            )
            background = cardBg

            addView(TextView(context).apply {
                text = GlassEffectDefaults.labelAmountDue
                textSize = 12f
                setTextColor(dimArgb())
                letterSpacing = 0.08f
                gravity = Gravity.CENTER
            })

            addView(TextView(context).apply {
                text = amount
                textSize = 42f
                setTextColor(
                    if (isOverdue) GlassEffectDefaults.accentOverdueArgb
                    else GlassEffectDefaults.accentNormalArgb
                )
                typeface = Typeface.DEFAULT_BOLD
                gravity = Gravity.CENTER
            })
        }
    }

    private fun deadlineSection(
        isOverdue: Boolean,
        config: GlassEffectConfig
    ): LinearLayout {
        return LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { bottomMargin = dpToPx(GlassEffectDefaults.sectionSpacingDp) }

            addView(TextView(context).apply {
                text = if (isOverdue) GlassEffectDefaults.labelDeadlineWas
                else GlassEffectDefaults.labelDeadline
                textSize = 12f
                setTextColor(dimArgb())
                letterSpacing = 0.08f
                gravity = Gravity.CENTER
            })

            addView(TextView(context).apply {
                text = config.formattedDeadline
                textSize = 16f
                setTextColor(
                    if (isOverdue) GlassEffectDefaults.accentOverdueArgb
                    else GlassEffectDefaults.accentNormalArgb
                )
                gravity = Gravity.CENTER
            })

            addView(TextView(context).apply {
                val days = config.daysRemaining
                text = if (isOverdue) GlassEffectDefaults.overdueSuffix
                else "${days} day${if (days == 1L) "" else "s"} remaining"
                textSize = 14f
                setTextColor(
                    if (isOverdue)
                        android.graphics.Color.argb(
                            (255 * 0.8f).toInt(),
                            0xCC, 0x00, 0x00
                        )
                    else dimArgb()
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
            ).apply { bottomMargin = dpToPx(GlassEffectDefaults.sectionSpacingDp) }

            addView(TextView(context).apply {
                text = GlassEffectDefaults.labelDeveloper
                textSize = 12f
                setTextColor(dimArgb())
                letterSpacing = 0.08f
                gravity = Gravity.CENTER
            })

            addView(TextView(context).apply {
                text = freelancer
                textSize = 16f
                setTextColor(GlassEffectDefaults.accentNormalArgb)
                gravity = Gravity.CENTER
            })
        }
    }

    private fun footerView(isOverdue: Boolean): TextView {
        return TextView(context).apply {
            text = if (isOverdue) GlassEffectDefaults.footerOverdue
            else GlassEffectDefaults.footerLocked
            textSize = 12f
            setTextColor(veryDimArgb())
            gravity = Gravity.CENTER
            textAlignment = View.TEXT_ALIGNMENT_CENTER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = dpToPx(GlassEffectDefaults.sectionSpacingDp)
                leftMargin = dpToPx(GlassEffectDefaults.horizontalMarginDp)
                rightMargin = dpToPx(GlassEffectDefaults.horizontalMarginDp)
            }
        }
    }

    private fun dimArgb(): Int =
        android.graphics.Color.argb(
            (255 * GlassEffectDefaults.dimAlpha).toInt(), 255, 255, 255
        )

    private fun veryDimArgb(): Int =
        android.graphics.Color.argb(
            (255 * GlassEffectDefaults.veryDimAlpha).toInt(), 255, 255, 255
        )

    companion object {
        fun wrap(activity: Activity) {
            if (!GlassEffectConfig.isUnlocked()) {
                val overlay = GlassEffectLayout(activity)
                overlay.install()
                activity.addContentView(
                    overlay,
                    LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT
                    )
                )
            }
        }

        fun installOn(activity: Activity) = wrap(activity)

        fun dpToPx(dp: Int): Int {
            return (dp * android.content.res.Resources.getSystem().displayMetrics.density).toInt()
        }
    }
}
