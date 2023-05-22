package bgu.spl.mics.application.objects;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Passive object representing the cluster.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Cluster {

    private ArrayList<CPU> cpus;
    private final LinkedBlockingQueue<DataBatch> unprocessed_data;
    private final ConcurrentHashMap<GPU, LinkedBlockingQueue<DataBatch>> gpus;
    private final Statistic statistics;
    private final AtomicBoolean con;
    private final AtomicInteger clock;

    /**
     * Retrieves the single instance of this class.
     */
    private Cluster() {
        gpus = new ConcurrentHashMap<>();
        cpus = null;
        statistics = new Statistic();
        unprocessed_data = new LinkedBlockingQueue<>();
        clock = new AtomicInteger(0);
        con = new AtomicBoolean(true);
    }

    public void increaseTicks() {
        clock.incrementAndGet();
    }


    private static class SingletonHolder {
        private static Cluster instance = new Cluster();
    }

    public static Cluster getInstance() {
        return SingletonHolder.instance;
    }

    public void setCpus(ArrayList<CPU> cpus) {
        this.cpus = cpus;
    }


    public void setGpus(ArrayList<GPU> gpus) {
        for (GPU gpu : gpus) {
            this.gpus.putIfAbsent(gpu, new LinkedBlockingQueue<>());
        }
    }

    @Override
    public String toString() {
        return "Cluster{" +
                "gpus=" + gpus +
                ", cpus=" + cpus +
                '}';
    }

    public void receiveDataBatch(DataBatch dataBatches) {
        this.unprocessed_data.add(dataBatches);
    }

    public synchronized DataBatch extractDataBatch() {
        return unprocessed_data.poll();
    }

    public  DataBatch getDataBatch(GPU gpu) {
        return gpus.get(gpu).poll();
    }

    public synchronized void sendProcessedData(DataBatch data) {
        gpus.get(data.getGPU()).add(data);
    }

    public synchronized Statistic getStatistics() {
        return statistics;
    }

    public int getClock() {
        return clock.intValue();
    }

    public void setFalse(){
        System.out.println("Starting termination");
        this.con.set(false);
    }

    public AtomicBoolean getCon(){
        return this.con;
    }
}
