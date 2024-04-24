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
ACM模式：新建数据结构类，建议与原始类并列写在下方（或着写成内部静态类），内部非静态类，需要先实例会外部类再实例化内部类
外部并列类：在同一个源文件中并列写多个类，但只能有一个公共（public）类，并且该类的名称必须与文件名相同。如果有多个非公共类，则可以在同一个文件中并列写它们，但它们的可见性只在同一个包内。
```

