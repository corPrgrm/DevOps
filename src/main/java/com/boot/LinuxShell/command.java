package com.boot.LinuxShell;

public class command {



    /***
     *
     * ***************************查看对应的应用日志********************
     *
     * man xxx 提示  也可以直接使用/-n 查找-n参数
     *
     * less
     * 1.打开文件-查找关键字模糊-上下翻页/上一个下一个-全局替换-删除一行-复制一行-行号显示
     *      more/head/tail  - 空格=下一个 b上一个  全局搜索 /  more好像不行呀  = 显示当前光标行，列
     *      less  n：重复前一个搜索（与 / 或 ? 有关）N：反向重复前一个搜索（与 / 或 ? 有关） G 跳到末尾  *********
     *      x,X  : 在一行中，x为向后删除一个字符（相当于del键），X为向前删除一个字符（相当于backspace键）。
            查看都可以通过q结束

        光标移动  上下左右、pageup/down

     2.编辑
        vi /vim打开
            显示行号：:set nu
            高亮行当前鼠标位置：set cursorcolumn    set cursorline
            搜索  /
            统计关键字出现次数：:%s/string//gn
            替换 ::s/old/new/ "，该命令会将当前这一行中第一个出现的old模式替换为new。
                 :s/old/new/g "会将当前这一行中的所有old模式替换为new
                " %s/old/new/g "就能将文件中所有的old模式替换为new，


           dd   : 删除光标所在的那一整行。
           ndd  : n 为数字。从光标开始，删除向下n列。
           yy   : 复制光标所在的那一行。
           nyy  : n为数字。复制光标所在的向下n行。
           p,P  : p 为将已复制的数据粘贴到光标的下一行，P则为贴在光标的上一行。
           u    : 复原前一个操作 ********相当于ctrl+z

         q 退出命令模式 esc退出编辑模式
            wq! 记得保存
           less 查看确认
     *
     *
     * 3.全局查找文件 - 按照大小排序 -按照时间
     *      find 查找文件目录
     *             -name
     *             -mtime -5  文件更改时间在5天内   +3 三天前
     *             -size +1000c  丹玉1000字节的
     *             -perm
     *             -user
     *             ..
     *             并执行 find . -name '*2*'|xargs rm -f  注意模糊必须使用* --- 删除一定要小心 -- 默认递归 -- 一定考虑性能 不要查询太长时间
     *
     *      #grep 查找字符 - 注意多个文件查找
     *      grep ‘energywise’ *           #在当前目录搜索带'energywise'行的文件
     #      grep -r ‘energywise’ *        #在当前目录及其子目录下搜索'energywise'行的文件
     #      grep -l -r ‘energywise’ *     #在当前目录及其子目录下搜索'energywise'行的文件，但是不显示匹配的行，只显示匹配的文件
     *      grep + more           more xx.log |grep 'xxx'
     *
     *      du -s *|sort -nr  输出是k
     *
     * 4.输出 读取
     *      “>”或”1>”输出重定向：把前面输出的东西输入到后边的文件中，会清除文件原有的内容。
     *      >>”或”1>>” 追加输出重定向：把前面输出的东西追加到后边的文件尾部，不会清除文件原有的内容。
     *      “<”或”0<”输入重定向：输入重定向用于改变命令的输入，后面指定输入内容，后面跟文件名。
     *      “2>>”错误追加重定向：把错误的信息追加到后边的文件中，不会删除文件原有的内容。
     *
     *
     * 5.查看磁盘、内存、cpu、进程、线程、网络、io
     *
     *      1.top      总体动态监控  cpu / 内存 / 进程占用      https://www.cnblogs.com/niuben/p/12017242.html
     *      2.vmstat                 cpu / 内存 / io /system    https://blog.csdn.net/ZYC88888/article/details/79028175 中的vmstat
     *                           r ： 运行和等待CPU时间片的进程数（若长期大于CPU的个数，说明CPU不足，需要增加CPU）
                                 b ： 在等待资源的进程数（如等待I/O或者内存交换等）
                                 如果si、so的值长期不为0，表示系统内存不足，需要增加系统内存
                                 bi+bo参考值为1000，若超过1000，且wa较大，表示系统IO有问题，应该提高磁盘的读写性能
                                 in与cs越大，内核消耗的CPU时间就越多
                                 us+sy参考值为80%，如果大于80%，说明可能存在CPU资源不足的情况

     *--memory
     *      1.free
     *
     *--cpu
     *      1.sar
     *
     *--磁盘
     *      1.iostat  磁盘读写速度
     *                      %util： 在统计时间内所有处理IO时间，除以总共统计时间。例如，如果统计间隔1秒，该设备有0.8秒在处理IO，
                     　　　　而0.2秒闲置，那么该设备的%util = 0.8/1 = 80%，
                     　　　　所以该参数暗示了设备的繁忙程度
                     　　　　。一般地，如果该参数是100%表示设备已经接近满负荷运行了
                     　　　　（当然如果是多磁盘，即使%util是100%，因为磁盘的并发能力，所以磁盘使用未必就到了瓶颈）。
     *          https://blog.csdn.net/ZYC88888/article/details/79028175
     *
     * --net
     *      1.从进程-端口  ps -ef|grep java          netstat -ntulp |grep 进程号     查看进程开启哪些端口
     *      2.从端口-进程  netstat -ntulp |grep 端口号    --- ps -ef|grep 进程号  可以查看端口占用情况
     *      3.lsof -i:端口号  查看端口情况
     *      4.查看端口是否对外开放  telnet ip 端口号
     *      5.iptable 防火墙  cat  /etc/sysconfig/iptables  --- https://www.jianshu.com/p/b3068288d80d
     *
     *      1.ping
     *      2.netstat -i
     *              正常情况下，RX-ERR，RX-DRP，RX-OVR，TX-ERR，TX-DRP，TX-OVR都应该为0，若不为0且很大，那么网络质量肯定有问题，网络传输性能也一定会下降。
                    当网络传输存在问题时，可以检测网卡设备是否存在故障，还可以检查网络部署环境是否合理。

            netstat -r （default行对应的值表示系统的默认路由）
     *
     *
     *6.从进程信息读取 - 进程号
     *          root      2328     1  0 18:43 ?        00:00:07 /usr/jdk/jdk1.8.0_112/jre/bin/java -Djava.util.logging.
     *          config.file=/usr/tomcat/apache-tomcat-8.0.38/conf/logging.properties -Djava.util.logging.manager=
     *          org.apache.juli.ClassLoaderLogManager -Djdk.tls.ephemeralDHKeySize=2048 -Djava.protocol.handler.pkgs=
     *          org.apache.catalina.webresources -Djava.rmi.server.hostname=192.168.129.128 -Dcom.sun.management.jmxremote
     *          -Dcom.sun.management.jmxremote.port=9999 -Dcom.sun.management.jmxremote.ssl=false
     *          -Dcom.sun.management.jmxremote.authenticate=true -Djava.endorsed.dirs=/usr/tomcat/apache-tomcat-8.0.38/
     *          endorsed -classpath /usr/tomcat/apache-tomcat-8.0.38/bin/bootstrap.jar:/usr/tomcat/apache-tomcat-8.0.38
     *          /bin/tomcat-juli.jar -Dcatalina.base=/usr/tomcat/apache-tomcat-8.0.38 -Dcatalina.home=/usr/tomca
     *          t/apache-tomcat-8.0.38 -Djava.io.tmpdir=/usr/tomcat/apache-tomcat-8.0.38/temp org.apache.catalina.st
     *          artup.Bootstrap start

     *      1.启动时间
     *           UID PID PPID C(处理器利用率（废弃率) 进程启动时间 TTY TIME（累积CPU时间） CMD
     *      2.配置信息
     *          使用jdk  - tomcat 启动参数配置 比如这里配置了远程连接Djava.rmi.server.hostname=192.168.129.128:9999等
     *
     * --------------------------以上信息已经从系统层面得到数据，接下来对该进程进行 ，使用java工具 -----------------
     *
     * 7.PerformTool.java
     *

     *
     *
     */


}
