package com.softweb.desktop.database.repositories;

import com.softweb.desktop.database.entity.ApplicationsSystems;
import com.softweb.desktop.database.entity.ApplicationsSystemsKey;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationSystemsRepository extends CrudRepository<ApplicationsSystems, ApplicationsSystemsKey> {
}
