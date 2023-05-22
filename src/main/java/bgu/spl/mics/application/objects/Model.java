package bgu.spl.mics.application.objects;

/**
 * Passive object representing a Deep Learning model.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Model {

    enum Result{None,Good,Bad}

    enum Status{PreTrained, Training , Trained , Tested}

    private String name;
    private Data data;
    private Student student;
    private Result results;
    private Status status;

    public Model() {
    }

    public Model(String name, Data data, Student student) {
        this.name = name;
        this.data = data;
        this.student = student;
        this.results=Result.None;
        this.status = Status.PreTrained;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getResults() {
        if(this.results.equals(Result.Good)){
            return "GOOD";
        }
        return "BAD";
    }

    public void setResults(double probabilaty) {
        if(student.isPHD()){
            if(probabilaty < 0.8){
                this.results=Result.Good;
            }else{
                this.results=Result.Bad;
            }
        }else{
            if(probabilaty < 0.6){
                this.results=Result.Good;
            }else{
                this.results=Result.Bad;
            }
        }
        status = Status.Tested;
    }

    public String getStatusString(){
        if(status.equals(Status.PreTrained)){
            return "PreTrained";
        }else if(status.equals(Status.Tested)){
            return "Tested";
        }else if(status.equals(Status.Trained))
            return "Trained";
        else return "Training";
    }

   public void setStatusTraining(){
        status=Status.Training;
   }

   public void setStatusTrained(){
        status=Status.Trained;
    }

    public void setStatusTested() {
        status = Status.Tested;
    }

    public String getResultsName() {
        if(results.equals(Result.None))
            return "None";
        else if(results.equals(Result.Good)) {
            return "Good";
        }
        else{
            return "Bad";
        }
    }

    @Override
    public String toString() {
        return "Model{" +
                "name='" + name + '\'' +
                ", data=" + data +
                ", student=" + student +
                ", results=" + results +
                '}';
    }
}
