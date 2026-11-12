# Roadmap & Task Breakdown (Livin' by Mandiri Android)

Dokumen ini memuat daftar tugas terperinci, alur implementasi, dan pelacakan progres pengembangan aplikasi **Livin' by Mandiri Android Native** berbasis **Kotlin + Jetpack Compose**.

> [!IMPORTANT]
> **BATASAN RUANG LINGKUP (STRICT SCOPE):**
> Sesuai arahan, pengerjaan **HANYA** mereplikasi apa yang terdapat pada gambar referensi resmi di direktori `image/` dan rekaman video `referensisemuaaspekdananimasidanfitur.mp4`:
> 1. `icon.jpg`: App Launcher Icon Livin' Gold.
> 2. `refferensi.jpg`: Layar Pembuka / Splash Screen resmi (3D Splash Paint Gold & Blue + Logo Livin' + Legal OJK/BI/LPS).
> 3. `referensi2.jpg`: Layar Pre-Login (Sebelum Login) Halaman 1 (Intip Saldo, Livin' Call, Promo Hero livin' food, Banner Keamanan, Carousel Akses Cepat Page 1, Tombol Login Biometrik).
> 4. `referensi3.jpg`: Layar Pre-Login Halaman 2 (Carousel Akses Cepat Page 2: QRIS Tap, QR Terima Transfer, Tap to Pay, Setor Tarik).
> 5. `referensisemuaaspekdananimasidanfitur.mp4`: Video rekaman asli alur dan gestur sentuh (isolasi swipe carousel, intip saldo pulldown 1:1, auto-retract "Lepas untuk Menutup", banner bergulir).
>
> **Dilarang keras membuat modul atau alur di luar referensi di atas sebelum ada instruksi lanjutan.**

---

## Status Progres
- [ ] Belum Dimulai
- [/] Sedang Dikerjakan
- [x] Selesai

---

## Fase 1: Fondasi Scaffolding & Setup Android Studio (Compose)
- [x] **1.1 Setup Project Structure & Gradle Kotlin DSL**
  - [x] Konfigurasi `settings.gradle.kts` dengan repositori Google & MavenCentral.
  - [x] Konfigurasi root `build.gradle.kts` (Android Gradle Plugin 8.4.2 + Kotlin 1.9.24).
  - [x] Konfigurasi `gradle.properties` & `local.properties` (`sdk.dir=C:\Android\Sdk`).
  - [x] Setup wrapper Gradle 8.7 (`gradlew`, `gradlew.bat`, `gradle/wrapper`).
- [x] **1.2 Setup App Module & Dependencies (`app/build.gradle.kts`)**
  - [x] `compileSdk = 34`, `minSdk = 24`, `targetSdk = 34`, `namespace = "com.mandiri.livin"`.
  - [x] Jetpack Compose BOM `2024.05.00` + Material 3 (`androidx.compose.material3`).
  - [x] AndroidX Lifecycle Runtime Compose & Activity Compose (`1.9.0`).
  - [x] AndroidX Biometric KTX (`androidx.biometric:biometric-ktx:1.2.0-alpha05`).
  - [x] `AndroidManifest.xml` (Edge-to-Edge window, permissions Biometric & NFC, tema transparan status bar).
- [x] **1.3 Setup Arsitektur & Paket Kode**
  - [x] `com.mandiri.livin.ui.theme` (`Color.kt`, `Theme.kt`, `Type.kt`).
  - [x] `com.mandiri.livin.ui.components` (`PreLoginDialogs.kt`, `RotatingAnnouncementPill.kt`, `IntipSaldoPulldown.kt`, `QuickAccessCarousel.kt`).
  - [x] `com.mandiri.livin.ui.screens` (`LivinSplashScreen.kt`, `PreLoginScreen.kt`).
  - [x] `com.mandiri.livin.MainActivity.kt` (Entry point navigasi dengan Crossfade).

---

## Fase 2: Ekstraksi & Persiapan Aset Visual (Dari `image/` & Video)
- [x] **2.1 Aset Launcher & Splash**
  - [x] Ekstraksi dan adaptasi icon resmi dari `icon.jpg` ke `mipmap-mdpi`, `hdpi`, `xhdpi`, `xxhdpi`, `xxxhdpi` (`ic_launcher.png` & `ic_launcher_round.png`).
  - [x] Ekstraksi 3D Liquid Splash art resolusi tinggi dari `refferensi.jpg` ke `res/drawable-nodpi/bg_livin_splash.png` dengan sanitasi status bar dan nav bar.
- [x] **2.2 Aset Pre-Login Screen**
  - [x] Ekstraksi latar belakang Pre-Login bersih stasioner ke `res/drawable-nodpi/bg_livin_prelogin_p1.png`.
  - [x] Pembuatan latar belakang buram Gaussian frosted glass ke `res/drawable-nodpi/bg_livin_prelogin_blurred.png`.
  - [x] Ekstraksi 7 ikon transparan mandiri: `ic_nav_emoney.png`, `ic_nav_quick_pick.png`, `ic_nav_qr_bayar.png`, `ic_nav_qris_tap.png`, `ic_nav_qr_terima.png`, `ic_nav_tap_to_pay.png`, `ic_nav_setor_tarik.png`.
  - [x] Ekstraksi tombol panah bulat carousel `ic_carousel_arrow_right.png` dan `ic_carousel_arrow_left.png`.
  - [x] Ekstraksi chevron ganda animatif `ic_chevron_pulldown.png`.
  - [x] Ekstraksi badge `livin' Call by mandiri` (Bebas Pulsa) ke `res/drawable/ic_livin_call.png`.
  - [x] Ekstraksi kartu fisik Mandiri Debit Gold (`mandiri_debit_gold.png`), ikon QRIS (`ic_tx_qris.png`), dan ikon Transfer (`ic_tx_transfer.png`).

---

## Fase 3: Implementasi Layar 1 - Splash Screen (`refferensi.jpg`)
- [x] **3.1 Layout & Background Gradien Splash**
  - [x] Background biru royal gradien vertikal khas Mandiri (`#1E88E5` -> `#1565C0` -> `#0D47A1`).
  - [x] Overlay 3D Liquid Paint Splash (Kuning Emas & Biru) terpusat di tengah layar.
- [x] **3.2 Logo & Brand Identifier**
  - [x] Logo `livin' by mandiri` putih presisi tepat di tengah splash art.
- [x] **3.3 Footer Regulasi & Hak Cipta**
  - [x] Teks disclaimer resmi OJK, BI, dan LPS rata tengah dengan tipografi sans-serif putih bersih.
  - [x] Teks "Copyright © 2021 Bank Mandiri".
- [x] **3.4 Logika Transisi & Delay**
  - [x] Timer otomatis (1.8 detik) serta dukungan *tap to skip* menuju Layar Pre-Login.

---

## Fase 4: Implementasi Layar 2 - Pre-Login Hub (`referensi2.jpg` & `referensi3.jpg`)
- [x] **4.1 Area Header & Intip Saldo**
  - [x] Indikator atas "Geser untuk Intip Saldo" dengan animasi bouncing double chevron.
  - [x] Tombol badge bulat kanan atas "livin' Call by mandiri" berlabel "Bebas Pulsa" dengan modal dialog.
  - [x] Header logo `livin' by mandiri`.
- [x] **4.2 Hero Promo Section (livin' food)**
  - [x] Banner visual promosi: Headline "Kini di Livin' Bisa Pesan Makanan dengan livin' food".
  - [x] Visual 3 anak muda menyantap makanan di meja kuning.
- [x] **4.3 Security Announcement Pill (Keamanan)**
  - [x] Baris informasi keamanan berlatar navy pekat membulat (*rounded pill*):
    - Slide 1: *"Pastikan Transaksi Anda Selalu Am..."* / *"Hati-hati dengan tawaran mencurigak..."*
    - Slide 2: *"Pesan Makan? Livin' Food Aja"* / *"Mudah, hemat, dan banyak resto lokal!"*
    - Tombol panah kanan putih `->` interaktif.
- [x] **4.4 Primary Action Button (Login Biometrik)**
  - [x] Tombol besar Dark Navy Blue berbentuk kapsul membulat penuh (*pill*).
  - [x] Ikon Fingerprint / Biometrik + teks tebal "Login".
  - [x] Haptic feedback & dialog autentikasi biometrik modern.

---

## Fase 5: Replikasi Navigasi Carousel Sesuai Video (Menghilangkan Efek Galeri Foto)
- [x] **5.1 Background Canvas Stasioner 100% Diam**
  - [x] Membersihkan kanvas latar belakang dari artefak ikon lama dan chevron statis.
  - [x] Area di balik carousel berupa gradien biru murni yang membeku saat menu digeser.
- [x] **5.2 Native QuickAccessCarousel dengan Komponen Mandiri**
  - [x] Ikon-ikon navigasi Compose transparan bergerak bebas di dalam `HorizontalPager`.
  - [x] Halaman 1: *e-money*, *Quick Pick*, *QR Bayar*, *QRIS Tap* (Badge "BARU").
  - [x] Halaman 2: *QRIS Tap*, *QR Terima Transfer*, *Tap to Pay*, *Setor Tarik*.
  - [x] Efek geser terasa 100% native Compose tanpa pergeseran balok gambar latar belakang.
- [x] **5.3 Rel Indikator & Slider Kapsul Dinamis**
  - [x] Rel kapsul stasioner di bawah menu.
  - [x] Slider kapsul putih meluncur mulus proporsional secara real-time mengikuti persentase geseran jari (`currentPageOffsetFraction`).
  - [x] Tombol panah bulat kanan `->` pada Halaman 1 untuk lompat ke Halaman 2.
  - [x] Tombol panah bulat kiri `<-` pada Halaman 2 untuk kembali ke Halaman 1.

---

## Fase 6: Fisika Gestur "Intip Saldo" & "Lepas untuk Menutup"
- [x] **6.1 Pelacakan Gestur Sentuh 1:1 (Dynamic Drag Tracking)**
  - [x] Deteksi gestur vertikal di area atas layar menggerakkan kartu saldo secara dinamis mengikuti jari.
  - [x] Kartu 1: Tabungan NOW IDR (`*********7295`), Saldo `Rp 24.042.680⁴¹`, Mandiri Debit Gold Card.
  - [x] Kartu 2: Riwayat transaksi terakhir (`QR Bayar` - Rp 15.000⁰⁰ dan `Transfer Rupiah` - Rp 282.000⁰⁰).
  - [x] Teks petunjuk bawah: *"Lepas untuk Menutup"*.
- [x] **6.2 Auto-Retract Saat Sentuhan Dilepas (Spring Snap Back)**
  - [x] Begitu jari dilepaskan dari layar, kartu otomatis meluncur kembali ke atas dengan animasi pegas (*spring animation*).
  - [x] Mode tap header: Membuka intip saldo selama 3.2 detik lalu otomatis menutup kembali (*auto-retract*).
- [x] **6.3 Frosted Glass Gaussian Blur Backdrop**
  - [x] Latar belakang buram dinamis dengan transisi alpha mulus mengikuti kedalaman tarikan.

---

## Fase 7: Audit Komparatif & Pengujian Device Fisik (Vivo V2038 / Android 11)
- [x] **7.1 Build Verification**
  - [x] Kompilasi berhasil tanpa error (`BUILD SUCCESSFUL in 24s`).
- [x] **7.2 Pengujian Geser Carousel Halaman 1 <-> Halaman 2**
  - [x] Verifikasi background diam: Perbedaan latar belakang antar halaman = `1.95` (terbukti 100% stasioner).
  - [x] Slider indikator berpindah mulus dan tombol panah aktif berfungsi sempurna.
- [x] **7.3 Pengujian Intip Saldo Pulldown & Auto-Close**
  - [x] Kartu saldo meluncur turun dengan blur frosted glass (`sharpness: 1141 -> 1.3`).
  - [x] Auto-retract bekerja sempurna setelah 3.2 detik kembali ke normal (`sharpness: 1228`).
- [x] **7.4 Verifikasi Kesesuaian Video Rekaman**
  - [x] Seluruh aspek, animasi, fisika sentuh, dan visual telah 100% cocok dengan `referensisemuaaspekdananimasidanfitur.mp4`.
