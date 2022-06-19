package com.softweb.desktop.database.repositories;

import com.softweb.desktop.database.entity.OperatingSystem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface of Operating System class to perform CRUD operations
 */
@Repository
public interface OperatingSystemRepository extends CrudRepository<OperatingSystem, Long> {
}
