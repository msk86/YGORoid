# YGORoid
An Android app that can emulate Yo-Gi-Oh Card game. You can play Yo-Gi-Oh on Android devices with others who use real deck.

# Screenshots
![pic1](https://imgsa.baidu.com/forum/w%3D580/sign=3c36844745c2d562f208d0e5d71190f3/1f74f3fb43166d22ca443fb34c2309f79052d271.jpg)
![pic2](https://imgsa.baidu.com/forum/w%3D580/sign=53c5a422efdde711e7d243fe97eecef4/27ad0e4a20a446238f50b1459222720e0df3d78a.jpg)

# Features
* Deck builder
  * Multi deck
  * Support self-defined cards
* YGO game under Master4 (Link monsters)
  * Duel
  * Change side cards
* LP calculator for 2 players
* Dice and Coin
* Quick card search
* Customised Background and Card Cover
* Multi-language(简体中文, 繁体中文, 日本語, English)

# Setup
You need to setup the card database and images by yourself (Online setup is disabled due to resourse limitation) or install YGO Mobile.

* Database: Find a latest `card.cdb` file from `ygocore`, download it and copy it to: 
  * `/Device/YGORoid/cards.cdb` or `{ExternalStorage}/YGORoid/cards.cdb` if you use a SD card.

* Images: Find a ygocore version of images and copy to:
  * `/Device/YGORoid/pics/` or `{ExternalStorage}/YGORoid/pics/` if you use a SD card.
  * Also support `pics.zip` in `/pics/` folder.
  
* Customizations
  * Background: `/Device/YGORoid/texture/dueldisk.png`
  * Card cover: `/Device/YGORoid/texture/cover.jpg`
  * Self-defined card images: `/Device/YGORoid/userDefined/{CardName}.jpg`


# Short user guide
All actions can be done by `click`, `double click`, `press`, `drag and drop`, `return button`, `menu button`, `volumn buttons`.
* `click`: usually to select things(cards/deck) with highlights. Throw `coin` and `dice`. Change `LP`. etc.
* `double click`: Flip cards. Open `deck`, `ex`, `gravyard`, `banished`, `temp`, `XYZ ORUs`. Create new Token on empty field. etc.
* `drag and drop`: Move, overray, etc.
* `return button`: Close window. etc.
* `menu button`: Open different menus base on selection (nothing, card, deck, etc.) (drag the infomation bar on the bottom up can also open the menu)
* `volumn buttons`: Increase/decrease indicator. Sort/suffle deck in deck builder.
* `gravity`: auto open/set hand cards.


# Something want to say
1. I don't have lot's of Android mobile devices, there might be some compability issues in this APP which haven't been tested out.
2. I don't have any online resource anymore (I use to have it) so the online database and images installation is disabled.
3. Sorry I don't have time to build an user guide, so it maybe hard to start... check it in this readme.
4. This APP is not under active development, but I will keep maintaining it, if you find any new issue, please raise git issue or send me email to my new mailbox: `wenbo.alex.fan@gmail.com`

# Development
No dependency management, No build script, No test. I started this when I was a non-android developer (still not), I didn't know those practices when started. Because of the low frequency of release, I have no motivation to introduce these practice anymore, so there is not package management and build at this moment.

If anyone want to contribute about this, feel free to make a pull request.

# Release notes
=========1.33=========
1. 大师4
2. 组卡时增加条件检索；
3. 支持日文系统；
4. 修复BUG若干。

=========1.32=========
1. 数据库卡图兼容ygomobile

=========1.30=========
1. 增加摇摆区，修改为标准决斗盘布局；
2. 增加了一个非常好用的双人LP计算器（只为了这个计算器，大家也要更新啊）；
3. 稍微修改了一些操作方法（主要是取消了返回键打开卡组列表的功能，打开XYZ怪兽素材列表请选中后双击）；
4. BUG修复。

=========1.25=========
1. 修复Bug。（搜索通常魔法和陷阱。）
2. 增加自动更新数据库的开关。（关闭后可以手动更新数据库文件而不会被自动更新再次覆盖。）
3. 优化内存使用。

=========1.24=========
1. 组卡时增加条件检索；
2. 支持日文系统；
3. 修复BUG若干。

=========1.23=========
1. 支持繁体中文；
2. 下载卡图速度提升；
3. 下载可以后台下载；
4. 修复Bug若干；
5. APP名称变更为YGORoid。

=========1.22=========
1. 修复英文操作系统卡片查找策略。

=========1.21=========
1. 自动更新数据库和卡图文件；
2. Roid可切换为英文并下载英文版数据库(动画卡等无法下载卡图)；
3. 增加作者和MyCard的Logo图片；
4. 内嵌简单的操作说明；
5. 程序自带背景图一张；
6. 修复Bug若干；
7. 正式发布到Google Play。
通知：YGORoid已经和MyCard做了整合，利用MyCard的资源为大家提供自动数据库和卡图更新。现在更名为MC YGORoid，望周知。同时，安装新apk时，会当做全新的程序安装，所以需要卸载老版本的程序，YGORoid文件夹无需改变。

=========1.20=========
1. 有卡图的TOKEN可以显示效果文字。
2. 新版本检测时会提示更新内容。
3. 洗牌算法优化。
4. 增加简易卡查（反口胡卡查，只能用卡名查找，决斗中可使用）。
5. 增加简易组卡器。
6. BUG修复。

组卡器说明：
1. 通过决斗盘菜单进入组卡器；
2. 组卡器中，输入查询关键字，会显示查询结果列表；
3. 点击查询结果列表中的卡片，会选中卡片并显示卡图；
4. 点击选中卡片，会把卡片加入卡组（白色高亮的被选择区域）中；
5. 点击卡组上半部分，会高亮主卡组和额外卡组部分；点击卡组下半部分，会高亮副卡组部分；
6. 双击卡组中的卡片，可以删除该卡片；
7. 选中卡片后，底部信息栏会有简要提示；
8. 点击底部信息栏，可以打开详细卡片信息窗口；
9. 详细信息窗口打开时，点击查询列表中的卡片，可以直接切换信息窗口的内容；
10. 详细信息窗口可使用“返回”键关闭；
11. 音量+，卡组排序；音量-，卡组打乱；
12. 点击“打开”按钮，可以选择要打开的卡组；
13. 新建卡组通过“打开”“新卡组...”操作；
14. 点击“保存”按钮，会保存当前卡组，新卡组需输入卡组名；
15. 点击“另存”按钮，会要求输入新卡组名称并保存；
16. “退出”键可以返回决斗盘，并直接使用组卡器当前卡组。

=========1.15=========
紧急修正BUG：
1. 修正重力感应失效的问题

=========1.14=========

修正BUG：
1. 增加了临时区菜单，修正临时区无法切洗的问题；
2. 修正了新装用户无法看到所有资源下载，只能看到最新升级包的问题；
3. 其他BUG若干；

新功能：
1. 优化菜单呼出操作（应对MX2等机型），拖动下方信息栏，即可呼出菜单；
2. 优化打开卡片列表操作（应对MX2等机型），选中卡组、XYZ怪兽等，双击下方信息栏，即可打开卡片列表；
3. 增加“开关设置”，内含“重力感应”，“自动切洗卡组”，“FPS显示”三个开关（自动切洗卡组：打开卡组查看窗口，关闭后是否自动切洗卡组）；
4. 提高卡组读取速度；

5. 简化换Side操作，选中卡片，双击想要换Side的卡片即可。

=========1.13=========

修正BUG：
1. 修正显示卡片详细信息时，有时候会灰屏；
2. 修正很多卡片都可以生成有名称Token的问题(因为CORE数据库中，替罪羊数据有误，故现在替罪羊无法生成TOKEN，请等待CORE修正)；
3. 修正场上有守备怪兽存在，换Side，该怪兽也守备的问题；
4. 修正了没有卡图时，查看卡片效果容易崩溃的问题；

新功能：
1. 可以自动提示更新卡片数据：数据库、图片包（更新的图片包名称格式为：pics-2013.07.13.zip，直接放在pics文件夹下即可，不用解压）；
2. 新增LP四则运算功能：调整LP时，支持“-+=*/#”操作符（例如输入“-400”，确定后LP减少400；另外“#”和“/”都是除法操作；无操作符直接输入400保持原样，为LP变为400）；
3. 增加配置功能，可配置重力感应是否打开，默认关闭（运行程序后，在程序YGORoid目录下，会生成config.properties配置文件，用文本编辑器打开后，修改GRAVITY的值为0即可关闭重力感应）；

=========1.12=========

新功能：
1. 增加了软件新版本检测功能，支持自动打开网盘下载；
2. 集成金数据表单，可以直接使用程序内嵌的金数据表单进行问题反馈；
3. 增加了批量检索功能：在卡组、墓地、除外、ORU列表中，双击卡片，可以把卡片放置在临时区，节省了重复打开卡组的操作；
4. 界面美化，去除了灰色背景，改为半透明；
5. 支持读取zip包（要求将pics.zip放在pics文件夹内；pics.zip中，图片都在pics文件夹内。即 YGORoid/pics/pics.zip[pics/12345678.jpg]

修正：
1. 彻底修正了中文卡组文件乱码问题（只支持GBK和UTF-8）；
2. 修正双击/拖动信息窗口时，程序崩溃的问题；
3. 修正了带有ORU的XYZ怪兽，改为守备后直接里侧除外、回卡组底部时，ORU卡片消失的问题；
4. 修正了无卡图时，查看卡片效果窗口中，怪兽卡使用错误卡图模板的问题；
5. 修正了换SIDE时，也能呼出菜单的问题；
6. 修正了XYZ怪兽打开ORU列表时，能双击覆盖素材的问题；

=========1.11=========

新添加功能：
1. 双击卡组、额外、墓地、除外、临时，会打开卡片选择器（即取消了双击卡组切洗功能，需要通过菜单切洗）
2. 手牌洗切
3. 卡牌拖动至魔法陷阱区域时，自动改为正位置摆放
4. 根据场上具体情况动态生成token（选中可以生成token的卡片后，双击空白区域）
5. 解决中文卡组文件编码问题（如果还有乱码情况，请发卡组文件给我）
6. token定义发生改变，可以用token叠放（该功能仅为一些特殊情况而改变定义，如NTR等。）
7. 额外区怪兽当需要回到卡组最底时自动回到EX卡组。
8. 首页增加卡牌Heaven，Heaven君帮助我收集需求、反馈、BUG尽心机理，管理QQ群324173920时，尽职尽责，你辛苦了。

小细节：
1. 版本中将出现当前软件版本号，已方便玩家区分。
2. 卡组菜单发生变化：原卡组菜单分成2部分，第一部分卡组菜单为：卡组洗切、顶部里侧除外、卡组翻转不变，第二部分不选中任何东西时按菜单：重新开始、更换卡组、换副卡组、镜像显示、退出。
3. Token不会出现的菜单。
4. 数据库不存在时，会给出路径提示，方便大家定位数据库文件位置。

BUG修复：
1. 修复token可以返回卡组最底而不消失的BUG
2. 修复因系统版本（4.1+）而造成的文字、指示物不显示的BUG
3. 修复了因更换side时造成的闪退或者跳出的BUG
4. 修复了token因为叠放而造成的程序显示不正常的BUG
5. 修复了浏览/检索卡片时，查看卡牌详细后无法选择拖拽的BUG
6. 修复了检索时可选择卡牌双击反面的BUG
7. 增强了重力感应的能力，目前将不会出现爪机向对手方向倾斜造成的重力感应偏差时，会向对手公布己方手牌信息的BUG

=========1.1=========

修正：
1. 未识别卡会被当做同一张卡验证
2. 休眠后再唤醒，回到初始界面
3. 界面上的英文改成中文
4. 风属性卡片不显示属性
5. 调整了一下墓地、除外、临时的顺序（手贱改的，大家抱歉，以后不会乱动布局）
6. 本机相册因为pics里图片太多太卡
- 要做的事情
- 1.运行新版程序
- 2.从设置，应用管理中清除图库（gallery）的应用数据
- 3.从设置，应用管理中清除媒体存储器（media storage）的应用数据
- 4.重启爪机

=========1.01=========

修复BUG：
1. 修复了选中卡组，打开列表，关闭列表后，按菜单键没有反应的问题；
2. 修复了通过菜单回卡底/里侧除外后，可以反复呼出菜单操作，从而复制卡片的问题；
