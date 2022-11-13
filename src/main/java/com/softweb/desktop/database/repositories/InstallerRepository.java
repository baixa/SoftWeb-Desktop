package com.softweb.desktop.database.repositories;

import com.softweb.desktop.database.entity.Installer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Installer class repository for performing CRUD operations
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
@Repository
public interface InstallerRepository extends CrudRepository<Installer, Integer> {
}
