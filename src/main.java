import akka.actor.*;
import akka.routing.*;
import scala.Option;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yketd on 12-10-2016.
 */
public class main {
    ActorSystem system;
    static Router router2;

    static ActorRef generalAdmission, reserverdFloorSeating, northGrandStand, southGrandStand, standingPit;

    public static final String CANCEL = "cancel",
            PAYMENT = "payment",
            REQUESTED = "requested",
            RESERVED = "reserved",
    SOLD = "sold";


    public static void main(String[] args) {
        new main().run();
    }

    public void run() {
        system = ActorSystem.create("Routing");

        ActorRef actor;
        List<Routee> routees = new ArrayList<Routee>();
        for (int i = 0; i < 5; i++) {
            actor = system.actorOf(Props.create(VerkoopAgent.class), "VA" + i);
            routees.add(new ActorRefRoutee(actor));
        }

        router2 = new Router(new SmallestMailboxRoutingLogic(), routees);

        for (int i = 1; i< 10; i++){
            router2.route(i,null);
        }
        instantiateVakAgents();
        instantiateKlanten();

    }

    public void instantiateVakAgents() {
         generalAdmission = system.actorOf(Props.create(VakAgent.class), "generaladmission");
         reserverdFloorSeating = system.actorOf(Props.create(VakAgent.class), "reservedfloorseating");
         northGrandStand = system.actorOf(Props.create(VakAgent.class), "northgrandstand");
         southGrandStand = system.actorOf(Props.create(VakAgent.class), "southgrandstand");
         standingPit = system.actorOf(Props.create(VakAgent.class), "standingpit");

    }



    public void instantiateKlanten() {
        for (int i = 0; i < 300; i++) {
            ActorRef klant = system.actorOf(Props.create(Klant.class), "klant" + i);
            klant.tell("go", null);
        }
    }

    public static int getRandom(int random) {
        return (int) (Math.random() * random);
    }
}
