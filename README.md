
默认入口是ViewPagerActivity，你可以在Manifest中配置该Activity为LAUNCHER模式，启动该Activity。
启动后即使一个完整的图片阅读器Demo，支持功能如下：
          
1、ViewPager 支持加载多张图片，实现左右滑动功能。
          
2、PhotoView 支持图片基本手势（放大、缩小，上下滑动退出）。
          
3、支持加载普通图片、GIF图、超长图等。
          
4、支持功能超长图分块加载(即将支持)。


加载多张高清图片时，内存消耗过大，容易引起OOM，所以需要做内存优化：

1、优化前消耗java内存160M
2、优化后消耗java内存为90M

优化内容：
优化前为每张图片均创建一个ImageView，假如加载一百张图片的时候，会生成一百张ImageView，无疑造成了资源浪费。
优化后只会创建3个ImageView，然后进行ImageView重用，大大减少了内存消耗。
