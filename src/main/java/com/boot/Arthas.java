package com.boot;


import java.util.UUID;


/**
 * 1.btrace和arthas关系：https://my.oschina.net/meituantech/blog/3016523  后者封装了可以修改值拦截
 * 2 不停机修改线上代码 、耗时、监控 https://blog.csdn.net/xinzhifu1/article/details/104201004
        * watch com.boot.Arthas uuid "{params,target,returnObj}" -x 5
        * jad --source--only com.boot.Arthas > ./Arthas.java
        * sc -d Arthas | grep classLoader
 *  3.二者结合使用：https://blog.csdn.net/hengyunabc/article/details/107948158
 */
public class Arthas {

    public static String uuid(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static void main(String[] args) {

        while(true){
            System.out.println("uuid = " + uuid());
            System.out.println("woshixinjiade neirong !!!" );
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
