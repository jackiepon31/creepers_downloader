package com.creepers.serverManager;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

import java.util.Date;
import java.util.UUID;

public class Download extends UntypedActor {


    @Override
    public void onReceive(Object obj) throws Throwable {
        Thread current = Thread.currentThread();
        if (obj instanceof String) {
            System.out.println(new Date() + "下载器开始下载url:" + obj + "    current.Id:" + current.getId());
            final ActorRef pipeline = getContext().actorOf(Props.create(Pipeline.class), UUID.randomUUID().toString());
            pipeline.tell("储存ABC", ActorRef.noSender());
            sender().tell(true, ActorRef.noSender());
//            context().stop(getSelf());
        } else if (obj instanceof Boolean) {
            context().stop(getSender());
            System.out.print("处理结果：" + obj);
        }
    }
}
