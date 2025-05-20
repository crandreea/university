package ubb.scs.map.domain;

import java.util.Objects;

public class Tuplu <E1, E2>{
    private final E1 e1;
    private final E2 e2;

    public Tuplu(E1 e1, E2 e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public String toString() {
        return e1 + ", " + e2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuplu<?, ?> tuplu = (Tuplu<?, ?>) o;
        return Objects.equals(e1, tuplu.e1) && Objects.equals(e2, tuplu.e2)
        ||
              Objects.equals(e1, tuplu.e2) && Objects.equals(e2, tuplu.e1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(e1, e2);
    }
}
