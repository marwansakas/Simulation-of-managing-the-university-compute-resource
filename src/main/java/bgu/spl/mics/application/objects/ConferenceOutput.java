package bgu.spl.mics.application.objects;

import java.util.ArrayList;

public class ConferenceOutput {

    String name;
    int date;
    ArrayList<ModelOutput> publications;

    public ConferenceOutput(String name, int date) {
        this.name = name;
        this.date = date;
        publications=new ArrayList<>();
    }

    public void addModel(Model model){
        publications.add(new ModelOutput(model.getName(),new DataOutput(model.getData().getTypeName(),model.getData().getSize()),model.getStatusString(),model.getResultsName()));
    }
}
