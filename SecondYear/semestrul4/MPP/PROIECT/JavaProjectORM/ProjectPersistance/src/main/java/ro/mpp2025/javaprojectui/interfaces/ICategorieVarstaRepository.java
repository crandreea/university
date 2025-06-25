package ro.mpp2025.javaprojectui.interfaces;

import ro.mpp2025.javaprojectui.*;

public interface ICategorieVarstaRepository extends Repository<Integer, CategorieVarsta> {
    public CategorieVarsta findVarstaByRange(String selectedCategory);
}
