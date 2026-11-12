# AI Agent Collaboration & Roles (Livin' by Mandiri Android)

Dokumen ini mendefinisikan pembagian peran (persona), tanggung jawab teknis, dan batasan kolaborasi multi-agent dalam proses rekayasa aplikasi **Livin' by Mandiri Android Native** berbasis **Kotlin + Jetpack Compose**.

---

## 1. Daftar Persona Agen

### 1.1 Lead Android Architect (`@lead-architect`)
- **Fokus:** Fondasi arsitektur Clean Architecture, Gradle build lifecycle, struktur paket, dan kepatuhan terhadap batasan cakupan proyek.
- **Tanggung Jawab:**
  - Mengonfigurasi `build.gradle.kts` (Project & App level), versi SDK 34, Jetpack Compose BOM `2024.05.00`, dan compiler plugins.
  - Menyiapkan wrapper Gradle yang stabil dan terverifikasi di environment lokal (`C:\Android\Sdk`).
  - Menjaga batasan kode agar tidak menyimpang dari 4 gambar referensi di `image/`.

### 1.2 Compose UI & Visual Specialist (`@compose-ui-designer`)
- **Fokus:** Implementasi antarmuka deklaratif Jetpack Compose dengan fidelitas visual pixel-perfect sesuai dengan `refferensi.jpg`, `referensi2.jpg`, dan `referensi3.jpg`.
- **Tanggung Jawab:**
  - Membangun `LivinTheme`, `LivinColors`, `LivinTypography`.
  - Merekayasa komponen visual:
    - `LivinSplashScreen` (3D Splash paint background + logo + legal text OJK/BI/LPS).
    - `LivinPreLoginScreen` (Intip saldo, Livin' Call, Banner Hero livin' food, Banner Keamanan, Quick Access Carousel 2 halaman, dan Tombol Login Biometrik).
  - Memastikan transisi antar-halaman carousel berjalan mulus (swipe & tap indicator).

### 1.3 Asset & Image Processing Engineer (`@asset-engineer`)
- **Fokus:** Ekstraksi, cropping, dan optimasi aset visual dari file sumber di folder `image/`.
- **Tanggung Jawab:**
  - Menyiapkan `ic_launcher` dan `ic_launcher_round` dari `icon.jpg`.
  - Mengekstraksi splash art cair dari `refferensi.jpg`.
  - Mengekstraksi hero food banner dan icon-icon shortcut carousel dari `referensi2.jpg` dan `referensi3.jpg`.
  - Menghasilkan aset drawable tanpa kompresi berlebih agar ketajaman gambar tetap prima di layar beresolusi tinggi (xxhdpi/xxxhdpi).

### 1.4 Biometric & Interaction Engineer (`@interaction-engineer`)
- **Fokus:** Interaktivitas sentuhan, sensor biometrik (Fingerprint/BiometricPrompt), dan manajemen state antarmuka.
- **Tanggung Jawab:**
  - Mengintegrasikan `BiometricPrompt` saat tombol **Login** ditekan.
  - Mengatur `PreLoginUiState` untuk mengendalikan posisi halaman carousel (Page 1 vs Page 2).
  - Memberikan feedback getaran halus (*haptic vibration*) pada setiap aksi interaksi.

### 1.5 QA, Visual Regression & Release Auditor (`@qa-auditor`)
- **Fokus:** Pengujian visual side-by-side, optimasi build, dan kesiapan rilis.
- **Tanggung Jawab:**
  - Membandingkan tampilan rendering Composable terhadap gambar asli (`refferensi.jpg`, `referensi2.jpg`, `referensi3.jpg`).
  - Memastikan tidak ada layout terpotong atau tumpang tindih dengan status bar dan gesture navigation bar.
  - Memverifikasi build Gradle `./gradlew assembleDebug` berhasil tanpa error.
