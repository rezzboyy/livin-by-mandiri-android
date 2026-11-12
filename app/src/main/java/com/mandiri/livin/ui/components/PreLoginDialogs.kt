package com.mandiri.livin.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.mandiri.livin.ui.theme.LivinBlueBottom
import com.mandiri.livin.ui.theme.LivinDeepNavy
import com.mandiri.livin.ui.theme.LivinGoldPrimary

@Composable
fun BiometricAuthDialog(
    onDismiss: () -> Unit,
    onSuccess: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            tonalElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Biometric Icon
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .background(LivinBlueBottom.copy(alpha = 0.1f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🔒",
                        fontSize = 32.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Login ke Livin' by Mandiri",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = LivinDeepNavy,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Sentuh sensor sidik jari ponsel Anda untuk masuk dengan cepat dan aman.",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onSuccess,
                    colors = ButtonDefaults.buttonColors(containerColor = LivinDeepNavy),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp)
                ) {
                    Text(
                        text = "Verifikasi Sidik Jari",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Batal",
                        color = LivinBlueBottom,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun LivinCallDialog(
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "livin' Call by mandiri",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = LivinBlueBottom
                )
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                    color = Color(0xFFE53935),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Bebas Pulsa",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Hubungi Mandiri Call 14000 langsung dari aplikasi tanpa menguras pulsa reguler seluler Anda melalui koneksi data internet.",
                    fontSize = 13.sp,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = LivinDeepNavy),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Tutup", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun IntipSaldoDialog(
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Intip Saldo",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = LivinDeepNavy
                    )
                    TextButton(onClick = onDismiss) {
                        Text("Tutup", color = LivinBlueBottom, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                    color = Color(0xFFF5F8FF),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Mandiri Tabungan Now",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "133-00-2491823-9",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = LivinDeepNavy
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Rp 18.450.000",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = LivinDeepNavy
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SecurityTipsDialog(
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(24.dp)
            ) {
                Text(
                    text = "Tips Transaksi Aman",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = LivinDeepNavy
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "1. Jangan pernah memberikan PIN, OTP, atau Password kepada siapapun.\n\n" +
                           "2. Waspadai nomor tidak dikenal yang mengatasnamakan Bank Mandiri.\n\n" +
                           "3. Pastikan selalu menggunakan aplikasi resmi Livin' by Mandiri.",
                    fontSize = 13.sp,
                    color = Color.DarkGray,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(18.dp))
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = LivinDeepNavy),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Saya Mengerti", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun FeatureInfoDialog(
    featureName: String,
    featureDesc: String,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = featureName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = LivinDeepNavy
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = featureDesc,
                    fontSize = 13.sp,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = LivinDeepNavy),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Tutup", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
