package MUD.View;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import MUD.Model.Character.Player;
import MUD.Model.DayNight.DayNightCycle;
import MUD.Model.Map.Map;
import MUD.Model.Map.Room;
import MUD.Model.Save.CSVSaveAdapter;
import MUD.Model.Save.JSONSaveAdapter;
import MUD.Model.Save.XMLSaveAdapter;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EndlessMapMode {
    
    public JSONSaveAdapter jsonAdapter = new JSONSaveAdapter();
    public CSVSaveAdapter csvAdapter = new CSVSaveAdapter();
    public XMLSaveAdapter xmlAdapter = new XMLSaveAdapter();

    public void startgame(Stage stage){
        Room Startroom = new Room(9, 9, 0);
        Map map = new Map(Startroom);
        Player player = new Player(GUI.username, "The player", 100, 10, 0, map);
        player.setCurrentVertex(map.getStartRoom());
        player.setCurrentTile(player.getCurrentVertex().getValue().getContents()[1][1]);
        DayNightCycle dnCycle = new DayNightCycle(player);
        dnCycle.startCycle();
        PTUI ptui = new PTUI(player, dnCycle);
        Game game = new Game();
        Scene scene = game.viewRoom(ptui);
        stage.setScene(scene);
        Game.stage = stage;
        stage.show();
    }

    @SuppressWarnings("unused")
    private void startloadgame(Stage stage, String file) {
        String[] fileExt = file.split(".");
        switch(fileExt[1]){
            case "json":
                try {
                    Map loadedMap = jsonAdapter.importSaveMap(file);
                    Player player = new Player(GUI.username, "The player", 100, 10, 0, loadedMap);
                    player.setCurrentVertex(loadedMap.getStartRoom());
                    player.setCurrentTile(player.getCurrentVertex().getValue().getContents()[1][1]);
                    DayNightCycle dnCycle = new DayNightCycle(player);
                    dnCycle.startCycle();
                    PTUI ptui = new PTUI(player, dnCycle);
                    Game game = new Game();
                    Scene scene = game.viewRoom(ptui);
                    stage.setScene(scene);
                    Game.stage = stage;
                    stage.show();

                }catch(Exception e){e.printStackTrace();}
                break;
            case "xml":
            try {
                Map loadedMap = xmlAdapter.importSaveMap(file);
                Player player = new Player(GUI.username, "The player", 100, 10, 0, loadedMap);
                player.setCurrentVertex(loadedMap.getStartRoom());
                player.setCurrentTile(player.getCurrentVertex().getValue().getContents()[1][1]);
                DayNightCycle dnCycle = new DayNightCycle(player);
                dnCycle.startCycle();
                PTUI ptui = new PTUI(player, dnCycle);
                Game game = new Game();
                Scene scene = game.viewRoom(ptui);
                stage.setScene(scene);
                Game.stage = stage;
                stage.show();
                }catch(Exception e){e.printStackTrace();}

                break;
            case "csv":
            try{
                Map loadedMap = csvAdapter.importSaveMap(file);
                Player player = new Player(GUI.username, "The player", 100, 10, 0, loadedMap);
                player.setCurrentVertex(loadedMap.getStartRoom());
                player.setCurrentTile(player.getCurrentVertex().getValue().getContents()[1][1]);
                DayNightCycle dnCycle = new DayNightCycle(player);
                dnCycle.startCycle();
                PTUI ptui = new PTUI(player, dnCycle);
                Game game = new Game();
                Scene scene = game.viewRoom(ptui);
                stage.setScene(scene);
                Game.stage = stage;
                stage.show();
            }catch(Exception e){e.printStackTrace();}
        default:
                break;
        }
    }
    

    public Scene createEndlessMenu(Stage stage){
        VBox view = new VBox();
        Label Endless = new Label("Endless Map Mode:");
        Button start = new Button("Start new game");
        start.setOnAction((e) -> {
            startgame(stage);
        });
        Label labelBlank = new Label("");
        Label label = new Label("Available Maps:");
        view.getChildren().addAll(Endless, start, labelBlank, label);

        try {
            Files.list(Paths.get("MUD\\Saves\\MapSaves")).forEach( e -> {
                Button butt = new Button(e.getFileName().toString());
                butt.setOnAction((l) -> {
                    try {
                        startgame(stage);
                    }catch(Exception e2) {
                        e2.printStackTrace();
                    }
                });
                view.getChildren().add(butt);
            });
            


        }catch(IOException e) {e.printStackTrace();}
        
        Label labelBlank2 = new Label("");
        view.getChildren().add(labelBlank2);
        Button back = new Button("Back");
        back.setOnAction((e) -> {
            Scene scene = new MainMenu().createMainMenu(stage);
            stage.setScene(scene);
            stage.show();
        });
        view.getChildren().add(back);
        Scene scene = new Scene(view, 300, 300);
        return scene;

    }

}
