package com.boot.fast;


import com.google.common.collect.Maps;
import org.assertj.core.util.Lists;

import java.util.*;
import java.util.concurrent.*;

/**
 * .pic\fast1.png 图示
 * 1.批量处理请求 list ==> 时间窗口/尺寸窗口 = blockQueue(mq底层) + schedual Thread + future
 *      1.list和queue:ArrayList is random access. You can insert and remove elements anywhere within the list. Yes, you can use this as a FIFO data structure, but it does not strictly enforce this behavior. If you want strict FIFO, then use Queue instead.
 *      2.schedual Thread 实现定时轮训的作用 取代 while(true)..sleep...
 * 2.代码最终没有测试，思想在这
 */

public class fast1 /*implements InitializingBean*/ {


    private LinkedBlockingDeque<Request> batchRequest = new LinkedBlockingDeque<>();

    /**
     * 为了测试不引入spring，所以这样代替inittalzinBean
     */
    {
        try {
            afterPropertiesSet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String , Object> queryOrderInfo(String orderNum){

        /**
         * 3.通过批量处理请求
         *      1.封装请求对象
         *      2.放到queue中等待轮训，实现窗口
         *      3.定时调度
         */
        Request request = new Request(UUID.randomUUID().toString(), orderNum, new CompletableFuture<Map<String, Object>>());

        batchRequest.add(request);


        return new OrderRemoteCall().queryOrderInfo(orderNum);
    }

    /**
     * 构建定时器
     * @throws Exception
     */
//    @Override
    public void afterPropertiesSet() throws Exception {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.schedule(() -> {

            if(batchRequest.size() == 0) return;

//            List<Map<String , Object>> batch = new ArrayList<>();
            List<Request> batch = new ArrayList<>();
            for (int i = 0; i < batchRequest.size(); i++) {
                Request request = batchRequest.poll();
                /**
                 * 4.转换通信级别对象，这里直接简化由对象到map - 其他方式
                 */
//                batch.add(JSON.parseObject(request.toString().getBytes(),Map.class));
                batch.add(request);
            }

            List<Map<String, Object>> responses = new OrderRemoteCall().batchQueryOrderInfo(batch);
            /**
             * 6.通信级别判断；方法封装
             */
//            batch.stream().map(request -> {
//                responses.stream().filter().map(() -> {
//                    Map<String, Object> stringObjectMap = responses.get(request.serialNo);
//                })
//            })
            for (Request re : batch) {
                for (Map result : responses) {
                    if(result.get("serialNo").equals(re.serialNo)){
                        re.completableFuture.complete(result);
                    }
                }
            }


        },10, TimeUnit.SECONDS);


    }


    class Request{
        private String serialNo;
        private String orderNum;
        private CompletableFuture completableFuture;

        public Request(String serialNo, String orderNum, CompletableFuture<Map<String, Object>> completableFuture){
            serialNo = serialNo;
            orderNum = orderNum;
            completableFuture = completableFuture;
        }
    }




    /**
     * 1.内部类的使用和扩展，这里只是模拟，但一会添加线程东西时，内部类是有必要的。
     */
     class OrderRemoteCall{

        public  Map<String , Object> queryOrderInfo(String orderNum){
            HashMap<String, Object> orderInfo = Maps.newHashMap();
            orderInfo.put("orderNum",orderNum);
            orderInfo.put("orderInfo","orderInfo");
            return orderInfo;
        }

        public  List<Map<String , Object>> batchQueryOrderInfo(List<Request> request){
            /**
             * 5.指定初始化大小
             */
            List response = Lists.newArrayList(request.size());
            for (Request requestInfo: request) {
                response.add( fast1.this.queryOrderInfo(requestInfo.orderNum));
            }
            return response;
        }
    }

}


