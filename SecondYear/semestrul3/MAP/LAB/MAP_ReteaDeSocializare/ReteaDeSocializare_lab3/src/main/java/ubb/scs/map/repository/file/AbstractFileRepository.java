package ubb.scs.map.repository.file;

import ubb.scs.map.domain.Entity;
import ubb.scs.map.domain.Utilizator;
import ubb.scs.map.domain.validators.Validator;
import ubb.scs.map.repository.Repository;
import ubb.scs.map.repository.memory.InMemoryRepository;

import java.io.*;
import java.nio.Buffer;

public abstract class AbstractFileRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID, E> {
    private final String fileName;

    public AbstractFileRepository(Validator<E> validator, String fileName) {
        super(validator);
        this.fileName = fileName;
        loadFromFile();
    }


    public abstract E createEntity(String line);

    public abstract String saveEntity(E entity);


    private void writeToFile() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (E entity : entities.values()) {
                String ent = saveEntity(entity);
                writer.write(ent);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                if (line.trim().isEmpty()) {
                    continue; // Sare peste liniile goale
                }
                E entity = createEntity(line);
                super.save(entity);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public E save(E entity) {
        E e = super.save(entity);
        writeToFile();
        return e;
    }

    @Override
    public E delete(ID id) {
        E entity = super.delete(id);
        writeToFile();
        return entity;
    }

    @Override
    public E update(E entity) {
        E e = super.update(entity);
        writeToFile();
        return e;
    }
}
