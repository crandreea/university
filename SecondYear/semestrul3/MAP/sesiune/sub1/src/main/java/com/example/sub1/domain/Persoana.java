package com.example.sub1.domain;

import java.util.Objects;

public class Persoana extends Entity<Long> {
    private String name;
    private String surname;
    private String username;
    private String password;
    private String oras;
    private String strada;
    private String Nrstrada;
    private String telefon;

    public Persoana(String name, String surname, String username, String password, String oras, String strada, String nrstrada, String telefon) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.oras = oras;
        this.strada = strada;
        Nrstrada = nrstrada;
        this.telefon = telefon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOras() {
        return oras;
    }

    public void setOras(String oras) {
        this.oras = oras;
    }

    public String getStrada() {
        return strada;
    }

    public void setStrada(String strada) {
        this.strada = strada;
    }

    public String getNrstrada() {
        return Nrstrada;
    }

    public void setNrstrada(String nrstrada) {
        Nrstrada = nrstrada;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persoana persoana = (Persoana) o;
        return Objects.equals(name, persoana.name) && Objects.equals(surname, persoana.surname) && Objects.equals(username, persoana.username) && Objects.equals(password, persoana.password) && Objects.equals(oras, persoana.oras) && Objects.equals(strada, persoana.strada) && Objects.equals(Nrstrada, persoana.Nrstrada) && Objects.equals(telefon, persoana.telefon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, username, password, oras, strada, Nrstrada, telefon);
    }
}
