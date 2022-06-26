package com.softweb.desktop.database.repositories;

import com.softweb.desktop.database.entity.ApplicationImage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий класса Application Image для выполнения CRUD операций
 *
 * @author Максимчук И.
 * @version 1.0
 */
@Repository
public interface ApplicationImageRepository extends CrudRepository<ApplicationImage, String> {
}
