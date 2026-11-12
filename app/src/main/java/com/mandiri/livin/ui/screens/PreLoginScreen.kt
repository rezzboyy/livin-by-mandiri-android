@file:OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
package com.mandiri.livin.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mandiri.livin.R
import com.mandiri.livin.ui.components.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PreLoginScreen(
    onLoginSuccess: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val haptic = LocalHapticFeedback.current
    val density = LocalDensity.current

    // Dialog States
    var showBiometricDialog by remember { mutableStateOf(false) }
    var showLivinCallDialog by remember { mutableStateOf(false) }
    var showSecurityDialog by remember { mutableStateOf(false) }
    var selectedFeature by remember { mutableStateOf<Pair<String, String>?>(null) }

    // Pulldown "Intip Saldo" Interactive Physics (1:1 tracking)
    // Resting target distance when fully pulled down (~380dp)
    val maxPullDistancePx = with(density) { 380.dp.toPx() }
    val pulldownY = remember { Animatable(0f) }

    // Double Chevron Bouncing Animation
    val infiniteTransition = rememberInfiniteTransition(label = "chevronTransition")
    val chevronBounce by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 6f,
        animationSpec = infiniteRepeatable(
            animation = tween(850, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "chevronBounce"
    )

    // Function to trigger tap-to-peek animation
    fun triggerPeek() {
        coroutineScope.launch {
            pulldownY.animateTo(
                targetValue = maxPullDistancePx,
                animationSpec = tween(380, easing = FastOutSlowInEasing)
            )
            delay(3200)
            pulldownY.animateTo(
                targetValue = 0f,
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            )
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B56BE))
            .pointerInput(Unit) {
                // Interactive 1:1 vertical drag gesture from the upper 40% of the screen
                detectVerticalDragGestures(
                    onVerticalDrag = { change, dragAmount ->
                        if (change.position.y < size.height * 0.45f || pulldownY.value > 10f) {
                            change.consume()
                            coroutineScope.launch {
                                val newOffset = (pulldownY.value + dragAmount).coerceIn(0f, maxPullDistancePx)
                                pulldownY.snapTo(newOffset)
                            }
                        }
                    },
                    onDragEnd = {
                        // "Lepas untuk Menutup": When finger is released, automatically spring back up!
                        coroutineScope.launch {
                            pulldownY.animateTo(
                                targetValue = 0f,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessMediumLow
                                )
                            )
                        }
                    },
                    onDragCancel = {
                        coroutineScope.launch {
                            pulldownY.animateTo(0f)
                        }
                    }
                )
            }
    ) {
        val screenWidth = maxWidth
        val screenHeight = maxHeight

        val refW = 1440f
        val refH = 3120f

        // ========================================================
        // 1. CLEAN STATIC BACKGROUND (100% DIAM / STATIONARY)
        // Sanitized background without baked-in icons or baked chevrons.
        // ========================================================
        Image(
            painter = painterResource(id = R.drawable.bg_livin_prelogin_p1),
            contentDescription = "Livin Pre-Login Static Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        // ========================================================
        // 2. HEADER INTERACTION & BOUNCING CHEVRON
        // "Geser untuk Intip Saldo" with vertical animated chevrons
        // ========================================================
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(top = 16.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    triggerPeek()
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Geser untuk Intip Saldo",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(3.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_chevron_pulldown),
                contentDescription = "Tarik ke bawah untuk Intip Saldo",
                modifier = Modifier
                    .size(width = 18.dp, height = 12.dp)
                    .offset(y = chevronBounce.dp)
            )
        }

        // Top-Right: "livin' Call by mandiri (Bebas Pulsa)" Button
        Box(
            modifier = Modifier
                .offset(x = screenWidth * (1110f / refW), y = screenHeight * (170f / refH))
                .size(width = screenWidth * (280f / refW), height = screenHeight * (270f / refH))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    showLivinCallDialog = true
                }
        )

        // ========================================================
        // 3. ROTATING ANNOUNCEMENT / PROMO PILL (DYNAMIC SLIDER)
        // Automatically alternates between security warning and livin' food!
        // ========================================================
        val pillTop = screenHeight * (1590f / refH)
        val pillHeight = screenHeight * (230f / refH)

        RotatingAnnouncementPill(
            modifier = Modifier
                .offset(x = screenWidth * (96f / refW), y = pillTop)
                .size(width = screenWidth * (1248f / refW), height = pillHeight)
        ) {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            showSecurityDialog = true
        }

        // ========================================================
        // 4. ISOLATED NATIVE QUICK ACCESS CAROUSEL (100% DIAM BACKGROUND)
        // Only icons and badges slide. Track and background remain fixed!
        // ========================================================
        val carouselTop = screenHeight * (1830f / refH)
        val carouselHeight = screenHeight * (690f / refH)

        QuickAccessCarousel(
            modifier = Modifier
                .fillMaxWidth()
                .height(carouselHeight)
                .align(Alignment.TopStart)
                .offset(y = carouselTop),
            onItemSelected = { feature ->
                selectedFeature = feature
            }
        )

        // ========================================================
        // 5. PRIMARY "LOGIN" BIOMETRIC BUTTON HOTSPOT (BOTTOM PILL)
        // ========================================================
        Box(
            modifier = Modifier
                .offset(x = screenWidth * (96f / refW), y = screenHeight * (2540f / refH))
                .size(width = screenWidth * (1248f / refW), height = screenHeight * (210f / refH))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    showBiometricDialog = true
                }
        )

        // ========================================================
        // 6. INTERACTIVE INTIP SALDO PULLDOWN (1:1 PEEK & RELEASE)
        // Slides down with finger tracking and frosted blur backdrop.
        // ========================================================
        val pullProgress = (pulldownY.value / maxPullDistancePx).coerceIn(0f, 1f)
        val sheetOffsetY = pulldownY.value - maxPullDistancePx

        IntipSaldoPulldown(
            pullProgress = pullProgress,
            sheetOffsetY = sheetOffsetY,
            onDismiss = {
                coroutineScope.launch {
                    pulldownY.animateTo(0f, animationSpec = tween(250))
                }
            }
        )
    }

    // Modal Dialogs
    if (showBiometricDialog) {
        BiometricAuthDialog(
            onDismiss = { showBiometricDialog = false },
            onSuccess = {
                showBiometricDialog = false
                onLoginSuccess()
            }
        )
    }

    if (showLivinCallDialog) {
        LivinCallDialog(onDismiss = { showLivinCallDialog = false })
    }

    if (showSecurityDialog) {
        SecurityTipsDialog(onDismiss = { showSecurityDialog = false })
    }

    selectedFeature?.let { (title, desc) ->
        FeatureInfoDialog(
            featureName = title,
            featureDesc = desc,
            onDismiss = { selectedFeature = null }
        )
    }
}
