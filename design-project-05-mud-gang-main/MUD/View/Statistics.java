package MUD.View;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Statistics {

    public Scene statisticsMenu(Stage stage) {
        VBox vbox = new VBox();
        Label header = new Label("Statistics");
        Label stats = new Label("Numer of games played: " + "0");
        Label stats2 = new Label("Number of games lost: " + "0");
        Label stats3 = new Label("Number of monsters slain: " + "0");
        Label stats4 = new Label("Number of items picked up: " + "0");
        Label stats5 = new Label("Total gold collected: " + "0");

        Label blank = new Label("");
        Label blank2 = new Label("");
        Button back = new Button("Back");
        back.setOnAction((e) -> {
            Scene scene = new MainMenu().createMainMenu(stage);
            stage.setScene(scene);
            stage.show();
        });

        vbox.getChildren().addAll(header, stats, stats2, stats3, stats4, stats5, blank, blank2, back);

        Scene scene = new Scene(vbox, 300, 300);
        return scene;
    }

}
