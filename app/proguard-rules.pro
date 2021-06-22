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

#-ignorewarnings

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile


-keep class com.google.** { *; }
-dontwarn com.google.**

-keep class com.crashlytics.sdk.android.** {*;}
-dontwarn com.crashlytics.sdk.android.**

-keep class com.squareup.retrofit2.** {*;}
-dontwarn com.squareup.retrofit2.**

-keep class retrofit2.** {*;}
-dontwarn retrofit2.**

-keep class com.devsh.suhanlee.** {*;}
-dontwarn com.devsh.suhanlee.**

-keep class in.dmart.apilibrary.model.** {*;}
-dontwarn in.dmart.apilibrary.model.**

-keep class in.dmart.enterprise.dad.remote_config.** {*;}
-dontwarn in.dmart.enterprise.dad.remote_config.**

-keep class androidmads.library.qrgenearator.** {*;}
-dontwarn androidmads.library.qrgenearator.**

-keep class honeywell.** {*;}
-dontwarn honeywell.**


-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}
