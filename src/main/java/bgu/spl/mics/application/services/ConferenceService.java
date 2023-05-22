package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.PublishConfrenceBroadcast;
import bgu.spl.mics.application.messages.PublishResultsEvent;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.objects.ConfrenceInformation;
import bgu.spl.mics.application.objects.Model;

/**
 * Conference service is in charge of
 * aggregating good results and publishing them via the {@link PublishConfrenceBroadcast},
 * after publishing results the conference will unregister from the system.
 * This class may not hold references for objects which it is not responsible for.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class ConferenceService extends MicroService {

    private final ConfrenceInformation confrence;



    public ConferenceService(ConfrenceInformation confrence) {
        super("ConferenceService");
        this.confrence = confrence;

    }

    @Override
    protected void initialize() {
        subscribeEvent(PublishResultsEvent.class, (PublishResultsEvent c) -> {
            Model model = c.getModel();
            confrence.addGoodModel(model);
            complete(c, true);
        });
        subscribeBroadcast(TickBroadcast.class, (TickBroadcast c) -> {
            if (c.getX() == -1) {
                terminate();
            } else if (c.getX() == confrence.getDate()) {
                sendBroadcast(new PublishConfrenceBroadcast(confrence.getGoodResult()));
                terminate();
            }
        });

    }
}
