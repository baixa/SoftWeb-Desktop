package com.softweb.desktop;

import com.softweb.desktop.controllers.RootController;
import com.softweb.desktop.database.utils.ConnectionValidator;
import com.softweb.desktop.database.utils.DataService;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * The class initializes the program window
 */
@Component
public class StageInitializer implements ApplicationListener<JavaFXMain.StageReadyEvent> {

    /**
     * FXML node containing the root markup element
     */
    @lombok.Getter
    private static BorderPane rootElement;

    /**
     * Root element controller
     */
    @lombok.Getter
    private static RootController rootController;

    /**
     * Center console controller
     */
    @lombok.Getter
    @lombok.Setter
    private static Initializable centerPanelController;

    /**
     * Program window
     */
    @lombok.Getter
    private static Stage stage;

    /**
     * Program title
     */
    private final String applicationTitle;

    /**
     * Context of a Spring Program
     */
    private final ApplicationContext applicationContext;

    /**
     * Service for interacting with entity repositories
     */
    public static DataService dataService;

    /**
     * Initializes the class object, sets the title of the program window, and fills in the context fields.
     *
     * @param applicationTitle Application title
     * @param dbUrl Database URL
     * @param applicationContext Application context
     * @param dataService Service for interacting with entity repositories
     */
    public StageInitializer(@Value("${spring.application.ui.title}") String applicationTitle, @Value("${spring.datasource.full-url}") String dbUrl, ApplicationContext applicationContext, DataService dataService) {
        this.applicationTitle = applicationTitle;
        this.applicationContext = applicationContext;
        StageInitializer.dataService = dataService;
        ConnectionValidator.checkConnectionStatus(dbUrl);
    }

    /**
     * Load root markup on application load
     *
     * @param event Application load event
     */
    @Override
    public void onApplicationEvent(JavaFXMain.StageReadyEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(StageInitializer.class.getResource("/layout/RootLayout.fxml"));
            loader.setControllerFactory(applicationContext::getBean);
            rootElement = loader.load();
            rootController = loader.getController();
            showMainPage();

            stage = event.getStage();
            stage.setMinWidth(1300);
            stage.setMinHeight(700);
            stage.setWidth(1300);
            stage.setHeight(700);
            stage.setTitle(applicationTitle);
            stage.getIcons().add(new Image(StageInitializer.class.getResourceAsStream("/images/logo.png")));
            Scene scene = new Scene(rootElement);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Загрузить страницу
     *      * @param path URL разметки
     *      * @return Контроллер страницы
     */
    public static Initializable navigate(String path) {
        FXMLLoader fxmlLoader = new FXMLLoader(StageInitializer.class.getResource(path + ".fxml"));
        try {
            Parent loaded = fxmlLoader.load();
            getRootElement().setCenter(loaded);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Initializable centerController = fxmlLoader.getController();
        setCenterPanelController(centerController);

        return centerController;
    }

    /**
     * Display start page
     */
    public static void showMainPage() {
        StageInitializer.navigate("/layout/PageDefaultApplicationsLayout");
    }
}
