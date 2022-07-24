package MUD.View;


/**
 * This class is what genreates the GUI for the game
 * 
 * @author Borna Eshraghi
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GUI extends Application {
    static String username;
    PTUI ptui;

    @Override
    public void start(Stage stage) {
        SignInMenu signInMenu = new SignInMenu();
        Scene scene = signInMenu.playerRegistration(stage);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
