package com.boot;

public class Tomcat {

    /**
     *
     * 1.tomcat配置远程连接信息 连接idea 进行本地debug
     * 2.借助tomcat-manager监控容器信息   容器堆栈信息，线程
     * 3.psi-probe监控
     * 4.优化
     *          1.线程优化：docs/config/http.html  maxConnection /acceptCount / maxThread /minSpareThreads
     *          2.server.xml <host>...</host>  autoDeploy="false" 不去自动更新  enableLoopups:false DNS查询
     *             reloadable:false lib检测  <connector:protocol:
     *          3.session jsp禁用session
     *
     *
     *
     *
     */


}
