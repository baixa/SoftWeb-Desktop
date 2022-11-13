package com.softweb.desktop.database.repositories;

import com.softweb.desktop.database.entity.OperatingSystem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Operating System class repository for performing CRUD operations
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
@Repository
public interface OperatingSystemRepository extends CrudRepository<OperatingSystem, Long> {
}
