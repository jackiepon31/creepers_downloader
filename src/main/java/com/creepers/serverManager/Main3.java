package com.creepers.serverManager;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Main3 {

    public static void main(String[] args) {
        ActorSystem sysManager = ActorSystem.create("EVA_manager");
        ActorRef worker = sysManager.actorOf(Props.create(ServerManager.class, 10, 100000), "worker");
        worker.tell("taskType", ActorRef.noSender());
        sysManager.actorOf(Props.create(Terminator.class, worker), "terminator");
    }

}
