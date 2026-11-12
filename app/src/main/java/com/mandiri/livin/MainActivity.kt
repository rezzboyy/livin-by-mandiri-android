package com.mandiri.livin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import com.mandiri.livin.data.updater.GitHubUpdateManager
import com.mandiri.livin.data.updater.UpdateCheckResult
import com.mandiri.livin.data.updater.UpdateInfo
import com.mandiri.livin.ui.components.LivinUpdateDialog
import com.mandiri.livin.ui.screens.LivinSplashScreen
import com.mandiri.livin.ui.screens.PreLoginScreen
import com.mandiri.livin.ui.theme.LivinBlueBottom
import com.mandiri.livin.ui.theme.LivinTheme
import kotlinx.coroutines.launch

enum class ScreenState {
    SPLASH,
    PRE_LOGIN
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Edge-to-Edge display
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            LivinTheme {
                val context = LocalContext.current
                val coroutineScope = rememberCoroutineScope()
                var currentScreen by remember { mutableStateOf(ScreenState.SPLASH) }

                var availableUpdate by remember { mutableStateOf<UpdateInfo?>(null) }
                var isDownloadingUpdate by remember { mutableStateOf(false) }
                var updateDownloadProgress by remember { mutableFloatStateOf(0f) }

                // Pengecekan otomatis rilis terbaru di latar belakang
                LaunchedEffect(Unit) {
                    try {
                        val currentVersion = context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: "1.0.0"
                        when (val res = GitHubUpdateManager.checkForUpdate(currentVersion)) {
                            is UpdateCheckResult.UpdateAvailable -> {
                                availableUpdate = res.info
                            }
                            else -> {}
                        }
                    } catch (_: Exception) {}
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = LivinBlueBottom
                ) {
                    Crossfade(
                        targetState = currentScreen,
                        animationSpec = tween(durationMillis = 500),
                        label = "ScreenTransition"
                    ) { screen ->
                        when (screen) {
                            ScreenState.SPLASH -> {
                                LivinSplashScreen(
                                    onSplashFinished = {
                                        currentScreen = ScreenState.PRE_LOGIN
                                    }
                                )
                            }
                            ScreenState.PRE_LOGIN -> {
                                PreLoginScreen(
                                    onLoginSuccess = {
                                        Toast.makeText(
                                            this@MainActivity,
                                            "Login Berhasil - Selamat datang di Livin' by Mandiri",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                )
                            }
                        }
                    }

                    // Tampilkan Dialog Pembaruan jika tersedia
                    if (availableUpdate != null) {
                        LivinUpdateDialog(
                            updateInfo = availableUpdate!!,
                            isDownloading = isDownloadingUpdate,
                            downloadProgress = updateDownloadProgress,
                            onDismiss = {
                                if (!isDownloadingUpdate) {
                                    availableUpdate = null
                                }
                            },
                            onConfirmUpdate = {
                                isDownloadingUpdate = true
                                coroutineScope.launch {
                                    GitHubUpdateManager.downloadAndInstallApk(
                                        context = context,
                                        downloadUrl = availableUpdate!!.apkDownloadUrl,
                                        onProgress = { updateDownloadProgress = it },
                                        onError = { errorMsg ->
                                            isDownloadingUpdate = false
                                            availableUpdate = null
                                            Toast.makeText(context, "Gagal mengunduh: $errorMsg", Toast.LENGTH_LONG).show()
                                        }
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
