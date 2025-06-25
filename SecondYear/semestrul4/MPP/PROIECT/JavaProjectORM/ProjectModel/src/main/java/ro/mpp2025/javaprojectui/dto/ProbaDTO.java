package ro.mpp2025.javaprojectui.dto;


import ro.mpp2025.javaprojectui.CategorieVarsta;
import ro.mpp2025.javaprojectui.Entity;
import ro.mpp2025.javaprojectui.Proba;

import java.io.Serializable;

public  class ProbaDTO extends Entity<Integer> implements Serializable {
    private final Proba proba;
    private final int registeredCount;

    public ProbaDTO(Proba proba, int registeredCount) {
        this.proba = proba;
        this.registeredCount = registeredCount;
    }

    public Proba getProba() {
        return proba;
    }

    public String getEventName() {
        return proba.getTip();
    }

    public String getAgeGroup() {
        CategorieVarsta cv = proba.getVarsta();
        return cv.getVarstaMin() + "-" + cv.getVarstaMax() + " ani";
    }

    public int getRegisteredCount() {
        return registeredCount;
    }

    @Override
    public String toString() {
        return "ProbaDTO{" +
                "proba=" + proba +
                ", registeredCount=" + registeredCount +
                '}';
    }
}
