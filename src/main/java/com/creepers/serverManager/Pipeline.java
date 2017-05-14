package com.creepers.serverManager;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

import java.util.Date;

public class Pipeline extends UntypedActor {

    public static void execute(String url) {

    }

    @Override
    public void onReceive(Object obj) throws Throwable {
        if (obj instanceof String) {
            Thread current = Thread.currentThread();
            // System.out.println(current.getPriority());
            // System.out.println(current.getName());
            // System.out.println(current.activeCount());
            // System.out.println(current.getId());
            // System.out.println(current.getThreadGroup());
            // System.out.println(current.getStackTrace());
            // System.out.println(current.hashCode());
            // System.out.println(current.toString());
            System.out.println(new Date() + "存储器开始储存数据：" + obj + " current.Id:" + current.getId());
            sender().tell(true, ActorRef.noSender());
        } else {
            unhandled(obj);
        }
    }
}
