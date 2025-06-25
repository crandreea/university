package ro.mpp2025.javaprojectui.dto;

import ro.mpp2025.javaprojectui.Entity;

import java.io.Serializable;
public class ParticipantDTO extends Entity<Integer> implements Serializable {
    private final String name;
    private final int age;

    public ParticipantDTO(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "ParticipantDTO{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
