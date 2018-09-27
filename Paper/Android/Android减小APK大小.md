###APK的组成结构###
META-INF：包含CERT.SF和CERT.RSA签名文件，和MANIFEST.MF清单文件。  
assets：包含APP的assets资源，代码中可以通过AssetManager对象访问。  
res：包含没有编译到resources.arsc中的资源。  
lib：包含为特定处理器编译的代码。这个目录包含不同平台类型的子目录，如armeabi，armeabi-v7a,arm64-v8a,x86,x86_64和mips。  
resources.arsc：包含被编译的资源。这个文件包含来自res/values目录下所有配置的XML内容。打包工具提取XML内容，将它们编译为二进制的形式，并且打包。这些内容包含语言字符串和样式，和在resources.arsc文件不包含的内容的路径，如布局文件和图片。  
classes.dex：包含被编译的类，以DalvikART虚拟机能理解的dex文件格式。  
AndroidManifest.xml：核心的Android清单文件。这个文件罗列了APP名称，版本，访问权限，和APP引用的库文件。这个文件使用Android的二进制XML格式。
###减小APK的大小的方式汇总###
1 Lint工具  
使用Android Lint工具，检测res目录下没有被引用的资源（并没有扫描assets目录下的资源）。  
当lint工具在你的项目中发现一个潜在的未使用的资源，它会打印一条如下示例的消息。只提醒，不主动移除。 

    res/layout/preferences.xml: Warning: The resource R.layout.preferences appears to be unused [UnusedResources]  
2 使用Gradle的ShrinkResources  
在Gradle中将shrinkResources和minifyEnabled设置为true。这样在构建的过程中，首先ProGuard会移除没有使用的代码，接着Gradle会移除没有使用的资源。  

    android{   
        buildTypes{   
            release{   
                minifyEnabled true   
                shrinkResources true   
                proguardFiles getDefaultProguardFile('proguard-android.txt'),'proguard-rules.pro'   
           }   
       }   
    }   
在Proguard中，是否保留符号表对APP的大小是有显著的影响的，可酌情注释下面这行代码，但是建议尽量保留，它可以用于保留调试信息。

    -keepattributes SourceFile,LineNumberTable  
3 使用一套资源  
对于绝大对数APP来说，只需要取一套设计图就足够了。鉴于现在分辨率的趋势，建议取720p的资源，放到xhdpi目录。在视觉上差别不大，很多大公司的产品也是如此，但却能显著的减少资源占用大小，这里不是说把非xhdpi的目录都删除，而是强调保留一套设计资源就够了。  
4 只保留中文的语言资源  
大部分应用其实并不需要支持几十种语言的国际化支持。强大的gradle支持语言的配置，比如国内应用只支持中文：

    android {  
        defaultConfig {  
            resConfigs "zh"  
        }  
    }  
5 关于PNG图片和JPG图片  
Android打包本身会对png进行无损压缩，Android的界面能用png最好是用png，因为32位的png颜色过渡平滑且支持透明。在res下这些icon用的都是png格式，就是说Google推荐使用的是png格式的图片。  
TinyPNG使用智能有损压缩技术，在尽量少的损失下来减少PNG文件的大小。具体TinyPNG的信息请访问https://tinypng.com/。  
jpg是像素化压缩过的图片，如果对于非透明的大图，jpg将会比png的大小有显著的优势，虽然不是绝对的，但是通常会减小到一半都不止。jpg质量已经下降了，再拿来做9path的按钮和平铺拉伸的控件必然惨不忍睹，要尽量避免。一些背景，启动页，宣传页的PNG图片比较大，这些图片图形比较复杂，如果转用有损JPG可能只有不到一半（当然是有损，不过通过设置压缩参数可以这种损失比较小到忽略）。  
6 关于WEBP图片  
相对于jpg、png，webp作为一种新的图片格式，压缩比比jpg更高但显示效果却不输于jpg，Android 4.0+才原生支持webp, 但是我们的app是兼容2.3+，所以4.0以下的设备将无法看到图片但不会崩溃。限于Android的支持情况暂时还没用在手机端广泛应用起来。并且直到Android 4.2.1+才支持显示含透明度的webp，官方评测quality参数等于75均衡最佳。    
官方介绍： https://developers.google.com/speed/webp/docs/precompiled    
7 覆盖某些图片  
一些aar库里面包含根本就没有用的图。最典型的是support-v4兼容库中包含一些“可能”用到的图片，实际上在你的app中不会用到。可以考虑把几张大一些的图用1×1的图片替换，如果9patch图的话，要做成3×3的9patch图替换。  
同理可用于覆盖第三方库中我们用不到的大图，可以在/build/intermediates/exploded-aar/下的各个aar库的res目录查找检验。    
8 删除armable-v7和x86包下的so     
基本上armable的so也是兼容armable-v7的，armable-v7a的库会对图形渲染方面有很大的改进，如果没有这方面的要求，可以精简。这里不排除有极少数设备会Crash，请务必测试周全后再发布。      
x86包下的so在x86型号的手机是需要的，如果产品没用这方面的要求也可以精简。      
建议实际工作的配置是只保留armable、armable-x86下的so文件，算是一个折中的方案。     
我们可以构建一个APK，它支持所有的 CPU 类型。但是反过来，我们可以为每个 CPU 类型都单独构建一个 APK，然后不同 CPU 类型的设备安装对应的 APK 即可，当然前提是应用市场得提供用户设备 CPU 类型设别的支持，就目前来说，至少 PLAY 市场是支持的。    
Gradle 可以通过以下配置生成不同 ABI 支持的 APK：    

    android {  
        ...  
        splits {  
            abi {  
                enable true  
                reset()  
                include 'x86', 'x86_64', 'armeabi-v7a', 'arm64-v8a' //select ABIs to build APKs for  
                universalApk true //generate an additional APK that contains all the ABIs  
            }  
        }  
        // map for the version code  
        project.ext.versionCodes = ['armeabi': 1, 'armeabi-v7a': 2, 'arm64-v8a': 3, 'mips': 5, 'mips64': 6, 'x86': 8, 'x86_64': 9]  
        android.applicationVariants.all { variant ->  
            // assign different version code for each output  
            variant.outputs.each { output ->  
                output.versionCodeOverride =  
                        project.ext.versionCodes.get(output.getFilter(com.android.build.OutputFile.ABI), 0) * 1000000 + android.defaultConfig.versionCode  
            }  
        }  
     }  
9 微信资源压缩打包  
微信中的资源混淆工具主要为了混淆资源ID长度(例如将res/drawable/welcome.png混淆为r/s/a.png)，同时利用7zip深度压缩，大大减少了安装包体积，同时也增加了逼格，提升了反破解难度。效果非常的好，强烈推荐。  
具体使用请查看：https://github.com/shwenzhang/AndResGuard  
10 proguard深度混淆代码  
之前为了简单起见，很多包都直接忽略了，现在启动严格模式，把能混淆的都混淆了。  
11 表情包在线化   
虽然应用的表情不多，只有50来个，但是如果能把这部分表情放到网上，不仅能有效减小apk大小，还可以方便后期扩展支持。  
12 着色方案以及使用shape背景  
相信你的工程里也有很多selector文件，也有很多相似的图片只是颜色不同，通过着色方案我们能大大减轻这样的工作量，减少这样的文件。借助于androidsupport库可实现一个全版本兼容的着色方案。  
具体实现原理请查看：http://www.race604.com/tint-drawable/  
在扁平化盛行的当下，很多纯色的渐变的圆角的图片都可以用shape实现，代码灵活可控，省去了大量的背景图片。
13 去除重复库
比如UIL和picasso，两个重复的图片下载库是完全没用必要的。  
14 去除无用库  
有一些鸡肋的功能或者库，是几无用处的。不如去掉。  
15 插件化  
插件化技术支持动态的加载代码和动态的加载资源，把APP的一部分分离出来了，对于业务庞大的项目来说非常有用，极大的分解了APP大小。因为插件化技术需要一定的技术保障和服务端系统支持，有一定的风险，如无必要（比如一些小型项目，也没什么扩展业务）就不需要了，建议酌情选择。  
16 资源的重用  
同一个图片的着色，阴影，或者旋转版本等等，可以重用同一个资源集合和定制它们。  
比如避免帧动画的使用就是一个很好的例子（因为帧动画每一帧都必须是一张明确的图片文件）。  
17 矢量图的使用   
矢量图在Android中以VectorDrawable对象代表（Android L版本引入）。使用VectorDrawable对象，允许开发人员以纯代码方式生成类似绘制的效果。一个100-byte文件可以生成一个屏幕大小清晰图像。   
然而，每个VectorDrawable对象的渲染明显的消耗系统时间，并且更大的图片需要更长的时间来展示在屏幕上。因此仅仅在显示小的图片的时候考虑使用矢量图。   
具体使用请查看：http://mobile.51cto.com/news-478709.htm  
18 移除枚举    
一个枚举会增加你的app的class.dex文件大约1.0到1.4K的大小。对于复杂的系统或者共享库这种情况会更加明显。如果可能的话，考虑使用@IntDef和ProGuard来剥离枚举，并转换成Integer。这种类型转换保留了所有枚举的安全效益。