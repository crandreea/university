package domain;

import java.util.Objects;

public class Nota {
    private Student student;
    private Tema tema;
    private double value;
    private String profesor;

    public Nota(Student student, Tema tema, Double value, String profesor) {
        this.student = student;
        this.tema = tema;
        this.value = value;
        this.profesor = profesor;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nota nota = (Nota) o;
        return Double.compare(value, nota.value) == 0 && Objects.equals(student, nota.student) && Objects.equals(tema, nota.tema) && Objects.equals(profesor, nota.profesor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, tema, value, profesor);
    }

    @Override
    public String toString() {
        return "Nota{" +
                "student=" + student +
                ", tema=" + tema +
                ", value=" + value +
                ", profesor='" + profesor + '\'' +
                '}';
    }
}
