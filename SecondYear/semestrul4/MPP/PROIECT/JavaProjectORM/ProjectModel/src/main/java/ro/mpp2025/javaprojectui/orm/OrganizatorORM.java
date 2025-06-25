package ro.mpp2025.javaprojectui.orm;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import ro.mpp2025.javaprojectui.Organizator;

import java.util.Objects;

@Entity
@Table(name = "organizatori")
public class OrganizatorORM extends ro.mpp2025.javaprojectui.Entity<Integer>{

    @NotNull
    @Column(name = "username")
    private String username;

    @NotNull
    @Column(name = "password")
    private String password;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public Integer getId() {
        return super.getId();
    }

    @Override
    public void setId(Integer id) {
        super.setId(id);
    }

    public OrganizatorORM() {}

    public OrganizatorORM(String username, String password) {
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
