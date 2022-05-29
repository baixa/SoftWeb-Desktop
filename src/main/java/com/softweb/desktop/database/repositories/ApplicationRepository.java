package com.softweb.desktop.database.repositories;

import com.softweb.desktop.database.entity.Application;
import com.softweb.desktop.database.entity.Developer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends CrudRepository<Application,Long> {
    List<Application> findByDeveloper(Developer developer);
    void deleteById(Long id);

    @Modifying
    @Query("update Application app set app.name = ?2, app.shortDescription = ?3, app.description = ?4 WHERE app.id = ?1")
    void updateApplication(Long id, String name, String shortDescription, String description);
}
