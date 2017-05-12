###Android中的四大组件和它们的作用###
- **Activity：**Activity是Android程序与用户交互的窗口，是Android构造块中最基本的一种，它需要为保持各界面的状态，做很多持久化的事情，妥善管理生命周期以及一些跳转逻辑。
- **Service：**后台服务于Activity，封装有一个完整的功能逻辑实现，接受上层指令，完成相关的事物，定义好需要接受的Intent提供同步和异步的接口。
- **Content Provider：**是Android提供的第三方应用数据的访问方案，可以派生Content Provider类，对外提供数据，可以像数据库一样进行选择排序，屏蔽内部数据的存储细节，向外提供统一的借口模型，大大简化上层应用，对数据的整合提供了更方便的途径。
- **BroadCast Receiver：**接受一种或者多种Intent作触发事件，接受相关消息，做一些简单处理，转换成一条Notification，统一了Android的事件广播模型。
###Android中常用的五种布局###
- **FrameLayout(框架布局):**所有东西依次都放在左上角，会重叠，这个布局比较简单，只能放一点比较简单的东西。
- **LinearLayout(线性布局):**每一个LinearLayout里面又可分为垂直布局`android:orientation="vertical"`和水平布局`android:orientation="horizontal"` 。当垂直布局时，每一行就只有一个元素，多个元素依次垂直往下；水平布局时，只有一行，每一个元素依次向右排列。
- **AbsoluteLayout(绝对布局):**用X,Y坐标来指定元素的位置，这种布局方式也比较简单，但是在屏幕旋转时，往往会出问题，而且多个元素的时候，计算比较麻烦。
- **RelativeLayout(相对布局):**可以理解为某一个元素为参照物，来定位的布局方式。主要属性有：相对于某一个元素`android:layout_below`、`android:layout_toLeftOf`相对于父元素的地方`android:layout_alignParentLeft`、`android:layout_alignParentRigh`。
- **TableLayout(表格布局):**每一个TableLayout里面有表格行TableRow，TableRow里面可以具体定义每一个元素。每一个布局都有自己适合的方式，这五个布局元素可以相互嵌套应用，做出美观的界面。
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
###MVC模式的原理和在android中的运用###
mvc是model,view,controller的缩写，mvc包含三个部分：  
模型（model）对象：是应用程序的主体部分，所有的业务逻辑都应该写在该层。  
视图（view）对象：是应用程序中负责生成用户界面的部分。也是在整个mvc架构中用户唯一可以看到的一层，接收用户的输入，显示处理结果。   
控制器（control）对象：是根据用户的输入，控制用户界面数据显示及更新model对象状态的部分，控制器更重要的一种导航功能，响应用户出发的相关事件，交给m层处理。   
android鼓励弱耦合和组件的重用，在android中mvc的具体体现如下：   
1)视图层（view）：一般采用xml文件进行界面的描述，使用的时候可以非常方便的引入，当然，如果你对android了解的比较的多了话，就一定可以想到在android中也可以使用JavaScript+html等的方式作为view层，当然这里需要进行java和javascript之间的通信，幸运的是，android提供了它们之间非常方便的通信实现。  
2)控制层（controller）：android的控制层的重任通常落在了众多的acitvity的肩上，这句话也就暗含了不要在acitivity中写代码，要通过activity交割model业务逻辑层处理，这样做的另外一个原因是android中的acitivity的响应时间是5s，如果耗时的操作放在这里，程序就很容易被回收掉。   
3)模型层（model）：对数据库的操作、对网络等的操作都应该在model里面处理，当然对业务计算等操作也是必须放在的该层的。