/*
 * Ma Xin
 * write by 2017.5.8  5:14:43
 */

/*
 * Ma Xin
 * write by 2017.5.8  4:1:57
 */

package com.creepers.cluster.server2;


/**
 * Created by Administrator on 2017/5/8.
 */


import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.ClusterEvent.MemberEvent;
import akka.cluster.ClusterEvent.MemberRemoved;
import akka.cluster.ClusterEvent.MemberUp;
import akka.cluster.ClusterEvent.UnreachableMember;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.typesafe.config.ConfigFactory;

public class SimpleClusterListener2 extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    Cluster cluster = Cluster.get(getContext().system());

    //subscribe to cluster changes
    @Override
    public void preStart() {
        //#subscribe
        cluster.subscribe(getSelf(), ClusterEvent.initialStateAsEvents(),
                MemberEvent.class, UnreachableMember.class);
        //#subscribe
    }

    //re-subscribe when restart
    @Override
    public void postStop() {
        cluster.unsubscribe(getSelf());
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof MemberUp) {
            MemberUp mUp = (MemberUp) message;
            log.info("Member is Up: {}", mUp.member());

        } else if (message instanceof UnreachableMember) {
            UnreachableMember mUnreachable = (UnreachableMember) message;
            log.info("Member detected as unreachable: {}", mUnreachable.member());

        } else if (message instanceof MemberRemoved) {
            MemberRemoved mRemoved = (MemberRemoved) message;
            log.info("Member is Removed: {}", mRemoved.member());

        } else if (message instanceof MemberEvent) {
            // ignore

        } else {
            unhandled(message);
        }

    }

    public static void main(String [] args){
        System.out.println("Start simpleClusterListener");
        ActorSystem system = ActorSystem.create("akkaClusterTest", ConfigFactory.load("application2.conf"));
        system.actorOf(Props.create(SimpleClusterListener2.class), "simpleClusterListener");
        System.out.println("Started simpleClusterListener");
    }
}