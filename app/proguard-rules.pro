# ProGuard rules for HGID - Notification and Permission Support
# Comprehensive rules for Game Performance Monitoring with Notification Support

# ===== OPTIMIZE FOR PERFORMANCE MONITORING =====
-optimizationpasses 5
-allowaccessmodification
-mergeinterfacesaggressively

# ===== PRESERVE ESSENTIAL ATTRIBUTES =====
-keepattributes *
-keepattributes SourceFile,LineNumberTable
-keepattributes Exceptions
-keepattributes Signature
-keepattributes Deprecated
-keepattributes EnclosingMethod
-keepattributes InnerClasses
-keepattributes PermittedSubclasses
-keepattributes Record
-keepattributes RuntimeVisibleAnnotations
-keepattributes RuntimeVisibleParameterAnnotations
-keepattributes RuntimeInvisibleAnnotations
-keepattributes RuntimeInvisibleParameterAnnotations
-keepattributes Synthetic
-keepattributes Metadata
-renamesourcefileattribute SourceFile

# ===== VERBOSE DEBUGGING =====
-verbose
-printmapping mapping.txt
-printconfiguration configuration.txt

# ===== KEEP ALL ANDROID FRAMEWORK CLASSES =====
-keep class android.** { *; }
-keep interface android.** { *; }
-keep class android.app.Activity { *; }
-keep class android.app.Fragment { *; }
-keep class android.app.Service { *; }
-keep class android.content.BroadcastReceiver { *; }
-keep class android.content.ContentProvider { *; }
-keep class android.content.Context { *; }

# ===== KEEP NOTIFICATION CLASSES =====
-keep class android.app.Notification { *; }
-keep class android.app.Notification$* { *; }
-keep class android.app.NotificationManager { *; }
-keep class android.app.NotificationChannel { *; }
-keep class android.app.NotificationChannel$* { *; }
-keep class androidx.core.app.NotificationCompat { *; }
-keep class androidx.core.app.NotificationCompat$* { *; }
-keep class androidx.core.app.NotificationCompatBase { *; }
-keepclassmembers class androidx.core.app.NotificationCompat$Builder {
    public <init>(...);
    public androidx.core.app.NotificationCompat$Builder setContentTitle(...);
    public androidx.core.app.NotificationCompat$Builder setContentText(...);
    public androidx.core.app.NotificationCompat$Builder setSmallIcon(...);
    public androidx.core.app.NotificationCompat$Builder setAutoCancel(...);
    public androidx.core.app.NotificationCompat$Builder setContentIntent(...);
    public androidx.core.app.NotificationCompat$Builder build();
}

# ===== KEEP PERMISSION CLASSES =====
-keep class android.content.pm.PackageManager { *; }
-keep class android.content.pm.PermissionInfo { *; }
-keep class android.content.pm.ApplicationInfo { *; }
-keep class androidx.core.content.ContextCompat { *; }
-keep class androidx.core.content.ContextCompat$* { *; }
-keep class androidx.core.app.ActivityCompat { *; }
-keep class androidx.core.app.ActivityCompat$* { *; }
-keepclassmembers class androidx.core.app.ActivityCompat {
    public static int checkSelfPermission(android.app.Activity, java.lang.String);
    public static void requestPermissions(android.app.Activity, java.lang.String[], int);
}

# ===== KEEP APPLICATION CLASS =====
-keep public class * extends android.app.Application {
    public void onCreate();
}

# ===== KEEP ALL ACTIVITIES =====
-keep public class * extends android.app.Activity {
    public <init>(...);
    public void onCreate(android.os.Bundle);
    public void onStart();
    public void onResume();
    public void onPause();
    public void onStop();
    public void onDestroy();
    public void onRestart();
}

# ===== KEEP ALL SERVICES =====
-keep class * extends android.app.Service {
    public <init>();
    public void onCreate();
    public void onDestroy();
    public int onStartCommand(android.content.Intent, int, int);
    public android.os.IBinder onBind(android.content.Intent);
}

# ===== KEEP ALL BROADCAST RECEIVERS =====
-keep class * extends android.content.BroadcastReceiver {
    public <init>();
    public void onReceive(android.content.Context, android.content.Intent);
}

# ===== KEEP ALL FRAGMENTS =====
-keep public class * extends androidx.fragment.app.Fragment {
    public <init>();
    public void onCreate(android.os.Bundle);
    public void onViewCreated(android.view.View, android.os.Bundle);
}

# ===== KEEP ALL VIEW CLASSES =====
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public <init>(android.content.Context, android.util.AttributeSet, int, int);
    public void set*(...);
    public *** get*(...);
}

# ===== KEEP ALL GAME CLASSES =====
-keep class com.mediatek.game.** { *; }
-keep class com.mediatek.game.services.** { *; }
-keep class com.mediatek.game.receivers.** { *; }
-keep class com.mediatek.game.activities.** { *; }
-keep class com.mediatek.game.fragments.** { *; }
-keep class com.mediatek.game.utils.** { *; }
-keep class com.mediatek.game.models.** { *; }

# ===== KEEP MONITORING CLASSES =====
-keep class **.fps.** { *; }
-keep class **.performance.** { *; }
-keep class **.monitor.** { *; }
-keep class **.meter.** { *; }
-keep class **.network.** { *; }
-keep class **.thermal.** { *; }
-keep class **.temperature.** { *; }
-keep class **.battery.** { *; }

# ===== KEEP ANDROIDX CLASSES =====
-keep class androidx.** { *; }
-keep interface androidx.** { *; }
-keep class androidx.core.** { *; }
-keep class androidx.appcompat.** { *; }
-keep class androidx.lifecycle.** { *; }
-keep class androidx.work.** { *; }
-keep class androidx.graphics.** { *; }
-keepclassmembers class androidx.** {
    <init>(...);
    public <methods>;
    public static <fields>;
}

# ===== KEEP RESOURCE IDS =====
-keepclasseswithmembernames class **.R$* { *; }
-keep class **.R { *; }
-keep class **.R$* { *; }

# ===== KEEP GRAPHICS AND RENDERING =====
-keep class android.graphics.** { *; }
-keep class android.view.animation.** { *; }
-keep class android.animation.** { *; }
-keep class android.view.Choreographer { *; }
-keep class android.view.Display { *; }
-keep class android.view.Display$Mode { *; }
-keep class android.view.WindowManager { *; }
-keep class android.util.DisplayMetrics { *; }

# ===== KEEP NETWORK CLASSES =====
-keep class android.net.** { *; }
-keep class android.telephony.** { *; }
-keep class java.net.** { *; }
-keepclassmembers class android.net.TrafficStats {
    public static long getTotalRxBytes();
    public static long getTotalTxBytes();
    public static long getMobileRxBytes();
    public static long getMobileTxBytes();
    public static long getUidRxBytes(int);
    public static long getUidTxBytes(int);
}

# ===== KEEP BATTERY AND THERMAL =====
-keep class android.os.BatteryManager { *; }
-keep class android.os.** { *; }
-keep class android.hardware.** { *; }

# ===== KEEP SYSTEM PROPERTIES =====
-keepclassmembers class android.os.SystemProperties {
    public static java.lang.String get(java.lang.String);
    public static java.lang.String get(java.lang.String, java.lang.String);
    public static int getInt(java.lang.String, int);
    public static long getLong(java.lang.String, long);
    public static boolean getBoolean(java.lang.String, boolean);
}

# ===== KEEP HANDLER AND THREADING =====
-keep class android.os.Handler { *; }
-keep class android.os.Looper { *; }
-keep class android.os.Message { *; }
-keep class java.lang.Thread { *; }
-keep class java.lang.Runnable { *; }

# ===== KEEP PREFERENCES =====
-keep class android.content.SharedPreferences { *; }
-keep class android.content.SharedPreferences$Editor { *; }
-keep class androidx.preference.** { *; }

# ===== KEEP NATIVE METHODS =====
-keepclasseswithmembernames class * {
    native <methods>;
}

# ===== KEEP SERIALIZABLE CLASSES =====
-keep class * implements java.io.Serializable { *; }
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# ===== KEEP PARCELABLE CLASSES =====
-keep class * implements android.os.Parcelable { *; }
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# ===== KEEP INTERFACES =====
-keep interface * { *; }
-keepclassmembers interface * { *; }

# ===== KEEP ENUMS =====
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
    **[] $VALUES;
    public <init>(...);
}

# ===== KEEP ANNOTATIONS =====
-keep @interface * { *; }
-keepclassmembers @interface * { *; }

# ===== KEEP KOTLIN METADATA =====
-keep class kotlin.** { *; }
-keep class kotlinx.** { *; }
-keepclassmembers class ** {
    *** lambda*(...);
}

# ===== KEEP COMMON LIBRARIES =====
-keep class com.google.** { *; }
-keep class com.squareup.** { *; }
-keep class com.jakewharton.** { *; }
-keep class io.reactivex.** { *; }
-keep class rx.** { *; }

# ===== SUPPRESS WARNINGS =====
-dontwarn android.**
-dontwarn androidx.**
-dontwarn com.google.**
-dontwarn java.**
-dontwarn javax.**
-dontwarn kotlin.**
-dontwarn kotlinx.**
-dontwarn sun.**
-dontwarn com.sun.**

# ===== FINAL SAFETY RULES =====
# Ensure nothing gets removed that shouldn't be
-keep,allowoptimization class * {
    <init>(...);
    <fields>;
    <methods>;
}

-keepclassmembers,allowoptimization class * {
    *** *(...);
    public <methods>;
    public static <fields>;
}

# End of ProGuard configuration
