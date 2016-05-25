package com.javarush.test.level29.lesson15.big01.human;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class University {
    private String name;
    private int age;
    private List<Student> students = new ArrayList<>();

    public University(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public List<Student> getStudents()

    {
        return students;
    }

    public void setStudents(List<Student> students)
    {
        this.students = students;
    }

    public Student getStudentWithAverageGrade(double averageGrade) {
        for (Student student : students) {
            if (student.getAverageGrade() == averageGrade)
                return student;
        }
        return null;
    }

    public Student getStudentWithMaxAverageGrade() {
        Iterator<Student> iterator = students.iterator();
        double maxAverageGrade = iterator.next().getAverageGrade();
        while (iterator.hasNext()){
            double temp = iterator.next().getAverageGrade();
            if (maxAverageGrade < temp)
                maxAverageGrade = temp;
        }
        return getStudentWithAverageGrade(maxAverageGrade);
    }

    public Student getStudentWithMinAverageGrade() {
        Iterator<Student> iterator = students.iterator();
        double minAverageGrade = iterator.next().getAverageGrade();
        while (iterator.hasNext()){
            double temp = iterator.next().getAverageGrade();
            if (minAverageGrade > temp)
                minAverageGrade = temp;
        }
        return getStudentWithAverageGrade(minAverageGrade);
    }

    public void expel(Student student) {
        students.remove(student);
    }
}
