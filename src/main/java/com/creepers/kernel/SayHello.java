/*
 * Ma Xin
 * write by 2017.5.5  4:18:46
 */

package com.creepers.kernel;


import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.kernel.Bootable;
/**
 * Created by Administrator on 2017/5/5.
 */
public class SayHello implements Bootable {
    final ActorSystem system = ActorSystem.create("hellokernel");

    public static class HelloActor extends UntypedActor {
        final ActorRef worldActor = getContext().actorOf(
                Props.create(WorldActor.class));

        public void onReceive(Object message) {
            if (message == "start")
                worldActor.tell("Hello", getSelf());
            else if (message instanceof String)
                System.out.println(String.format("Received message '%s'", message));
            else
                unhandled(message);
        }
    }

    public static class WorldActor extends UntypedActor {
        public void onReceive(Object message) {
            if (message instanceof String)
                getSender().tell(((String) message).toUpperCase() + " world!",
                        getSelf());
            else
                unhandled(message);
        }
    }

    public void startup() {
        system.actorOf(Props.create(HelloActor.class)).tell("start", null);
    }

    public void shutdown() {
//        system.stop();
        system.terminate();
    }
}