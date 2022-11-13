package com.softweb.desktop.database.repositories;

import com.softweb.desktop.database.entity.License;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * License class repository for performing CRUD operations
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
@Repository
public interface LicenseRepository extends CrudRepository<License, String> {
}
