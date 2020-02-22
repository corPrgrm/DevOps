package com.boot.LinuxShell;

public class command {



    /***
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
     *--memory
     *      1.top
     *
     *
     *
     * --net
     *      1.从进程-端口  ps -ef|grep java          netstat -ntulp |grep 进程号     查看进程开启哪些端口
     *      2.从端口-进程  netstat -ntulp |grep 端口号    --- ps -ef|grep 进程号  可以查看端口占用情况
     *      3.lsof -i:端口号  查看端口情况
     *      4.查看端口是否对外开放  telnet ip 端口号
     *      5.iptable 防火墙  cat  /etc/sysconfig/iptables  --- https://www.jianshu.com/p/b3068288d80d
     *
     *
     *6.从进程信息读取 - 进程号
     *      1.启动时间
     *      2.配置信息
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     */


}
