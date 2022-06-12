### 该Demo具有比较完整的图片阅读器的功能。

#### 默认入口是ViewPagerActivity，你可以在Manifest中配置该Activity为LAUNCHER模式，启动该Activity。


<br></br>
#### 启动后即使一个完整的图片阅读器Demo，支持功能如下：
          
1、ViewPager 支持加载多张图片，实现左右滑动功能。
          
2、PhotoView 支持图片基本手势（放大、缩小，上下滑动退出）。
          
3、支持加载普通图片、GIF图、超长图等。
          
4、支持功能超长图分块加载(即将支持)。


<br></br>
#### 加载多张高清图片时，内存消耗过大，容易引起OOM，所以需要做内存优化：

1、优化前消耗java内存160M。

2、优化后消耗java内存为90M。

<br></br>
#### 优化内容：
优化前为每张图片均创建一个ImageView，假如加载一百张图片的时候，会生成一百张ImageView，无疑造成了资源浪费。
优化后只会创建4个ImageView，然后进行ImageView重用，大大减少了内存消耗。

<br></br>
#### 备注：
1、大长图加载时 高度(或宽度)超过GPU texture时，会报"Bitmap too large to be uploaded into a texture"异常，图片黑屏无法显示。
   这种情况，在【低端手机】如锤子7.1系统上才有；测试的10.0 9.0系统手机不存在该问题，猜测可能是高版本系统已将此问题修复。
   
2、开启硬件加速时，View.isHardwareAccelerated在某些情况下打印值为false。可通过延迟打印正确值。此情况不影响正常渲染效果。

<br></br>
#### 新增画布Canvas示例【CanvasView / CanvasView2中】
参考-[Android 画布Canvas](http://wuxiaolong.me/2016/08/27/Canvas/)
参考-[Android关于Canvas你所知道的和不知道的一切](https://juejin.cn/post/6844903705930629128)

<br></br>
#### 获取缩略图--图片/视频【ThumbnailActivity】
关注原生ThumbnailUtils类

<br></br>
#### 如果需要查看图片压缩相关的知识点，请查阅BitmapUtil工具类，这里做了详细的说明。

<br></br>
#### 可参考：https://github.com/davemorrissey/subsampling-scale-image-view

<br></br>
<br></br>
####### =================================================================================
## 以下是关于ViewPager的探索：
### ViewPager使用可直接参考--ViewPagerActivity：
#### 自定义高度自适应ViewPager【FlexibleViewPager】
#### 自定义固定最大高度ViewPager【MaxHeightViewPager】

### ViewPager2使用可直接参考--ViewPager2Activity【TODO】：
