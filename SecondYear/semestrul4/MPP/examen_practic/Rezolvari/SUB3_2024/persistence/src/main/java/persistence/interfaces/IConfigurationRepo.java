package persistence.interfaces;

import model.Configuration;
import persistence.Repository;

import java.util.Optional;

public interface IConfigurationRepo extends Repository<Integer, Configuration> {
    Optional<Configuration> delete(Long id);

    Optional<Configuration> update(Configuration entity);
}
