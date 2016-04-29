# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\tools\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
# dump.txt 描述apk文件中所有类文件间的内部结构。
# mapping.txt 列出了原始的类，方法，和字段名与混淆后代码之间的映射。
# seeds.txt 列出了未被混淆的类和成员
# usage.txt 列出了从apk中删除的代码

-optimizationpasses 5   #设置混淆的压缩比率 0 ~ 7
-dontusemixedcaseclassnames #包明不混合大小写
-dontskipnonpubliclibraryclasses #如果应用程序引入的有jar包,并且想混淆jar包里面的class
-dontoptimize #优化  不优化输入的类文件
-dontpreverify  #预校验
-verbose  #混淆后生产映射文件 map 类名->转化后类名的映射
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*   #混淆采用的算法.

#忽略警告
#-ignorewarning

#-keepattributes Signature   #保持签名
-keepattributes SourceFile,LineNumberTable #保持源文件和行号的信息,用于混淆后定位错误位置

#-printmapping build/outputs/mapping.txt

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class com.android.vending.licensing.ILicensingService

-keep public class android.** { public protected private *; }

#-libraryjars lib/commons-logging-1.1.jar  #保持三方引用库
#-dontwarn com.zsx.**  #忽略警告

#保持 Jni 方法
-keepclasseswithmembernames class * {
    native <methods>;
}

#View 构造方法不混淆
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

#View 构造方法不混淆
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

#枚举类不去混淆.
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#Parcelable 不去混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}

#保证gson 对应文件不被混淆
#-keepclasseswithmembernames class com.weishang.qwapp.entity.** {*;}
#保证gson 对应文件的匿名内部类不被混淆
#-keepnames class com.weishang.qwapp.entity.**$* {
#    public <fields>;
#}

#如果有WebView 相应JS 事件
-keepclassmembers class com.zsx.tools.Lib_WebViewHelper {
   public *;
}
#保护注解
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*
#-------------------------------------------------Base End------------------------------------------------------------#

#保持 三方库 NineOldAndorid.jar
-keep class com.nineoldandroids.**{*;}

