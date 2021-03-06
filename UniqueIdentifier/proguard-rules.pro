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

-keep class com.asus.msa.** {*;}
-keep class com.bun.** {*;}
-keep class com.htytap.openid.** {*;}
-keep class com.huawei.android.hms.pps.** {*;}
-keep class com.meizu.flyme.openidsdk.** {*;}
-keep class com.samsung.android.devicedservice.** {*;}
-keep class com.zui.** {*;}

-keep class com.pillowcase.identifier.UniqueIdentifierUtils{
    public *;
}
-keep class com.pillowcase.identifier.UniqueIdentifierApplication{
    public *;
}
-keep class com.pillowcase.identifier.impl.ISupportListener{
    public *;
}
-keep class com.pillowcase.identifier.models.ResultParams{
    public *;
}
-keep class com.pillowcase.identifier.utils.SystemVersionUtils{
    public *;
}