using Model;
using  Persistence.database;

namespace Server;
public class CategorieVarstaService : AbstractService<int, CategorieVarsta>
{
    private readonly CategorieVarstaRepository categorieVarstaRepository;

    public CategorieVarstaService(CategorieVarstaRepository repository) : base(repository)
    {
        this.categorieVarstaRepository = repository;
    }

    public CategorieVarsta FindVarstaByRange(string selectedCategory)
    {
        return categorieVarstaRepository.FindVarstaByRange(selectedCategory);
    }
}