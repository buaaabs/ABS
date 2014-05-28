HHARobot机器人API文档
=====================

![SmartBoy](file:\\\D:\web\Workspace\AliceConnect\image\Logo.png)

HAARobot机器人是一个Android版语音聊天机器人，他能通过语音和你对话。

本软件使用了讯飞语音云平台进行语音识别，本地机器人使用Alice机器人作为引擎，内部组织了一定语料，还内部集成了jcseg分词库，对Alice机器人语料和用户输入自动进行分词处理，提高了识别准确率。

## 整个软件的结构
```
src—源文件目录  
  |—hha.aiml 包含Alice机器人的控制层代码和分词库控制层代码  
  |—hha.main Android工程的界面类和主菜单等UI  
  |—hha.util.music 包含一个封装好的音乐播放器和几个音乐播放语义xml文件识别类  
  |—hha.xf 讯飞语音云的控制层，包装了语音识别和语音合成的接口方法  
gen—Android工程自动生成的代码  
jcseg-src—jcseg分词库原代码，详见Jcseg-开发帮助文档  
alice-src—alice机器人源代码 [开发文档](http://www.geocities.ws/phelio/chatterbean/)  
  |—bitoflife.chatterbean 包含了大量chatterbean的控制层代码  
  |—bitoflife.chatterbean.aiml 包含了各个AMIL语言的标签类  
  |—bitoflife.chatterbean.config chatterbean配置类  
  |—bitoflife.chatterbean.parser 包含aiml文件解析器类，上下文解析器，各种xml文件的解析器  
  |—bitoflife.chatterbean.script 机器人的脚本引擎
  |—bitoflife.chatterbean.text 文本的标准化包
  |—bitoflife.chatterbean.util 两个小工具
assets—静态语料文件和分词库词典文件  
  |—lexicon 这个文件夹下包含大量的分词库文件  
  |—jcseg.properties 分词库的配置文件  
  |—idiom.aiml AIML基础语料库  
  |—splitters.xml 句子的分隔符库  
  |—substitutions.xml 容错库和人称转换库  
bin—编译好的Android APK文件  
libs—使用到的库目录  
  |—SpeechApi.jar 讯飞的语音识别与合成库  
  |—bsh.jar AliceBot的java脚本引擎  
  |—jcseg Jcseg分词库  
res—Android资源文件  
```

## 控制层代码详解

### hha.aiml包 —— 控制本地机器人的问答和上下文

#### Robot类

这是一个Alice机器人的顶级控制类，是控制器模型
里面可以获得拥有实质功能的AliceBot对象，context上下文对象和Graphmaster匹配树等
还有一个AssetManager进行Android目录下的Asserts文件夹内数据进行读取。
也拥有一个MainActivity的回调指针。

Robot类只会被实例化一次，主要目的是控制整个机器人的运作。

这时Robot的构造方法，需要文件读取器和主Activity的回调，用来获取初始化数据和在窗口中显示调试信息
public Robot(AssetManager am, MainActivity main)

InitRobot()方法是用于初始化整个机器人的，包括分词库等一系列必要数据。
同时Robot类是派生自Runnable接口，run方法会自动调用该初始化方法，BeginInit()方法就实现Robot的异步初始化。
在MainActivity中，可以这样初始化：
```
Robot bot = new Robot(getAssets(),this);
bot.BeginInit();
```

`public String Respond(String str)`方法接受一个用户的输入，返回一个用户的输出，若是还未初始化完成，则返回一个空串

机器人有时并不会找到答案，但会返给一个没听懂的回答，这时可以在调用Respond方法后，再调用`CanFindAnswer()`，来检查上次查询是否成功。

```
String ansString = bot.Respond(input);

if (!bot.CanFindAnswer())
	ansString = data.content; //来着讯飞的数据
```

有的时候，Respond时会触发一个命令，这些命令可以自己定义和控制。我们可以用getCommand()方法，获得上一个Respond所触发的命令。

机器人内部有一个属性表，可以采用set/getProperty方法进行设置和获取

机器人也支持运行时学习新的语料，可以用LearnFromUrl和LearnFromStream进行学习。

#### Jcseg类

这是一个静态类，用来控制分词

initDic 初始化分词词库  
initSeg 初始化Segment  
分词库的初始化会随着Robot的初始化而被初始化  
最核心的就是chineseTranslate，中文分词，只要初始化完成，可以在任何时间调用。

#### BotEmotion类

这个类是机器人的情感模型类，本身自带初始化方法，会随Robot初始化而初始化。  
这个类也是个静态类，每个参数可以方便的被获取出来。

### hha.main包 —— Android工程的界面类和主菜单等UI  

#### MainActivity类

这是一个主Activity，是Android程序的启动类，也包含大量的界面显示方法  
ShowTip() 显示一个小提示  
ShowText() 在文本框中添加一条文本  
Show() 显示文本并开始语音合成
OnCreate()方法被UI线程调用，触发整个软件的初始化工作

### hha.util.music包 —— 音乐指令解析和一个播放器

#### Player类

这是一个封装好的Android播放器类，能通过一个URL播放网络音乐。

#### SaxParseService类

这个类是负责解析百度音乐返回的xml文件，将其中的第一个url作为音乐url返回

#### GetMusicUrl类

这个类提供一些静态方法，最重要的方法就是`getMusic`
你可以传入一个歌名和一个歌手，然后返回一个默认URL
```
public static String getMusic(String name,String singer)
```

### hha.xf包 —— 讯飞语音云的控制层

![xunfei](http://www.voicecloud.cn/images/iflyban.gif)

#### NetRobot类

这个类是讯飞的控制类，负责网络语义理解的接口操作

你可以用`BeginSpeechUnderstand`和`EndSpeechUnderstand`两个方法来控制讯飞开始和关闭语音录入并理解。

`SpeechUnderstanderListener`是一个巨大的回调函数类，你可以重载其中的方法来实现回调。
这里最重要的是`onResult`方法，它会在每次有数据返回时都被触发，并且他下面的函数是运1行在UI线程下的，可以直接进行UI控制，当然，不要做过于复杂的事情。


## AliceBot代码详解


## 附录 AliceBot机器人相关文档资料

[官网帮助文档 http://www.alicebot.org/documentation/](http://www.alicebot.org/documentation/)  
[chatterbean官方帮助文档 http://www.geocities.ws/phelio/chatterbean](http://www.geocities.ws/phelio/chatterbean)  
[人工智能：用AIML写一个机器人 http://lcllcl987.iteye.com/blog/473256](http://lcllcl987.iteye.com/blog/473256)  
[聊天机器人文档（AIML）http://blog.csdn.net/wxlfight/article/details/8093792](http://blog.csdn.net/wxlfight/article/details/8093792)  
[AIML聊天机器人 http://blog.sina.com.cn/s/blog_933dc435010148dn.html](http://blog.sina.com.cn/s/blog_933dc435010148dn.html)  
[aiml规范研究文档 http://blog.csdn.net/woowindice/article/details/302298](http://blog.csdn.net/woowindice/article/details/302298)  
[leo108AIML Program-O http://leo108.com/pid-tag/aiml](http://leo108.com/pid-tag/aiml)  
