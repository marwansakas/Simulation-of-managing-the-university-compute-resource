package bgu.spl.mics.application.objects;

import java.util.Collection;

/**
 * Passive object representing a single CPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class CPU {
    private final int cores;
    private Collection<DataBatch> data;
    private final Cluster cluster;
    private int numTicks;
    private DataBatch d1;
    private int time_to_process;

    public CPU(int cores) {
        this.cores = cores;
        time_to_process = 32 / cores;
        this.data = null;
        this.cluster = Cluster.getInstance();

        d1 = null;
    }

    public int getTimeToProcess() {
        return time_to_process;
    }

    public DataBatch getD1() {
        return d1;
    }


    public void setD1(DataBatch d1) {
        if (d1 != null) {
            if (d1.getData().getType().equals(Data.Type.Images)) {
                time_to_process = (32 / cores) * 4;
            } else if (d1.getData().getType().equals(Data.Type.Text)) {
                time_to_process = (32 / cores) * 2;
            }else
                time_to_process = (32 / cores);
        }
        this.d1 = d1;
    }

    public void setData(Collection<DataBatch> d) {
        this.data = d;
    }

    public int getCores() {
        return cores;
    }

    public Collection<DataBatch> getData() {
        return data;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public int getNumTicks() {
        return numTicks;
    }

    public void setnumTicks(int numTicks) {
        this.numTicks = numTicks;
    }

    public void resetNumOfTicks(){
        this.numTicks = 0;
    }

    public void increaseNumTicks() {
        this.numTicks++;
    }

    @Override
    public String toString() {
        return "CPU{" +
                "cores=" + cores +
                ", data=" + data +
                ", cluster=" + cluster +
                '}';
    }


}
