package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.objects.Cluster;

/**
 * TimeService is the global system timer There is only one instance of this micro-service.
 * It keeps track of the amount of ticks passed since initialization and notifies
 * all other micro-services about the current time tick using {@link TickBroadcast}.
 * This class may not hold references for objects which it is not responsible for.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class TimeService extends MicroService {

    private final long speed;
    private final long duration;

    public TimeService(int speed, int duration) {
        super("TimeService");
        this.speed = speed;
        this.duration = duration;
    }

    @Override
    protected void initialize() {
        int x = 1;
        while (x != duration) {
            sendBroadcast(new TickBroadcast(x));
            try {
                Thread.sleep(speed);
                x++;
                Cluster.getInstance().increaseTicks();
            } catch (Exception e) {
            }
        }
        sendBroadcast(new TickBroadcast(-1));
        sendBroadcast(new TerminateBroadcast());
        Cluster.getInstance().setFalse();
        terminate();
    }

}
