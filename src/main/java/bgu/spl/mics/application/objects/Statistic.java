package bgu.spl.mics.application.objects;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Statistic {

    private final ArrayList<String> modelNames;
    private final AtomicInteger numberOfDataBatchesProcessed;
    private final AtomicInteger numberOfTimeCPUTime;
    private int numberOfTimeGPU;

    public Statistic() {
        modelNames = new ArrayList<>();
        numberOfTimeCPUTime = new AtomicInteger(0);
        numberOfDataBatchesProcessed = new AtomicInteger(0);
        numberOfTimeGPU = 0;
    }

    public ArrayList<String> getModelNames() {
        return modelNames;
    }

    public int getNumberOfDataBatchesProcessed() {
        return numberOfDataBatchesProcessed.intValue();
    }

    public int getNumberOfTimeCPUTime() {
        return numberOfTimeCPUTime.intValue();
    }


    public synchronized void increaseCPUTime() {
        numberOfTimeCPUTime.incrementAndGet();
    }

    public synchronized void increaseGPUTime(int x) {
        numberOfTimeGPU += x;
    }

    public synchronized void increaseNumOfBatches() {
        numberOfDataBatchesProcessed.incrementAndGet();
    }

    public int getNumberOfTimeGPU() {
        return numberOfTimeGPU;
    }
}
