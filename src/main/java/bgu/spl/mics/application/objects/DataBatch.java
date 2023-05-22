package bgu.spl.mics.application.objects;

/**
 * Passive object representing a data used by a model.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */

public class DataBatch {

    private Data data;
    private final GPU processing_gpu;
    private int start_index;

    public DataBatch(Data data,GPU gpu, int start_index) {
        this.data = data;
        this.start_index = start_index;
        this.processing_gpu = gpu;
    }
    public GPU getGPU(){
        return processing_gpu;
    }
    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int getStart_index() {
        return start_index;
    }

    public void setStart_index(int start_index) {
        this.start_index = start_index;
    }
}
