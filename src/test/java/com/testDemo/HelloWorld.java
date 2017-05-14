package com.testDemo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
public class HelloWorld extends UntypedActor {

    // @Override
    // public void preStart() {
    // // create the greeter actor
    // final ActorRef greeter =
    // getContext().actorOf(Props.create(Greeter.class), "greeter");
    // // tell it to perform the greeting
    // greeter.tell(Greeter.Msg.GREET, getSelf());
    // }

    @Override
    public void onReceive(Object msg) {
        if ("start".equals(msg)) {
            System.out.println(getSelf().toString() + "get :" +msg +"then tell greeter a message:GREET");
            final ActorRef greeter = getContext().actorOf(Props.create(Greeter.class), "greeter");
            greeter.tell(Greeter.Msg.GREET, getSelf());
        } else if (msg == Greeter.Msg.DONE) {
            // when the greeter is done, stop this actor and with it the
            // application
            System.out.println(getSelf().toString() + " get :" + msg + " then tell sender close itself");
            getSender().tell("close", getSelf());
        } else if ("已经关闭".equals(msg)) {
            System.out.println(getSelf().toString() + "sender 已经关闭成功，开始关闭自己");
            getContext().stop(getSelf());
        } else {
            System.out.println(this.getClass() + ": unhandled " + msg);
            unhandled(msg);
        }
    }
}
