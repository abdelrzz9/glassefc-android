package com.glassefc

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark

@Preview(
    name = "Locked — Before Deadline",
    showBackground = true,
    backgroundColor = 0xFF1A1A1A,
    showSystemUi = true
)
@PreviewLightDark
@Composable
private fun GlassEffectPreviewBeforeDeadline() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            GlassEffectOverlay(
                isOverdue = false,
                daysRemaining = 14L,
                formattedDeadline = "15 Jul 2026",
                project = "Client App",
                amount = "2,500 USD",
                freelancer = "Jane Freelancer"
            )
        }
    }
}

@Preview(
    name = "Locked — Overdue",
    showBackground = true,
    backgroundColor = 0xFF2A0A0A,
    showSystemUi = true
)
@Composable
private fun GlassEffectPreviewAfterDeadline() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            GlassEffectOverlay(
                isOverdue = true,
                daysRemaining = -14L,
                formattedDeadline = "15 Mar 2024",
                project = "Client App",
                amount = "2,500 USD",
                freelancer = "Jane Freelancer"
            )
        }
    }
}
