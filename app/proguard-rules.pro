# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\AndroidStudio\SDK/tools/proguard/proguard-android.txt
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

#指定代码的压缩级别
-optimizationpasses 5

#包明不混合大小写
-dontusemixedcaseclassnames

#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses

 #优化  不优化输入的类文件
-dontoptimize

 #预校验
-dontpreverify

 #混淆时是否记录日志
-verbose

 # 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#保护注解
-keepattributes *Annotation*

# 保持哪些类不被混淆
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

#保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

#保持自定义控件类不被混淆
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

#保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

#保持 Serializable 不被混淆
-keepnames class * implements java.io.Serializable

#保持 Serializable 不被混淆并且enum 类也不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# 保持枚举 enum 类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}


#如果有引用v4包可以添加下面这行
-keep public class * extends android.support.v4.app.Fragment

#如果引用了v4或者v7包
-dontwarn android.support.**
-keep class com.android.support.**{ *; }

-keep class com.nostra13.universalimageloader.** { *; }
-keep class com.android.support.** { *; }
-keep class org.xutils** {*;}
-keep class com.google.code.gson.**{*;}

-keep class com.github.chrisbanes.photoview.** { *; }
-keep class com.github.castorflex.smoothprogressbar.** { *; }
-keep class vi.com.gdi.bgl.android.**{*;}
-keep class com.android.volley.**{*;}
-keep class com.github.simplelife.tools.**{*;}
-keep class com.github.simplelife.bean.**{*;}
-keep class com.github.simplelife.base.**{*;}
#-keep class class io.vov.vitamio.**{ *; }
#-keep class pl.droidsonroids.gif.**{*;}

# 保留所有具有公有访问权限的类和其公共成员
-keepclasseswithmembers class *{
    public *;
}

#gson
#-libraryjars libs/gson-2.2.2.jar
-keepattributes Signature
# Gson specific classes
-keep class sun.misc.Unsafe { *; }
# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }

## webview + js
#-keepattributes *JavascriptInterface*
## keep 使用 webview 的类
#-keepclassmembers class  com.veidy.activity.WebViewActivity {
#   public *;
#}
## keep 使用 webview 的类的所有的内部类
#-keepclassmembers  class  com.veidy.activity.WebViewActivity$*{
#    *;
#}
