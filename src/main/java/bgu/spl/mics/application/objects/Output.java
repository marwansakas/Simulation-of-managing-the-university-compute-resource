package bgu.spl.mics.application.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Output {

    ArrayList<StudentOutput> students;
    ConferenceOutput[] conferences;
    int cpuTimeUsed;
    int gpuTimeUsed;
    int batchesProcessed;

    public Output(ArrayList<Model> models, ConfrenceInformation[] conferences) {
        students = new ArrayList<>();
        HashMap<String, StudentOutput> students1;
        students1 = new HashMap<>();
        for (Model model : models) {
            Student student = model.getStudent();
            String studentName = student.getName();
            students1.putIfAbsent(studentName, new StudentOutput(studentName, model.getStudent().getDepartment(), student.getStatusName(), student.getPublications(), student.getPapersRead()));
            if (model.getStatusString().equals("Tested") || model.getStatusString().equals("Trained")) {

                ModelOutput toBeAdded = new ModelOutput(model.getName(), new DataOutput(model.getData().getTypeName(), model.getData().getSize()), model.getStatusString(), model.getResultsName());
                if (students1.containsKey(studentName)) {
                    students1.get(studentName).addModel(toBeAdded);
                }
            }
        }
        for(StudentOutput studentOutput : students1.values()){
            students.add(studentOutput);
        }
        this.conferences = new ConferenceOutput[conferences.length];
        int i = 0;
        for(ConfrenceInformation conference : conferences){
            this.conferences[i]=new ConferenceOutput(conference.getName(),conference.getDate());
            ArrayList<Model> conferencesModels = conference.getGoodResult();
            for(Model model: conferencesModels){
                this.conferences[i].addModel(model);
            }
            i++;
        }
        cpuTimeUsed = Cluster.getInstance().getStatistics().getNumberOfTimeCPUTime();
        gpuTimeUsed = Cluster.getInstance().getStatistics().getNumberOfTimeGPU();
        batchesProcessed = Cluster.getInstance().getStatistics().getNumberOfDataBatchesProcessed();

    }
}
