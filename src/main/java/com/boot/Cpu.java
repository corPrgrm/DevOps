package com.boot;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
@RequestMapping({"/cpu"})
public class Cpu {

  /*  public static void main(String[] args) {

        new Cpu().deadCircle();

    }*/


//这种设置只适合于本地。无法发布后出发，所以还会提供简单的controller入口模拟请求

    @RequestMapping("/test")
    public String test(@RequestParam("name")String name){
        System.out.println("test");
        return "hello"+name ;
    }


    @RequestMapping("/dc")
    public void dc(){
        deadCircle();
    }

    //死循环
    private void deadCircle(){
        while(true){
            System.out.println("---deadCircle doing --------");
        }
    }



    @RequestMapping("/dl")
    public void dl(){

        deadlock();
    }
 Object lock1 = new Object();
 Object lock2 = new Object();


    private void deadlock() {
        new Thread(()->{
            //1.字符串可以做锁吗？ 为什么这里没有锁住   ---
            //synchronized ("lock1") {
            synchronized (lock1) {
                try {
                    Thread.sleep(1000);
                    synchronized (lock2){}
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(()->{
            synchronized (lock2) {
                try {
                    Thread.sleep(1000);
                    synchronized (lock1){}
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @RequestMapping("/dlslock")
    public void dlslock(){

        deadlockforTestStringLock();
    }

    private void deadlockforTestStringLock() {
        new Thread(()->{
            synchronized ("lock1") {
                try {
                    Thread.sleep(1000);
                    synchronized ("lock2"){}
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(()->{
            synchronized ("lock2") {
                try {
                    Thread.sleep(1000);
                    synchronized ("lock1"){}
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
