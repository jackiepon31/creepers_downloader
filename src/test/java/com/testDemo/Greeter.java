package com.testDemo;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class Greeter extends UntypedActor {

    public static enum Msg {
        GREET, DONE;
    }

    @Override
    public void onReceive(Object msg) {
        if (msg == Msg.GREET) {
            System.out.println(getSelf().toString() +  " get :" + Msg.GREET + " then say Hello World! and send done to sender " );
            getSender().tell(Msg.DONE, getSelf());
        } else if ("close".equals(msg)) {
            System.out.println(getSelf().toString() + " get :"+ msg +" then tell sender 已经关闭 and stop self");            
            getSender().tell("已经关闭", ActorRef.noSender());
            System.out.println(getSelf() + " ：已经关闭");
            getContext().stop(getSelf());
        } else {
            System.out.println(msg + getSelf().toString() );
            getSender().tell("unhandled " + msg, getSelf());
            unhandled(msg);
        }
    }

}
