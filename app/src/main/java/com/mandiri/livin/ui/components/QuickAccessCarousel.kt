@file:OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
package com.mandiri.livin.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mandiri.livin.R
import kotlinx.coroutines.launch

data class CarouselMenuItem(
    val iconRes: Int,
    val title: String,
    val desc: String
)

val page1Items = listOf(
    CarouselMenuItem(
        iconRes = R.drawable.ic_nav_emoney,
        title = "e-money",
        desc = "Cek dan perbarui saldo kartu Mandiri e-money Anda secara instan menggunakan fitur NFC smartphone."
    ),
    CarouselMenuItem(
        iconRes = R.drawable.ic_nav_quick_pick,
        title = "Quick Pick",
        desc = "Lakukan transaksi favorit rutin seperti beli pulsa, token listrik, dan transfer tanpa harus login terlebih dahulu."
    ),
    CarouselMenuItem(
        iconRes = R.drawable.ic_nav_qr_bayar,
        title = "QR Bayar",
        desc = "Pindai QRIS merchant secara cepat untuk pembayaran di jutaan gerai dan toko di seluruh Indonesia."
    ),
    CarouselMenuItem(
        iconRes = R.drawable.ic_nav_qris_tap,
        title = "QRIS Tap",
        desc = "Fitur inovasi pembayaran nirsentuh: Cukup tempelkan ponsel Anda ke terminal pembayaran bertanda QRIS Tap."
    )
)

val page2Items = listOf(
    CarouselMenuItem(
        iconRes = R.drawable.ic_nav_qris_tap,
        title = "QRIS Tap",
        desc = "Fitur inovasi pembayaran nirsentuh: Cukup tempelkan ponsel Anda ke terminal pembayaran bertanda QRIS Tap."
    ),
    CarouselMenuItem(
        iconRes = R.drawable.ic_nav_qr_terima,
        title = "QR Terima\nTransfer",
        desc = "Tampilkan kode QR pribadi rekening Anda untuk menerima dana transfer secara cepat dari bank manapun."
    ),
    CarouselMenuItem(
        iconRes = R.drawable.ic_nav_tap_to_pay,
        title = "Tap to Pay",
        desc = "Ubah ponsel Android Anda menjadi kartu pembayaran nirsentuh untuk bertransaksi di mesin EDC Contactless."
    ),
    CarouselMenuItem(
        iconRes = R.drawable.ic_nav_setor_tarik,
        title = "Setor Tarik",
        desc = "Tarik atau setor uang tunai di seluruh jaringan ATM Bank Mandiri tanpa perlu membawa kartu debit."
    )
)

@Composable
fun QuickAccessCarousel(
    modifier: Modifier = Modifier,
    onItemSelected: (Pair<String, String>) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { 2 })
    val haptic = LocalHapticFeedback.current

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. HORIZONTAL PAGER: ONLY the transparent icons and labels move!
        // Background behind this component remains completely fixed/stationary.
        HorizontalPager(
            state = pagerState,
            pageSpacing = 0.dp,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { page ->
            val items = if (page == 0) page1Items else page2Items

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                items.forEach { item ->
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                onItemSelected(Pair(item.title.replace("\n", " "), item.desc))
                            },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = item.iconRes),
                            contentDescription = item.title,
                            modifier = Modifier.size(56.dp)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = item.title,
                            color = Color.White,
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            lineHeight = 13.5.sp,
                            maxLines = 2
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // 2. STATIONARY INDICATOR BAR (DIAM DI TEMPAT)
        // Fixed track + sliding pill driven directly by pager fraction + interactive arrow buttons!
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .padding(bottom = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Previous Page Button (Left Arrow, appears on Page 1)
            AnimatedVisibility(
                visible = pagerState.currentPage == 1,
                enter = fadeIn(tween(200)) + scaleIn(tween(200)),
                exit = fadeOut(tween(200)) + scaleOut(tween(200))
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_carousel_arrow_left),
                        contentDescription = "Halaman Sebelumnya",
                        modifier = Modifier
                            .size(22.dp)
                            .clip(CircleShape)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(0, animationSpec = tween(350))
                                }
                            }
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }

            // Fixed Pill Track (Stationary capsule)
            Box(
                modifier = Modifier
                    .width(44.dp)
                    .height(4.5.dp)
                    .background(Color(0x40FFFFFF), shape = RoundedCornerShape(2.25.dp))
            ) {
                // Sliding Pill: Moves smoothly between left and right based on pager drag fraction!
                val fraction = (pagerState.currentPage + pagerState.currentPageOffsetFraction).coerceIn(0f, 1f)
                val pillOffset = 22.dp * fraction

                Box(
                    modifier = Modifier
                        .offset(x = pillOffset)
                        .width(22.dp)
                        .height(4.5.dp)
                        .background(Color.White, shape = RoundedCornerShape(2.25.dp))
                )
            }

            // Next Page Button (Right Arrow, appears on Page 0)
            AnimatedVisibility(
                visible = pagerState.currentPage == 0,
                enter = fadeIn(tween(200)) + scaleIn(tween(200)),
                exit = fadeOut(tween(200)) + scaleOut(tween(200))
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.width(10.dp))
                    Image(
                        painter = painterResource(id = R.drawable.ic_carousel_arrow_right),
                        contentDescription = "Halaman Selanjutnya",
                        modifier = Modifier
                            .size(22.dp)
                            .clip(CircleShape)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(1, animationSpec = tween(350))
                                }
                            }
                    )
                }
            }
        }
    }
}
