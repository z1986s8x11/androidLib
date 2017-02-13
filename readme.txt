需要的权限
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.INTERNET" />

android studio 下载
https://dl.google.com/dl/android/studio/install/1.2.2.0/android-studio-bundle-141.1980579-windows.exe

html工具 :http://www.mobl-lang.org/get/
chrome://inspect/#devices

DEMO : https://github.com/Trinea/android-open-project
https://github.com/wasabeef/awesome-android-ui

http://www.jcodecraeer.com/plus/list.php?tid=31&TotalResult=139&PageNo=1

github
http://www.cnblogs.com/yc-755909659/p/3753626.html

android:windowSoftInputMode="adjustPan|stateHidden"


中文API 文档 :http://wiki.eoeandroid.com/

genymotion Android模拟器


/** 获取手机通过 2G/3G 接收的字节流量总数 */
list.add(TrafficStats.getMobileRxBytes());
/** 获取手机通过 2G/3G 接收的数据包总数 */
list.add(TrafficStats.getMobileRxPackets());
/** 获取手机通过 2G/3G 发出的字节流量总数 */
list.add(TrafficStats.getMobileTxBytes());
/** 获取手机通过 2G/3G 发出的数据包总数 */
list.add(TrafficStats.getMobileTxPackets());
/** 获取手机通过所有网络方式接收的字节流量总数(包括 wifi) */
list.add(TrafficStats.getTotalRxBytes());
/** 获取手机通过所有网络方式接收的数据包总数(包括 wifi) */
list.add(TrafficStats.getTotalRxPackets());
/** 获取手机通过所有网络方式发送的字节流量总数(包括 wifi) */
list.add(TrafficStats.getTotalTxBytes());
/** 获取手机通过所有网络方式发送的数据包总数(包括 wifi) */
list.add(TrafficStats.getTotalTxPackets());
/** 获取手机指定 UID 对应的应程序用通过所有网络方式接收的字节流量总数(包括 wifi) */
// list.add(TrafficStats.getUidRxBytes(uid);
/** 获取手机指定 UID 对应的应用程序通过所有网络方式发送的字节流量总数(包括 wifi) */
// list.add(TrafficStats.getUidTxBytes(uid);



php相关：

/*获取实体数据  如json*/
$GLOBALS['HTTP_RAW_POST_DATA']
/*获取实体数据  如json*/
file_get_contents("php://input");

/**true 将结果转换成数组*/
json_decode($param,true);


  1. 安卓巴士      http://www.apkbus.com
  2. CSDN           http://www.csdn.net/   最多关注里面的微博和论坛
  3. 开源中国      http://www.oschina.net/
  4. 程序员联合开发网   http://www.pudn.com/
  5. Web开发者    http://www.admin10000.com/
  6. Linux公社      http://www.linuxidc.com/
  7. Code4App      http://d.apkbus.com/   http://a.code4app.com/
  8. Android开发者 http://developer.android.com/
  9. 黄蜂网           http://woofeng.cn/works/Handheld/ 设计相关
  10. Android社区  http://www.eoeandroid.com/portal.php
  11. 代码托管      http://git.oschina.net/
  12. 博客园         http://www.cnblogs.com/
  13. github          https://github.com/
  14. UI素材         http://sc.chinaz.com/ http://www.ui4app.com/
  15. O'Reilly 北京 http://www.oreilly.com.cn/index.php
  16. JavaApk       http://www.javaapk.com/
  17. 神马Code     http://www.godcoder.com/
  18. Java6           http://www.oracle.com/technetwork/java/javasebusiness/downloads/java-archive-downloads-javase6-419409.html
  19. Eclipse         http://www.eclipse.org/downloads/
  20. ADT             http://dl-ssl.google.com/android/ecilpse/
  21. SVN1.8        http://subclipse.tigris.org/update_1.8.x


  SDK 代理:
  设置:
  Android SDK Manager --> Tools -->Options

 Http Proxy Server : ubuntu.buct.edu.cn
 Http Proxy Port : 80
 Others
 √ Use download cache
 √ Force Http://....

Android SDK在线更新镜像服务器
中国科学院开源协会镜像站地址:
IPV4/IPV6: http://mirrors.opencas.cn 端口：80
IPV4/IPV6: http://mirrors.opencas.org 端口：80
IPV4/IPV6: http://mirrors.opencas.ac.cn 端口：80

上海GDG镜像服务器地址:
http://sdk.gdgshanghai.com 端口：8000

北京化工大学镜像服务器地址:
IPv4: http://ubuntu.buct.edu.cn/ 端口：80


most_12@sina.com(396354013)=======


牛人博客
http://blog.csdn.net/huang_xw/article/details/7957908>>>>>>> .r645


disable.android.first.run=true

git教程
http://pcottle.github.io/learnGitBranching/

最新翻译博客
http://www.devtf.cn/?p=730

1. 如果已经启动了四个Activity：A，B，C和D。在D Activity里，我们要跳到B Activity，同时希望C finish掉，可以在startActivity(intent)里的intent里添加flags标记，如下所示：
Java代码
1.Intent intent = new Intent(this, B.class);
2.intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
3.startActivity(intent);
  这样启动B Activity，就会把D，C都finished掉，如果你的B Activity的启动模式是默认的（multiple） ，则B Activity会finished掉，再启动一个新的Activity B。
  如果不想重新再创建一个新的B Activity，则在上面的代码里再加上：


Java代码
1.intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
这样B Activity就会再创建一个新的了，而是会重用之前的B Activity，同时调用B Activity的onNewIntent()方法。

2. 如果已经启动了四个Activity：A，B，C和D，在D Activity里，想再启动一个Actvity B，但不变成A,B,C,D,B，而是希望是A,C,D,B，则可以像下面写代码：
Java代码
1.Intent intent = new Intent(this, MainActivity.class);
2.intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
3.startActivity(intent);

视频播放源码:https://github.com/lipangit/jiecaovideoplayer
https://github.com/tigerguixh/QuickNews
https://github.com/yixia/VitamioBundle
https://github.com/kimhou/Android/tree/master/VideoDemoPro
https://github.com/tcking/GiraffePlayer
https://github.com/Bilibili/ijkplayer
https://github.com/wlsh/JieCaoVideoPlayer/

博客
http://blog.csdn.net/lmj623565791/article/list/1

UI卡顿工具  :BlockCanary
内存泄漏工具 :LeakCanary

chrome 模拟请求工具  :http://chromecj.com/web-development/2014-09/60/download.html

ViewPager 无限滚动 :https://github.com/saiwu-bigkoo/Android-ConvenientBanner.git
开源集合:https://github.com/snowdream/awesome-android

RecyclerView 滑动删除 拖动效果 https://github.com/HoneyNeutrons/RecyclerViewUndoSwipe
google Demo: https://github.com/googlesamples
上传jcenter http://blog.csdn.net/ls1110924/article/details/46470059
gradle文档:https://docs.gradle.org/current/userguide/userguide
判断前台进程的6种方法:https://github.com/wenmingvs/AndroidProcess
悬浮球相关:https://github.com/fashare2015/ActivityTracker