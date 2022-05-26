package com.softweb.desktop.database.repositories;

import com.softweb.desktop.database.entity.Application;
import com.softweb.desktop.database.entity.Developer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends CrudRepository<Application,Long> {
    List<Application> findByDeveloper(Developer developer);
    void deleteById(Long id);
}
