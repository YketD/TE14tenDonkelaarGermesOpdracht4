import akka.actor.ActorRef;

/**
 * Created by yketd on 30-10-2016.
 */
public class Reservation {
    int aantal;



    int[] seats = new int[4];
    ActorRef vakAgent;
    ActorRef klant;
    String status;

    public Reservation(int aantal, ActorRef vakAgent, ActorRef klant, String status) {
        this.aantal = aantal;
        this.vakAgent = vakAgent;
        this.klant = klant;
        this.status = status;
    }

    public ActorRef getVakAgent() {
        return vakAgent;
    }

    public void setSeats(int[] seats) {
        this.seats = seats;
    }
}
