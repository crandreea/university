package ro.mpp2025.javaprojectui.service;


import ro.mpp2025.javaprojectui.CategorieVarsta;
import ro.mpp2025.javaprojectui.database.CategorieVarstaRepository;

public class CategorieVarstaService extends AbstractService<Integer, CategorieVarsta> {
    private final CategorieVarstaRepository categorieVarstaRepository;
    public CategorieVarstaService(CategorieVarstaRepository repository) {
        super(repository);
        this.categorieVarstaRepository = repository;
    }

    public CategorieVarsta findVarstaByRange(String selectedCategory) {
        return categorieVarstaRepository.findVarstaByRange(selectedCategory);
    }
}
