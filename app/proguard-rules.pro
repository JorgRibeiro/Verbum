# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Keep data model classes (they may be used with serialization in the future)
-keep class com.verbum.app.data.model.** { *; }
-keep class com.verbum.app.data.repository.** { *; }

# Keep repository interfaces for future API integration
-keep interface com.verbum.app.data.repository.** { *; }

# Compose-related rules
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# Kotlin serialization (for future API integration)
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt

# Uncomment this to preserve the line number information for debugging stack traces.
# -keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to hide the original source file name.
# -renamesourcefileattribute SourceFile
