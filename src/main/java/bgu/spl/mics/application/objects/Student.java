package bgu.spl.mics.application.objects;

import java.util.List;

/**
 * Passive object representing single student.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Student {

    /**
     * Enum representing the Degree the student is studying for.
     */
    enum Degree {
        MSc, PhD
    }

    private String name;
    private String department;
    Degree status;
    int publications;
    int papersRead;

    public Student(String name, String department, String status) {
        this.name = name;
        this.department = department;
        if (status.equals("MSc"))
            this.status = Degree.MSc;
        else
            this.status = Degree.PhD;
        this.publications = 0;
        this.papersRead = 0;
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

    public Degree getStatus() {
        return status;
    }

    public void setStatus(Degree status) {
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

    public boolean isPHD() {
        return status.equals(Degree.PhD);
    }

    public void increaseNumOfPaper() {
        papersRead++;
    }

    public void increaseNumOfPublications() {
        publications++;
    }

    public String getStatusName() {
        if (status.equals(Degree.MSc))
            return "MSc";
        else return "PhD";
    }


    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", status=" + status +
                ", publications=" + publications +
                ", papersRead=" + papersRead +
                '}';
    }
}
