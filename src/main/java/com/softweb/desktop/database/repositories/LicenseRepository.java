package com.softweb.desktop.database.repositories;

import com.softweb.desktop.database.entity.License;
import org.springframework.data.repository.CrudRepository;

public interface LicenseRepository extends CrudRepository<License, String> {
}
