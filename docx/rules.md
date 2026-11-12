# Rules & Pedoman Teknis Pengembangan (Livin' by Mandiri Android)

Dokumen ini memuat standar teknis, konvensi penamaan, arsitektur, dan batasan ketat dalam rekayasa aplikasi **Livin' by Mandiri Android Native** berbasis **Kotlin + Jetpack Compose**.

---

## 1. Aturan Mutlak Ruang Lingkup (Strict Scope Rules)

> [!CAUTION]
> **ATURAN #1: HANYA KERJAKAN YANG ADA DI GAMBAR REFERENSI**
> 1. Dilarang keras menambahkan fitur, alur navigasi, atau layar fiktif di luar apa yang ditampilkan secara visual pada folder `image/` (`icon.jpg`, `refferensi.jpg`, `referensi2.jpg`, `referensi3.jpg`).
> 2. Seluruh elemen visual (logo, warna gradien, tata letak, banner promo hero, kartu peringatan keamanan, carousel shortcut 2 halaman, dan tombol login biometrik) wajib 100% identik dengan gambar referensi.
> 3. Jangan membuat koneksi API backend nyata atau database transaksi di luar mock presenter yang diperlukan untuk menjalankan visual dan interaktivitas carousel.

---

## 2. Tech Stack & Library Standards

| Komponen | Spesifikasi / Library | Catatan |
|---|---|---|
| **Bahasa Pemrograman** | Kotlin 1.9+ / 2.0+ | Modern Kotlin, null-safety ketat |
| **UI Toolkit** | Jetpack Compose (BOM 2024.05.00+) | Deklaratif, reactive state-driven |
| **Design System** | Material 3 + Custom Mandiri Theme Tokens | Token warna Mandiri Blue, Deep Navy, Golden Yellow |
| **Minimum SDK** | `minSdk = 24` (Android 7.0+) | Kompatibel dengan 99%+ perangkat |
| **Target & Compile SDK** | `compileSdk = 34`, `targetSdk = 34` | Standar Android 14 (Upside Down Cake) |
| **Java Version** | OpenJDK 17 LTS | Sesuai environment sistem lokal |
| **Biometric Auth** | `androidx.biometric:biometric-ktx:1.2.0-alpha05` | Sensor biometrik bawaan hardware |
| **Image Loading** | Jetpack Compose Foundation / Painter Resources | Zero external heavy overhead |
| **Insets / Layout** | Accompanist / Core WindowInsets | Dukungan Edge-to-Edge Android 14+ |

---

## 3. Arsitektur & Prinsip Kode

### 3.1 Pola MVVM / MVI dengan Jetpack Compose
1. **Composable Screen (Presentation Layer):**
   - Composable murni bertindak sebagai rendering visual dari state dan pengirim event user (*State Down, Events Up*).
   - Wajib menerapkan **State Hoisting**: Pisahkan stateful screen (`*Route` / `*ScreenContainer`) dari stateless presentation (`*Content`).
   - Setiap screen wajib memiliki anotasi `@Preview` dengan variasi device (smartphone standar 390x844dp, 360x800dp).

2. **ViewModel Layer:**
   - Menyimpan seluruh state UI dalam objek `StateFlow<PreLoginUiState>` yang immutable.
   - Mengelola pergantian halaman carousel (Halaman 1 <-> Halaman 2), gestur drag intip saldo, dan trigger biometrik login.
   - Dilarang memegang referensi ke `Context` atau `Activity`.

3. **Domain & Data Layer:**
   - Menyediakan mock data terstruktur untuk carousel item dan status login tanpa ketergantungan jaringan eksternal.

---

## 4. Konvensi Penamaan (Naming Conventions)

### 4.1 Kode Kotlin & Compose
| Tipe | Format Penamaan | Contoh |
|---|---|---|
| **Composable Function** | PascalCase (Kata Benda/Frasa) | `LivinSplashScreen()`, `PreLoginScreen()`, `QuickAccessCarousel()` |
| **Screen Route / Container** | `<Feature>Screen` / `<Feature>Route` | `LivinSplashRoute`, `PreLoginRoute` |
| **ViewModel** | `<Feature>ViewModel` | `PreLoginViewModel` |
| **UI State (Data Class)** | `<Feature>UiState` | `PreLoginUiState` |
| **UI Event / Intent (Sealed)** | `<Feature>UiEvent` | `PreLoginUiEvent.OnNextCarouselClicked` |
| **Constants** | UPPER_SNAKE_CASE | `CAROUSEL_PAGE_COUNT`, `SPLASH_DURATION_MS` |

### 4.2 Resources (Drawables, Colors, Strings)
Semua aset Android wajib menggunakan format `lowercase_snake_case`:
- **Vector / Icon Drawables:** `ic_livin_<nama>.xml` (contoh: `ic_livin_logo.png`, `ic_livin_call.png`, `ic_fingerprint_livin.xml`).
- **Bitmap / Backgrounds:** `bg_livin_<deskripsi>.png` (contoh: `bg_livin_splash.png`, `banner_livin_food.png`).
- **Color Tokens:** `livin_<tujuan>_<shade>` (contoh: `livin_blue_top`, `livin_navy_deep`, `livin_gold_primary`).
- **String Resources:** `<feature>_<deskripsi>` (contoh: `prelogin_peek_balance`, `prelogin_login_button`).

---

## 5. Standar UI/UX Livin' Mandiri (High-Fidelity)

1. **Akurasi Visual Presisi:**
   - Background utama wajib mereplikasi gradien vertikal biru khas Livin' Mandiri: `#1A74E2` -> `#1464CA` -> `#0B56BE`.
   - Warna tombol Login wajib menggunakan biru dongker pekat (`#0B1B3D`) dengan radius membulat penuh (*pill shape*).
2. **Spacing & Padding:**
   - Wajib kelipatan 4dp / 8dp (`4.dp`, `8.dp`, `12.dp`, `16.dp`, `20.dp`, `24.dp`, `28.dp`).
   - Ukuran teks wajib menggunakan `sp`.
3. **Sentuhan & Feedback (Touch Feedback):**
   - Setiap elemen interaktif (tombol Login, tombol panah carousel, icon shortcut, Livin' Call badge) wajib memiliki ripple effect halus dan haptic feedback standar Android.
4. **Edge-to-Edge Display:**
   - Gunakan `Modifier.navigationBarsPadding()` dan `Modifier.statusBarsPadding()` agar tidak ada konten yang tertutup notch/kamera depan maupun tombol navigasi sistem.
