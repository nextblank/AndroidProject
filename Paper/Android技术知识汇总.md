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