package com.creepers.serverManager;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by MaXin on 2017/5/3.
 */
public class ServerManager extends UntypedActor {

    private AtomicInteger processThreadNum = new AtomicInteger(0);
    private AtomicInteger total = new AtomicInteger(0);

    private int maxThreadNum = 0;
    private int maxTotal = 0;

    public ServerManager(int maxThreadNum, int maxTotal) {
        this.maxThreadNum = maxThreadNum;
        this.maxTotal = maxTotal;
    }

    @Override
    public void onReceive(Object o) throws Throwable {

        if (o instanceof String) {
            String content = (String) o;
            if ("taskType".equals(content)) {
                while (true) {
                    if (processThreadNum.get() < maxThreadNum && total.get() < maxTotal) {
                        processThreadNum.incrementAndGet();
                        System.out.println("init task  10: " + processThreadNum.get());
//                        Downloader用反射去实现  通过taskType去查找这个class
                        ActorRef download = context().actorOf(Props.create(Download.class), UUID.randomUUID().toString());
                        download.tell(" treadNum:" + processThreadNum.get() + "   url : " + processThreadNum.get(), getSelf());
                        total.incrementAndGet();
                    } else {
                        break;
                    }
                }
            }
        } else if (o instanceof Boolean) {
            System.out.println("processorNum:" + processThreadNum.get());
            context().stop(getSender());
            processThreadNum.decrementAndGet();
            if (total.get() == 499) {
                System.out.println("test 499");
            }
            if (processThreadNum.get() < maxThreadNum && total.get() < maxTotal) {
                System.out.println("entry backCall to create new!");
                ActorRef download = context().actorOf(Props.create(Download.class), UUID.randomUUID().toString());
                download.tell("开始下载这个url : " + total.incrementAndGet(), getSelf());
                processThreadNum.incrementAndGet();
            } else if (processThreadNum.get() == 0) {
                System.out.println("download 处理完成！");
                System.out.println("当前线程数为0,结束！");
                context().stop(getSelf());
            } else {
                System.out.println("total is too big！！！！！" + total.get() + "   processThreadNum:" + processThreadNum.get());
            }
        }


    }
}
