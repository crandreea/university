package model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.io.Serial;
import java.io.Serializable;

@MappedSuperclass
public class Entity<ID> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1234987612L;

    private ID id;

    @Id
    @GeneratedValue(generator="increment")
    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}