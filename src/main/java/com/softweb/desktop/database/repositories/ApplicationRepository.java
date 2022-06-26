package com.softweb.desktop.database.repositories;

import com.softweb.desktop.database.entity.Application;
import com.softweb.desktop.database.entity.Developer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий класса Application для выполнения CRUD операций
 *
 * @author Максимчук И.
 * @version 1.0
 */
@Repository
public interface ApplicationRepository extends CrudRepository<Application,Long> {
}
