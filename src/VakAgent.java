import akka.actor.*;
import scala.Option;
import scala.PartialFunction;
import scala.collection.generic.BitOperations;
import scala.collection.immutable.HashMap;
import scala.runtime.BoxedUnit;

import java.util.Map;

/**
 * Created by yketd on 30-10-2016.
 */
public class VakAgent extends UntypedActor {
    static final int AMT_OF_SEATS = 5;
    int vaknr;
    Reservation reservation;

    Map<Integer, Boolean> availableSeats;
    Map<Integer, ActorRef> reservedSeats;
    Map<Integer, ActorRef> soldSeats;

    @Override
    public void preStart() throws Exception {
        super.preStart();
        System.out.println(self().path().name());
        initSeats();
        System.out.println(getSelf() + " instantiated and starting..");
    }

    private void initSeats() {
        availableSeats = new java.util.HashMap<>();
        reservedSeats = new java.util.HashMap<>();
        soldSeats = new java.util.HashMap<>();
        for (int i = 0; i < AMT_OF_SEATS; i++) {
            availableSeats.put(i, true);
            reservedSeats.put(i, null);
            soldSeats.put(i, null);
        }
    }

    @Override
    public void onReceive(Object o) throws Throwable {
        if (o instanceof Reservation) {
            reservation = (Reservation) o;
            System.out.println("got a message! status is: "+ reservation.status);
            if (reservation.status == main.REQUESTED){
                if (reserve(reservation.aantal, reservation.klant)){
                    reservation.status = main.RESERVED;
                    getSender().tell(reservation, self());
                }   else
                    reservation.status = main.SOLD;
                    getSender().tell(reservation, self());
            }else if (reservation.status == main.CANCEL){
                cancelReservation(reservation);
            }else if (reservation.status == main.PAYMENT){
                sellSeats(reservation);
            }
        }
    }

    public boolean reserve(int amt, ActorRef customer) {
        boolean reserved = false;
        for (int i = 0; i < availableSeats.size(); i++) {
            if (availableSeats.get(i)) {
                for (int j = 0; j < amt; j++) {
                    if (i+j < availableSeats.size()){
                    if (availableSeats.get(i + j))
                        reserved = true;
                    else {
                        reserved = false;
                        j = amt;
                    }
                    }
                }
            }
            if (reserved){
                for (int j = 0; j< amt; j++){
                    availableSeats.put(i+ j, false);
                    reservedSeats.put(i+j, customer);
                    reservation.seats[j] = i;

                }
                i = availableSeats.size();
            }
        }
        if (reserved){
            System.out.println("some seats have been reserved, sending a message back");
        }

        return reserved;
    }

    public void cancelReservation(Reservation reservation) {
        for (int i = 0; i < reservation.seats.length; i++){
            availableSeats.put(reservation.seats[i], true);
            reservedSeats.put(reservation.seats[i], null);
        }
    }

    public void sellSeats(Reservation reservation){
        for (int i = 0; i < reservation.seats.length; i++){
            soldSeats.put(reservation.seats[i], reservedSeats.get(reservation.seats[i]));
        }
    }
}
