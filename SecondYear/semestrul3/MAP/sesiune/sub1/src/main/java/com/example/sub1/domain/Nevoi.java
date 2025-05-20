package com.example.sub1.domain;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

import java.time.LocalDateTime;
import java.util.Objects;

public class Nevoi extends Entity<Long> {

    private SimpleStringProperty titlu;
    private SimpleStringProperty descriere;
    private SimpleStringProperty status;
    private SimpleObjectProperty<LocalDateTime> deadline;
    private SimpleObjectProperty<Long> omInNevoie;
    private SimpleObjectProperty<Long> omSalvator;

    public Nevoi(String titlu, String descriere, String status, LocalDateTime deadline, Long omInNevoie, Long omSalvator) {
        this.titlu = new SimpleStringProperty(titlu);
        this.descriere = new SimpleStringProperty(descriere);
        this.status = new SimpleStringProperty(status);
        this.deadline = new SimpleObjectProperty<>(deadline);
        this.omInNevoie = new SimpleObjectProperty<>(omInNevoie);
        this.omSalvator = new SimpleObjectProperty<>(omSalvator);
    }

    public String getTitlu() {
        return titlu.get();
    }

    public void setTitlu(String titlu) {
        this.titlu.set(titlu);
    }

    public SimpleStringProperty titluProperty() {
        return titlu;
    }

    public String getDescriere() {
        return descriere.get();
    }

    public void setDescriere(String descriere) {
        this.descriere.set(descriere);
    }

    public SimpleStringProperty descriereProperty() {
        return descriere;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public LocalDateTime getDeadline() {
        return deadline.get();
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline.set(deadline);
    }

    public SimpleObjectProperty<LocalDateTime> deadlineProperty() {
        return deadline;
    }

    public Long getOmInNevoie() {
        return omInNevoie.get();
    }

    public void setOmInNevoie(Long omInNevoie) {
        this.omInNevoie.set(omInNevoie);
    }

    public SimpleObjectProperty<Long> omInNevoieProperty() {
        return omInNevoie;
    }

    public Long getOmSalvator() {
        return omSalvator.get();
    }

    public void setOmSalvator(Long omSalvator) {
        this.omSalvator.set(omSalvator);
    }

    public SimpleObjectProperty<Long> omSalvatorProperty() {
        return omSalvator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nevoi nevoi = (Nevoi) o;
        return Objects.equals(titlu.get(), nevoi.titlu.get()) &&
                Objects.equals(descriere.get(), nevoi.descriere.get()) &&
                Objects.equals(status.get(), nevoi.status.get()) &&
                Objects.equals(deadline.get(), nevoi.deadline.get()) &&
                Objects.equals(omInNevoie.get(), nevoi.omInNevoie.get()) &&
                Objects.equals(omSalvator.get(), nevoi.omSalvator.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(titlu.get(), descriere.get(), status.get(), deadline.get(), omInNevoie.get(), omSalvator.get());
    }
}
