package com.testDemo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.creepers.serverManager.Terminator;

public class Main2 {

    public static void main(String[] args) {
        // ActorSystem 相当于ActorManager，管理各种Acor调度、线程管理等
        ActorSystem system = ActorSystem.create("hello");
        // 创建一个HelloWorld 类型的Actor，在Actor启动前，会调preStart（），此时会想Greeter发消息
        ActorRef actor = system.actorOf(Props.create(HelloWorld.class),"helloword");
        actor.tell("start", actor);
        // 添加结束终结Actor，当ActorSystem调Stop时，会向每个Actor发送Terminated消息
        system.actorOf(Props.create(Terminator.class, actor), "terminator");
    }
}
