package MUD.View;

import java.io.FileNotFoundException;

import MUD.Model.Character.Player;
import MUD.Model.DayNight.DayNightCycle;
import MUD.Model.Map.Map;
import MUD.Model.Map.PremadeMaps.DiffEnum;
import MUD.Model.Map.PremadeMaps.PremadeGenerator;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PreMadeMapMode {

    private void startGame(Stage stage, DiffEnum diffEnum) {
        PremadeGenerator pg = new PremadeGenerator(diffEnum);
        Map map = null;
        try {
            map = pg.generate();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
    
    public Scene createPreMadeMapModeMenu(Stage stage) {
        VBox view = new VBox();
        Label label = new Label("Choose a difficulty setting");
        Button easy = new Button("Easy");
        easy.setOnAction((e) -> {
            startGame(stage, DiffEnum.EASY);
        });
        Button medium = new Button("Medium");
        medium.setOnAction((e) -> {
            startGame(stage, DiffEnum.NORMAL);
        });
        Button hard = new Button("Hard");
        hard.setOnAction((e) -> {
            startGame(stage, DiffEnum.HARD);
        });
        
        Label labelb = new Label("");

        Button back = new Button("Back");
        back.setOnAction((e) -> {
            Scene scene = new MainMenu().createMainMenu(stage);
            stage.setScene(scene);
            stage.show();
        });

        view.getChildren().addAll(label, easy, medium, hard,labelb, back);
        Scene scene = new Scene(view);
        return scene;
    }
}
