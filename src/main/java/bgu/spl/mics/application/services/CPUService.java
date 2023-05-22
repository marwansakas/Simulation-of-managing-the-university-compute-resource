package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.objects.CPU;
import bgu.spl.mics.application.objects.Cluster;

/**
 * CPU service is responsible for handling the {@link }.
 * This class may not hold references for objects which it is not responsible for.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class CPUService extends MicroService {
    private CPU cpu;

    public CPUService(CPU cpu) {
        super("CPUService");
        this.cpu = cpu;
    }

    @Override
    protected void initialize() {
        subscribeBroadcast(TickBroadcast.class, (TickBroadcast c) -> {
            if(cpu.getD1() == null){
                cpu.setD1(cpu.getCluster().extractDataBatch());
                cpu.resetNumOfTicks();
            }
            if(cpu.getD1() != null){
                cpu.getCluster().getStatistics().increaseCPUTime();
                cpu.increaseNumTicks();
                if(cpu.getNumTicks() >= cpu.getTimeToProcess()){
                    Cluster.getInstance().getStatistics().increaseNumOfBatches();
                    cpu.getD1().getData().increaseProccesed();
                    cpu.getCluster().sendProcessedData(cpu.getD1());
                    cpu.setD1(null);
                    cpu.resetNumOfTicks();
                }
            }
        });
        subscribeBroadcast(TerminateBroadcast.class,(TerminateBroadcast c)->{
            terminate();
        });
    }
}
