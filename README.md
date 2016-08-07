一直想自己做一个基于 Material Design 的 APP，但一直不知道从哪入手，直到看到了
简阅（https://github.com/SkillCollege/SimplifyReader），感觉这个就是我想要做的，
然后就开始了我的抄袭之旅。一开始想仿照简阅一步做完，但是做着做着就发现就算是抄也没那么简单，
最后决定还是按自己方式来改，结果就做出了这个四不像。。。

项目只完成了一半，有些模块还在开发中，利用业余时间慢慢做，整个做完预计还要半年左右，反正自己的项目
没人催，高兴怎么做就怎么做( ^_^ )

由于使用 Vitamio 4.x 会导致视频卡顿，所以改为使用没有开源的 Vitamio 5.0 。又因为 Vitamio 5.0
没有的 SO 包没有64位的，而 gifdrawable 却包含所有平台的 SO ，这导致了在64位的手机上无法正常运行，
所以只能修改 gifdrawable 只生成和 Vitamio 同样平台的 SO 库。

gifdrawable 修改方式：
    将 src\main\jni\Application.mk 中的 APP_ABI := all 改为 APP_ABI := armeabi-v7a  x86  mips 。

由于 gifdrawable 和 Vitamio 我都做了一点点的修改，所以只能以 library 工程的形式存放在我的项目中，
但不知道为什么不能和项目一起上传，如果有需要还请自己去添加（添加原版的就行）。

项目中使用到的一些开源项目：

https://github.com/chrisbanes/PhotoView
https://github.com/wyouflf/xUtils3
https://github.com/nostra13/Android-Universal-Image-Loader
https://github.com/SkillCollege/SimplifyReader
https://github.com/castorflex/SmoothProgressBar
https://github.com/google/gson
https://github.com/yixia/VitamioBundle
https://github.com/koral--/android-gif-drawable

