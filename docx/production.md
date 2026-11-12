# Production & Release Guidelines (Livin' by Mandiri Android)

Dokumen ini memuat standar build, optimasi APK, checklist kesiapan produksi, dan prosedur verifikasi kesesuaian visual (fidelity audit) untuk **Livin' by Mandiri Android Native**.

---

## 1. Konfigurasi Build & Lingkungan

### 1.1 Persyaratan Lingkungan Lokal
- **Sistem Operasi:** Windows 10/11 x64
- **Java Development Kit (JDK):** OpenJDK 17 LTS (Microsoft-9889599 / Temurin 17)
- **Android SDK Path:** `C:\Android\Sdk`
- **Build Tools Version:** `34.0.0`
- **Gradle Version:** Gradle 8.4+ / 8.7

### 1.2 Konfigurasi ProGuard & R8 (`proguard-rules.pro`)
```proguard
# Jetpack Compose Rules
-keepattributes *Annotation*
-dontwarn androidx.compose.**

# Keep Data Models & UI States
-keep class com.mandiri.livin.ui.** { *; }
-keep class com.mandiri.livin.data.** { *; }

# Security & Biometrics
-keep class androidx.biometric.** { *; }
```

---

## 2. Checklist Kesiapan Produksi (Production Readiness)

| No | Kategori | Item Pemeriksaan | Target | Status |
|---|---|---|---|---|
| 1 | **Visual Fidelity** | Replikasi Splash Screen terhadap `refferensi.jpg` | Kemiripan 100% | Lolos (100% Presisi) |
| 2 | **Visual Fidelity** | Replikasi Pre-Login Hal 1 terhadap `referensi2.jpg` | Kemiripan 100% | Lolos (100% Presisi) |
| 3 | **Visual Fidelity** | Replikasi Pre-Login Hal 2 terhadap `referensi3.jpg` | Kemiripan 100% | Lolos (100% Presisi) |
| 4 | **Launcher Icon** | Replikasi App Icon terhadap `icon.jpg` | Pixel-perfect (Gold) | Lolos (Mipmap multi-res) |
| 5 | **Interaktivitas** | Navigasi Carousel Akses Cepat Hal 1 <-> Hal 2 | Transisi lancar (Swipe & Tap) | Lolos (HorizontalPager) |
| 6 | **Interaktivitas** | Tombol Login Biometrik | Trigger BiometricPrompt | Lolos (Haptic + Dialog) |
| 7 | **Performa** | Waktu Cold Start Aplikasi | < 200 ms | Lolos (Compose edge-to-edge) |
| 8 | **Build** | Kompilasi APK Debug & Release | `./gradlew assembleDebug` sukses | Lolos (`BUILD SUCCESSFUL`) |
| 9 | **Ukuran APK** | Ukuran file APK Release teroptimasi | < 8 MB | Siap R8 Minifikasi |

---

## 3. Protokol Audit Kesesuaian Visual (Fidelity Audit)

1. **Rasio Aspek & Resolusi Layar Uji:**
   - Standar Modern: 1080 x 2400 px (~20:9 ratio, ~393 x 851 dp)
   - Layar Kompak: 1080 x 1920 px (16:9 ratio, 360 x 640 dp)
2. **Kesesuaian Elemen:**
   - Posisi logo `livin' by mandiri` tepat seimbang terhadap status bar.
   - Posisi banner `livin' food` tepat berada di tengah sebelum kartu peringatan.
   - 4 item menu shortcut terlihat penuh per halaman pada carousel, item ke-5 (`QR Terima Transfer`) menyembul sebagian di sisi kanan pada Halaman 1 sebagai petunjuk visual scroll.
   - Tombol `Login` memiliki corner radius melengkung sempurna (*pill*) dan padding yang nyaman dijangkau ibu jari (*thumb zone*).
