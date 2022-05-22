package com.softweb.desktop.database.repository;

import com.softweb.desktop.database.entity.Developer;
import org.springframework.data.repository.CrudRepository;

public interface DeveloperRepository extends CrudRepository<Developer, Long> {
}
