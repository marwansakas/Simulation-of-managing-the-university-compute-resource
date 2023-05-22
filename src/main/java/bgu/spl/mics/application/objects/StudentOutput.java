package bgu.spl.mics.application.objects;

import java.util.ArrayList;

public class StudentOutput {

    String name;
    String department;
    String status;
    int publications;
    int papersRead;
    ArrayList<ModelOutput> trainedModels;

    public StudentOutput(String name, String department, String status, int publications, int papersRead) {
        this.name = name;
        this.department = department;
        this.status = status;
        this.publications = publications;
        this.papersRead = papersRead;
        trainedModels= new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPublications() {
        return publications;
    }

    public void setPublications(int publications) {
        this.publications = publications;
    }

    public int getPapersRead() {
        return papersRead;
    }

    public void setPapersRead(int papersRead) {
        this.papersRead = papersRead;
    }

    public void addModel(ModelOutput model){
        trainedModels.add(model);
    }
}
