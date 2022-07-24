package MUD.View;

// import com.google.gson.annotations.JsonAdapter;

/**
 * This is responsible for creating the main menu
 * for the class and is able to switch to the game
 * 
 * @author Borna Eshraghi
 */

// import MUD.Model.Character.Player;
// import MUD.Model.DayNight.DayNightCycle;
// import MUD.Model.Map.Map;
// import MUD.Model.Save.JSONSaveAdapter;
// import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
// import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainMenu {
    protected PTUI ptui;

    /**
     * Creates the main menu which will be 
     * able to get to other menues
     * @param stage the stage that is used to display the scene
     * @return the scene that contains all the nodes to display
     * the main menu
     */
    public Scene createMainMenu(Stage stage) {
        VBox vBox = new VBox();
        Label title = new Label("Multi User Dungeon");
        title.setFont(new Font("Impact", 48));
        Button newGame = new Button("Pre Made Map Mode");
        newGame.setOnAction((e) -> {
            PreMadeMapMode preMadeMapMode = new PreMadeMapMode();
            stage.setScene(preMadeMapMode.createPreMadeMapModeMenu(stage));
            stage.show();
            // String name = GUI.username;
            // Map map = new Map(2);
            // map.populate();
            // Player player = new Player(name, "", 100, 10, 0, map);
            // player.setCurrentVertex(map.getStartRoom());
            // player.setCurrentTile(player.getCurrentVertex().getValue().getContents()[1][1]);
            // DayNightCycle DNC = new DayNightCycle(player);
            // DNC.startCycle();
            // ptui = new PTUI(player, DNC);
            // Scene scene = game.viewRoom(ptui);
            // stage.setScene(scene);
            // Game.stage = stage;
            // stage.show();
        });

        Button endless = new Button("Endless Mode");
        endless.setOnAction((e) -> {
            EndlessMapMode endlessmapmode = new EndlessMapMode();
            stage.setScene(endlessmapmode.createEndlessMenu(stage));
            stage.show();
        });

        Button loadGame = new Button("Load Game");
        loadGame.setOnAction((e) -> {
            LoadGameMenu loadGameMenu = new LoadGameMenu();
            stage.setScene(loadGameMenu.createLoadMenu(stage));
            stage.show();
        });

        Button changePassword = new Button("Change Password");
        changePassword.setOnAction((e) -> {
            ChangePassword changePasswordMenu = new ChangePassword();
            stage.setScene(changePasswordMenu.changePasswordMenu(stage));
            stage.show();
        });

        Button statistics = new Button("Statistics");
        statistics.setOnAction((e) -> {
            Statistics statisticsMenu = new Statistics();
            stage.setScene(statisticsMenu.statisticsMenu(stage));
            stage.show();
        });

        Button quit = new Button("Quit");
        quit.setOnAction((e) -> {
            stage.close();
        });
        vBox.getChildren().addAll(title, newGame, endless, loadGame, statistics, changePassword, quit);
        Scene scene = new Scene(vBox);
        return scene;        
    }    
}
