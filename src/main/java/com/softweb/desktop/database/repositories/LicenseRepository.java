package com.softweb.desktop.database.repositories;

import com.softweb.desktop.database.entity.License;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий класса License для выполнения CRUD операций
 *
 * @author Максимчук И.
 * @version 1.0
 */
@Repository
public interface LicenseRepository extends CrudRepository<License, String> {
}
