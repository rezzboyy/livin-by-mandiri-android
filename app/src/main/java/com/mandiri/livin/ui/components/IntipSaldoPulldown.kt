package com.mandiri.livin.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mandiri.livin.R
import com.mandiri.livin.ui.theme.LivinDeepNavy
import kotlin.math.roundToInt

@Composable
fun IntipSaldoPulldown(
    pullProgress: Float,       // 0.0f (hidden) to 1.0f (fully pulled down)
    sheetOffsetY: Float,       // Negative when hidden, 0 when fully down
    onDismiss: () -> Unit
) {
    if (pullProgress > 0.005f) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onDismiss()
                }
        ) {
            // 1. Frosted Glass Blur Background (Gaussian blurred overlay with dynamic alpha)
            Image(
                painter = painterResource(id = R.drawable.bg_livin_prelogin_blurred),
                contentDescription = "Latar Belakang Buram",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(pullProgress)
            )

            // Semi-transparent subtle blue scrim
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x33001F4D).copy(alpha = 0.35f * pullProgress))
            )

            // 2. Sliding Drawer Cards (1:1 tracking finger position)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .offset { IntOffset(0, sheetOffsetY.roundToInt()) }
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Card 1: Tabungan NOW IDR & Debit Gold Card
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    shadowElevation = (8.dp * pullProgress).coerceAtLeast(2.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 18.dp, vertical = 18.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Tabungan NOW IDR",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = LivinDeepNavy
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "*********7295",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFF6B7280)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.Top) {
                                Text(
                                    text = "Rp 24.042.680",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = LivinDeepNavy
                                )
                                Text(
                                    text = "⁴¹",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = LivinDeepNavy,
                                    modifier = Modifier.padding(start = 2.dp, top = 1.dp)
                                )
                            }
                        }

                        // Mandiri Debit Gold Card image
                        Image(
                            painter = painterResource(id = R.drawable.mandiri_debit_gold),
                            contentDescription = "Mandiri Debit Gold Card",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .width(65.dp)
                                .height(95.dp)
                                .clip(RoundedCornerShape(6.dp))
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Card 2: Riwayat Transaksi Terakhir (QR Bayar & Transfer Rupiah)
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    shadowElevation = (8.dp * pullProgress).coerceAtLeast(2.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 18.dp, vertical = 14.dp)
                    ) {
                        // Transaction 1: QR Bayar
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_tx_qris),
                                    contentDescription = "QR Bayar",
                                    modifier = Modifier.size(28.dp)
                                )
                                Spacer(modifier = Modifier.width(14.dp))
                                Column {
                                    Text(
                                        text = "QR Bayar",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = LivinDeepNavy
                                    )
                                    Text(
                                        text = "04 Nov 2026",
                                        fontSize = 12.sp,
                                        color = Color(0xFF6B7280)
                                    )
                                }
                            }

                            Row(verticalAlignment = Alignment.Top) {
                                Text(
                                    text = "- Rp 15.000",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = LivinDeepNavy
                                )
                                Text(
                                    text = "⁰⁰",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = LivinDeepNavy,
                                    modifier = Modifier.padding(start = 1.dp)
                                )
                            }
                        }

                        HorizontalDivider(
                            color = Color(0xFFF0F2F5),
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )

                        // Transaction 2: Transfer Rupiah
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_tx_transfer),
                                    contentDescription = "Transfer Rupiah",
                                    modifier = Modifier.size(28.dp)
                                )
                                Spacer(modifier = Modifier.width(14.dp))
                                Column {
                                    Text(
                                        text = "Transfer Rupiah",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = LivinDeepNavy
                                    )
                                    Text(
                                        text = "04 Nov 2026",
                                        fontSize = 12.sp,
                                        color = Color(0xFF6B7280)
                                    )
                                }
                            }

                            Row(verticalAlignment = Alignment.Top) {
                                Text(
                                    text = "- Rp 282.000",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = LivinDeepNavy
                                )
                                Text(
                                    text = "⁰⁰",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = LivinDeepNavy,
                                    modifier = Modifier.padding(start = 1.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Bottom Hint: "Lepas untuk Menutup"
                Text(
                    text = "Lepas untuk Menutup",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.9f * pullProgress),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
