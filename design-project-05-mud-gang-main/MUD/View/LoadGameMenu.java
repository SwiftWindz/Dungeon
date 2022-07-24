package MUD.View;

/**
 * Handles whether the user wants to load
 * or start a new game
 * 
 * @author Borna Eshraghi
 */

import MUD.Model.Character.Player;
import MUD.Model.DayNight.DayNightCycle;
import MUD.Model.Map.Map;
import MUD.Model.Save.CSVSaveAdapter;
import MUD.Model.Save.JSONSaveAdapter;
import MUD.Model.Save.XMLSaveAdapter;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoadGameMenu {

    private void loadGame(Stage stage, Player player, Map map) {
        // DayNightCycle DNC = new DayNightCycle(player);
        PTUI ptui = new PTUI(player, new DayNightCycle(player));
        Game game = new Game();
        Scene scene = game.viewRoom(ptui);
        stage.setScene(scene);
        Game.stage = stage;
        stage.show();
    }
    

    /**
     * Creates the menu where the user can choose to 
     * load a saved game
     * @param stage the stage that is going be used for 
     * the game
     * @return a scene with the differnt nodes
     */
    public Scene createLoadMenu(Stage stage) {
        VBox menu = new VBox();
        Label playerLabel = new Label("Enter the name of the player file you want to load:");
        TextField playerFileName = new TextField();
        Label mapLabel = new Label("Enter the name of the map file you want to load");
        TextField mapFileName = new TextField();
        
        Button loadJSON = new Button("Load JSON file");
        loadJSON.setOnAction((e) -> {
            String playerSaveName = playerFileName.getText();
            String mapSaveName = mapFileName.getText();
            JSONSaveAdapter adapter = new JSONSaveAdapter();
            Player player = adapter.importSavePlayer(playerSaveName);
            Map map = adapter.importSaveMap(mapSaveName);
            System.out.println(player);
            System.out.println(map);
            loadGame(stage, player, map);
        });

        Button loadXML = new Button("Load XML file");
        loadXML.setOnAction((e) -> {
            String playerSaveName = playerFileName.getText();
            String mapSaveName = mapFileName.getText();
            XMLSaveAdapter adapter = new XMLSaveAdapter();
            Player player;
            try {
                player = adapter.importSavePlayer(playerSaveName);
                Map map = adapter.importSaveMap(mapSaveName);
                System.out.println(player);
                System.out.println(map.getNumberOfRooms());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        Button loadCSV = new Button("Load CSV file");
        loadCSV.setOnAction((e) -> {
            String playerSaveName = playerFileName.getText();
            String mapSaveName = mapFileName.getText();
            CSVSaveAdapter adapter = new CSVSaveAdapter();
            Player player = adapter.importSavePlayer(playerSaveName);
            Map map = adapter.importSaveMap(mapSaveName);
            System.out.println(player);
            System.out.println(map);
            
        });

        Label blank = new Label("");

        Button back = new Button("Back");
        back.setOnAction((e) -> {
            MainMenu mainMenu = new MainMenu();
            stage.setScene(mainMenu.createMainMenu(stage));
            stage.show();
        });
        menu.getChildren().addAll(playerLabel, playerFileName, mapLabel, mapFileName, loadJSON, loadXML, loadCSV,blank, back);
        Scene scene = new Scene(menu);
        return scene;

    }
}
