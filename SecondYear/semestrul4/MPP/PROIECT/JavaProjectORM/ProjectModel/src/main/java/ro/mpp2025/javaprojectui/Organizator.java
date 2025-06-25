package ro.mpp2025.javaprojectui;

import java.util.Objects;

public class Organizator extends Entity<Integer> {

    private String username;
    private String password;


    public Organizator(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organizator that = (Organizator) o;
        return Objects.equals(username, that.getUsername()) && Objects.equals(password, that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "Organizator{" +
                "org_id=" + getId() +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
