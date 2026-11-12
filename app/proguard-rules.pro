# Jetpack Compose Rules
-keepattributes *Annotation*
-dontwarn androidx.compose.**

# Keep Data Models & UI States
-keep class com.mandiri.livin.ui.** { *; }
-keep class com.mandiri.livin.data.** { *; }

# Security & Biometrics
-keep class androidx.biometric.** { *; }
