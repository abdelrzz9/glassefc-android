package com.glassefc

import android.app.Activity
import android.content.Context
import android.graphics.Color
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
import androidx.core.graphics.ColorUtils

class GlassEffectLayout(context: Context) : FrameLayout(context) {

    init {
        if (!GlassEffectConfig.isUnlocked) {
            buildLockScreen()
        }
    }

    private fun buildLockScreen() {
        val config = GlassEffectConfig
        val isOverdue = config.isOverdue

        val bgColor = if (isOverdue) Color.parseColor("#1A000000") else Color.BLACK
        setBackgroundColor(bgColor)

        val scrollView = ScrollView(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
        }

        val outerLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        }

        // Spacer top
        outerLayout.addView(createSpacer())

        // Icon
        outerLayout.addView(createIcon(isOverdue))

        // Title + project
        outerLayout.addView(createTitleSection(isOverdue, config.project))

        // Amount card
        outerLayout.addView(createAmountCard(isOverdue, config.amount))

        // Deadline section
        outerLayout.addView(createDeadlineSection(isOverdue, config))

        // Freelancer section
        outerLayout.addView(createFreelancerSection(config.freelancer))

        // Spacer bottom
        outerLayout.addView(createSpacer())

        // Footer
        outerLayout.addView(createFooter(isOverdue))

        scrollView.addView(outerLayout)
        addView(scrollView)
    }

    private fun createSpacer(): View {
        return View(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        }
    }

    private fun createIcon(isOverdue: Boolean): ImageView {
        return ImageView(context).apply {
            val iconRes = if (isOverdue)
                android.R.drawable.ic_dialog_alert
            else
                android.R.drawable.ic_lock_lock

            setImageDrawable(ResourcesCompat.getDrawable(resources, iconRes, null))
            setColorFilter(
                if (isOverdue) Color.RED else Color.WHITE,
                android.graphics.PorterDuff.Mode.SRC_IN
            )
            val size = dpToPx(60)
            layoutParams = LinearLayout.LayoutParams(size, size).apply {
                bottomMargin = dpToPx(32)
            }
            scaleType = ImageView.ScaleType.FIT_CENTER
        }
    }

    private fun createTitleSection(isOverdue: Boolean, project: String): LinearLayout {
        return LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { bottomMargin = dpToPx(32) }

            addView(TextView(context).apply {
                text = if (isOverdue) "Payment Overdue" else "Payment Required"
                textSize = 20f
                setTextColor(Color.WHITE)
                typeface = Typeface.DEFAULT_BOLD
                gravity = Gravity.CENTER
            })

            addView(TextView(context).apply {
                text = project
                textSize = 14f
                setTextColor(ColorUtils.setAlphaComponent(Color.WHITE, 128))
                gravity = Gravity.CENTER
            })
        }
    }

    private fun createAmountCard(isOverdue: Boolean, amount: String): LinearLayout {
        val card = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = dpToPx(32)
            }

            val bg = GradientDrawable().apply {
                setColor(ColorUtils.setAlphaComponent(Color.WHITE, 20))
                cornerRadius = dpToPx(20).toFloat()
            }
            setPadding(dpToPx(40), dpToPx(24), dpToPx(40), dpToPx(24))
            background = bg
        }

        card.addView(TextView(context).apply {
            text = "AMOUNT DUE"
            textSize = 12f
            setTextColor(ColorUtils.setAlphaComponent(Color.WHITE, 128))
            letterSpacing = 0.08f
            gravity = Gravity.CENTER
        })

        card.addView(TextView(context).apply {
            text = amount
            textSize = 42f
            setTextColor(if (isOverdue) Color.RED else Color.WHITE)
                typeface = Typeface.DEFAULT_BOLD
                gravity = Gravity.CENTER
            })

            return card
    }

    private fun createDeadlineSection(
        isOverdue: Boolean,
        config: GlassEffectConfig
    ): LinearLayout {
        val section = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { bottomMargin = dpToPx(32) }
        }

        section.addView(TextView(context).apply {
            text = if (isOverdue) "DEADLINE WAS" else "DEADLINE"
            textSize = 12f
            setTextColor(ColorUtils.setAlphaComponent(Color.WHITE, 128))
            letterSpacing = 0.08f
            gravity = Gravity.CENTER
        })

        section.addView(TextView(context).apply {
            text = config.formattedDeadline
            textSize = 16f
            setTextColor(if (isOverdue) Color.RED else Color.WHITE)
            gravity = Gravity.CENTER
        })

        section.addView(TextView(context).apply {
            val days = config.daysRemaining
            text = if (isOverdue) "Payment is overdue"
            else "${days} day${if (days == 1L) "" else "s"} remaining"
            textSize = 14f
            setTextColor(
                if (isOverdue) ColorUtils.setAlphaComponent(Color.RED, 200)
                else ColorUtils.setAlphaComponent(Color.WHITE, 128)
            )
            gravity = Gravity.CENTER
        })

        return section
    }

    private fun createFreelancerSection(freelancer: String): LinearLayout {
        val section = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { bottomMargin = dpToPx(32) }
        }

        section.addView(TextView(context).apply {
            text = "DEVELOPER"
            textSize = 12f
            setTextColor(ColorUtils.setAlphaComponent(Color.WHITE, 128))
            letterSpacing = 0.08f
            gravity = Gravity.CENTER
        })

        section.addView(TextView(context).apply {
            text = freelancer
            textSize = 16f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        })

        return section
    }

    private fun createFooter(isOverdue: Boolean): TextView {
        return TextView(context).apply {
            text = if (isOverdue)
                "This app is locked. Payment deadline has passed.\nContact the developer immediately."
            else
                "This app is locked pending payment.\nContact the developer to unlock."
            textSize = 12f
            setTextColor(ColorUtils.setAlphaComponent(Color.WHITE, 102))
            gravity = Gravity.CENTER
            textAlignment = View.TEXT_ALIGNMENT_CENTER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = dpToPx(32)
                leftMargin = dpToPx(32)
                rightMargin = dpToPx(32)
            }
        }
    }

    companion object {
        fun wrap(activity: Activity) {
            if (!GlassEffectConfig.isUnlocked) {
                val overlay = GlassEffectLayout(activity)
                activity.addContentView(
                    overlay,
                    LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT
                    )
                )
            }
        }

        private fun dpToPx(dp: Int): Int {
            return (dp * android.content.res.Resources.getSystem().displayMetrics.density).toInt()
        }
    }
}
