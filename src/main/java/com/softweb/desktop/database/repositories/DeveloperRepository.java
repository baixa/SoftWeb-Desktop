package com.softweb.desktop.database.repositories;

import com.softweb.desktop.database.entity.Developer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeveloperRepository extends CrudRepository<Developer, Long> {
    Developer findDeveloperByUsername(String username);
    List<Developer> findByUsername(String username);
}
