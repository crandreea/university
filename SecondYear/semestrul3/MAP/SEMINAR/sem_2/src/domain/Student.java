package domain;

import java.util.Objects;

public class Student extends Entity<Long> implements Comparable<Student>{
    private String name;
    private float medie;

    public Student(String name, float medie) {

        this.name = name;
        this.medie = medie;
    }

    @Override
    public String toString() {
        return "domain.Student [name=" + name + ", medie=" + medie + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Float.compare(medie, student.medie) == 0 && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, medie);
    }


    @Override
    public int compareTo(Student o) {
        return (int) (this.medie - o.medie);
    }

    public float getMedie() {
        return medie;
    }

    public void setMedie(float medie) {
        this.medie = medie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
