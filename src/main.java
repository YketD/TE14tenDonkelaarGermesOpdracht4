import akka.actor.Actor;
import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.SupervisorStrategy;
import scala.Option;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

/**
 * Created by yketd on 12-10-2016.
 */
public class main {
    public static void main(String[] args) {
        new main().run();
    }
    public void run(){
        Actor actor = new Actor() {
            @Override
            public void akka$actor$Actor$_setter_$context_$eq(ActorContext actorContext) {

            }

            @Override
            public void akka$actor$Actor$_setter_$self_$eq(ActorRef actorRef) {

            }

            @Override
            public ActorContext context() {
                return null;
            }

            @Override
            public ActorRef self() {
                return null;
            }

            @Override
            public ActorRef sender() {
                return null;
            }

            @Override
            public PartialFunction<Object, BoxedUnit> receive() {
                return null;
            }

            @Override
            public void aroundReceive(PartialFunction<Object, BoxedUnit> partialFunction, Object o) {

            }

            @Override
            public void aroundPreStart() {

            }

            @Override
            public void aroundPostStop() {

            }

            @Override
            public void aroundPreRestart(Throwable throwable, Option<Object> option) {

            }

            @Override
            public void aroundPostRestart(Throwable throwable) {

            }

            @Override
            public SupervisorStrategy supervisorStrategy() {
                return null;
            }

            @Override
            public void preStart() throws Exception {

            }

            @Override
            public void postStop() throws Exception {

            }

            @Override
            public void preRestart(Throwable throwable, Option<Object> option) throws Exception {

            }

            @Override
            public void postRestart(Throwable throwable) throws Exception {

            }

            @Override
            public void unhandled(Object o) {

            }
        }ctor;
    }
}
