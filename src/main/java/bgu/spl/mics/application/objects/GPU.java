package bgu.spl.mics.application.objects;


import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Passive object representing a single GPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class GPU {



    /**
     * Enum representing the type of the GPU.
     */
    enum Type {RTX3090, RTX2080, GTX1080}

    private final Type type;
    private final Cluster cluster;
    private final int training_time;
    private final int maximumBatches;
    private Model model;
    private int numberOfBatchesToTrain;
    private int batchesTrained;
    private int current_index;
    private boolean working;


    public GPU(String type) {
        if (type.equals("RTX3090")) {
            this.type = Type.RTX3090;
            maximumBatches = 32;
            training_time = 1;
        } else if (type.equals("RTX2080")) {
            this.type = Type.RTX2080;
            maximumBatches = 16;
            training_time = 2;
        } else {
            this.type = Type.GTX1080;
            maximumBatches = 8;
            training_time = 4;
        }
        this.cluster = Cluster.getInstance();
        this.model = null;
        numberOfBatchesToTrain = 0;
        batchesTrained = 0;
        working = true;
        current_index = 0;
    }

    public Type getType() {
        return type;
    }


    public Type getAType(String s) {
        if (s.equals("RTX3090")) {
            return Type.RTX3090;
        }
        if (s.equals("RTX2080")) {
            return Type.RTX2080;
        }
        return Type.GTX1080;
    }


    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void createDataBatches() {
        if(!cluster.getCon().get()){
            return;
        }
        int index = this.current_index;
        Data data = model.getData();
        int data_size = data.getSize();
        int counter = 0;
        while (index < data_size && counter < maximumBatches) {
            cluster.receiveDataBatch(new DataBatch(data, this, index));
            index += 1000;
            numberOfBatchesToTrain++;
            counter++;
        }
        this.current_index = index;
    }

    @Override
    public String toString() {
        return "GPU{" +
                "type=" + type +
                ", cluster=" + cluster +
                ", model=" + model +
                '}';
    }


    public void train() {
        int wanted_time = cluster.getClock() + training_time;
        while (batchesTrained < numberOfBatchesToTrain) {
            if(!cluster.getCon().get()){
                break;
            }
            DataBatch x = cluster.getDataBatch(this);
            if (x != null) {
                int current_time = cluster.getClock();
                while (wanted_time > current_time) {
                    current_time = cluster.getClock();
                    if(!cluster.getCon().get()){
                        break;
                    }
                }
                batchesTrained++;
                cluster.getStatistics().increaseGPUTime(training_time);
            }
            if(!cluster.getCon().get()){
                break;
            }
        }
        if(batchesTrained >= numberOfBatchesToTrain){
            return;
        }
        if(!cluster.getCon().get()){
            working = false;
        }
    }

    public void resetState(){
        this.batchesTrained = 0;
        this.model = null;
        this.numberOfBatchesToTrain = 0;
        this.current_index = 0;

    }

    public void setNotWorking(){
        this.working = false;
    }

    public int getCurrent_index() {
        return current_index;
    }

    public boolean isWorking() {
        return working;
    }

}
