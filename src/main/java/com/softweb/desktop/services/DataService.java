package com.softweb.desktop.services;

import com.softweb.desktop.database.entity.Application;
import com.softweb.desktop.database.repositories.*;
import org.springframework.stereotype.Component;

@Component
public class DataService {

    private static DeveloperRepository developerRepository;
    private static ApplicationRepository applicationRepository;
    private static OperationSystemRepository operationSystemRepository;
    private static ApplicationImageRepository applicationImageRepository;
    private static ApplicationSystemsRepository applicationSystemsRepository;

    public DataService(DeveloperRepository developerRepository, ApplicationRepository applicationRepository,
                       OperationSystemRepository operationSystemRepository, ApplicationImageRepository applicationImageRepository,
                       ApplicationSystemsRepository applicationSystemsRepository) {
        DataService.developerRepository = developerRepository;
        DataService.applicationRepository = applicationRepository;
        DataService.operationSystemRepository = operationSystemRepository;
        DataService.applicationImageRepository = applicationImageRepository;
        DataService.applicationSystemsRepository = applicationSystemsRepository;
    }

    public static DeveloperRepository getDeveloperRepository() {
        return developerRepository;
    }

    public static ApplicationRepository getApplicationRepository() {
        return applicationRepository;
    }

    public static OperationSystemRepository getOperationSystemRepository() {
        return operationSystemRepository;
    }

    public static ApplicationImageRepository getApplicationImageRepository() {
        return applicationImageRepository;
    }

    public static ApplicationSystemsRepository getApplicationSystemsRepository() {
        return applicationSystemsRepository;
    }

    public static void updateApplication (Application application) {
        applicationRepository.save(application);
    }

}
