package bgu.spl.mics.application.objects;

import bgu.spl.mics.application.objects.ConfrenceInformation;
import bgu.spl.mics.application.objects.Student;

import java.util.Arrays;

public class JSONInput {
    private StudentJSON[] Students;
    private String[] GPUS;
    private int[] CPUS;
    private ConfrenceInformation[] Conferences;
    private int TickTime;
    private int Duration;

    public ConfrenceInformation[] getConferences() {
        return Conferences;
    }

    public int getDuration() {
        return Duration;
    }

    public int getTickTime() {
        return TickTime;
    }

    public int[] getCPUS() {
        return CPUS;
    }

    public String[] getGPUS() {
        return GPUS;
    }

    public void setStudents(StudentJSON[] students) {
        Students = students;
    }

    public void setGPUS(String[] GPUS) {
        this.GPUS = GPUS;
    }

    public void setCPUS(int[] CPUS) {
        this.CPUS = CPUS;
    }

    public void setConferences(ConfrenceInformation[] conferences) {
        Conferences = conferences;
    }

    public void setTickTime(int tickTime) {
        TickTime = tickTime;
    }

    public void setDuration(int duration) {
        Duration = duration;
    }

    public StudentJSON[] getStudents() {
        return Students;
    }

    @Override
    public String toString() {
        return "JSONInput{" +
                "Students=" + Arrays.toString(Students) +
                ", GPUS=" + Arrays.toString(GPUS) +
                ", CPUS=" + Arrays.toString(CPUS) +
                ", Conferences=" + Arrays.toString(Conferences) +
                ", TickTime=" + TickTime +
                ", Duration=" + Duration +
                '}';
    }
}