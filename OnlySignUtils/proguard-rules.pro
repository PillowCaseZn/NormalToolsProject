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
-keep class com.bun.miitmdid.** {*;}
-keep class com.htytap.openid.** {*;}
-keep class com.huawei.android.hms.pps.** {*;}
-keep class com.meizu.flyme.openidsdk.** {*;}
-keep class com.samsung.android.devicedservice.** {*;}
-keep class com.zui.** {*;}

-keep class com.pillowcase.normal.tools.only.sign.OnlySignUtils{
    public *;
}
-keep class com.pillowcase.normal.tools.only.sign.OnlySignApplication{
    public *;
}
-keep class com.pillowcase.normal.tools.only.sign.impl.ISupportListener{
    public *;
}
-keep class com.pillowcase.normal.tools.only.sign.models.ResultParams{
    public *;
}
-keep class com.pillowcase.normal.tools.only.sign.utils.SystemVersionUtils{
    public *;
}