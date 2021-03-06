package com.boot;

/***
 *
 *1.devops--敏捷开发--将本地代码快速推到测试环境--docker...
 *
 * ***** oracle.com相关指令文档 *** 去官网查看
 *
 * 2.基于JDK命令工具
 *      0.-xx参数
 *             1.-XX:[+1]<name> 启用或禁用参数
 *             2.-XX:<name>=<value>
 *         -Xms 等价于 -XX:initilaHeapSize
 *         -Xmx等价于  -XX:MaxHeapSize
 *         -Xss        -XX:ThreadstackSize

 *******************************************
 *
 *     1.jinfo
 *              1、 jps / ps ..查看进程号
 *              2.获取参数值  jinfo 进程号 > tomcat.txt
*                   jinfo -flag ThreadstackSize 进程号
*                   jinfo -flags 进程号    查看进程下所有设置修改过的参数，可能是自己设置的，也可能是tomcat设置的
 *                   java -XX:PrintFlagsFinal -version 得到参数键值  = 默认值   := 被用户或者jvm修改后的值
 *                  > a.txt 重定向到文件
 *              3.less tomcat.txt ...进行查找 G ‘最后’   查看当前进程的堆栈、设置参数 开启关闭 等信息
                   没有单位默认就是byte ????
 *                  Non-default VM flags: -XX:InitialHeapSize=16777216 -XX:+ManagementServer -XX:MaxHeapSize=264241152
 *                  -XX:MaxNewSize=88080384 -XX:MinHeapDeltaBytes=131072 -XX:NewSize=5570560 -XX:OldSize=11206656
 *                  -XX:+UseFastUnorderedTimeStamps
                4.当你不知道某个参数在那个文件时候
                    grep 'JAVA_OPTS' * 去找

                5.java -XX:+PrintFlagsFinal -version 查看当前虚拟机默认JVM参数


 *      2.jstat 主要用来通过垃圾回收相关信息来判断 JVM 性能问题，也可以查看类加载、编译的情况，主要的用法是通过持续的固定时间间隔的输出来观察。
 *              比如每 3 秒打印一次 GC 回收次数，连续打印 10 次，通过动态的变化来观察 GC 是否过于密集。
 *
 *              jstat -gc 2328  5000 每隔5s输出一次
          单位：kb
                S0C    S1C    S0U    S1U      EC       EU        OC         OU       MC     MU    CCSC   CCSU   YGC     YGCT    FGC    FGCT     GCT
                960.0  960.0   0.0    0.4    8192.0   5751.2   20132.0    19609.0   12312.0 12009.2  0.0    0.0       17    0.213   1      0.022    0.235

                S0C：第一个幸存区的大小
                S1C：第二个幸存区的大小
                S0U：第一个幸存区的使用大小
                S1U：第二个幸存区的使用大小
                EC：伊甸园区的大小
                EU：伊甸园区的使用大小
                OC：老年代大小
                OU：老年代使用大小
                MC：方法区大小
                MU：方法区使用大小
                CCSC:压缩类空间大小
                CCSU:压缩类空间使用大小
                YGC：年轻代垃圾回收次数
                YGCT：年轻代垃圾回收消耗时间
                FGC：老年代垃圾回收次数
                FGCT：老年代垃圾回收消耗时间
                GCT：垃圾回收消耗总时间

            类似于jvisual中的GC --图形化 xmanager

            jvm1.8 内存结构：https://www.cnblogs.com/wengshuhang/articles/10938383.html

            问题：这里堆加起来和总大小对比，不要一看比例就，因为还会在范围内自增的。    jmap -heap 进程号 查看进程信息

 *      3.jmap  内存dump和分析
 *            方式1.-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./   -->C:\YangWenjunData\mySrc\MockFramework11
 *            方式2.使用jmap  --> jps查看已开启进程 jmap -help  jmap -dump:format=b,file=heap.hprof 进程号   ---> sz 下载
 *
 *            jvisual加载文件分析 -- jprofile初试
 *
 *       4.jstack 进程号 - 线程间状态转化 - 代码层级
 *          实战死循环导致cpu飙高：
 *                  1.现在本地此时代码可以访问
 *                  2.打包远程 --为了结合linux命令
 *                          1.mvn clean package -DMaven.test.skip
 *                          2.
 *          远程推送代码并启动：1.为了部署流程  2.为了 使用linux相关命令进行定位问题
 *                  1.切到项目目录 mvn clean package -Dmaven.test.skiip
 *                  2.nohup java -jar xxxx.jar &
 *
 *          结合Package.java
 *
 * 死循环
 *          jstack  进程号 > xx.text  --> sz xx.text --> top -p 进程号 -H （查看该进程号对应的线程号）--> 找到前几个 printf "%x" 线程号(转为16进制) --> 在本地的text文件中搜索查看对应栈信息  -> 查看调用代码位置
 *
 * 死锁
 *
 *          上面一样，访问一次下载文件查看最后 deadlock字眼
 *
 *
 *         5.jvisualVM  -- 当前是本地 如何和远程服务器建立  远程连接？
 *                 1.在java_home 找到该工具 --选择左侧java程序 点击查看具体信息
 *                 2.可以导入生成的dump文件，也可以直接查看当前线程信息。执行时间-抽样器...cpu/内存 --> ***直接动态查看***
 *                 3.添加VisualGC、btrace插件  修改插件中心的地址 对应java版本下不下来所以本地下载安装
 *                          --https://visualvm.github.io/pluginscenters.html
 *                          --https://visualvm.github.io/uc/8u131/updates.html  插件地址 手动下载
 *            连接远程
 *                 1.添加远程地址
 *                 2.修改tomcat.sh 中添加jmx相关信息
 *                      CATALINA_OPTS="$CATALINA_OPTS
                         -Djava.rmi.server.hostname=192.168.129.128
                         -Dcom.sun.management.jmxremote
                         -Dcom.sun.management.jmxremote.port=9999 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=true"
 *                  没有启动起来，查看 ‘ 日志 ’发现8080冲突了
 *                  还是没起来：https://www.cnblogs.com/wjwen/p/4861419.html
 *                  3.在jvisualvm添加地址端口信息 -- 任然连不上，估计是ip地址问题和本地连接远程服务器连不上一样
 *                         ip地址问题
 *
 *        ************ 扩展idea同样可以连接远程服务器和这里监控一个道理。判断是数据问题还是程序问题，判断当前运行环境而无需dump后才看*************
 *
 *         6.btrace 不重启直接修改字节码技术 调试  --- 生产应急处理  数据问题
 *                1.安装  btrace在github地址 - 解压 - 配置home /path
 *
 *                2.运行脚本（两种方式）
 *                      1.在jvisualvm中添加btrace插件，添加查拉退货path - 切到对应目录下使用命令行btrace <pid>(被监控id) <script>（编写的java及脚本）
 *                      2.在jvsualvm中执行
 *
 *                3.btrace代码编写
 *                       1.引入三个jar包
 *                       2.使用btrace对应的注解 类似java代码一样编写切面一样 Btrace.java
 *                       3.启动应用
 *                       4.使用btrace方式  btrace 进程号 btrace对应代码类
 *                              1.jps -l 查看当前id
 *                              2.切到对应btrace应用类下执行命令
 *
 *                4.btrace使用  -- 这些类都是固定的，只要修改部分就好，都是固定套路
 *                      1.拦截方法  重载方式 、构造方式 ...
 *                      2.拦截时机  Kind.ENTRY....
 *                      3.拦截this.参数，返回值 对象(反射 执行时候需要将该类指定到path ..-cp ...)-正则-环境变量等
 *                      4.其他  行数=某行是否执行到
 *
 *                ***btrace只能本地执行，不可以远程，所以有linux版本****
 *                ****主要使用在生活环境，但是被修改的字节码不会被还原，在jvm不重启的前提下
 *                    所以一定要在本地调试好，并且注意性能不要太消耗时间
 *
 *
 *
 *
 *
 *          7.远程调试：Debug.java

 *
 *
 */



public class PerformTool {



}
