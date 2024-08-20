package com.lj.concurrency.blockingqueue;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 合并扣减库存案例: 通过降级锁的持有时间，将几个请求合并在一起，然后一起扣减库存，降低了io的次数，从而提高tps
 */
public class KillDemo {
    /**
     * 启动10个用户线程
     * 库存6个
     * 生成一个合并队列
     * 每个用户能拿到自己的请求响应
     */
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        KillDemo killDemo = new KillDemo();
        //合并扣减库存
        killDemo.mergeJob();
        Thread.sleep(2000);


        CountDownLatch countDownLatch = new CountDownLatch(10);

        System.out.println("-------- 库存 --------");
        System.out.println("库存初始数量 :" + killDemo.stock);

        List<Future<Result>> futureList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final Long orderId = i + 100L;
            final Long userId = Long.valueOf(i);
            UserRequest userRequest = new UserRequest(orderId, userId, 1);

            Future<Result> future = executorService.submit(() -> {
                countDownLatch.countDown();
                countDownLatch.await(1, TimeUnit.SECONDS);
                return killDemo.operate(userRequest);
            });
            futureList.add(future);

        }

        System.out.println("------- 客户端响应 -------");
        Thread.sleep(1000);
        futureList.forEach(future -> {
            try {
                Result result = future.get(3000, TimeUnit.MILLISECONDS);
                System.out.println("客户端请求响应:" + result);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });


    }



    // 模拟数据库行
    private Integer stock = 6;

    private BlockingQueue<RequestPromise> queue = new LinkedBlockingQueue<>(10);
    /**
     * 用户库存扣减
     * @param userRequest
     * @return
     */
    public Result operate(UserRequest userRequest) throws InterruptedException {
        // TODO 阈值判断
        // TODO 队列的创建
        RequestPromise requestPromise = new RequestPromise(userRequest);
        synchronized (requestPromise) {
            boolean enqueueSuccess = queue.offer(requestPromise, 100, TimeUnit.MILLISECONDS);
            if (! enqueueSuccess) {
                return new Result(false, "系统繁忙");
            }
            try {
                requestPromise.wait(200);
                if (requestPromise.getResult() == null) {
                    return new Result(false, "等待超时");
                }
            } catch (InterruptedException e) {
                return new Result(false, "被中断");
            }
        }
        return requestPromise.getResult();
    }

    public void mergeJob() {
        new Thread(() -> {
            List<RequestPromise> list = new ArrayList<>();
            while (true) {
                if (queue.isEmpty()) {
                    try {
                        Thread.sleep(10);
                        continue;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                while (queue.peek() != null){
                    list.add(queue.poll());
                }


                //System.out.println(Thread.currentThread().getName() + ":合并扣减库存:" + list);

                int sum = list.stream().mapToInt(e -> e.getUserRequest().getCount()).sum();

                // 两种情况
                if (sum <= stock) {
                    stock -= sum;
                    // notify user
                    list.forEach(requestPromise -> {
                        requestPromise.setResult(new Result(true, "ok"));
                        synchronized (requestPromise) {
                            requestPromise.notify();
                        }
                    });
                    list.clear();
                    continue;
                }
                for (RequestPromise requestPromise : list) {
                    int count = requestPromise.getUserRequest().getCount();
                    if (count <= stock) {
                        stock -= count;
                        requestPromise.setResult(new Result(true, "ok"));
                    } else {
                        requestPromise.setResult(new Result(false, "库存不足"));
                    }
                    synchronized (requestPromise) {
                        requestPromise.notify();
                    }
                }
                list.clear();
            }
        }, "mergeThread").start();

    }




}

class RequestPromise {
    private UserRequest userRequest;
    private Result result;

    public RequestPromise(UserRequest userRequest) {
        this.userRequest = userRequest;
    }

    public RequestPromise(UserRequest userRequest, Result result) {
        this.userRequest = userRequest;
        this.result = result;
    }

    public UserRequest getUserRequest() {
        return userRequest;
    }

    public void setUserRequest(UserRequest userRequest) {
        this.userRequest = userRequest;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "RequestPromise{" +
                "userRequest=" + userRequest +
                ", result=" + result +
                '}';
    }
}
class Result {
    private Boolean success;
    private String msg;

    public Result(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                '}';
    }
}
class UserRequest {
    private Long orderId;
    private Long userId;
    private Integer count;

    public UserRequest(Long orderId, Long userId, Integer count) {
        this.orderId = orderId;
        this.userId = userId;
        this.count = count;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", count=" + count +
                '}';
    }
}