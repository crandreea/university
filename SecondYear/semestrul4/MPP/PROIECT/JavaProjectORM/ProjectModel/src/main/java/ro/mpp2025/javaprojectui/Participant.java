//package ro.mpp2025.javaprojectui;
//
//public class Participant extends ro.mpp2025.javaprojectui.Entity<Integer> {
//
//    private String nume;
//    private String cnp;
//
//    public Participant (String nume, String cnp) {
//        this.nume = nume;
//        this.cnp = cnp;
//    }
//
//    public String getNume() {
//        return nume;
//    }
//
//    public String getCnp() {
//        return cnp;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        return false;
//    }
//
//    @Override
//    public int hashCode() {
//        return 0;
//    }
//
//    @Override
//    public String toString() {
//        return "Participant{" +
//                "nume='" + nume + '\'' +
//                ", cnp='" + cnp + '\'' +
//                '}';
//    }
//}

package ro.mpp2025.javaprojectui;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@jakarta.persistence.Entity
@Table(name = "participanti")
public class Participant extends ro.mpp2025.javaprojectui.Entity<Integer> {

    @NotNull
    @Column(name = "nume")
    private String nume;

    @NotNull
    @Column(name = "cnp")
    private String cnp;

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

    public Participant() {}

    public Participant(String nume, String cnp) {
        this.nume = nume;
        this.cnp = cnp;
    }

    public String getNume() {
        return nume;
    }

    public String getCnp() {
        return cnp;
    }

    public void setNume(@NotNull String nume) {
        this.nume = nume;
    }

    public void setCnp(@NotNull String cnp) {
        this.cnp = cnp;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "nume='" + nume + '\'' +
                ", cnp='" + cnp + '\'' +
                '}';
    }
}
