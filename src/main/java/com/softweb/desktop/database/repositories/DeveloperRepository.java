package com.softweb.desktop.database.repositories;

import com.softweb.desktop.database.entity.Developer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий класса Developer для выполнения CRUD операций
 *
 * @author Максимчук И.
 * @version 1.0
 */
@Repository
public interface DeveloperRepository extends CrudRepository<Developer, Long> {
}
