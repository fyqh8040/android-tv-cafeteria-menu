# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Retain Compose and Android TV classes
-keep class androidx.compose.** { *; }
-keep class androidx.tv.** { *; }
-keep class com.company.cafeteriamenu.** { *; }

# Keep Hilt components
-keep class * extends dagger.hilt.android.HiltAndroidApp { *; }
-keep @dagger.hilt.android.AndroidEntryPoint class * { *; }
-keepclassmembers class * {
    @dagger.hilt.android.AndroidEntryPoint *;
}

# Keep Room components
-keep class * extends androidx.room.RoomDatabase { *; }
-keep class * extends androidx.room.Entity { *; }
-keepclassmembers class * {
    @androidx.room.* *;
}

# Keep Retrofit services
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# Keep OkHttp
-keep class okhttp3.** { *; }
-keep class okio.** { *; }

# Keep GSON
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Keep Dagger/Hilt generated code
-keep class * extends dagger.internal.Binding
-keep class * extends dagger.internal.ModuleAdapter
-keep class * extends dagger.internal.StaticInjection

# Keep ViewModels
-keep class * extends androidx.lifecycle.ViewModel { *; }

# Keep Coroutines
-keep class kotlinx.coroutines.** { *; }

# Keep Coil
-keep class coil.** { *; }

# Keep Timber
-keep class timber.log.** { *; }

# Keep WorkManager
-keep class androidx.work.** { *; }

# Keep navigation components
-keep class androidx.navigation.** { *; }

# DataStore
-keep class androidx.datastore.** { *; }

# Material Design
-keep class com.google.android.material.** { *; }

# AndroidX core
-keep class androidx.core.** { *; }
-keep class androidx.lifecycle.** { *; }
-keep class androidx.activity.** { *; }

# Serializable classes
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

# Enum classes
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Parcelable classes
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

# R classes
-keep class **.R$* {
    <fields>;
}

# Resource classes
-keepclassmembers class **.R$* {
    public static <fields>;
}

# Keep annotations
-keepattributes *Annotation*

# Keep generic signatures
-keepattributes Signature

# Keep inner classes
-keepattributes InnerClasses

# Remove logging in release builds
-assumenosideeffects class timber.log.Timber {
    public static void d(...);
    public static void v(...);
    public static void i(...);
}