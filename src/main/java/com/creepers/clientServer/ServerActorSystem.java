/*
 * Ma Xin
 * write by 2017.5.9  1:50:55
 */

package com.creepers.clientServer;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.kernel.Bootable;
import com.typesafe.config.ConfigFactory;

/**
 * Created by Administrator on 2017/5/9.
 */
public class ServerActorSystem implements Bootable {

    private LoggingAdapter log = null;
    private ActorSystem system;

    public ServerActorSystem() {
        system = ActorSystem.create("ServerSys", ConfigFactory.load().getConfig("ServerSys"));
        log = Logging.getLogger(system, this);
        ActorRef actor = system.actorOf(Props.create(ServerActor.class), "serverActor");
        ActorRef heartBeat = system.actorOf(Props.create(ServerHeartBeatActor.class), "serverHeartBeatActor");
    }

    public void startup() {
        log.info("Starting up the ServerActorSystem!");
    }

    public void shutdown() {
        log.info("Shutting down the ServerActorSystem!");
    }

    public static void main(String[] args) {
        new ServerActorSystem();
    }
}
