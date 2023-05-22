package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.application.objects.Model;

import java.util.ArrayList;

public class PublishConfrenceBroadcast implements Broadcast {

    ArrayList<Model> models;

    public PublishConfrenceBroadcast( ArrayList<Model>  models) {
        this.models = models;
    }

    public ArrayList<Model> getModels() {
        return models;
    }
}
