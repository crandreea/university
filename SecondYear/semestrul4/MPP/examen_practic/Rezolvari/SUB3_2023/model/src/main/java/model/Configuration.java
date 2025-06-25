package model;

import jakarta.persistence.Table;

@jakarta.persistence.Entity
@Table(name = "configurations")
public class Configuration extends Entity<Integer> {

    public Configuration() {
    }

    @Override
    public String toString() {
        return "Configuration{" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
