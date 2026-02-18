package dk.sdu.cbse;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IGamePluginService;
import dk.sdu.cbse.common.util.ServiceLocator;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import java.util.ServiceLoader;

public class App extends Application {
    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GameData gameData = new GameData();
        World world = new World();

        gameData.setDisplayHeight(800);
        gameData.setDisplayWidth(600);

        ServiceLoader<IGamePluginService> serviceLoader = ServiceLoader.load(IGamePluginService.class);

        for (IGamePluginService service : serviceLoader) {
            ServiceLocator.addService(IGamePluginService.class, service);
            service.start(gameData, world);
        }

        Canvas canvas = new Canvas(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        Group group = new Group(canvas);
        Scene scene = new Scene(group);

        primaryStage.setScene(scene);
        primaryStage.show();

    }
}

