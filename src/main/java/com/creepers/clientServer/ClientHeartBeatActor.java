/*
 * Ma Xin
 * write by 2017.5.9  1:1:44
 */

package com.creepers.clientServer;


import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import static java.lang.Thread.sleep;

/**
 * Created by Administrator on 2017/5/9.
 */
public class ClientHeartBeatActor extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private ActorRef romete;

    public ClientHeartBeatActor(ActorRef romete) {
        this.romete = romete;
    }

    @Override
    public void onReceive(Object message) throws Throwable {

        if (message instanceof String) {
            if (((String) message).startsWith("heartBeat")) {
                log.info("ClientHeartBeatActor:  #" + getSelf().path() + "  hashcode:" + this.hashCode() + "  send a message  'heartBeat' --->>> ServerHeartBeatActor  ");
                romete.tell("heartBeat", getSelf());
            } else {
                log.info("ClientHeartBeatActor:  #" + getSelf().path() + "  hashcode:" + this.hashCode() + "  receive a message  from ServerHeartBeatActor" + message);
                log.info("message :" + message + "------>>>   then ClientHeartBeat will copy the heartBeat response to somewhere!");
            }
        }
    }
}
