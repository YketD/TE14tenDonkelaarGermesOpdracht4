import akka.actor.UntypedActor;
import org.apache.camel.processor.loadbalancer.StickyLoadBalancer;

/**
 * Created by yketd on 30-10-2016.
 */
public class VerkoopAgent extends UntypedActor {
    @Override
    public void preStart() throws Exception {
        super.preStart();
        System.out.println(getSelf() + " instantiated and starting..");
    }

    @Override
    public void onReceive(Object o) throws Throwable {
        if (o instanceof Reservation) {
            Reservation message = (Reservation) o;
            System.out.println("got a message! status is: " + message.status);
            if (message.status == main.REQUESTED)
                message.getVakAgent().tell(message, self());
            else if (message.status == main.RESERVED || message.status == main.SOLD) {
                message.klant.tell(message, self());
            }else if (message.status == main.PAYMENT){
                message.vakAgent.tell(message, self());
            }else if (message.status == main.CANCEL){
                message.vakAgent.tell(message, self());
            }

        }
    }
}
