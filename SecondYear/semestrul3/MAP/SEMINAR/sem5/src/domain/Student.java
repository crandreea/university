package domain;

import java.util.Objects;

public class Student extends Entity<Long>{
    private int group;
    private String name;

    public Student(int group, String name) {
        this.group = group;
        this.name = name;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return group == student.group && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, name);
    }
}
