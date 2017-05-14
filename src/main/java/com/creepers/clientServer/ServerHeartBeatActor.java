package com.creepers.clientServer;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2017/5/10.
 */
public class ServerHeartBeatActor extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private AtomicInteger instanceCounter = new AtomicInteger(0);


    @Override
    public void preStart() throws Exception {
        log.info("Starting ServerHeartBeatActor instance # " + instanceCounter.incrementAndGet() + ", hashconde # " + this.hashCode());
    }

    @Override
    public void postStop() throws Exception {
        log.info("Stoping ServerHeartBeatActor instance # " + instanceCounter.getAndDecrement() + ", hashconde # " + this.hashCode());
    }


    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof String) {
            log.info("ServerHeartBeatActor:  #" + getSelf().path() + "  hashcode:" + this.hashCode() + "  receive a message --->>>" + message);
            if (((String) message).equals("heartBeat")) {
                log.info("this is a heartBeat message ,then return a json as callBack!!");
                sender().tell("task type and threadNum json", ActorRef.noSender());
            }
        } else {
            log.info("ServerHeartBeatActor:  #" + getSelf().path() + "  hashcode:" + this.hashCode() + "  receive a ### unhandled type #### message --->>>" + message);
            unhandled(null);
        }
    }
}
