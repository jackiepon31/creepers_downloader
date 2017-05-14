/*
 * Ma Xin
 * write by 2017.5.9  1:50:41
 */

package com.creepers.clientServer;

import akka.actor.PoisonPill;
import akka.actor.ReceiveTimeout;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.concurrent.duration.Duration;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2017/5/9.
 */
public class ServerActor extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private AtomicInteger instanceCounter = new AtomicInteger(0);


    @Override
    public void preStart() throws Exception {
        log.info("Starting ServerActor instance # " + instanceCounter.incrementAndGet() + ", hashconde # " + this.hashCode());
        this.getContext().setReceiveTimeout(Duration.create("1 seconds"));
    }

    @Override
    public void postStop() throws Exception {
        log.info("Stoping ServerActor instance # " + instanceCounter.getAndDecrement() + ", hashconde # " + this.hashCode());
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof String) {
            log.info("serverActor ### " + this.getSelf().path() + this.hashCode() + " ###: I get something from client @@@@@@@:" + message);
            getSender().tell(" #### serverActor get something!" + message, getSelf());
        } else if (message instanceof ReceiveTimeout) {
            log.info("serverActor ### " + this.getSelf().path() + this.hashCode() + " ###:" + instanceCounter.get() + " ##: get message timeout&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
            getContext().setReceiveTimeout(Duration.Undefined());
        } else if (message instanceof PoisonPill) {
            log.info("serverActor ### " + this.getSelf().path() + this.hashCode() + " ###: ## " + instanceCounter.get() + " ##: get something from client #:" + message + " ,then I will stop ServerActor");
            getContext().system().stop(getSelf());
        }
    }
}
