package com.softweb.desktop.database.repositories;

import com.softweb.desktop.database.entity.ApplicationImage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Application Image class repository for performing CRUD operations
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
@Repository
public interface ApplicationImageRepository extends CrudRepository<ApplicationImage, String> {
}
