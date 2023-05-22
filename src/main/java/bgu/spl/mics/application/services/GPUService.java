package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.messages.TestModelEvent;
import bgu.spl.mics.application.messages.TrainModelEvent;
import bgu.spl.mics.application.objects.*;

/**
 * GPU service is responsible for handling the
 * {@link TrainModelEvent} and {@link TestModelEvent},
 * in addition to sending the ,
 * This class may not hold references for objects which it is not responsible for.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class GPUService extends MicroService {

    private final GPU gpu;

    public GPUService(GPU gpu) {
        super("GPUService " + gpu.getType() );
        this.gpu = gpu;
    }

    @Override
    protected void initialize() {
        subscribeEvent(TrainModelEvent.class, (TrainModelEvent c) -> {
            c.getModel().setStatusTraining();
            gpu.setModel(c.getModel());
            int size = c.getModel().getData().getSize();
            while ( gpu.getCurrent_index() < size && gpu.isWorking()
                    && Cluster.getInstance().getCon().get()) {
                gpu.createDataBatches();
                gpu.train();
            }
            if(gpu.isWorking()) {
                c.getModel().setStatusTrained();
                gpu.resetState();
            }
            complete(c, "finished");
        });

        subscribeEvent(TestModelEvent.class, (TestModelEvent c) -> {
            if (Cluster.getInstance().getCon().get()) {
                c.getModel().setResults(Math.random());
                c.getModel().setStatusTested();
            }
            complete(c, c.getModel().getResults());
        });

        subscribeBroadcast(TerminateBroadcast.class, (TerminateBroadcast c) -> {
            gpu.setNotWorking();
            terminate();
        });
    }

}
