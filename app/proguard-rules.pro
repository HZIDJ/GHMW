# ProGuard configuration for GHMW - Issues #1-9 optimization
# Preserves critical classes while allowing optimization

# Keep all game classes
-keep class com.mediatek.game.** { *; }

# Keep notification related classes - Issue #1, #4, #8
-keep class com.mediatek.game.services.NotificationService { *; }
-keep class com.mediatek.game.notifications.** { *; }

# Keep performance optimization classes - Issue #5, #7
-keep class com.mediatek.game.optimization.** { *; }
-keep class com.mediatek.game.services.PerformanceMonitorService { *; }

# Keep battery optimization classes - Issue #2, #9
-keep class com.mediatek.game.services.BatteryOptimizationService { *; }

# Keep network optimization - Issue #5, #7, #9
-keep class com.mediatek.game.networking.** { *; }

# Keep version management - Issue #1
-keep class com.mediatek.game.version.** { *; }

# Keep launcher security - Issue #5, #6, #7
-keep class com.mediatek.game.launcher.** { *; }

# Keep receiver classes - Issue #2, #9
-keep class com.mediatek.game.receivers.** { *; }

# Android framework classes
-keep class android.** { *; }
-keep interface android.** { *; }

# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

# Keep enum values
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep AndroidX classes
-keep class androidx.** { *; }
-keep interface androidx.** { *; }

# Optimization settings for better performance - Issue #2, #7 (Battery/Thermal)
-optimizationpasses 5
-dontusemixedcaseclassnames
-verbose

# Remove logging in release builds
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}

# Preserve line numbers for debugging
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Keep Exception classes
-keep class * extends java.lang.Exception { <init>(...); }

# Keep Serializable classes
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
