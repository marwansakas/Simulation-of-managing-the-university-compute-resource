package bgu.spl.mics.application;


import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.objects.*;
import bgu.spl.mics.application.services.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * This is the Main class of Compute Resources Management System application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output a text file.
 */
public class CRMSRunner {

    public static void main(String[] args) {
        ArrayList<Model> models = new ArrayList<>();
        ArrayList<Model> allModels = new ArrayList<>();
        ArrayList<Student> students = new ArrayList<>();
        ArrayList<GPU> gpus = new ArrayList<>();

        ArrayList<Thread> conferenceThreads = new ArrayList<>();
        ArrayList<Thread> cpuThreads = new ArrayList<>();
        ArrayList<Thread> gpuThreads = new ArrayList<>();
        ArrayList<Thread> studentThreads = new ArrayList<>();

        JSONInput input = null;
        try {
            //input
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Reader reader = Files.newBufferedReader(Paths.get(args[0]));
            input = gson.fromJson(reader, JSONInput.class);
            reader.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        Student tempStudent;
        Model tempModel;


        for (ConfrenceInformation confrenceInformation : input.getConferences()) {
            conferenceThreads.add(new Thread(new ConferenceService(confrenceInformation)));
        }

        ArrayList<CPU> queue = new ArrayList<>();
        for (int cpu : input.getCPUS()) {
            CPU tempCPU = new CPU(cpu);
            queue.add(tempCPU);
            cpuThreads.add(new Thread(new CPUService(tempCPU)));
        }

        for (String gpu : input.getGPUS()) {
            GPU tempGPU = new GPU(gpu);
            gpus.add(tempGPU);
            gpuThreads.add(new Thread(new GPUService(tempGPU)));
        }


        for (StudentJSON student : input.getStudents()) {
            tempStudent = new Student(student.getName(), student.getDepartment(), student.getStatus());
            students.add(tempStudent);
            for (ModelJSON model : student.getModels()) {
                tempModel = new Model(model.getName(), new Data(model.getType(), model.getSize()), tempStudent);
                models.add(tempModel);
                allModels.add(tempModel);
            }
            studentThreads.add(new Thread(new StudentService(tempStudent, models)));
            models = new ArrayList<>();
        }

        Thread timeThread = new Thread(new TimeService(input.getTickTime(), input.getDuration()));

        Cluster.getInstance().setCpus(queue);
        Cluster.getInstance().setGpus(gpus);


        ArrayList<Thread> allthreads = new ArrayList<>();
        for (Thread thread : conferenceThreads) {
            thread.start();
            allthreads.add(thread);
        }
        for (Thread thread : cpuThreads) {
            thread.start();
            allthreads.add(thread);
        }
        for (Thread thread : gpuThreads) {
            thread.start();
            allthreads.add(thread);
        }

        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Thread thread : studentThreads) {
            thread.start();
            allthreads.add(thread);
        }

        timeThread.start();
        allthreads.add(timeThread);

        try {
            timeThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Output output = new Output(allModels, input.getConferences());

        Gson outputFile = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fileWriter = new FileWriter(args[1]);
            outputFile.toJson(output, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
