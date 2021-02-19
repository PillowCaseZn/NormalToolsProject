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
#包名不混合大小写
-dontusemixedcaseclassnames
#不跳过非公共的库的类
-dontskipnonpubliclibraryclasses
#混淆时记录日志
-verbose
#关闭预校验
-dontpreverify
#不优化输入的类文件
-dontoptimize
#保护注解
-keepattributes *Annotation*
#保持所有拥有本地方法的类名及本地方法名
-keepclasseswithmembernames class * {
    native <methods>;
}
#保持自定义View的get和set相关方法
-keepclassmembers public class * extends android.view.View {
    void set*(***);
    *** get*();
}
#保持Activity中View及其子类入参的方法
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}
#枚举
-keepclassmembers enum * {
    **[] $VALUES;
    public *;
}
#Parcelable
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator CREATOR;
}
#R文件的静态成员
-keepclassmembers class **.R$* {
    public static <fields>;
}
-dontwarn android.support.**

#自行处理
-dontwarn com.pillowcase.logger.**
-keep class com.pillowcase.logger.impl.ILoggerOperation{
    public *;
}
-keep class com.pillowcase.logger.LoggerUtils{
    public *;
}