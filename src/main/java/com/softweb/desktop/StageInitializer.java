package com.softweb.desktop;

import com.softweb.desktop.database.repositories.ApplicationRepository;
import com.softweb.desktop.database.utils.ConnectionValidator;
import com.softweb.desktop.services.DataService;
import com.softweb.desktop.utils.ftp.FtpLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StageInitializer implements ApplicationListener<JavaFXMain.StageReadyEvent> {

    private static BorderPane rootElement;
    private String applicationTitle;
    private ApplicationContext applicationContext;

    public static DataService dataService;

    public StageInitializer(@Value("${spring.application.ui.title}") String applicationTitle, ApplicationContext applicationContext, DataService dataService) {
        this.applicationTitle = applicationTitle;
        this.applicationContext = applicationContext;
        StageInitializer.dataService = dataService;
        ConnectionValidator.isConnectionValid();
        FtpLoader.testUpLoadFromDisk();
    }

    public static BorderPane getRootElement() {
        return rootElement;
    }

    @Override
    public void onApplicationEvent(JavaFXMain.StageReadyEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(StageInitializer.class.getResource("/layout/RootLayout.fxml"));
            loader.setControllerFactory(aClass -> applicationContext.getBean(aClass));
            rootElement = loader.load();
            showDefaultContent();

            Stage stage = event.getStage();
            stage.setMinWidth(1200);
            stage.setMinHeight(600);
            stage.setTitle(applicationTitle);
            Scene scene = new Scene(rootElement);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static Parent loadFXML(String path) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StageInitializer.class.getResource(path + ".fxml"));
        return fxmlLoader.load();
    }

    public static void navigate(String path) {
        try {
            getRootElement().setCenter(loadFXML(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showDefaultContent() {
        StageInitializer.navigate("/layout/PageDefaultApplicationsLayout");
    }
}
