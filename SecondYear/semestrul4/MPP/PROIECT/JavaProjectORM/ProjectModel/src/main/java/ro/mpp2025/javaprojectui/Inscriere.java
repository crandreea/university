package ro.mpp2025.javaprojectui;
import java.util.Objects;

public class Inscriere extends Entity<Tuplu<Participant, Proba>> {
    private Participant IDparticipant;
    private Proba IDproba;

    public Inscriere(Participant IDparticipant, Proba IDproba) {
        this.IDparticipant = IDparticipant;
        this.IDproba = IDproba;
        this.setId(new Tuplu<>(IDparticipant, IDproba));
    }

    public Participant getIDparticipant() {
        return IDparticipant;
    }

    public Proba getIDproba() {
        return IDproba;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inscriere inscriere = (Inscriere) o;
        return Objects.equals(IDparticipant, inscriere.IDparticipant) && Objects.equals(IDproba, inscriere.IDproba);
    }

    @Override
    public int hashCode() {
        return Objects.hash(IDparticipant, IDproba);
    }
}
