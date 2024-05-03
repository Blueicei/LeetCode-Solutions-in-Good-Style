```
Typora恢复
C:\Users\wolf\AppData\Roaming\Typora\draftsRecover

Nacos后台启动
D:\Program Files\nacos-server-2.3.2\nacos\bin
startup.cmd -m standalone
http://localhost:8848/nacos/

Sentinel后台启动
D:\Program Files\nacos-server-2.3.2
java -jar sentinel-dashboard-1.8.7.jar
后台8719默认
前台8080开启
http://localhost:8080/#/login
sentinel
sentinel

JMeter
D:\Program Files\nacos-server-2.3.2\apache-jmeter-5.6.3\bin
双击jmeter.bat

Copy Configuration 复制Provider另起端口9002，共享一个application name
-Dserver.port=9002

tcp长连接需要服务端操作系统分配一个线程来维持吗，多路复用中的多路实际物理存在形式是什么？
1.在一个TCP长连接中，它的状态是由操作系统内核中的一个文件描述符来表示的，而不会为每个请求都创建一个新的文件描述符。当有新的请求到来时，操作系统会更新当前TCP连接的文件描述符的状态，而不是创建一个新的文件描述符。
2.对于事件驱动模型如epoll，当一个TCP长连接上有数据到达时，操作系统会更新该连接的文件描述符的状态，通知应用程序准备好去读取或写入数据。应用程序通过检测这个文件描述符的状态变化，能够及时进行相应的操作而不是为每个请求创建一个新的文件描述符。
3.多路复用技术实际上是通过一个线程同时监听多个文件描述符（套接字）的 I/O 事件，当有事件发生时，通知应用程序进行相应的处理。这样可以避免为每个连接分配一个独立的线程，提高系统的并发处理能力。
```



验证鉴权思路

```
版本：
java21(17) + spring.boot 3.2.0 + spring.cloud 2023.0.0 + spring.cloud.alibaba 2022.0.0.0
maven3.9.6 + mysql 8.0.36 + redis 5.0.14.1 + nacos 2.3.2 + sentinel 1.8.7 + jmeter 5.6.3
使用组件：
gateway + spring security + jwt + redis(nacos动态配置)
gateway:暴漏后台唯一端口，拦截全局请求，路由分发（lb负载均衡byApplicationName）
spring security:现成的验证和鉴权框架（自定义验证鉴权流程，exception handler处理未通过请求返回定制body），预设好强大的安全过滤链，service方法级的权限校验@PreAuthorize("hasRole("ADMIN")")
jwt:创建Authorization header token，保存用户信息和过期时间，服务端加盐（头部，载荷，签名），解析Claim
redis:保存角色-uri分支权限（nacos动态配置）
userdetails：保存用户名，密码，角色信息，对密码保护加密
ouath2: 授权第三方应用访问受保护资源框架

抽象流程：
1.暴漏微服务唯一对外端口（gateway），在到达具体controller接口前收集全局请求，过滤
2.自由放行白名单接口，用来create/refresh/validate token
3.拦截其余所有接口请求，认证+鉴权，解析jwt token(jwt excpetion token为空，格式不正确，解密失败，token过期)，对比userdetails.loadByUsername获取到角色权限，reids中获取角色下uri权限，对比请求uri分支有无交集，放行
4.catch抛出的各种类型exception，定制response

gategway与web互斥，内置webflux，响应式构建异步、非阻塞请求。能够处理高并发请求，效率远高于web

AuthenticationManager & AuthorizaitonManager：自定义认证，鉴权流程

SecurityExpressionRoot:@PreAuthority注解实现类，验证principal中的getAuthorities

SecurityContextHolder.getContext().setAuthentication(authenticationToken);获取当前线程中ThreadLocal保存的用户权限信息，但是在微服务调用链中某个环节可能会新建/覆盖线程丢失权限信息（controller->service）
管理SecurityContext. 管理对象SecurityContextHolder。MODE_THREALOCAL: 允许每个线程在安全上下文存储自己的详细信息，

spring security6.2缺点：
1.面向场景主要还是servlet-template前后端不分离，更别提微服务了，权限信息无法一直跟随调用链传递
2.集成度太高，流程刻板，自定义困难，可查阅文档和开源项目少
3.过滤链冗余，有些自定义的甚至重复过滤，增加系统负担

失败探索:
1.开源项目目录结构太复杂，调用关系太复杂，找不到切入点，只能从spring security官方文档的demo入手，但官方文档也不符合开箱即用的理解，还是前后端不分离的
2.找切入点找了很久，一直在spring security开源项目的代码上浪费时间。应该首先理清整个验证链逻辑，从gateway入手拦截到各个微服务请求，又得知spring security过滤在gateway之前，正好验证白名单放行，其他访问请求需要走一个验证流程。接下来就是自定义这个验证流程，create jwt token，解析token，对比权限
3.SecurityContextHolder.getContext().setAuthentication(*): 当前线程中ThreadLocal保存的用户权限信息，但是在微服务调用链中某个环节可能会新建/覆盖线程丢失权限信息（controller->service）——未解决，考虑在AuthenticationManager中把setAuthentication(*)方法绑定到调用链上（参考demo6, cloud-gateway:JwtAuthenticationManager）
4.鉴权从具体微服务sevice method层转移到url层中，只要在任一url传递链上进行过滤就行了
```



```
SecurityExpressionRoot
AuthorizationManagerBeforeMethodInterceptor
ThreadLocalSecurityContextHolderStrategy

——————
UsernamePasswordAuthenticationToken
UsernamePasswordAuthenticationToken
DefaultMethodSecurityExpressionHandler
AnonymousAuthenticationToken

——————————
normal
null
DefaultMethodSecurityExpressionHandler
UsernamePasswordAuthenticationToken

spring-security JwtAuthenticationManager（ReactiveAuthenticationManager）.addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION);
```



```
您好面试官，我先自我介绍一下，我叫付珂，今年24岁，家在山东济宁。目前位于山东省济南市，就读于山东大学软件学院攻读硕士学位，预计25年毕业，正在寻求一份前端开发的暑期实习，对于实习地点没有要求，实习时间可以从现在开始到春节前结束。

CPU不能直接访问物理内存地址，通过操作系统划分出一部分高速虚拟内存，间接映射，一是由于程序大小不可控，在用户感知上需要划分出足够大的逻辑区域，也就相当于对物理内存扩容，由于程序可分性，理论上可以通过局部性特性在一块限制大小的内存区域上通过换入换出实现承载超过区域大小的效果。二是不直接操作物理内存，是考虑到多线程并发的情况，开辟一块缓冲地带，交由操作系统规划对同一块物理内存的读写顺序。
如果说上面是软件逻辑层面对空间和资源在保证正确性下的更高效利用。CPU高速缓存就是在物理上加速其原子操作，不受操作系统调控，机械式地缓存连续空间，缩短两端速度不匹配造成的一方等待问题，同时为了提高缓存命中率，可以在编写程序时考虑该情况，尽量使用连续空间，逻辑分支可预测。
内存的碎片化和泄露问题。可以通过对于内存空间的逻辑组织尽量消弱，因为释放后的无论复制还是整理都无比低效，只能用分页方法用足够小的页作为划分组织单元，尽量减小实际浪费。分页的前提是基于程序/数据可分+容忍地址不连续带来的频繁IO的开销（因为碎片本质上还是存在，只是这里能利用起来，代价就是碎片不连续，不能一次获取到连续的空间，需要指针在行之间来回挪动，类似用遍历a[j][i]）

malloc只是在开辟用户态内存中原有预留出来的空闲地址，实际存在但逻辑上未被使用

一切存在的需要载体的，但载体都是有边界的，而货物理论上是可以无限增长的也就是总会大于边界，载体的扩充伴随开销，合理利用资源，花多少钱办多少事就是一个权衡问题，没有最优解。冯诺依曼体系结构。内存有什么用，解决双端速度不匹配，但这种解决思路不能无限套娃，必须在一处终结，那就是内存。首先要保证基本的承载需求，程序或者数据的大小是可以无穷大的，但现实世界物理存储是要有边界的，存储足够大的空间（2T），就面临速度差极大，优点就是空间是内存的数倍足够覆盖内存换入换出的需求
1.存储是有边界的，而且硬件是你不能决定的（或者上位分配的资源足够应付派发的任务）
2.内存换入换出的目的还是基于内存大小，协商一块面向程序和数据的逻辑边界，在限制了无界数据的大小的情况下满足任务能够完成（在边界下能完成是因为本身是线性可分的——局部性理论，存在部分之间不会相互影响的最小单元且完成子任务可以推进主任务进度至完成，可微），IO速度也有保障，还有硬盘给他托底
3.一旦内存实在紧张，频繁换入换出导致恶性循环，负载非但没有减轻反而加重，就会崩溃OOM
4.32位3G 64位128T。虚拟内存页也会占内存，实际到不了128T就会OOM，还是要看实际物理内存，开启swap就可以是128T。32位3G申请不了8G是因为3G就是虚拟内存的极限了，超过了地址空间都没了，分配了也找不到

预读机制：靠近当前被访问数据的数据，在未来很大概率会被访问到。缓存污染：全表扫描
```



```
ACM模式：新建数据结构类，建议与原始类并列写在下方，内部静态类，内部非静态类（需要先实例会外部类再实例化内部类）
外部并列类：在同一个源文件中并列写多个类，但只能有一个公共（public）类，并且该类的名称必须与文件名相同。如果有多个非公共类，则可以在同一个文件中并列写它们，但它们的可见性只在同一个包内。
静态内部类：首先跟其他静态方法调用一致，特点在main方法中可以直接实例化（不需要先实例化外部类，或者通过外部类类名引用），可以被正常实例化成对象，可以被GC回收
匿名内部类：实现接口或抽象类的实例化，new Interface(){@Override...}
Lamda表达式：原理与匿名内部类一致，写法简化了，同时只能实现一个接口的方法

private vs 不加修饰符
private：只能在声明它们的类内部访问
默认（不加修饰符）：在同一个包内可见，对同一个包中的其他类是可见的
1.private是最小权限，默认次之
2.同一包不一定是指在同一文件夹下，指在逻辑上的统一命名空间下，只要声明了同一package地址即可
```



```
关于继承的原理，子类和父类之间的关系
面向对象特点封装，继承，多态。继承都是单继承，拥有父类的所有变量和方法，可以重写(也就是重名)，引用时会先在子类内部再到父类(不会找父类的父类)
重名也可用this,super，无前缀(局部变量)来区分
子类构造函数默认实现父类无惨构造函数，隐式实例化
重写是默认的(检测重名，但是有两小一大的限制)，@Override只起伪代码作用，方便阅读父类不存在会报错

ArrayList源码
randomaccess需要空间连续，因此arraylist都是以开辟一块全新的连续空间来扩容
默认空，基础容量10，自动扩充1.5倍，dataElement.legnth>=size

HashMap源码
hashmap空间容量以2^n为单位，-1=尾数都为1，&运算就等价于(n-1)&hash=hash%n，且位与运算远快于取模

泛型
泛型编译时不确定，运行时才确定。类内只能保证派生自object类，因此只能调用object方法。编译时感知不到谁在调用实例他，运行时才知道
<？extends Comparable>就是想在Object之外调用一些其它类方法，就用类绑定限定泛型父类，同时可以调用父类方法——可以绑定类和接口，因此可以&多绑定
区分?和T，T只能用于声明，不参与实例化。?只用于填充。Point<?> point；

目前项目的数据数量级level：
时间：车数量-点数量-轨迹车数量-轨迹数量
2021-02-01：120w-300w-25w-100w
02/01-02/28：400w-7.5y-245w-6143w
卡口选取：47km*22km 2424个（有经纬度的1880）
出租车：2023.4-5月 6829辆出租车，gps采样率15s 
一辆车一天平均3500个点，30个订单，10小时，250km。一个月90w个点，600个订单

数据库和中间件选型和比较
clickhouse vs hadoop+hbase vs kudu
京东轨迹数据库细节（组织架构和应用场景）

```



```
排序算法:
选择，冒泡，插入：都是n^2，但如果原数组局部有序，冒泡和插入能优化到n，而且插入的原子操作比冒泡的swap简单

局部有序的情况下，冒泡能提前终止，插入能线性排序的原因
冒泡：尾部序列位置绝对正确的情况下，首部相对有序即可中断（两两之间&&有序）
插入：保证首部相对有序，尾部元素不破坏有序序列即可，且插入首部序列时可提前终止

希尔比插入好在哪（n^2 -> n^1.3）：
插入排序移动效率低，特别是较小值集中在后半段时，提前跨步交换在期望上能缩短时间
最差情况，每次局部排序对整体收益不会有负面影响（原理是每次交换的步骤，在插入排序中都是必然的，且compare操作耗时可不记——只受数组长度数量级影响，能尽早在大跨步下完成交换都能缩短时间）

自适应性：每次操作都是对于整体排序都是正向的，因此可以简化后续操作（冒泡和插入的提前终止，选择就不具备）

快排：快排是排序算法中最合适的，时间复杂度O(nlogn)，空间复杂度O(1)*递归栈帧O(logn)，最差O(n)。非稳定。
时间复杂度优于n^2算法（优化了操作总量，一步到位），空间复杂度又优于nlogn算法，由于局部连续的特性比堆排序对于元素访问顺序快。
最差情况是完全有序数组，划分永远是0和n-1子数组，时间复杂度O(n^2)，同时空间复杂度受递归栈帧影响到O(n)

归并：时间复杂度稳定，空间复杂度都是O(n)
堆排序：非稳定，访问元素开销大。适合作为优先队列和topk

桶排序，计数排序，基数排序：
基数排序和计数排序都可以看作是桶排序。
都是类似hash，按索引存放元素再合并，需要已知单边边界，按步长划分索引范围。区别是计数是一种特殊情况，不需要重排序桶内元素，将元素直接映射到hash索引，统计数量，限制是元素需要为整数（hash key可数且能覆盖到可能出现的所有元素），且范围小（key数量不太多，O(n+m)m如果过大就很慢）。要求非负整数是hash中key值无序，数组下标有序但需要从0开始，将count作为累计offset（前缀和）
基数排序是从个位开始，逐位进行计数排序，特点是可以覆盖的范围大，但位数不能过大，时间复杂度是O(nk)
稳定性：计数排序counter前缀和计算出来的下标在末尾，取其中的元素是顺序取从0，因此要倒叙遍历原数组放入矩阵行中

循环不变量：跟目标结果有关，是迭代中不变的性质，用于理清思路。举例：原数组重组元素，外层遍历数组，内层按条件指针移动，指针即为不变量（不随外层遍历而改变）

排序算法的思想潜藏着各种解题思路：
1.归并排序（merge sort）拆分再合并的思想，比暴力遍历n^2要省时
```

