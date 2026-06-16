package com.glassefc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GlassEffect(
    content: @Composable () -> Unit
) {
    if (GlassEffectConfig.isUnlocked) {
        content()
    } else {
        GlassEffectOverlay()
    }
}

@Composable
private fun GlassEffectOverlay() {
    val config = GlassEffectConfig
    val isOverdue = config.isOverdue
    val days = config.daysRemaining

    val bgColor = if (isOverdue) Color(0x261A0000.toInt()) else Color.Black
    val accentColor = if (isOverdue) Color(0xFFCC0000) else Color.White
    val dimText = Color.White.copy(alpha = 0.5f)
    val veryDim = Color.White.copy(alpha = 0.4f)

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
                .padding(horizontal = 32.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = if (isOverdue) "\u26A0\uFE0F" else "\uD83D\uDD12",
                fontSize = 60.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = if (isOverdue) "Payment Overdue" else "Payment Required",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = config.project,
                style = MaterialTheme.typography.bodyMedium,
                color = dimText
            )

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(vertical = 24.dp, horizontal = 40.dp)
                    .background(Color.White.copy(alpha = 0.08f))
                    .padding(24.dp)
            ) {
                Text(
                    text = "Amount Due",
                    style = MaterialTheme.typography.labelSmall,
                    color = dimText,
                    letterSpacing = 1.5.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = config.amount,
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold,
                    color = accentColor
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = if (isOverdue) "Deadline was" else "Deadline",
                    style = MaterialTheme.typography.labelSmall,
                    color = dimText,
                    letterSpacing = 1.5.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = config.formattedDeadline,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (isOverdue) Color(0xFFCC0000) else Color.White
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = if (isOverdue) "Payment is overdue"
                    else "${days} day${if (days == 1L) "" else "s"} remaining",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isOverdue) Color(0xFFCC0000).copy(alpha = 0.8f) else dimText
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Developer",
                    style = MaterialTheme.typography.labelSmall,
                    color = dimText,
                    letterSpacing = 1.5.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = config.freelancer,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = if (isOverdue)
                    "This app is locked. Payment deadline has passed.\nContact the developer immediately."
                else
                    "This app is locked pending payment.\nContact the developer to unlock.",
                style = MaterialTheme.typography.bodySmall,
                color = veryDim,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )
        }
    }
}
