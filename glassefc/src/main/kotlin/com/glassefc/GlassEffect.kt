package com.glassefc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GlassEffect(
    config: GlassEffectConfig = GlassEffectConfig,
    content: @Composable () -> Unit
) {
    if (config.isUnlocked()) {
        content()
    } else {
        GlassEffectOverlay(
            isOverdue = config.isOverdue,
            daysRemaining = config.daysRemaining,
            formattedDeadline = config.formattedDeadline,
            project = config.project,
            amount = config.amount,
            freelancer = config.freelancer
        )
    }
}

@Composable
internal fun GlassEffectOverlay(
    isOverdue: Boolean,
    daysRemaining: Long,
    formattedDeadline: String,
    project: String,
    amount: String,
    freelancer: String
) {
    val bgColor = if (isOverdue) GlassEffectDefaults.bgOverdue else GlassEffectDefaults.bgNormal
    val accentColor = if (isOverdue) GlassEffectDefaults.accentOverdue else GlassEffectDefaults.accentNormal
    val dimText = GlassEffectDefaults.accentNormal.copy(alpha = GlassEffectDefaults.dimAlpha)
    val veryDim = GlassEffectDefaults.accentNormal.copy(alpha = GlassEffectDefaults.veryDimAlpha)
    val overdueAccentDim = GlassEffectDefaults.accentOverdue.copy(alpha = 0.8f)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = GlassEffectDefaults.horizontalMarginDp.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = if (isOverdue) Icons.Filled.Warning else Icons.Filled.Lock,
                contentDescription = null,
                modifier = Modifier
                    .width(GlassEffectDefaults.iconSizeDp.dp)
                    .height(GlassEffectDefaults.iconSizeDp.dp),
                tint = accentColor
            )

            Spacer(modifier = Modifier.height(GlassEffectDefaults.sectionSpacingDp.dp))

            Text(
                text = if (isOverdue) GlassEffectDefaults.titleOverdue else GlassEffectDefaults.titleLocked,
                style = MaterialTheme.typography.titleLarge,
                color = GlassEffectDefaults.accentNormal,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(GlassEffectDefaults.innerSectionSpacingDp.dp))

            Text(
                text = project,
                style = MaterialTheme.typography.bodyMedium,
                color = dimText
            )

            Spacer(modifier = Modifier.height(GlassEffectDefaults.sectionSpacingDp.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(
                        color = GlassEffectDefaults.accentNormal.copy(
                            alpha = GlassEffectDefaults.cardBgAlpha
                        )
                    )
                    .padding(
                        horizontal = GlassEffectDefaults.cardHorizontalPaddingDp.dp,
                        vertical = GlassEffectDefaults.cardVerticalPaddingDp.dp
                    )
            ) {
                Text(
                    text = GlassEffectDefaults.labelAmountDue,
                    style = MaterialTheme.typography.labelSmall,
                    color = dimText,
                    letterSpacing = 1.5.sp
                )

                Spacer(modifier = Modifier.height(GlassEffectDefaults.innerSectionSpacingDp.dp))

                Text(
                    text = amount,
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold,
                    color = accentColor
                )
            }

            Spacer(modifier = Modifier.height(GlassEffectDefaults.sectionSpacingDp.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = if (isOverdue) GlassEffectDefaults.labelDeadlineWas else GlassEffectDefaults.labelDeadline,
                    style = MaterialTheme.typography.labelSmall,
                    color = dimText,
                    letterSpacing = 1.5.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = formattedDeadline,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (isOverdue) GlassEffectDefaults.accentOverdue else GlassEffectDefaults.accentNormal
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = if (isOverdue) GlassEffectDefaults.overdueSuffix
                    else "${daysRemaining} day${if (daysRemaining == 1L) "" else "s"} remaining",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isOverdue) overdueAccentDim else dimText
                )
            }

            Spacer(modifier = Modifier.height(GlassEffectDefaults.sectionSpacingDp.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = GlassEffectDefaults.labelDeveloper,
                    style = MaterialTheme.typography.labelSmall,
                    color = dimText,
                    letterSpacing = 1.5.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = freelancer,
                    style = MaterialTheme.typography.titleMedium,
                    color = GlassEffectDefaults.accentNormal
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = if (isOverdue) GlassEffectDefaults.footerOverdue
                else GlassEffectDefaults.footerLocked,
                style = MaterialTheme.typography.bodySmall,
                color = veryDim,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = GlassEffectDefaults.sectionSpacingDp.dp)
            )
        }
    }
}
