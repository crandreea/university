package ro.mpp2025.javaprojectui;

import java.util.Objects;

public class CategorieVarsta extends Entity<Integer>{

    private int varstaMin;
    private int varstaMax;

    public CategorieVarsta(int varstaMin, int varstaMax) {
        this.varstaMin = varstaMin;
        this.varstaMax = varstaMax;
    }

    public CategorieVarsta() {}

    public int getVarstaMin() {
        return varstaMin;
    }

    public int getVarstaMax() {
        return varstaMax;
    }

    public void setVarstaMax(int varstaMax) {
        this.varstaMax = varstaMax;
    }

    public void setVarstaMin(int varstaMin) {
        this.varstaMin = varstaMin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategorieVarsta that = (CategorieVarsta) o;
        return varstaMin == that.varstaMin && varstaMax == that.varstaMax;
    }

    @Override
    public int hashCode() {
        return Objects.hash(varstaMin, varstaMax);
    }

    @Override
    public String toString() {
        return "CategorieVarsta{" +
                "varstaMin=" + varstaMin +
                ", varstaMax=" + varstaMax +
                '}';
    }
}
