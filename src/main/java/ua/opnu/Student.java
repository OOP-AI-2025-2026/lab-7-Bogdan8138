package ua.opnu;

import java.util.Arrays;

public class Student {
    private String name;
    private String group;
    private int[] marks;

    public Student() {} 

    public Student(String name, String group, int[] marks) {
        this.name = name;
        this.group = group;
        this.marks = marks;
    }

    public String getName() { return name; }
    public String getGroup() { return group; }
    public int[] getMarks() { return marks; }

    public void setName(String name) { this.name = name; }
    public void setGroup(String group) { this.group = group; }
    public void setMarks(int[] marks) { this.marks = marks; }

    @Override public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", group='" + group + '\'' +
                ", marks=" + Arrays.toString(marks) +
                '}';
    }
}
