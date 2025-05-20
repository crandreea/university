package com.example.examen.domain;

import java.util.Objects;

public class Client extends Entity<Long> {
    private String name;
    private String username;

    public Client(String name, String username) {
        this.name = name;
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Client client = (Client) o;
        return Objects.equals(name, client.name) && Objects.equals(username, client.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, username);
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
