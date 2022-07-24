package MUD.View;

/**
 * This class is responsible for the view and behavior to 
 * change the users password
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
import javafx.stage.Stage;

public class ChangePassword {
    

    /**
     * This method is for creating the gui for 
     * changing a users password
     * @param stage the stage that is used to disply the scene for the gui
     * @return the scene for dislaying the gui
     */
    public Scene changePasswordMenu(Stage stage) {
        VBox view = new VBox();
        Label usernameLabel = new Label("Enter your username"); 
        TextField username = new TextField();
        Label oldPasswordLabel = new Label("Enter your current password");
        TextField oldPassword = new TextField();
        Label newPasswordLabel = new Label("Enter a new password");
        TextField newPassword = new TextField();
        Button changePassword = new Button("Change password");
        changePassword.setOnAction((e) -> {
            String user = username.getText();
            String oldPlass = oldPassword.getText();
            String newPass = newPassword.getText();
            if (PlayerRegistration.changePassword(user, oldPlass, newPass)) {
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
                stage.setScene(changePasswordMenu(stage));
                stage.show();
            }
        });

        Label labelb = new Label("");
        Button back = new Button("Back");
        back.setOnAction((e) -> {
            Scene scene = new MainMenu().createMainMenu(stage);
            stage.setScene(scene);
            stage.show();
        });

        view.getChildren().addAll(usernameLabel, username, oldPasswordLabel, oldPassword, newPasswordLabel, newPassword, changePassword,labelb, back);
        Scene scene = new Scene(view);
        return scene;
    }
}
