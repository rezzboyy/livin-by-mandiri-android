package com.mandiri.livin.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

data class AnnouncementItem(
    val title: String,
    val subtitle: String
)

val announcements = listOf(
    AnnouncementItem(
        title = "Pastikan Transaksi Anda Selalu Am...",
        subtitle = "Hati-hati dengan tawaran mencurigak..."
    ),
    AnnouncementItem(
        title = "Pesan Makan? Livin' Food Aja",
        subtitle = "Mudah, hemat, dan banyak resto lokal!"
    )
)

@Composable
fun RotatingAnnouncementPill(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    var currentIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(4000)
            currentIndex = (currentIndex + 1) % announcements.size
        }
    }

    val item = announcements[currentIndex]

    Surface(
        color = Color(0xFF091E3E),
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 6.dp,
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AnimatedContent(
                targetState = item,
                transitionSpec = {
                    (slideInVertically(tween(350)) { it } + fadeIn(tween(350))) togetherWith
                    (slideOutVertically(tween(350)) { -it } + fadeOut(tween(350)))
                },
                modifier = Modifier.weight(1f),
                label = "AnnouncementRotation"
            ) { targetItem ->
                Column {
                    Text(
                        text = targetItem.title,
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = targetItem.subtitle,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFFD1D5DB)
                    )
                }
            }

            // White circular right-arrow button
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "➔",
                    fontSize = 12.sp,
                    color = Color(0xFF091E3E),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
