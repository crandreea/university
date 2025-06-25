package persistence.interfaces;

import model.Configuration;
import model.ConfigurationWord;
import model.Word;
import persistence.Repository;

import java.util.Optional;

public interface IConfigurationWordRepo extends Repository<Integer, ConfigurationWord> {
    Iterable<Word> findWordsByConfiguration(Configuration configuration);
    Iterable<ConfigurationWord> findConfigurationWordsByConfiguration(Configuration configuration);

    Optional<ConfigurationWord> delete(Long id);

    Optional<ConfigurationWord> update(ConfigurationWord entity);
}
