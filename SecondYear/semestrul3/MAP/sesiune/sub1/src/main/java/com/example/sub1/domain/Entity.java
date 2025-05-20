package com.example.sub1.domain;

import java.io.Serializable;
import java.util.Objects;

public class Entity<ID> implements Serializable {
    static final long serialVersionUID = 1L;
    private ID id;
    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity<?> entity = (Entity<?>) o;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
