package bgu.spl.mics.application.objects;

public class ModelOutput {

    String name;
    DataOutput data;
    String status;
    String results;

    public ModelOutput(String name, DataOutput data, String status, String results) {
        this.name = name;
        this.data = data;
        this.status = status;
        this.results = results;
    }
}
