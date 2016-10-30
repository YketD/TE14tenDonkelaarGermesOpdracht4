import akka.actor.UntypedActor;

/**
 * Created by yketd on 30-10-2016.
 */
public class Klant extends UntypedActor {
    int index = 0;

    @Override
    public void preStart() throws Exception {
        super.preStart();
        System.out.println(self().path().name() + " instantiated and starting...");
    }

    @Override
    public void onReceive(Object o) throws Throwable {
        if (o instanceof String){
            String message = o.toString();
            if (message == "go"){
                int vak = main.getRandom(5);
                if (vak == 0) {
                    main.router2.route(new Reservation(getRandom(3), main.generalAdmission, self(), main.REQUESTED), self());
                }   else if (vak == 1){
                    main.router2.route(new Reservation(getRandom(3), main.northGrandStand, self(), main.REQUESTED), self());
                }   else if (vak == 2){
                    main.router2.route(new Reservation(getRandom(3), main.reserverdFloorSeating, self(), main.REQUESTED), self());
                }   else if (vak == 3){
                    main.router2.route(new Reservation(getRandom(3), main.southGrandStand, self(), main.REQUESTED), self());
                }   else if (vak == 4){
                    main.router2.route(new Reservation(getRandom(3), main.standingPit, self(), main.REQUESTED), self());
                }
            }
        }
        if (o instanceof Reservation) {
            Reservation message = (Reservation) o;
            if (message.status == main.RESERVED) {
                //flip a coin, shall i go or not?
                int coinFlip = main.getRandom(2);
                if (coinFlip == 0) {
                    getSender().tell(main.PAYMENT, self());
                    System.out.println(self().path().name() + " bought a ticket! vak: " + message.vakAgent.path().name() + " seats: " + message.seats);
                } else {
                    getSender().tell(main.CANCEL, self());
                    System.out.println(self().path().name() + " decided not to go");
                }

            }
            if (message.status == main.SOLD) {
                System.out.println(self().path().name() + " and his friends didnt get a ticket, its full!");
            }
        }
    }

    public static int getRandom(int random) {
        return (int) (Math.random() * random + 1);
    }

}