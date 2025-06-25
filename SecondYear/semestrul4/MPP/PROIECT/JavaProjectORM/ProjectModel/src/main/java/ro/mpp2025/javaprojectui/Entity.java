package ro.mpp2025.javaprojectui;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;


public class Entity<ID> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1234987612L;

    private ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
