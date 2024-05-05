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

seata后台启动
D:\Program Files\nacos-server-2.3.2\seata\bin
seata-server.bat
http://localhost:7091/#/login
seata
seata

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



```
2024/4/30-5/3 上海学习记录
Redis AOF(Append Only File)和RDB 数据持久化
持久化写入时的数据一致性问题：
1.只读：靠虚存来共享页表，指向同一位置
2.缓存被修改：写时复制，启动AOF后台进程，写时复制跟mvcc快照类似先写入某时刻缓存快照，再写入过程中被修改的缓存
RabbitMQ 消息队列中间件
为什么要使用：
1，无论是消息队列还是微服务RPC—openfeign。关键原因就是服务解耦，如果没解耦，调用链某一点报错整体都错了，想看到具体哪里错了还要一个函数调用写容错(类似try catch)。现在我想统一一下容错方式，定位到哪个调用链上出了问题，还知道显示前面的没错，又想避免繁多的容错逻辑就用微服务或消息队列解耦合，错误信息统一回复

amqp是协议，有一套固定的框架和规范元素（Provider, Consumer, Exchange, Queue, Binding）

RabbitMQ实现流程
1.rabbitmq 生产者通过exchange路由和路由键与消费者相连，消费者还要额外指定queue来接收用于持久化和查看抽象等待队列，因为是异步所以需要可见性相当于consumer name（但不完全是consumer_name,因为一个queue可以对应对各consumer实现负载均衡）
2.queue是消息的容器，同一队列名可以实现负载均衡，round_robin轮询
3.queue和consumer的关系是一对多，反过来是一对一。也就是先判断exchange再判断key，两个consumer具有不同的exchsnge和key匹配规则，但有相同的queue，来自不同consumer的消息消息会被存放在同一个queue，之后实现负载均衡只由其中一个来消费。因此queue可以看做是基于exchange和key之上抽象引入的多路复用的思想
4.什么情况会导致消息乱序。处理顺序123，绑定多个consumer到同一个queue上了，有的返回快有的返回慢返回就按132。解决，有序消息，放入同一队列中只由一个consumer来处理，或者内部设阻塞锁。放入的顺序是123，返回的顺序132。假设单个consumer遵循单线程串行化执行模式，queue符合队列数据结构特性

为什么需要分布式集群（为什么单个节点会崩溃）
1.同一时间大量请求会使分布式节点或微服务程序崩溃，触发了oom进程被操作系统干掉了，或用户请求被降级熔断被排队等待逻辑上停滞了，或者物理层面被关闭(因为操作系统不会主动关机)。
2.操作系统层面进程崩溃分为内存回收被kill，cpu线程创建或任务堆积被kill。内存层面是硬盘换入换出恶性循环(频繁io开销本身属于额外的，开销又大)直接回收也没有足够的空间(后台回收，回收页分为文件和匿名，回收时机优先级，)

心得：
1.复习主要反思，谦虚的心哪里学的不足，以点来扩展，不要以面来浏览。主要精力探索新知识和干活
2.书读百遍，其义自见
```



```
2024/5/3 优化gateway+security+jwt统一验证+鉴权机制
之前弊端：
1.Authentication被跳过了或实际没有作用，不参与过滤链。只有Authority在工作，原理是放行/auth和/test，其他jwt请求解析请求header中的Authorization token，比对内置的User存在且没有过期则通过，否则鉴权失败交给EntryPoint返回结果
2.@PreAuthorize失效，因为Authority过程中无法保存加载的User ROLE Authority到过滤链中，只能生成一个匿名Authticattion User且无权限
3.具体模块的实际功能不符合spring security预设的期望功能，也就是能跑出期望的结果但代码不健壮，没有弄清security实际的原理和流程，不符合规范之后拓展功能留下隐患

改进：
1.设计Authentication，使其发挥出应有的作用，而不是把一切都堆到Authority，其只负责鉴权，也就是比对uri/path和ROLE_Authority
2.TokenAuthenticationConverter默认在开头解析请求header中的Authorization token，并加入到Authentication中之后的过滤链中能访问到该token。此时token为空，会自动生成一个匿名无权限的token，在之后鉴权中会拦截，这一步是考虑到/auth没有token，在鉴权中才能根据uri白名单放行，但其他uri会因为匿名权限被鉴权拦截
3.AuthenticationManager负责解析token，并根据解析过程中的各种情况携带Message抛出异常，由GlobalHandler处理返回Response。成功解析出的UsernamePasswordAuthenticationToken保存到过滤链中能随时获取到
4.AuthenticationWebFilter携带AuthenticationManagervert加入到过滤链Order.AUTHENTICATION位置
5.AuthorizationManager一部分是白名单uri放行，其余需要被鉴权。拿到AuthenticationManager中保存的AuthenticationToken，get到用户的Authority和请求中的uri，拿到redis中保存的uri-ROLE，验证是否存在交集判断是否放行。不放行则交给AccessDeniedHandler处理

经验总结：
1.别人项目的源码没有完全弄懂就着急写代码，应该要先理清整个框架，过滤链上的各个组件都扮演着什么角色，不要弄混，有了抽象的认知才能找到切入点逐个完成
2.重点还是思考>写代码，遇见困难一定要琢磨透一条线都理清楚了在开始写，可以记笔记或画图来记录思维脉络。同时遇见难点应该发散着思考一下，他的前置条件都有什么，为什么我调用不到该对象，是不是前面哪里漏了一步，我应该怎么实现该前置条件，前置都准备好了问题就是迎刃而解了copy代码就行了
3.谦虚+坚毅不倒，现阶段的做法感觉不对劲有些问题，可以先放一会但一定要契而不舍地攻克他。还是思考>坐在电脑前面对代码发呆，可以刻意去干别的，留出空闲时间好好思考，问题总会解决，不要陷入死胡同
4.commit保存先有能运行的结果，再继续探索试错，方便回滚，试错过程注意代码整洁性。


```



```
Spring源码解析
总：Spring是基础框架，提供Bean的容器，方便装载具体的Bean对象（之前使用需要具体new实例化对象，现在只需要告诉容器有哪些对象，帮我们创建并维护生命周期）。其上可扩展出完整的生态（spring boot, spring cloud...）
分：
IOC(Inversion of Control):
通常需要自己new一个对象，现在交由SpringBean容器来统一创建、管理生命周期
AOP(Aspect-Oriented Programming)：
开发业务逻辑之外的可扩展功能，日志、权限控制和事物等

IOC:
思考：
总的需求->
使用spring最方便的地方，在于不需要手动new对象，而由容器帮我们创建，并放到容器中。

用的时候怎么取出->
ApplicationContext.getBean(Person.class)，一堆Map方式存储Bean对象，循环依赖-三级缓存-三个Map结构
容器怎么知道需要创建哪些对象->xml or 注解。

->如何被容器识别并解析
xml: io stream读取到内存->SAX/dom4j(解析)->document(文件结构对象)->包含一系列node节点->解析key/value 放入某个对象中
注解: 反射->class loader->class对象.getAnnotation->判断是否加了spring特性注解@Controller@Component@Service->创建对象放入容器中
解析规则不一致但最终解析成同一个东西，接口定义不同规则（约束和规范，BeanDefinitionReader）
总结：定义创建Bean对象所需要的基本信息

->装载到容器中
Map结构存放(BeanDefinition) BeanFactory.BeanDefinitionMap(Bean定义信息)。BeanDefinition是接口，定义一系列具体的规范

-->创建完整的BeanDefiniton对象(可选，可扩展)
一系列BeanFactoryPostProcessor来set属性值，替换占位符等

->创建Bean对象：反射（效率比较低为什么用，有条件的当需要创建的对象非常多才效率低）
1.获取Class对象
Class clazz = Class.forName("包名.类名")
Class clazz = 对象.getClass();
Class clazz = 类名.class;
ClassLoader.getxxxClassLoaderr().loadClass("包名.类名");
通过类加载器获取 Class 对象不会进行初始化，意味着不进行包括初始化等一系列步骤，静态代码块和静态对象不会得到执行。仅仅获取了类的定义信息，而没有触发类的初始化过程。这种延迟加载的特性允许你在需要时才初始化类，节省了资源
2.获取构造器
Constructor ctor = clazz.getDeclaredConstructor();
3.创建对象
Object obj = ctor.newInstance()

->使用Bean对象
->销毁Bean对象

=============================================================
spring作为基础框架、基石的可扩展性。以BeanFactoryPostProcessor为例进行阐述

PostProcessor增强器，提供某些扩展功能。针对处理对象不同，分两类如下：
BeanFactoryPostProcessor -> BeanFactory
BeanPostProcessor -> Bean
什么是BeanFactory：
定义：访问SpringBean容器的root接口，功能上是bean对象创建的整体流程，Bean生命周期是一个完整标准化流程，相对比很麻烦。流水线工作
举例：ApplicationContext.getBean(Person.class)
1.ApplicationContext实现了BeanFactory，通过ApplicationContext访问容器中的Bean实际通过BeanFactory访问容器。
2.同样也可以通过BeanFactory来修改Bean，如下
什么是FactoryBean接口：
定义：用来创建Bean对象。私人定制
区别：
1.isSingleton: 判断是否是单例对象
2.getObjectType：获取返回对象的类型
3.getObject：创建对象 1)new 2)反射 3)动态代理

举例：对于xml文件 <property name="username" value="${jdbc.username}"/>
BeanDefinition需要调用PostProcessor来替换占位符
BeanFactoryPostProcessor.PlaceholderConfigurerSupport实现类替换上面的${}占位符(db.properties)
总结：
1.通过BeanFactoryPostProcessor定义完整的BeanDefinition对象
2.可扩展性=可以任意实现BeanFactoryPostProcessor来自定义修改BeanDefiniton（set属性值，替换占位符），并加载到容器中。可以@Order调整PostProcessor顺序
3.日常业务流程中一般用不到，二次开发中会用到

举例加载Bean过程的可扩展性，先有的xml方式来帮助容器识别定义Bean，后来的注解方式如何基于该流程做扩展？
xml->标准解析处理流程
注解->是推到全部重写 or 原有基础上做扩展 ->@ComponentScan @Import @ImportResource
1.注解扮演的角色就是在xml标准解析处理流程基础上导入额外对象
2.具体依靠BeanDefinitionRegistryPostProcessor：扩展原有的xml流程到注解，额外通过注解注册Bean

Interface——接口带来的可扩展性
举例：比如实现BeanDefinition接口，可以实现从不同文件格式读取Bean定义信息（不只是xml），json/properties/yml
接口和抽象类区别：
1.抽象类单继承，接口多实现。抽口定义不同规则（约束和规范）
2.内部只能有抽象方法，抽象类可以有具体的实现方法
（上面是语法上的区别，想问的是功能需求上的区别）
3.接口是自上向下的，抽象类是自下而上的，接口不需要考虑后面具体的子类实现，抽象类要抽取出子类的共性的。

除了接口还有上面可以实现可扩展性->设计模式：模板方法
举例：spirng boot嵌入tomcat，通过实现模板方法（模板方法留空带实现）
==============================================================

之前讲的都是Bean的识别和加载到Bean容器的过程，包含完整BeanDefinition的创建，是Bean对象创建的前置过程。接下来才是Bean对象的生命周期，是在Bean容器中进行的

Bean生命周期：从对象的创建到使用到销毁的过程
实例化和初始化的区别：new和setXXX()
->实例化：在堆空间中申请内存空间，对象的属性值一般是默认值。->createBeanInstance反射创建对象
->初始化属性赋值：给自定义属性赋值->populateBean.set方法完成赋值操作
->初始化属性赋值：检查aware相关接口并设置依赖（给容器对象属性赋值，类似于依赖注入）：Aware意识到，里面没有定义任何方法。->invokeAwareMethods完成赋值操作

为什么要识别Aware，Aware的主体是什么：
1.初始化就是避免成员变量为空，因为为空就没有任何意义，需要set成员变量，可以是直接值传递也可以是间接引用传递。而间接引用需要确保首先存在实例，其次知道去哪找。
2.下面可知容器对象应该是先于自定义对象被加载到容器的，属于公共资源，因此存在实例。对于那些可能要被注入/依赖的容器Bean对象，需要提供对外开放的接口/标识，便于找到对应的Bean
总结：
1.定义的成员变量，属于Bean容器中的公共资源，Bean容器需要提供对外开放的Aware接口/标识，才能在初始化赋值过程中被访问到
2.Aware主体是Bean容器的公共资源，也就是预先创建好的容器对象
spring中按照bean对象的使用者分为几类：
1.自定义对象
2.容器对象（BeanFactory, ApplicaitonContext, Environment）
谁在什么时候调用这些方法（Aware:set成员变量）？
容器 在 需要给一个统一的标识，然后在统一的地方进行处理

执行到此步骤之后，对象的创建和属性赋值都完成了，此时对象是否可以直接拿来使用了?
理论是可以的，但注意Spring要考虑扩展性
->获得普通对象，是否需要进行扩展（挂载预定义好的AOP，做Bean对象的扩展实现）
->执行前置处理方法：AOP.BeanPostProcessor.BeforeInitialization
->执行初始化方法：invokeInitMethod(90%用不到，判断逻辑监测bean是否实现了InitalizingBean接口->调用afterPropertiesSet方法（自定义额外工作）)
->执行后置处理方法：AOP.BeanPostProcessor.AfterInitialization

AOP原理：
动态代理：jdk和cglib
AOP的入口就在此处：后置处理方法
AOP挂在IOC流程中的Bean初始化过程中，也是IOC整体流程中的一个扩展点（本质是Bean的生命周期，创建流程，IOC全程参与是个抽象的概念）

代理模式：
静态代理：实现相同接口的两个类，一个业务类Service，一个代理类Proxy，实现同一个方法，Service作为Proxy成员变量在实现方法中调用自己的方法，而Proxy就在该方法前后添加业务逻辑
动态代理：从JVM角度来说，动态代理是在运行时动态生成类字节码，并加载到JVM中的。Spring AOP、RPC的实现都依赖了动态代理
1.JDK动态代理只能代理实现了接口的类或者直接代理接口，而CGLIB可以代理未实现任何接口的类。 另外，CGLIB动态代理是通过生成一个被代理类的子类来拦截被代理类的方法调用（Enhancer），因此不能代理声明为 final 类型的类和方法。
2.就二者的效率来说，大部分情况都是 JDK 动态代理更优秀，随着 JDK 版本的升级，这个优势更加明显。


此时可以使用Bean对象了
->使用Bean对象
->销毁Bean对象：从不使用，不是GC，关闭Bean容器时销毁->context.close()

源码没有那么难，只不过大家看不下去：抽丝剥茧，保留线性主干再去扩展
```



```
循环依赖
Bean默认单例模式，还有原型模式
A(b) <-> B(a)
同时自定义Bean如何作为成员遍历被初始化
三级缓存，提前暴露对象

创建A对象->实例化A对象->给A对象的b属性赋值/属性注入->A，B都是单例，先去容器内查找，是否有B对象->有即返回，没有即创建B对象，流程和创建A对象一致->...->B：去容器查找是否有A对象

将对象按照状态分类：
1.成品->完成实例化和初始化
2.半成品->完成实例化但未完成初始化

当持有某一个对象的引用后，能否在后续步骤的时候给对象进行赋值操作？可以，也就是切断最后一步形成闭环步骤
实例化和初始化可以分开执行，破坏死锁条件，重点在预判死锁的形成，阻塞其他请求，并实例化后缓存该状态，初始化后再缓存该状态，完成之前实例化引用的赋值。因此上面死锁状态更新为如下流程：
实例化A（map A:半成品）。实例化B后缓存B(map B:半成品)，初始化B时直接赋值缓存的A:半成品，缓存B(map B:成品)，回到初始化A返回B:成品，缓存A(map A:成品)

上述缓存map中一个Bean存在两种状态的key-value，冗余。因此有了三级缓存，3个map结构
一级：Map<String, Object> singletonObejcts ConcurrentHashMap.最终形态的单例bean,我们一般获取一个bean都是从这个缓存中获取
二级：Map<String, Object> earlySingletonObjects ConcurrentHashMap
三级：Map<String, ObjectFactory> singletonFactories HashMap
ObjectFactory:函数式接口，java8，可以将lambda表达式作为参数放到方法的是惨重，在方法执行时，并不会实际的调用当前lanbda表达式，只有在调用getObejct方法的时候才会调用lambda表达式
通过调用ObjectFactory的getObject()方法，就能够在需要动态代理的情况下为原始对象生成代理对象并返回，否则返回原始对象

三级缓存当中创建对象是需要牺牲一定得性能,二级缓存的作用防止在多级循环依赖的情况下重复从三级缓存当中创建对象
只有在调用了三级缓存中的ObjectFactory的getObject() 方法获取原始对象（的代理对象）时，才会将原始对象（的代理对象）放入二级缓存，而调用三级缓存中的ObjectFactory的getObject() 方法获取原始对象（的代理对象）这种情况只会发生在有循环依赖的时候，所以，二级缓存在没有循环依赖的情况下不会被使用到。二级缓存是为了提前暴露 Bean 来解决循环依赖问题，此时的 Bean 可能还没有进行属性注入，只有等完成了属性注入、初始化后的 Bean 才会上移到一级缓存（单例池）中。
根本目的是想map分开保存成品和半成品bean。而只有循环依赖的情况下才会用到半成品bean，因此需要代理对象能直接获取到单例半成品bean，因为多级循环依赖创建代理对象开销大又引入二级缓存保存创建的代理对象
这里为什么用objectFactory代理对象而不是直接保存原本的半成品bean对象？
1.当 Spring 在实例化 A 类时，会将 A 类的半成品对象放入第三级缓存中，同时返回一个代理对象，这个代理对象负责处理 A 类中对 B 类的依赖，完成B的初始化后可以通过代理对象回调完成A的初始化。
2.目的是延迟 Bean 的初始化过程，解决循环串行化必要的，B初始化完成后回调上一步A半成品继续初始化
```

