package ro.mpp2025.javaprojectui;

import java.util.Objects;

public class Proba extends Entity<Integer> {
    private String tip;
    private CategorieVarsta varsta;

    public Proba(){}
    public Proba(String tip, CategorieVarsta varsta) {
        this.tip = tip;
        this.varsta = varsta;
    }

    public String getTip() {
        return tip;
    }

    public CategorieVarsta getVarsta() {
        return varsta;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public void setVarsta(CategorieVarsta varsta) {
        this.varsta = varsta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proba proba = (Proba) o;
        return Objects.equals(tip, proba.tip) && Objects.equals(varsta, proba.varsta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tip, varsta);
    }

    @Override
    public String toString() {
        return "Proba{" +
                "tip='" + tip + '\'' +
                ", varsta=" + varsta +
                '}';
    }
}
