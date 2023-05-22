package bgu.spl.mics.application.objects;

import java.util.Arrays;
import java.util.List;

public class StudentJSON {
    private String name;
    private String department;
    private String status;
    private List<ModelJSON> models;

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

    public List<ModelJSON> getModels() {
        return models;
    }

    public void setModels(List<ModelJSON> models) {
        this.models = models;
    }

    @Override
    public String toString() {
        return "StudentJSON{" +
                "name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", status='" + status + '\'' +
                ", models=" + models +
                '}';
    }
}
