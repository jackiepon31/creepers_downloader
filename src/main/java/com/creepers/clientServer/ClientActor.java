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
public class ClientActor extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private ActorRef romete;

    public ClientActor(ActorRef romete) {
        this.romete = romete;
    }

    @Override
    public void onReceive(Object message) throws Throwable {

        if (message instanceof String) {
            if (((String) message).startsWith("Start")) {
                log.info("###" + this.getSelf().path() + this.hashCode() + "  Sending message to server - message # Hi gay ,look at here!!!!" + message);
                romete.tell("Hi serverActor,i come from clientActor !!" + message, getSelf());
            } else {
                log.info("###" + this.getSelf().path() + this.hashCode() + "  Message receive from Server --->>> " + message);
                sleep(2000);
                sender().tell("clientActor wait 2000ms to tell ServerActor something!  |||||  " + message, ActorRef.noSender());
                log.info("###" + this.getSelf().path() + this.hashCode() + "  send wait message to Server--->>> ");

            }
        }
    }
}
