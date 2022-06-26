package com.softweb.desktop.database.repositories;

import com.softweb.desktop.database.entity.OperatingSystem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий класса Operating System для выполнения CRUD операций
 *
 * @author Максимчук И.
 * @version 1.0
 */
@Repository
public interface OperatingSystemRepository extends CrudRepository<OperatingSystem, Long> {
}
