package ubb.scs.map.service;

import ubb.scs.map.domain.Entity;

public interface Service<ID, E extends Entity<ID>> {

    E findOne(ID id);

    Iterable<E> findAll();

    void save(E entity);

    E delete(ID id);

    E update(E entity);
}