package bgu.spl.mics.application.services;

import bgu.spl.mics.Future;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.objects.Cluster;
import bgu.spl.mics.application.objects.Model;
import bgu.spl.mics.application.objects.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Student is responsible for sending the {@link TrainModelEvent},
 * {@link TestModelEvent} and {@link PublishResultsEvent}.
 * In addition, it must sign up for the conference publication broadcasts.
 * This class may not hold references for objects which it is not responsible for.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class StudentService extends MicroService {

    private final Student student;
    private final List<Model> models;
    private boolean terminated = false;


    public StudentService(Student student, List<Model> models) {
        super("StudentService");
        this.student = student;
        this.models = models;
    }

    @Override
    protected void initialize() {

        Thread t = new Thread(()->{
            Future<String> train_results;
            Future<String> test_results;
            String answer = null;
            String answer2 = null;
            for (Model model : models) {
                train_results = sendEvent(new TrainModelEvent(model));
                if(train_results != null) {
                    while(answer == null) {
                        answer = train_results.get(100, TimeUnit.MILLISECONDS);
                        if (terminated)
                            break;
                    }
                    if (!answer.equals("")) {
                        Cluster.getInstance().getStatistics().getModelNames().add(model.getName());
                    }
                    test_results = sendEvent(new TestModelEvent(model));
                    if (test_results != null) {
                        while(answer2 == null) {
                            answer2 = test_results.get(100, TimeUnit.MILLISECONDS);
                            if(terminated)
                                break;
                        }
                        if (answer2.equals("GOOD")) {
                            sendEvent(new PublishResultsEvent(model));
                        }
                    }
                }
            }
        });
        t.start();

        subscribeBroadcast(PublishConfrenceBroadcast.class, (PublishConfrenceBroadcast c) -> {
            ArrayList<Model> models = c.getModels();
            for (Model model : models) {
                if (model.getStudent().equals(student))
                    student.increaseNumOfPublications();
                else
                    student.increaseNumOfPaper();
            }
        });

        subscribeBroadcast(TerminateBroadcast.class, (TerminateBroadcast c) -> {
            terminate();
            terminated = true;
        });


    }


}

