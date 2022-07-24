package MUD.View;

/**
 * This is menu that is shown when the program is 
 * ran. The user is able to log in or register
 * an account
 * 
 * @author Borna Eshraghi
 */
import MUD.Model.Save.PlayerRegistration;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SignInMenu {
    
    /**
     * Creates the scene for the menu 
     * @param stage the stage on which the sce
     * is shown on
     * @return the scene that contains all the nodes
     */
    public Scene playerRegistration(Stage stage) {
        VBox view = new VBox();
        Label label = new Label("Log in to your MUD account");
        label.setFont(new Font("Arial", 25));
        Label usernameLabel = new Label("Enter username");
        TextField inputUsername = new TextField();
        Label passwordLabel = new Label("Password");
        TextField inputPassword = new TextField();
        Button register = new Button("Register");
        register.setOnAction((e) -> {
            String result = PlayerRegistration.register(inputUsername.getText(), inputPassword.getText());
            if (result.equals("Success")) {
                GUI.username = inputUsername.getText();
                MainMenu mainMenu = new MainMenu();
                stage.setScene(mainMenu.createMainMenu(stage));
                stage.show();
            }
            else {
                Label message = new Label(result);
                StackPane stackPane = new StackPane(message);
                stage.setScene(new Scene(stackPane, 250, 100));
                stage.show();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                stage.setScene(playerRegistration(stage));
                stage.show();
                }
        });
        Button logIn = new Button("Log in");
        logIn.setOnAction((e) -> {
            if (PlayerRegistration.signIn(inputUsername.getText(), inputPassword.getText())) {
                GUI.username = inputUsername.getText();
                MainMenu mainMenu = new MainMenu();
                stage.setScene(mainMenu.createMainMenu(stage));
                stage.show();
            }
            else {
                Label message = new Label("Username or password is incorrect");
                StackPane stackPane = new StackPane(message);
                stage.setScene(new Scene(stackPane, 250, 100));
                stage.show();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                stage.setScene(playerRegistration(stage));
                stage.show();
            }
            
        });
        view.getChildren().addAll(label, usernameLabel, inputUsername, passwordLabel, inputPassword, register, logIn);
        Scene scene = new Scene(view);
        return scene;
    }
}
