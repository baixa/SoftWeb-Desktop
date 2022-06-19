package com.softweb.desktop.database.repositories;

import com.softweb.desktop.database.entity.License;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface of License class to perform CRUD operations
 */
@Repository
public interface LicenseRepository extends CrudRepository<License, String> {
}
