###Android中的四大组件和它们的作用###
- **Activity：**Activity是Android程序与用户交互的窗口，是Android构造块中最基本的一种，它需要为保持各界面的状态，做很多持久化的事情，妥善管理生命周期以及一些跳转逻辑。
- **Service：**后台服务于Activity，封装有一个完整的功能逻辑实现，接受上层指令，完成相关的事物，定义好需要接受的Intent提供同步和异步的接口。
- **Content Provider：**是Android提供的第三方应用数据的访问方案，可以派生Content Provider类，对外提供数据，可以像数据库一样进行选择排序，屏蔽内部数据的存储细节，向外提供统一的借口模型，大大简化上层应用，对数据的整合提供了更方便的途径。
- **BroadCast Receiver：**接受一种或者多种Intent作触发事件，接受相关消息，做一些简单处理，转换成一条Notification，统一了Android的事件广播模型。
###ContentProvider是如何实现数据共享的###
一个程序可以通过实现一个Content provider的抽象接口将自己的数据完全暴露出去，而且Content providers是以类似数据库中表的方式将数据暴露。Content providers存储和检索数据，通过它可以让所有的应用程序访问到，这也是应用程序之间唯一共享数据的方法。
要想使应用程序的数据公开化，可通过2种方法：创建一个属于你自己的Content provider或者将你的数据添加到一个已经存在的Content provider中，前提是有相同数据类型并且有写入Content provider的权限。  
如何通过一套标准及统一的接口获取其他应用程序暴露的数据？  
Android提供了ContentResolver，外界的程序可以通过ContentResolver接口访问ContentProvider提供的数据。
###Android中常用的五种布局###
- **FrameLayout(框架布局):**所有东西依次都放在左上角，会重叠，这个布局比较简单，只能放一点比较简单的东西。
- **LinearLayout(线性布局):**每一个LinearLayout里面又可分为垂直布局`android:orientation="vertical"`和水平布局`android:orientation="horizontal"` 。当垂直布局时，每一行就只有一个元素，多个元素依次垂直往下；水平布局时，只有一行，每一个元素依次向右排列。
- **AbsoluteLayout(绝对布局):**用X,Y坐标来指定元素的位置，这种布局方式也比较简单，但是在屏幕旋转时，往往会出问题，而且多个元素的时候，计算比较麻烦。
- **RelativeLayout(相对布局):**可以理解为某一个元素为参照物，来定位的布局方式。主要属性有：相对于某一个元素`android:layout_below`、`android:layout_toLeftOf`相对于父元素的地方`android:layout_alignParentLeft`、`android:layout_alignParentRigh`。
- **TableLayout(表格布局):**每一个TableLayout里面有表格行TableRow，TableRow里面可以具体定义每一个元素。每一个布局都有自己适合的方式，这五个布局元素可以相互嵌套应用，做出美观的界面。
###文字竖排绘制###
canvas.rotate旋转绘制文字  

    canvas = holder.lockCanvas();  
    Paint paint = new Paint();  
    paint.setColor(Color.WHITE);  
    paint.setTextSize(20);  
    canvas.drawLine(100, 100, 100, 400, paint);  
    drawText(canvas,"Hello", 80, 200, paint,-90); 
###View绘制的过程,draw/onDraw和drawChild###
- **流程一：mesarue()过程**  
主要作用：为整个View树计算实际的大小，即设置实际的高(对应属性:mMeasuredHeight)和宽(对应属性:mMeasureWidth)，每个View的控件的实际宽高都是由父视图和本身视图决定的。  
具体的调用链如下：ViewRoot根对象地属性mView(其类型一般为ViewGroup类型)调用measure()方法去计算View树的大小，回调View/ViewGroup对象的onMeasure()方法，该方法实现的功能如下：
1. 设置本View视图的最终大小，该功能的实现通过调用setMeasuredDimension()方法去设置实际的高(对应属性：mMeasuredHeight)和宽(对应属性：mMeasureWidth)；
2. 如果该View对象是个ViewGroup类型，需要重写该onMeasure()方法，对其子视图进行遍历的measure()过程。对每个子视图的measure()过程，是通过调用父类ViewGroup.java类里的measureChildWithMargins()方法去实现，该方法内部只是简单地调用了View对象的measure()方法。(由于measureChildWithMargins()方法只是一个过渡层更简单的做法是直接调用View对象的measure()方法)。整个measure调用流程就是个树形的递归过程。 
measure函数原型为View.java该函数不能被重载。  
- **流程二、 layout布局过程：**   
主要作用 ：为将整个根据子视图的大小以及布局参数将View树放到合适的位置上。具体的调用链如下：host.layout()开始View树的布局，继而回调给View/ViewGroup类中的layout()方法。  
具体流程如下：  
1. layout方法会设置该View视图位于父视图的坐标轴，即mLeft，mTop，mLeft，mBottom(调用setFrame()函数去实现)接下来回调onLayout()方法(如果该View是ViewGroup对象，需要实现该方法，对每个子视图进行布局)；
2. 如果该View是个ViewGroup类型，需要遍历每个子视图chiildView，调用该子视图的layout()方法去设置它的坐标值。layout函数原型位于View.java。
- **流程三、 draw()绘图过程**   
由ViewRoot对象的performTraversals()方法调用draw()方法发起绘制该View树，值得注意的是每次发起绘图时，并不会重新绘制每个View树的视图，而只会重新绘制那些“需要重绘”的视图，View类内部变量包含了一个标志位DRAWN，当该视图需要重绘时，就会为该View添加该标志位。调用流程：  
mView.draw()开始绘制，draw()方法实现的功能如下：
1. 绘制该View的背景
2. 为显示渐变框做一些准备操作 
3. 调用onDraw()方法绘制视图本身(每个View都需要重载该方法，ViewGroup不需要实现该方法)
4. 调用dispatchDraw()方法绘制子视图(如果该View类型不为ViewGroup，即不包含子视图，不需要重载该方法)。dispatchDraw()方法内部会遍历每个子视图，调用drawChild()去重新回调每个子视图的draw()方法(注意，这个地方“需要重绘”的视图才会调用draw()方法)。值得说明的是，ViewGroup类已经为我们重写了dispatchDraw()的功能实现，应用程序一般不需要重写该方法，但可以重载父类函数实现具体的功能。
- **invalidate()方法：**  
说明：请求重绘View树，即draw()过程，假如视图发生大小没有变化就不会调用layout()过程，并且只绘制那些“需要重绘的”
视图，即谁(View的话，只绘制该View；ViewGroup，则绘制整个ViewGroup)请求invalidate()方法，就绘制该视图。  
一般引起invalidate()操作的函数如下：  
1. 直接调用invalidate()方法，请求重新draw()，但只会绘制调用者本身。  
2. setSelection()方法：请求重新draw()，但只会绘制调用者本身。  
3. setVisibility()方法：当View可视状态在INVISIBLE转换VISIBLE时，会间接调用invalidate()方法，
继而绘制该View。当View的可视状态在INVISIBLE/VISIBLE转换为GONE状态时，会间接调用requestLayout()和invalidate方法。
同时，由于整个个View树大小发生了变化，会请求measure()过程以及draw()过程，同样地，只绘制需要“重新绘制”的视图。
4. setEnabled()方法：请求重新draw()，但不会重新绘制任何视图包括该调用者本身。
5. requestLayout()方法：会导致调用measure()过程和layout()过程 。
说明：只是对View树重新布局layout过程包括measure()和layout()过程，不会调用draw()过程，但不会重新绘制
任何视图包括该调用者本身。
- **requestFocus()方法：**  
说明：请求View树的draw()过程，但只绘制“需要重绘”的视图。
###Android事件处理机制###
在ViewGroup中，有下面三个方法：  
（1）dispatchTouchEvent     该方法用来分发事件，一般不会重写这个方法  
（2）onInterceptTouchEvent  用来拦截事件  
（3）onTouchEvent           用来处理事件  
而View中，只有两个方法，即：  
（1）dispatchTouchEvent     该方法用来分发事件，一般不会重写这个方法    
（2）onTouchEvent           用来处理事件  
正常情况下，android中的事件是必须要经过传递流程然后再经过处理流程的。要记住这个先后的顺序。  
在传递流程和处理流程中，你都可以修改方法的返回值，来对流程做控制。如下：  
对于事件的拦截，我们主要重写就是OnInterceptTouchEvent和onTouchEvent方法。两句就可以总结：  
事件的传递，返回结果为true，表示拦截，不再往下传递，为false，不拦截，继续往下传递。主要针对的就是OnInterceptTouchEvent方法。  
事件的处理，返回结果为true，表示拦截，不再往上传递（即我处理的很完美，不需要你再来审核我！），返回结果为false（没有成功处理事件），继续向上传递。
###什么是dpi,ps,sp?如何做适配###
px：是屏幕的像素点  
dp：一个基于density的抽象单位，如果一个160dpi的屏幕，1dp=1px  
dip：等同于dp  
sp：同dp相似，但还会根据用户的字体大小偏好来缩放(建议使用sp作为文本的单位，其它用dip)  
通过上面的知识我们可以看到这里只要弄懂px和dp之间的关系就可以了。那么下面重点来看一下他们两之间的关系：  
针对dip和px的关系，做以下概述：  
1). px(pixels)像素 ：  
一个像素通常被视为图像的最小的完整采样，这个用的比较多,特别是web开发,页面基本都是使用像素作为单位的.  
2). dip或dp (device independent pixels)：  
设备独立像素—这个和设备硬件有关，一般我们为了支持手机上多种分辨率,如WVGA、HVGA和QVGA,都会使用dip作为长度的单位
###Android中的动画种类有哪些？它们的特点和区别###
 android3.0之前，主要包括两种动画方式：补间动画（Tween Animation）和帧动画（Frame Animation或者Drawable Animation），这两种动画统称为view动画，针对视图动画存在的不足，3.0之后google增加了属性动画（Property Animation）。之后动画就被分成了View Animation和Property Animation。  
- **补间动画（Tween Animation）：**  
**Tween动画，这种实现方式可以使视图组件移动、放大、缩小以及产生透明度的变化**  
补间动画是视图动画的一种，Tween中文意思是在两者之间，中文翻译成补间还是挺贴合意思的，view从一个位置的特定状态变化到另外一个位置，中间过程我们开发者不需要自己完成，补间动画会根据我们属性的设置自己进行每一帧的补充，最后展现一个变化的过程，就叫做补间动画。补间动画可以完成view的位置、大小、旋转、透明度等一系列简单的变换。通常补间动画都是利用xml文件实现，属性设置简单明了，又可以重复使用。基于View的渐变动画，它只改变了View的绘制效果，而实际属性值未变。比如动画移动一个控件的位置，但控件实际位置仍未改变，如果我们此时想选中控件，它的位置仍在动画之前的位置。可以在res/anim/文件夹中定义XML文件实现动画，也可以利用AnimationSet类和Animation的子类完成动画。  
1.alpha  渐变透明度动画效果  
2.scale  渐变尺寸伸缩动画效果  
3.translate 画面转换位置移动动画效果  
4.rotate  画面转移旋转动画效果  
- **帧动画（Frame Animation或者Drawable Animation）：**  
**传统的动画方法，通过顺序的播放排列好的图片来实现，类似电影**  
帧动画也是view动画的一种，帧动画是通过读取xml文件中设置的一系列Drawable，以类似幻灯片的方式展示这些drawable,就形成了动画效果，当然也可以利用代码实现帧动画。可能大家觉着帧动画不太常用，其实类似的原理可以借鉴，类似android手机开机的很多动画效果就是类似帧动画，加载一系列图片，实现开机的动画效果。在代码中定义动画帧，使用AnimationDrawable类；XML文件能更简单的组成动画帧，在res/drawable文件夹，使用<animation-list>采用<item>来定义不同的帧。但是依旧推荐使用xml，具体如下：<animation-list>必须是根节点，包含一个或者多个<item>元素，属性有：android:oneshot true代表只执行一次，false循环执行。<item>类似一帧的动画资源。<item>animation-list的子项，包含属性如下：android:drawable一个frame的Drawable资源。android:duration一个frame显示多长时间。  
- **属性动画（Property Animation）：**  
属性动画在视图动画之后推出，API 11以上才能够使用，是为了弥补视图动画存在的问题，从名字可以看出属性动画和视图动画的不同，视图动画主要针对视图起作用，属性动画则是通过改变Object的属性进行动画实现。通过改变view或者object的属性实现动画是属性动画的最根本的特点。  
- **区别**  
视图动画早于属性动画，视图动画在API 1里面就已经存在，属性动画直到API3.0才出现，视图动画所在的包名为android.view.animation,属性动画为android.animation,可见视图动画只针对view起作用；试图动画中用到的类一般以Animation结尾，而属性动画则以Animator结尾。  
（1）属性动画比视图动画更强大，不但可以实现缩放、平移等操作，还可以自己定义动画效果，监听动画的过程，在动画过程中或完成后做响应的动作。  
（2）属性动画不但可以作用于View，还能作用于Object。  
（3）属性动画利用属性的改变实现动画，而视图动画仅仅改变了view的大小位置，但view真正的属性没有改变。  
###Android中有解析xml的几种方式？它们的原理和区别###  
XML解析主要有三种方式，SAX、DOM、PULL。常规在PC上开发我们使用Dom相对轻松些，但一些性能敏感的数据库或手机上还是主要采用SAX方式，SAX读取是单向的，优点:不占内存空间、解析属性方便，但缺点就是对于套嵌多个分支来说处理不是很方便。而DOM方式会把整个XML文件加载到内存中去，这里提醒大家该方法在查找方面可以和XPath很好的结合如果数据量不是很大推荐使用，而PULL常常用在J2ME对于节点处理比较好，类似SAX方式，同样很节省内存，在J2ME中我们经常使用的KXML库来解析。  
###Android中的数据存储方式###
- **SharedPreferences存储数据：**用来存储一些简单配置信息的一种机制，例如：登录用户的用户名与密码。其采用了Map数据结构来存储数据，以键值的方式存储。使用SharedPreferences只能在同一个包内使用，不能在不同的包之间使用。  
- **文件存储数据：**是一种较常用的方法，与Java中实现I/O的程序是完全一样。
- **SQLite数据库存储数据：**
- **ContentProvider存储数据：**可以向其他应用共享其数据。虽然使用其他方法也可以对外共享数据，但数据访问方式会因数据存储的方式而不同，如：采用文件方式对外共享数据，需要进行文件操作读写数据；采用sharedpreferences共享数据，需要使用sharedpreferencesAPI读写数据。而使用ContentProvider共享数据的好处是统一了数据访问方式。
- **网络存储数据:**与Android网络数据包打交道。  
Preference，File， DataBase这三种方式分别对应的目录是`/data/data/Package Name/Shared_Pref, /data/data/Package Name/files, /data/data/Package Name/database`。
###如何将SQLite数据库(dictionary.db文件)与apk文件一起发布###
可以将dictionary.db文件复制到Android工程中的res raw目录中。所有在res raw目录中的文件不会被压缩，这样可以直接提取该目录中的文件。
###如何将打开res raw目录中的数据库文件###
在Android中不能直接打开res raw目录中的数据库文件，而需要在程序第一次启动时将该文件复制到手机内存或SD卡的某个目录中，然后再打开该数据库文件。  
复制的基本方法是使用getResources().openRawResource方法获得res raw目录中资源的InputStream对象，然后将该InputStream对象中的数据写入其他的目录中相应文件中。在Android SDK中可以使用SQLiteDatabase.openOrCreateDatabase方法来打开任意目录中的SQLite数据库文件。
###Android中的activity的启动模式###
在android里，有4种activity的启动模式，分别为：  
- **standard (默认)：**默认模式，可以不用写配置。在这个模式下，都会默认创建一个新的实例。因此，在这种模式下，可以有多个相同的实例，也允许多个相同Activity叠加。   
- **singleTop：**可以有多个实例，但是不允许多个相同Activity叠加。即，如果Activity在栈顶的时候，启动相同的Activity，不会创建新的实例，而会调用其onNewIntent方法。  
- **singleTask：**只有一个实例。在同一个应用程序中启动他的时候，若Activity不存在，则会在当前task创建一个新的实例，若存在，则会把task中在其之上的其它Activity destory掉并调用它的onNewIntent方法。  
如果是在别的应用程序中启动它，则会新建一个task，并在该task中启动这个Activity，singleTask允许别的Activity与其在一个task中共存，也就是说，如果我在这个singleTask的实例中再打开新的Activity，这个新的Activity还是会在singleTask的实例的task中。  
- **singleInstance：**只有一个实例，并且这个实例独立运行在一个task中，这个task只有这个实例，不允许有别的Activity存在。  
**区别**  
1. 如何决定所属task
“standard”和”singleTop”的activity的目标task，和收到的Intent的发送者在同一个task内，除非intent包括参数`FLAG_ACTIVITY_NEW_TASK`。  
如果提供了`FLAG_ACTIVITY_NEW_TASK`参数，会启动到别的task里。
“singleTask”和”singleInstance”总是把activity作为一个task的根元素，他们不会被启动到一个其他task里。  
2. 是否允许多个实例
“standard”和”singleTop”可以被实例化多次，并且存在于不同的task中，且一个task可以包括一个activity的多个实例；    
“singleTask”和”singleInstance”则限制只生成一个实例，并且是task的根元素。 singleTop要求如果创建intent的时候栈顶已经有要创建的Activity的实例，则将intent发送给该实例，而不发送给新的实例。  
3. 是否允许其它activity存在于本task内
“singleInstance”独占一个task，其它activity不能存在那个task里；如果它启动了一个新的activity，不管新的activity的launch mode如何，新的activity都将会到别的task里运行（如同加了`FLAG_ACTIVITY_NEW_TASK`参数）。  
而另外三种模式，则可以和其它activity共存。  
4. 是否每次都生成新实例  
“standard”对于没一个启动Intent都会生成一个activity的新实例；  
“singleTop”的activity如果在task的栈顶的话，则不生成新的该activity的实例，直接使用栈顶的实例，否则，生成该activity的实例。  
比如现在task栈元素为A-B-C-D（D在栈顶），这时候给D发一个启动intent，如果D是 “standard”的，则生成D的一个新实例，栈变为A－B－C－D－D。
如果D是singleTop的话，则不会生产D的新实例，栈状态仍为A-B-C-D  
如果这时候给B发Intent的话，不管B的launchmode是”standard” 还是 “singleTop” ，都会生成B的新实例，栈状态变为A-B-C-D-B。
“singleInstance”是其所在栈的唯一activity，它会每次都被重用。  
“singleTask”如果在栈顶，则接受intent，否则，该intent会被丢弃，但是该task仍会回到前台。  
当已经存在的activity实例处理新的intent时候，会调用onNewIntent()方法 如果收到intent生成一个activity实例，那么用户可以通过back键回到上一个状态；如果是已经存在的一个activity来处理这个intent的话，用户不能通过按back键返回到这之前的状态。
###跟activity和Task有关的Intent启动方式有哪些？其含义？###
核心的Intent Flag有：  
- **`FLAG_ACTIVITY_NEW_TASK`**  
- **`FLAG_ACTIVITY_CLEAR_TOP`**  
- **`FLAG_ACTIVITY_RESET_TASK_IF_NEEDED`**  
- **`FLAG_ACTIVITY_SINGLE_TOP`**  

    FLAG_ACTIVITY_NEW_TASK
 如果设置，这个Activity会成为历史stack中一个新Task的开始。一个Task（从启动它的Activity到下一个Task中的Activity）定义了用户可以迁移的Activity原子组。Task可以移动到前台和后台；在某个特定Task中的所有Activity总是保持相同的次序。  
  这个标志一般用于呈现“启动”类型的行为：它们提供用户一系列可以单独完成的事情，与启动它们的Activity完全无关。
使用这个标志，如果正在启动的Activity的Task已经在运行的话，那么，新的Activity将不会启动；代替的，当前Task会简单的移入前台。参考`FLAG_ACTIVITY_MULTIPLE_TASK`标志，可以禁用这一行为。  
  这个标志不能用于调用方对已经启动的Activity请求结果。  

    FLAG_ACTIVITY_CLEAR_TOP
  如果设置，并且这个Activity已经在当前的Task中运行，因此，不再是重新启动一个这个Activity的实例，而是在这个Activity上方的所有Activity都将关闭，然后这个Intent会作为一个新的Intent投递到老的Activity（现在位于顶端）中。  
  例如，假设一个Task中包含这些Activity：A，B，C，D。如果D调用了startActivity()，并且包含一个指向Activity B的Intent，那么，C和D都将结束，然后B接收到这个Intent，因此，目前stack的状况是：A，B。  
  上例中正在运行的Activity B既可以在onNewIntent()中接收到这个新的Intent，也可以把自己关闭然后重新启动来接收这个Intent。如果它的启动模式声明为 “multiple”(默认值)，并且你没有在这个Intent中设置`FLAG_ACTIVITY_SINGLE_TOP`标志，那么它将关闭然后重新创建；对于其它的启动模式，或者在这个Intent中设置`FLAG_ACTIVITY_SINGLE_TOP`标志，都将把这个Intent投递到当前这个实例的onNewIntent()中。  
  这个启动模式还可以与`FLAG_ACTIVITY_NEW_TASK`结合起来使用：用于启动一个Task中的根Activity，它会把那个Task中任何运行的实例带入前台，然后清除它直到根Activity。这非常有用，例如，当从Notification Manager处启动一个Activity。  

    FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
如果设置这个标志，这个activity不管是从一个新的栈启动还是从已有栈推到栈顶，它都将以the front door of the task的方式启动。这就将导致任何与应用相关的栈都将重置到正常状态（不管是正在讲activity移入还是移除），如果需要，或者直接重置该栈为初始状态。  

    FLAG_ACTIVITY_SINGLE_TOP
  如果设置，当这个Activity位于历史stack的顶端运行时，不再启动一个新的。  

    FLAG_ACTIVITY_BROUGHT_TO_FRONT
  这个标志一般不是由程序代码设置的，如在launchMode中设置singleTask模式时系统帮你设定。  

    FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET
  如果设置，这将在Task的Activity stack中设置一个还原点，当Task恢复时，需要清理Activity。也就是说，下一次Task带着`FLAG_ACTIVITY_RESET_TASK_IF_NEEDED`标记进入前台时（典型的操作是用户在主画面重启它），这个Activity和它之上的都将关闭，以至于用户不能再返回到它们，但是可以回到之前的Activity。
  这在你的程序有分割点的时候很有用。例如，一个e-mail应用程序可能有一个操作是查看一个附件，需要启动图片浏览Activity来显示。这个Activity应该作为e-mail应用程序Task的一部分，因为这是用户在这个Task中触发的操作。然而，当用户离开这个Task，然后从主画面选择e-mail app，我们可能希望回到查看的会话中，但不是查看图片附件，因为这让人困惑。通过在启动图片浏览时设定这个标志，浏览及其它启动的Activity在下次用户返回到mail程序时都将全部清除。  

    FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
  如果设置，新的Activity不会在最近启动的Activity的列表中保存。  

    FLAG_ACTIVITY_FORWARD_RESULT
  如果设置，并且这个Intent用于从一个存在的Activity启动一个新的Activity，那么，这个作为答复目标的Activity将会传到这个新的Activity中。这种方式下，新的Activity可以调用setResult(int)，并且这个结果值将发送给那个作为答复目标的 Activity。  

    FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY
  这个标志一般不由应用程序代码设置，如果这个Activity是从历史记录里启动的（常按HOME键），那么，系统会帮你设定。  

    FLAG_ACTIVITY_MULTIPLE_TASK
  不要使用这个标志，除非你自己实现了应用程序启动器。与`FLAG_ACTIVITY_NEW_TASK`结合起来使用，可以禁用把已存的Task送入前台的行为。当设置时，新的Task总是会启动来处理Intent，而不管这是是否已经有一个Task可以处理相同的事情。  
  由于默认的系统不包含图形Task管理功能，因此，你不应该使用这个标志，除非你提供给用户一种方式可以返回到已经启动的Task。  
  如果F`LAG_ACTIVITY_NEW_TASK`标志没有设置，这个标志被忽略。  

    FLAG_ACTIVITY_NO_ANIMATION
  如果在Intent中设置，并传递给`Context.startActivity()`的话，这个标志将阻止系统进入下一个Activity时应用 Acitivity迁移动画。这并不意味着动画将永不运行——如果另一个Activity在启动显示之前，没有指定这个标志，那么，动画将被应用。这个标志可以很好的用于执行一连串的操作，而动画被看作是更高一级的事件的驱动。  

    FLAG_ACTIVITY_NO_HISTORY
  如果设置，新的Activity将不再历史stack中保留。用户一离开它，这个Activity就关闭了。这也可以通过设置noHistory特性。  

    FLAG_ACTIVITY_NO_USER_ACTION
  如果设置，作为新启动的Activity进入前台时，这个标志将在Activity暂停之前阻止从最前方的Activity回调的onUserLeaveHint()。  
  典型的，一个Activity可以依赖这个回调指明显式的用户动作引起的Activity移出后台。这个回调在Activity的生命周期中标记一个合适的点，并关闭一些Notification。
  如果一个Activity通过非用户驱动的事件，如来电或闹钟，启动的，这个标志也应该传递给Context.startActivity，保证暂停的Activity不认为用户已经知晓其Notification。  

    FLAG_ACTIVITY_PREVIOUS_IS_TOP
  如果给Intent对象设置了这个标记，并且这个Intent对象被用于从一个既存的Activity中启动一个新的Activity，这个Activity不能用于接受发送给顶层Activity的新的Intent对象，通常认为使用这个标记启动的Activity会被自己立即终止。 

    FLAG_ACTIVITY_REORDER_TO_FRONT
  如果在Intent中设置，并传递给Context.startActivity()，这个标志将引发已经运行的Activity移动到历史stack的顶端。  
  例如，假设一个Task由四个Activity组成：A,B,C,D。如果D调用startActivity()来启动Activity B，那么，B会移动到历史stack的顶端，现在的次序变成A,C,D,B。如果`FLAG_ACTIVITY_CLEAR_TOP`标志也设置的话，那么这个标志将被忽略。
###Activity的生命周期###
onCreate()、onStart()、onReStart()、onResume()、onPause()、onStop()、onDestory()；  
可见生命周期：从onStart()直到系统调用onStop()  
前台生命周期：从onResume()直到系统调用onPause()
###Activity在屏幕旋转时的生命周期###
不设置Activity的android:configChanges时，切屏会重新调用各个生命周期，切横屏时会执行一次，切竖屏时会执行两次；  
设置Activity的android:configChanges="orientation"时，切屏还是会重新调用各个生命周期，切横、竖屏时只会执行一次；  
设置Activity的android:configChanges="orientation|keyboardHidden"时，切屏不会重新调用各个生命周期，只会执行onConfigurationChanged方法
###如何退出Activity？如何安全退出已调用多个Activity的Application？###
对于单一Activity的应用来说，退出很简单，直接finish()即可。当然，也可以用killProcess()和System.exit()这样的方法。  
对于多个activity  
1. 记录打开的Activity：每打开一个Activity，就记录下来。在需要退出时，关闭每一个Activity即可。  
2. 发送特定广播：在需要结束应用时，发送一个特定的广播，每个Activity收到广播后，关闭即可。  
3. 递归退出：在打开新的Activity时使用startActivityForResult，然后自己加标志，在onActivityResult中处理，递归关闭。为了编程方便，最好定义一个Activity基类，处理这些共通问题。
###如果后台的Activity由于某原因被系统回收了，如何在被系统回收之前保存当前状态###
重写onSaveInstanceState()方法，在此方法中保存需要保存的数据，该方法将会在activity被回收之前调用。通过重写onRestoreInstanceState()方法可以从中提取保存好的数据
###如何将一个Activity设置成窗口的样式###
<activity>中配置：android :theme="@android:style/Theme.Dialog"  
另外android:theme="@android:style/Theme.Translucent" 是设置透明
###Service的启用和停用###
第一步：继承Service类
public class SMSService extends Service {}  
第二步：在AndroidManifest.xml文件中的<application>节点里对服务进行配置:<service android:name=".SMSService" />  
服务不能自己运行，需要通过调用Context.startService()或Context.bindService()方法启动服务。这两个方法都可以启动Service，但是它们的使用场合有所不同。使用startService()方法启用服务，调用者与服务之间没有关连，即使调用者退出了，服务仍然运行。使用bindService()方法启用服务，调用者与服务绑定在了一起，调用者一旦退出，服务也就终止，大有“不求同时生，必须同时死”的特点。  
如果打算采用Context.startService()方法启动服务，在服务未被创建时，系统会先调用服务的onCreate()方法，接着调用onStart()方法。如果调用startService()方法前服务已经被创建，多次调用startService()方法并不会导致多次创建服务，但会导致多次调用onStart()方法。采用startService()方法启动的服务，只能调用Context.stopService()方法结束服务，服务结束时会调用onDestroy()方法。  
如果打算采用Context.bindService()方法启动服务，在服务未被创建时，系统会先调用服务的onCreate()方法，接着调用onBind()方法。这个时候调用者和服务绑定在一起，调用者退出了，系统就会先调用服务的onUnbind()方法，接着调用onDestroy()方法。如果调用bindService()方法前服务已经被绑定，多次调用bindService()方法并不会导致多次创建服务及绑定(也就是说onCreate()和onBind()方法并不会被多次调用)。如果调用者希望与正在绑定的服务解除绑定，可以调用unbindService()方法，调用该方法也会导致系统调用服务的onUnbind()-->onDestroy()方法。  
服务常用生命周期回调方法如下：  
onCreate()该方法在服务被创建时调用，该方法只会被调用一次，无论调用多少次startService()或bindService()方法，服务也只被创建一次。  
onDestroy()该方法在服务被终止时调用。  
与采用Context.startService()方法启动服务有关的生命周期方法  
onStart()只有采用Context.startService()方法启动服务时才会回调该方法。该方法在服务开始运行时被调用。多次调用startService()方法尽管不会多次创建服务，但onStart()方法会被多次调用。  
与采用Context.bindService()方法启动服务有关的生命周期方法  
onBind()只有采用Context.bindService()方法启动服务时才会回调该方法。该方法在调用者与服务绑定时被调用，当调用者与服务已经绑定，多次调用    Context.bindService()方法并不会导致该方法被多次调用。  
onUnbind()只有采用Context.bindService()方法启动服务时才会回调该方法。该方法在调用者与服务解除绑定时被调用
###IntentService有何优点###
IntentService是Service的子类，比普通的Service增加了额外的功能。  
- **Service本身存在两个问题**  
Service不会专门启动一条单独的进程，Service与它所在应用位于同一个进程中；
Service也不是专门一条新线程，因此不应该在Service 中直接处理耗时的任务；  
- **IntentService特征**  
会创建独立的worker线程来处理所有的Intent请求；
会创建独立的worker线程来处理onHandleIntent()方法实现的代码，无需处理多线程问题；
所有请求处理完成后，IntentService会自动停止，无需调用stopSelf()方法停止Service；
为Service的onBind()提供默认实现，返回null；
为Service的onStartCommand提供默认实现，将请求Intent添加到队列中；  
- **IntentService优点**  
IntentService使用队列的方式将请求的Intent加入队列，然后开启一个worker thread(线程)来处理队列中的Intent，对于异步的startService请求，IntentService会处理完成一个之后再处理第二个，每一个请求都会在一个单独的worker thread中处理，不会阻塞应用程序的主线程
###Service和Thread的区别###
Servie是系统的组件，它由系统进程托管（servicemanager）；它们之间的通信类似于client和server，是一种轻量级的ipc通信，这种通信的载体是binder，它是在linux层交换信息的一种ipc。  
而thread是由本应用程序托管。  
1. Thread：Thread是程序执行的最小单元，它是分配CPU的基本单位。可以用Thread来执行一些异步的操作。
2. Service：Service是android的一种机制，当它运行的时候如果是Local Service，那么对应的Service是运行在主进程的main线程上的。如：onCreate，onStart 这些函数在被系统调用的时候都是在主进程的main线程上运行的。如果是Remote Service，那么对应的Service则是运行在独立进程的main线程上。  
既然这样，那么我们为什么要用Service呢？其实这跟 android 的系统机制有关，我们先拿Thread来说。Thread的运行是独立于Activity的，也就是说当一个Activity被finish之后，如果你没有主动停止Thread或者Thread里的run方法没有执行完毕的话，Thread也会一直执行。因此这里会出现一个问题：当Activity被finish之后，你不再持有该Thread的引用。另一方面，你没有办法在不同的Activity中对同一Thread进行控制。   
举个例子：如果你的Thread需要不停地隔一段时间就要连接服务器做某种同步的话，该Thread需要在Activity没有start的时候也在运行。这个时候当你start一个Activity就没有办法在该Activity里面控制之前创建的Thread。因此你便需要创建并启动一个Service ，在Service里面创建、运行并控制该Thread，这样便解决了该问题（因为任何Activity都可以控制同一Service，而系统也只会创建一个对应Service的实例）。 
因此你可以把Service想象成一种消息服务，而你可以在任何有Context的地方调用Context.startService、Context.stopService、Context.bindService，Context.unbindService，来控制它，你也可以在Service里注册BroadcastReceiver，在其他地方通过发送 broadcast来控制它，当然这些都是Thread做不到的。
###注册广播的方式和优缺点###
首先写一个类要继承BroadcastReceiver  
第一种使用代码进行注册如:  

    IntentFilter filter =  new IntentFilter("android.provider.Telephony.SMS_RECEIVED");  
    IncomingSMSReceiver receiver = new IncomgSMSReceiver();  
    registerReceiver(receiver.filter);  
第二种:在清单文件中声明,添加  

    <receive android:name=".IncomingSMSReceiver " >
    <intent-filter>
    <action android:name="android.provider.Telephony.SMS_RECEIVED")
    <intent-filter>
    <receiver>  
两种注册类型的区别是：  
1)第一种不是常驻型广播，也就是说广播跟随程序的生命周期。  
2)第二种是常驻型，也就是说当应用程序关闭后，如果有信息广播来，程序也会被系统调用自动运行。
###在单线程模型中Message、Handler、Message Queue、Looper之间的关系###
1. Message消息，理解为线程间交流的信息，处理数据后台线程需要更新UI，则发送Message内含一些数据给UI线程。  
2. Handler处理者，是Message的主要处理者，负责Message的发送，Message内容的执行处理。后台线程就是通过传进来的 Handler对象引用来sendMessage(Message)。而使用Handler，需要implement 该类的 handleMessage(Message)方法，它是处理这些Message的操作内容，例如Update UI。通常需要子类化Handler来实现handleMessage方法。  
3. Message Queue消息队列，用来存放通过Handler发布的消息，按照先进先出执行。  
每个message queue都会有一个对应的Handler。Handler会向message queue通过两种方法发送消息：sendMessage或post。这两种消息都会插在message queue队尾并按先进先出执行。但通过这两种方法发送的消息执行的方式略有不同：通过sendMessage发送的是一个message对象,会被 Handler的handleMessage()函数处理；而通过post方法发送的是一个runnable对象，则会自己执行。  
4. Looper是每条线程里的Message Queue的管家。Android没有Global的Message Queue，而Android会自动替主线程(UI线程)建立Message Queue，但在子线程里并没有建立Message Queue。所以调用Looper.getMainLooper()得到的主线程的Looper不为NULL，但调用Looper.myLooper() 得到当前线程的Looper就有可能为NULL。对于子线程使用Looper，API Doc提供了正确的使用方法：这个Message机制的大概流程：  
    1. 在Looper.loop()方法运行开始后，循环地按照接收顺序取出Message Queue里面的非NULL的Message。  
    2. 一开始Message Queue里面的Message都是NULL的。当Handler.sendMessage(Message)到Message Queue，该函数里面设置了那个Message对象的target属性是当前的Handler对象。随后Looper取出了那个Message，则调用 该Message的target指向的Hander的dispatchMessage函数对Message进行处理。在dispatchMessage方法里，如何处理Message则由用户指定，三个判断，优先级从高到低：  1) Message里面的Callback，一个实现了Runnable接口的对象，其中run函数做处理工作；2) Handler里面的mCallback指向的一个实现了Callback接口的对象，由其handleMessage进行处理；3) 处理消息Handler对象对应的类继承并实现了其中handleMessage函数，通过这个实现的handleMessage函数处理消息。由此可见，我们实现的handleMessage方法是优先级最低的！  
    3. Handler处理完该Message (update UI) 后，Looper则设置该Message为NULL，以便回收！  
 在网上有很多文章讲述主线程和其他子线程如何交互，传送信息，最终谁来执行处理信息之类的，个人理解是最简单的方法——判断Handler对象里面的Looper对象是属于哪条线程的，则由该线程来执行1. 当Handler对象的构造函数的参数为空，则为当前所在线程的Looper；2. Looper.getMainLooper()得到的是主线程的Looper对象，Looper.myLooper()得到的是当前线程的Looper对象。
###简要解释一下Activity、Intent 、Intent filter、Service、Broadcase、BroadcaseReceiver###
一个activity呈现了一个用户可以操作的可视化用户界面；  
一个service不包含可见的用户界面，而是在后台运行，可以与一个activity绑定，通过绑定暴露出来接口并与其进行通信；  
一个broadcast receiver是一个接收广播消息并做出回应的component，broadcast receiver没有界面；  
一个intent是一个Intent对象，它保存了消息的内容。对于activity和service来说，它指定了请求的操作名称和待操作数据的URI，  Intent对象可以显式的指定一个目标component。如果这样的话，android会找到这个component(基于manifest文件中的声明)并激活它。但如果一个目标不是显式指定的，android必须找到响应intent的最佳component。它是通过将Intent对象和目标的intent filter相比较来完成这一工作的；  
一个component的intent filter告诉android该component能处理的intent。intent filter也是在manifest文件中声明的。
###MVC模式的原理和在Android中的运用###
mvc是model,view,controller的缩写，mvc包含三个部分：  
模型（model）对象：是应用程序的主体部分，所有的业务逻辑都应该写在该层。  
视图（view）对象：是应用程序中负责生成用户界面的部分。也是在整个mvc架构中用户唯一可以看到的一层，接收用户的输入，显示处理结果。   
控制器（control）对象：是根据用户的输入，控制用户界面数据显示及更新model对象状态的部分，控制器更重要的一种导航功能，响应用户出发的相关事件，交给m层处理。   
android鼓励弱耦合和组件的重用，在android中mvc的具体体现如下：   
1)视图层（view）：一般采用xml文件进行界面的描述，使用的时候可以非常方便的引入，当然，如果你对android了解的比较的多了话，就一定可以想到在android中也可以使用JavaScript+html等的方式作为view层，当然这里需要进行java和javascript之间的通信，幸运的是，android提供了它们之间非常方便的通信实现。  
2)控制层（controller）：android的控制层的重任通常落在了众多的acitvity的肩上，这句话也就暗含了不要在acitivity中写代码，要通过activity交割model业务逻辑层处理，这样做的另外一个原因是android中的acitivity的响应时间是5s，如果耗时的操作放在这里，程序就很容易被回收掉。   
3)模型层（model）：对数据库的操作、对网络等的操作都应该在model里面处理，当然对业务计算等操作也是必须放在的该层的。
###什么是ANR？如何避免它？
在 Android 上，如果你的应用程序有一段时间响应不够灵敏，系统会向用户显示一个对话框，这个对话框称作应
用程序无响应（ANR：Application Not Responding）对话框。用户可以选择让程序继续运行，但是，他们在使用你
的应用程序时，并不希望每次都要处理这个对话框。因此，在程序里对响应性能的设计很重要，这样，系统不会显示
ANR给用户。  
不同的组件发生 ANR 的时间不一样，主线程（Activity、Service）是5秒，BroadCastReceiver是10秒。  
解决方案：  
将所有耗时操作，比如访问网络，Socket通信，查询大量SQL语句，复杂逻辑计算等都放在子线程中去，然后通过handler.sendMessage、runonUITread、AsyncTask等方式更新UI。无论如何都要确保用户界面操作的流畅度。如果耗时操作需要让用户等待，那么可以在界面上显示进度条。
###什么情况会导致ForceClose？如何避免？能否捕获导致其的异常？###
抛出运行时异常时就会导致Force Close，比如空指针、数组越界、类型转换异常等等。  
捕获：可以通过logcat查看抛出异常的代码出现的位置，然后到程序对应代码中进行修改。   
避免：编写程序时，要思维缜密，在可能出现异常的地方都作相应的处理，增强程序的健壮性。
###DDMS和TraceView的区别###
DDMS是一个程序执行查看器，在里面可以看见线程和堆栈等信息，TraceView是程序性能分析器。
###Android的IPC（进程间通信）机制###
IPC是内部进程通信的简称，是共享"命名管道"的资源。Android中的IPC机制是为了让Activity和Service之间可以随时的进行交互，故在Android中该机制，只适用于Activity和Service之间的通信，类似于远程方法调用，类似于C/S模式的访问。通过定义AIDL接口文件来定义IPC接口。Servier端实现IPC接口，Client端调用IPC接口本地代理。
###AIDL的全称是什么？如何工作？能处理哪些类型的数据？###
全称是：Android Interface Define Language  
在Android中, 每个应用程序都可以有自己的进程. 在写UI应用的时候, 经常要用到Service. 在不同的进程中, 怎样传递对象呢?显然, Java中不允许跨进程内存共享. 因此传递对象, 只能把对象拆分成操作系统能理解的简单形式, 以达到跨界对象访问的目的. 在J2EE中,采用RMI的方式, 可以通过序列化传递对象. 在Android中, 则采用AIDL的方式. 理论上AIDL可以传递Bundle,实际上做起来却比较麻烦。  
AIDL(AndRoid接口描述语言)是一种接口描述语言; 编译器可以通过aidl文件生成一段代码，通过预先定义的接口达到两个进程内部通信进程的目的. 如果需要在一个Activity中, 访问另一个Service中的某个对象, 需要先将对象转化成AIDL可识别的参数(可能是多个参数), 然后使用AIDL来传递这些参数, 在消息的接收端, 使用这些参数组装成自己需要的对象。  
AIDL的IPC的机制和COM或CORBA类似, 是基于接口的，但它是轻量级的。它使用代理类在客户端和实现层间传递值. 如果要使用AIDL, 需要完成2件事情: 1. 引入AIDL的相关类.2. 调用aidl产生的class.  
- **AIDL的创建方法:**  
AIDL语法很简单,可以用来声明一个带一个或多个方法的接口，也可以传递参数和返回值。由于远程调用的需要, 这些参数和返回值并不是任何类型.下面是些AIDL支持的数据类型:  
1. 不需要import声明的简单Java编程语言类型(int,boolean等)  
2. String, CharSequence不需要特殊声明  
3. List, Map和Parcelables类型, 这些类型内所包含的数据成员也只能是简单数据类型, String等其他比支持的类型
###Java中如何引用本地语言###
可以用JNI（java native interface java本地接口）接口。
###简述Android中JNI的调用过程###
1)安装和下载Cygwin，下载 Android NDK  
2)在ndk项目中JNI接口的设计  
3)使用C/C++实现本地方法    
4)JNI生成动态链接库.so文件  
5)将动态链接库复制到java工程，在java工程中调用，运行java工程即可  
###NDK是什么###
NDK是一些列工具的集合，NDK提供了一系列的工具，帮助开发者迅速的开发C/C++的动态库，并能自动将so和java 应用打成apk包。  
NDK集成了交叉编译器，并提供了相应的mk文件和隔离cpu、平台等的差异，开发人员只需简单的修改mk文件就可以创建出so。
###Android源码和系统的架构###
android系统架构采用了分层架构的思想，从上到下共4层，分别为：应用程序层、应用程序框架层、系统库和android运行时层、Linux内核层。  
- **应用程序层（Java应用程序）：**
  该层提供一些核心应用程序包，例如电子邮件、短信、日历、地图、浏览器和联系人管理等。同时，开发者可以利用Java语言设计和编写属于自己的应用程序，而这些程序与那些核心应用程序彼此平等、友好共处。  
- **应用程序框架层（JAVA框架）：**
  该层是Android应用开发的基础，开发人员大部分情况是在和她打交道。应用程序框架层包括活动管理器、窗口管理器、内容提供者、视图系统、包管理器、电话管理器、资源管理器、位置管理器、通知管理器和XMPP服务十个部分。在Android平台上，开发人员可以完全访问核心应用程序所使用的API框架。并且，任何一个应用程序都可以发布自身的功能模块，而其他应用程序则可以使用这些已发布的功能模块。基于这样的重用机制，用户就可以方便地替换平台本身的各种应用程序组件。  
- **系统库和android运行时层（本地框架和JAVA运行环境）：**
  系统库包括九个子系统，分别是图层管理、媒体库、SQLite、OpenGLEState、FreeType、WebKit、SGL、SSL和libc。
  Android运行时包括核心库和Dalvik虚拟机，前者既兼容了大多数Java语言所需要调用的功能函数，又包括了Android的核心库，比如android.os、android.NET、android.media等等。后者是一种基于寄存器的java虚拟机，Dalvik虚拟机主要是完成对生命周期的管理、堆栈的管理、线程的管理、安全和异常的管理以及垃圾回收等重要功能。  
- **LINUX内核层：**
  Android核心系统服务依赖于Linux内核，如安全性、内存管理、进程管理、网络协议栈和驱动模型。Linux内核也是作为硬件与软件栈的抽象层。
  驱动：显示驱动、摄像头驱动、键盘驱动、WiFi驱动、Audio驱动、flash内存驱动、Binder（IPC）驱动、电源管理等。
###Android系统的优势和不足###
- **Android平台手机5大优势：**  
**一、开放性**  
在优势方面，Android平台首先就是其开发性，开发的平台允许任何移动终端厂商加入到Android联盟中来。显著的开放性可以使其拥有更多的开发者，随着用户和应用的日益丰富，一个崭新的平台也将很快走向成熟。开放性对于Android的发展而言，有利于积累人气，这里的人气包括消费者和厂商，而对于消费者来讲，随大的受益正是丰富的软件资源。开放的平台也会带来更大竞争，如此一来，消费者将可以用更低的价位购得心仪的手机。  
**二、挣脱运营商的束缚**  
在过去很长的一段时间，特别是在欧美地区，手机应用往往受到运营商制约，使用什么功能接入什么网络，几乎都受到运营商的控制。从去年iPhone 上市 ，用户可以更加方便地连接网络，运营商的制约减少。随着EDGE、HSDPA这些2G至3G移动网络的逐步过渡和提升，手机随意接入网络已不是运营商口中的笑谈，当你可以通过手机IM软件方便地进行即时聊天时，再回想不久前天价的彩信和图铃下载业务，是不是像噩梦一样？互联网巨头Google推动的Android终端天生就有网络特色，将让用户离互联网更近。  
**三、丰富的硬件选择**  
这一点还是与Android平台的开放性相关，由于Android的开放性，众多的厂商会推出千奇百怪，功能特色各具的多种产品。功能上的差异和特色，却不会影响到数据同步、甚至软件的兼容，好比你从诺基亚 Symbian风格手机 一下改用苹果 iPhone ，同时还可将Symbian中优秀的软件带到iPhone上使用、联系人等资料更是可以方便地转移，是不是非常方便呢？  
**四、不受任何限制的开发商**  
Android平台提供给第三方开发商一个十分宽泛、自由的环境，不会受到各种条条框框的阻扰，可想而知，会有多少新颖别致的软件会诞生。但也有其两面性，血腥、暴力、情色方面的程序和游戏如可控制正是留给Android难题之一。  
**五、无缝结合的Google应用**  
如今叱诧互联网的Google已经走过10年度历史，从搜索巨人到全面的互联网渗透，Google服务如地图、邮件、搜索等已经成为连接用户和互联网的重要纽带，而Android平台手机将无缝结合这些优秀的Google服务。  
- **再说Android的5大不足：**  
**一、安全和隐私**  
由于手机 与互联网的紧密联系，个人隐私很难得到保守。除了上网过程中经意或不经意留下的个人足迹，Google这个巨人也时时站在你的身后，洞穿一切，因此，互联网的深入将会带来新一轮的隐私危机。  
**二、首先开卖Android手机的不是最大运营商**  
众所周知，T-Mobile在23日，于美国纽约发布 了Android首款手机G1。但是在北美市场，最大的两家运营商乃AT&T和Verizon，而目前所知取得Android手机销售权的仅有 T-Mobile和Sprint，其中T-Mobile的3G网络相对于其他三家也要逊色不少，因此，用户可以买账购买G1，能否体验到最佳的3G网络服务则要另当别论了！  
**三、运营商仍然能够影响到Android手机**  
在国内市场，不少用户对购得移动定制机不满，感觉所购的手机被人涂画了广告一般。这样的情况在国外市场同样出现。Android手机的另一发售运营商Sprint就将在其机型中内置其手机商店程序。  
**四、同类机型用户减少**  
在不少手机论坛都会有针对某一型号的子论坛，对一款手机的使用心得交流，并分享软件资源。而对于Android平台手机，由于厂商丰富，产品类型多样，这样使用同一款机型的用户越来越少，缺少统一机型的程序强化。举个稍显不当的例子，现在山寨机泛滥，品种各异，就很少有专门针对某个型号山寨机的讨论和群组，除了哪些功能异常抢眼、颇受追捧的机型以外。  
**五、过分依赖开发商缺少标准配置**  
在使用PC端的Windows Xp系统的时候，都会内置微软Windows Media Player这样一个浏览器程序，用户可以选择更多样的播放器，如Realplay或暴风影音等。但入手开始使用默认的程序同样可以应付多样的需要。在 Android平台中，由于其开放性，软件更多依赖第三方厂商，比如Android系统的SDK中就没有内置音乐 播放器，全部依赖第三方开发，缺少了产品的统一性。  
###主流的三类应用：Native App, Web App, Hybrid App.###
Native App特点:
性能好
完美的用户体验
开发成本高，无法跨平台
升级困难(审核),维护成本高
Web App特点:
开发成本低,更新快,版本升级容易,自动升级
跨平台，Write Once , Run Anywhere
无法调用系统级的API
临时入口，用户留存度低
性能差,体验差,设计受限制
相比Native App，Web App体验中受限于以上5个因素：网络环境，渲染性能，平台特性，受限于浏览器，系统限制。
Hybrid App特点:
Native App 和 Web App 折中的方案，保留了 Native App 和 Web App 的优点。
但是还是性能差。页面渲染效率低，在Webview中绘制界面，实现动画，资源消耗都比较大,受限于技术,网速等因素相同点:
都采用Web的开发模式，使用JS开发；
都可以直接在Chrome中调试JS代码；
都支持跨平台的开发；
都可以实现hot reload，边更新代码边查看效果；
不同点:
学习成本
1.环境配置：
ReactNative需要按照文档安装配置很多依赖的工具，相对比较麻烦。 weex安装cli之后就可以使用
2.vue vs react:上面已经做过对比
react模板JSX学习使用有一定的成本 vue更接近常用的web开发方式，模板就是普通的html，数据绑定使用mustache风格，样式直接使用css
社区支持
Weex开源较晚，互联网上相关资料还比较少，社区规模较小；
React Native社区则比较活跃，可以参考的项目和资料也比较丰富
一张图:从渲染时间,内存使用,CPU占用,帧率(图形处理器每秒钟能够刷新几次,高的帧率可以得到更流畅、更逼真的动画。每秒钟帧数 （fps） 愈多，所显示的动作就会愈流畅。)
###常见的网络通信库优缺点###
- **Volley**  
Google提供网络通信库，在2013年Google I/O大会上推出了一个新的网络通信框架  
- 特点：  
自动调度网络请求  
多线程并发网络连接、请求优先级  
请求Cache和内存管理  
扩展性强 如：支持自定义重连等  
支持请求取消  
强大的Debug、tracing  
- 缺点：  
对于大数据量的网络操作糟糕，如：下载文件等  
它的设计目标就是非常适合去进行数据量不大，但通信频繁的网络操作，如list加载等等  
- **okhttp**  
square开源的http协议工具类，Android系统API19以后HttpURLConnection内部实现就是使用了okhttp  
- 特点：  
支持Http/2，Http/2主要支持 SPDY( http://zh.wikipedia.org/wiki/SPDY )协议。SPDY协议是Google开发的基于传输控制协议的应用层协议，通过压缩，多路复用(一个 TCP 链接传送网页和图片等资源)和优先级来缩短加载时间。  
如果Http/2不可用，利用连接池减少请求延迟  
Gzip压缩  
Response缓存减少不必要的请求  
支持请求取消  
- 缺点：  
在服务器不支持speedy的情况下没有特别明显的优化  
- **Retrofit**  
Square开源RESTFUL API库， Retrofit的跟Volley是一个套路，但解耦的更彻底。同时自己内部对OkHtttp客户端做了封装，用Retrofit+OkHttp基本上已经可以处理任何业务场景了。  
- 特点：  
简化了网络请求流程，支持注解请求  
支持多种Converter、还可以自定义，如：Gson、Jackson、protobuf、xml  
可以配合RxJava使用  
- 缺点：  
看不出缺点  
- **android-async-http**  
由于HttpClient在Android API23后就不能使用了，android-async-http内部实现了HttpClient。  
###常见的图片加载库优缺点###
- **Fresco**  
Fresco是Facebook推出的开源图片缓存工具，主要特点包括：两个内存缓存加上Native缓存构成了三级缓存，支持流式，可以类似网页上模糊渐进式显示图片，对多帧动画图片支持更好，如Gif、WebP。它的优点是其他几个框架没有的,或者说是其他几个框架的短板。  
- 优点:
 1. 图片存储在安卓系统的匿名共享内存, 而不是虚拟机的堆内存中, 图片的中间缓冲数据也存放在本地堆内存, 所以, 应用程序有更多的内存使用, 不会因为图片加载而导致oom, 同时也减少垃圾回收器频繁调用回收Bitmap 导致的界面卡顿, 性能更高。
 2. 渐进式加载JPEG图片, 支持图片从模糊到清晰加载。
 3. 图片可以以任意的中心点显示在ImageView, 而不仅仅是图片的中心。
 4. JPEG图片改变大小也是在native进行的, 不是在虚拟机的堆内存, 同样减少OOM。
 5. 很好的支持GIF图片的显示。  
- 缺点:
 1. 框架较大, 影响Apk体积
 2. 使用较繁琐
- **ImageLoader**  
是比较老的框架,是github社区上star最多的一个项目,可以理解为点赞最多滴,应该是最有名的一个国内很多知名软件都用它包括淘宝京东聚划算等等。  
- 优点：  
1.支持下载进度监听  
2.可以在View滚动中暂停图片加载，通过PauseOnScrollListener接口可以在View滚动中暂停图片加载。  
3.默认实现多种内存缓存算法。这几个图片缓存都可以配置缓存算法，不过 ImageLoader默认实现了较多缓存算法，如Size最大先删除、使用最少先删除、最近最少使用、先进先删除、时间最长先删除等。  
4.支持本地缓存文件名规则定义
- **Picasso**    
- 优点  
1.自带统计监控功能。支持图片缓存使用的监控，包括缓存命中率、已使用内存大小、节省的流量等。  
2.支持优先级处理。每次任务调度前会选择优先级高的任务，比如App页面中Banner的优先级高于Icon时就很适用。  
3.支持延迟到图片尺寸计算完成加载  
4.支持飞行模式、并发线程数根据网络类型而变。手机切换到飞行模式或网络类型变换时会自动调整线程池最大并发数，比如wifi最大并发为4，4g为3，3g为2。这里Picasso根据网络类型来决定最大并发数，而不是CPU核数。  
5.“无”本地缓存。“无”本地缓存，不是说没有本地缓存，而是Picasso自己没有实现，交给了Square的另外一个网络库okhttp去实现，这样的好处是可以通过请求Response Header中的Cache-Control及Expired控制图片的过期时间。
- **Glide**   
- 优点  
1.不仅仅可以进行图片缓存还可以缓存媒体文件。Glide不仅是一个图片缓存，它支持Gif、WebP、缩略图。甚至是 Video，所以更该当做一个媒体缓存。  
2.支持优先级处理。  
3.与Activity/Fragment生命周期一致，支持trimMemory。Glide对每个context都保持一个RequestManager，通过FragmentTransaction保持与Activity/Fragment生命周期一致，并且有对应的trimMemory接口实现可供调用。  
4.支持okhttp、Volley。Glide默认通过UrlConnection获取数据，可以配合okhttp或是Volley使用。实际 ImageLoader、Picasso 也都支持okhttp、Volley。  
5.内存友好。Glide的内存缓存有个active的设计，从内存缓存中取数据时，不像一般的实现用get，而是用 remove，再将这个缓存数据放到一个value为软引用的activeResources map中，并计数引用数，在图片加载完成后进行判断，如果引用计数为空则回收掉。内存缓存更小图片，Glide 以url、view_width、view_height、屏幕的分辨率等做为联合key，将处理后的图片缓存在内存缓存中，而不是原始图片以节省大小与Activity/Fragment 生命周期一致，支持trimMemory。图片默认使用默认RGB_565而不是ARGB_888，虽然清晰度差些，但图片更小，也可配置到ARGB_888。  
6.Glide 可以通过signature或不使用本地缓存支持url过期。