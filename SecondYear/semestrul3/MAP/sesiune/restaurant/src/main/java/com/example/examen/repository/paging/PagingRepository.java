package com.example.examen.repository.paging;

import com.example.examen.domain.Entity;
import com.example.examen.repository.Repository;
import com.example.examen.utils.paging.Page;
import com.example.examen.utils.paging.Pageable;

import java.sql.SQLException;

public interface PagingRepository<ID, E extends Entity<ID>> extends Repository<ID, E> {
    Page<E> findAllOnPage(Pageable pageable) throws SQLException;
}
