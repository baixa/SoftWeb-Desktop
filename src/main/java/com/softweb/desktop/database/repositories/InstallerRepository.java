package com.softweb.desktop.database.repositories;

import com.softweb.desktop.database.entity.Installer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface of Installer class to perform CRUD operations
 */
@Repository
public interface InstallerRepository extends CrudRepository<Installer, Integer> {
}
