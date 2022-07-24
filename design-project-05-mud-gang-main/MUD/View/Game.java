package MUD.View;
import java.util.Set;
import MUD.Controller.Interpreter;
import MUD.Model.Character.Bedtime;
import MUD.Model.Character.Behavior;
import MUD.Model.Character.NPC;
/**
 * This is responsible for creating the view of the game and will
 * be responsible for choosing what action the player will take 
 * at a given time
 * 
 * @author Borna Eshraghi
 */
import MUD.Model.Character.Player;
import MUD.Model.DayNight.Day;
import MUD.Model.DayNight.DayNightCycle;
import MUD.Model.DayNight.Time;
import MUD.Model.Map.Map;
import MUD.Model.Map.Room;
import MUD.Model.Map.Tile;
import MUD.Model.Save.CSVSaveAdapter;
import MUD.Model.Save.JSONSaveAdapter;
import MUD.Model.Save.XMLSaveAdapter;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Game {
    static Stage stage; 
    private static final Image DAYTILE = new Image("file:MUD/View/images/dayTile.jpg");
    private static final Image NIGHTTILE = new Image("file:MUD/View/images/nightTile.png");
    private static final Image PLAYER = new Image("file:MUD/View/images/player.png");
    private static final Image CHEST = new Image("file:MUD/View/images/chest.png");
    private static final Image TRAP = new Image("file:MUD/View/images/trap.png");
    private static final Image OBSTACLE = new Image("file:MUD/View/images/obstacle.png");
    private static final Image EMPTY = new Image("file:MUD/View/images/empty.png");
    private static final Image DOOR = new Image("file:MUD/View/images/door.png");
    private static final Image VICTORY = new Image("file:MUD/View/images/victory.png");
    private static final Image DIED = new Image("file:MUD/View/images/death.png");
    private static final Image MERCHANT = new Image("file:MUD/View/images/merchant.png");
    private static final Image SHRINE = new Image("file:MUD/View/images/shrine.png");
    private static final Image GOAL = new Image("file:MUD/View/images/goal.png");


    /**
     * This is a label factory method for making
     * stats labels
     * @param text
     * @return
     */
    public Label makeStatsLabel(String text) {
        Label label = new Label(text);
        label.setTextFill(Color.WHITE);
        return label;
    }
    /**
     * This method creates the labels for the stats
     * for the UI
     * @param player uses the player to get the 
     * current stats
     * @return an Hbox with the labels
     */
    public VBox createStatsView(Player player) {
        double health = player.getCurrentHealth();
        double attack = player.getAttack();
        double defense = player.getDefense();
        Label healthLabel = makeStatsLabel("HP: " + health + "     ");
        Label attackLabel = makeStatsLabel("ATK: " +  attack + "     ");
        Label defenseLabel = makeStatsLabel("DEF: " + defense);
        VBox stats = new VBox();
        stats.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        stats.getChildren().addAll(healthLabel, attackLabel, defenseLabel);
        stats.setMaxHeight(20);
        stats.setMaxWidth(75);
        return stats;
    }


    private NPC convertTreeMapToNPC(com.google.gson.internal.LinkedTreeMap<String, String> treeMap) {
        String name = treeMap.get("name");
        String description = treeMap.get("description");
        Object maxHealthString = treeMap.get("maxHealth");
        Double maxHealth = (Double)maxHealthString;
        Object attackString = treeMap.get("attack");
        Double attack = (Double)attackString;
        Object defensObject = treeMap.get("defense");
        Double defense = (Double)defensObject;
        String behaviorString = treeMap.get("behavior");
        Behavior behavior = Behavior.Docile;
        if (behaviorString.equals("Hostile")) {
            behavior = Behavior.Hostile;
        }
        String bedtimeString = treeMap.get("bedtime");
        Bedtime bedtime = Bedtime.Nocturnal;
        if (bedtimeString.equals("Diurnal")) {
            bedtime = Bedtime.Diurnal;
        }
        String sprite = treeMap.get("sprite");
        NPC npc = new NPC(name, description, maxHealth, attack, defense, behavior, bedtime, sprite);
        Object currentHealthObject = treeMap.get("currentHealth");
        Double currentHealth = (Double)currentHealthObject;
        Object isDefeatedObject = treeMap.get("defeated");
        boolean isDefeated = (boolean)isDefeatedObject;
        npc.setDefeated(isDefeated);
        npc.setCurrentHealth(currentHealth);
        return npc;
    }

    /**
     * This method sets up the board for the game
     * @param game this is an instance of the game so that the GUI can access it
     * @return
     */
    @SuppressWarnings("unchecked")
    public Scene viewRoom(PTUI game) {
        StackPane gameView = new StackPane();
        Player player = game.getPlayer();
        VBox stats = createStatsView(player);
        Tile playerTile = player.getCurrentTile();
        Room currentRoom = player.getCurrentVertex().getValue();
        Tile[][] tiles = currentRoom.getContents();
        GridPane roomView = new GridPane();
        Label label;
        Image activeTile;
        DayNightCycle DNC = game.getDnCycle();
        Time currentTime = DNC.getTime();
        if (currentTime instanceof Day) {
            activeTile = DAYTILE;
        }
        else {
            activeTile = NIGHTTILE;
        }
        for (int currentRowIndex = 0; currentRowIndex < tiles.length; currentRowIndex += 1) {
            Tile[] currentRow = tiles[currentRowIndex];
            for (int currentTileIndex = 0; currentTileIndex < currentRow.length; currentTileIndex += 1) {
                Tile tile = currentRow[currentTileIndex];
                ImageView tileSprite = new ImageView(activeTile);
                tileSprite.setFitHeight(78);
                tileSprite.setFitWidth(78);
                label = new Label("", tileSprite);
                Label sprite;
                if (playerTile == tile) {  
                    ImageView playerSprite = new ImageView(PLAYER);           
                    playerSprite.setFitHeight(78);
                    playerSprite.setFitWidth(78);
                    sprite = new Label("", playerSprite);
                }
                else {
                    String contents = tile.getContents();
                    switch (contents) {
                        case "C":
                            ImageView chestSprite = new ImageView(CHEST);
                            chestSprite.setFitHeight(72);
                            chestSprite.setFitWidth(72);
                            sprite = new Label("", chestSprite);
                            break;
                        case "E":
                            NPC enemy;
                            if (tile.getOccupant() instanceof NPC) {
                                enemy = (NPC)tile.getOccupant();
                            }                          
                            else {
                                enemy = convertTreeMapToNPC((com.google.gson.internal.LinkedTreeMap<String, String>)tile.getOccupant());
                            }
                            System.out.println(enemy.getName());
                            Image image = new Image(enemy.getSprite());
                            ImageView enemySprite = new ImageView(image);
                            enemySprite.setFitHeight(72);
                            enemySprite.setFitWidth(72);
                            sprite = new Label("", enemySprite);
                            break;
                        case "T":
                            ImageView trapSprite = new ImageView(TRAP);
                            trapSprite.setFitHeight(70);
                            trapSprite.setFitWidth(70);
                            sprite = new Label("", trapSprite);
                            break;
                        case "O":
                            ImageView obstacleSprite = new ImageView(OBSTACLE);
                            obstacleSprite.setFitHeight(72);
                            obstacleSprite.setFitWidth(72);
                            sprite = new Label("", obstacleSprite);
                            break;
                        case "D":
                            ImageView doorSprite = new ImageView(DOOR);
                            doorSprite.setFitHeight(75);
                            doorSprite.setFitWidth(67);
                            sprite = new Label("", doorSprite);
                            break;
                        case "M":
                            ImageView merchantSprite = new ImageView(MERCHANT);
                            merchantSprite.setFitHeight(70);
                            merchantSprite.setFitWidth(70);
                            sprite = new Label("", merchantSprite);
                            break;
                        case "S":
                            ImageView shrineSprite = new ImageView(SHRINE);
                            shrineSprite.setFitHeight(70);
                            shrineSprite.setFitWidth(70);
                            sprite = new Label("", shrineSprite);
                            break;
                        case "G":
                            ImageView goalSprite = new ImageView(GOAL);
                            goalSprite.setFitHeight(62);
                            goalSprite.setFitWidth(72);
                            sprite = new Label("", goalSprite);
                            break;
                        default:
                            ImageView imageView = new ImageView(EMPTY);
                            sprite = new Label("", imageView);
                            break;
                    }
                }
                roomView.add(label, currentTileIndex, currentRowIndex);
                roomView.add(sprite, currentTileIndex, currentRowIndex);
            }
        }
        
        roomView.setBackground(new Background(new BackgroundFill(Color.BLACK , CornerRadii.EMPTY, Insets.EMPTY)));
        HBox actionMenuView = viewActions(game);
        gameView.getChildren().addAll(roomView,stats, actionMenuView);
        StackPane.setAlignment(stats, Pos.TOP_RIGHT);
        StackPane.setAlignment(actionMenuView, Pos.BOTTOM_LEFT);
        Scene scene = new Scene(gameView);
        
        return scene;
    }

    /**
     * This is a factory method to make buttons for the general menu
     * @param name this is the string that will be shown the button
     * @return a menu button
     */
    public Button makeMenuButton(String name) {
        Button button = new Button(name);
        return button;
    }

    /**
     * Notifies the player how much damage their attack did to the 
     * enemy
     * @param player the player character in the game
     * @param enemies the enemies that are adjacent to the player
     * @param userCommand the command to attack an enemy
     */
    public void notifyPlayer(Player player, java.util.List<NPC> enemies ,String userCommand) {
            String[] command = userCommand.split("Attack ");
            String name = command[1];
            NPC attackedEnemy = null;;
            for (NPC enemy : enemies) {
                if (enemy.getName().equals(name)) {
                    attackedEnemy = enemy;
                    break;
                }
            }
            double damageGiven = player.getAttack() - attackedEnemy.getDefense();
            if (damageGiven <= 0) {
                damageGiven = 1;
            }
            String message = player.getName() + " attacked " + name + " for " + damageGiven + " damage";
            Label label = new Label(message);
            Scene scene = stage.getScene();
            StackPane view = (StackPane)scene.getRoot();
            view.getChildren().add(new StackPane(label));
            StackPane stackPane = new StackPane();
            stackPane.getChildren().add(new StackPane(label));
            stage.setScene(new Scene(stackPane, 250, 100));
            stage.show();
            try {
                Thread.sleep(750);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
    }

    /**
     * This makes a button that are for performing differnet
     * actions in the game
     * @param game the game that is being played
     * @param userCommand this is the command for what action the 
     * user can do
     * @return this returns a button that when pressed will perform an
     * action in the game
     */
    public Button makeActionButton(PTUI game, String userCommand) {
        java.util.List<NPC> enemies = game.getAttackingNPCs();
        Button button = makeMenuButton(userCommand);
        Player player = game.getPlayer();
        button.setOnAction((e) -> {
            game.makeMove(userCommand);
            if (userCommand.charAt(0) == 'A') {
                notifyPlayer(player, enemies, userCommand);
            }
            if (PTUI.getHasWon()) {
                StackPane view = new StackPane();
                view.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
                ImageView victory = new ImageView(VICTORY);
                view.getChildren().add(victory);
                stage.setScene(new Scene(view));
                stage.show();
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                MainMenu mainMenu = new MainMenu();
                stage.setScene(mainMenu.createMainMenu(stage));
                stage.show();
                return;
            }
            for (NPC enemy : game.getAttackingNPCs()) {
                enemy.attack(player);
                if (player.isDefeated()) {
                    StackPane stackPane = new StackPane();
                    ImageView died = new ImageView(DIED);
                    Label label = new Label("", died);
                    stackPane.getChildren().add(label);
                    stage.setScene(new Scene(stackPane));
                    stage.show();
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    MainMenu mainMenu = new MainMenu();
                    stage.setScene(mainMenu.createMainMenu(stage));
                    stage.show();
                    break;
                }
            }
            if (!player.isDefeated()) {
                DayNightCycle DNC = game.getDnCycle();
                DNC.changeTime();
                Scene scene = viewRoom(game);
                stage.setScene(scene);
                stage.show();
            }
        });
        return button;
    }

    /**
     * Parses through a set of strings to find move actions
     * @param game this is the game that is currently running
     * @param vBox this the node that holds the different 
     * buttons for eqch move
     * @param stringMoves a set of moves that can be used my the 
     * player
     */
    public void makeMoveList(PTUI game, HBox hBox, Set<String> stringMoves, char command) {
        for (String move : stringMoves) {
            if (move.charAt(0) == command) {
                Button button = makeActionButton(game, move);
                hBox.getChildren().add(button);
            }
        }
    }

    /**
     * This is a helper method that creates the event that will happen 
     * if a button is pressed
     * @param button the button that the event will be bound to
     * @param game the current game running
     * @param actionMenu the node that holds the button
     * @param stringMoves the set of possible moves
     * @param userCommand the command that is used to parse
     * through the moves
     */
    private void action(Button button, PTUI game, HBox actionMenu, Set<String> stringMoves, char userCommand) {

        Button back = makeMenuButton("Back");
            back.setOnAction((e) -> {
                Scene scene = viewRoom(game);
                stage.setScene(scene);
                stage.show();
            });
        button.setOnAction((e) -> {
            actionMenu.getChildren().clear();
            makeMoveList(game, actionMenu, stringMoves, userCommand);
            actionMenu.getChildren().add(back);
        });
    }

    private void quitMenu(PTUI game, HBox actionMenu) {
        Label label = new Label("Are you sure: ");
        label.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        label.setFont(new Font("Arial", 20));
        Button yes = makeMenuButton("Yes");
        yes.setOnAction((e) -> {
            MainMenu mainMenu = new MainMenu();
            stage.setScene(mainMenu.createMainMenu(stage));
            stage.show();

        });
        Button no = makeMenuButton("No");
        no.setOnAction((e) -> {
            Scene scene = viewRoom(game);
            stage.setScene(scene);
            stage.show();
        });
        actionMenu.getChildren().addAll(label, yes, no);
    }

    /**
     * This creates a menu that can be used
     * to save a game
     * @param game the game currently running
     * @param actionMenu the node that will hold the
     * nodes for saving the game
     */
    private void saveMenu(PTUI game, HBox actionMenu) {
        Label label = new Label("What do you want to name this save file: ");
        label.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        label.setFont(new Font("Arial", 20));
        TextField saveName = new TextField();
        Button JsonSave = makeMenuButton("JSON Save");
        JsonSave.setOnAction((e) -> {
        JSONSaveAdapter jsonSave = new JSONSaveAdapter();
        Player player = game.getPlayer();
        Map map = player.getMap();
        jsonSave.exportSavePlayer(new Player(player), saveName.getText());
        jsonSave.exportSaveMap(map, saveName.getText());
        Scene scene = viewRoom(game);
        stage.setScene(scene);
        stage.show();
        });

        Button XMLSave = makeMenuButton("XML Save");
        XMLSave.setOnAction((e) -> {
        XMLSaveAdapter xmlSave = new XMLSaveAdapter();
        Player player = game.getPlayer();
        Map map = player.getMap();
        try {
            xmlSave.exportSavePlayer(new Player(player), saveName.getText());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        xmlSave.exportSaveMap(map, saveName.getText());
        Scene scene = viewRoom(game);
        stage.setScene(scene);
        stage.show();
        });

        Button CSVSave = makeMenuButton("CSV Save");
        CSVSave.setOnAction((e) -> {
        CSVSaveAdapter csvSave = new CSVSaveAdapter();
        Player player = game.getPlayer();
        Map map = player.getMap();
        csvSave.exportSavePlayer(new Player(player), saveName.getText());
        csvSave.exportSaveMap(map, saveName.getText());
        Scene scene = viewRoom(game);
        stage.setScene(scene);
        stage.show();
        });
        actionMenu.getChildren().addAll(label, saveName, JsonSave, XMLSave, CSVSave);
    }

    /**
     * This method creates ViewBox for what actions
     * the player character can do in the game
     * @param game
     * @return
     */
    public HBox viewActions(PTUI game) {
        game.findValidMoves();
        java.util.Map<String, Interpreter> validMoves = game.getValidMoves();
        java.util.Set<String> stringMoves = validMoves.keySet();
        HBox actionMenu = new HBox();
        actionMenu.setMaxHeight(20);
        Button move = makeMenuButton("Move");
        Button attack = makeMenuButton("Attack");
        Button inventory = makeMenuButton("Inventory");
        Button openChest = makeMenuButton("Open chest");
        Button disarmTrap = makeMenuButton("Disarm Trap");
        Button save = makeMenuButton("Save");
        save.setOnAction((e) -> {
            actionMenu.getChildren().clear();
            saveMenu(game, actionMenu);
        });
        Button quit = makeMenuButton("Quit");
        quit.setOnAction((e) -> {
            actionMenu.getChildren().clear();
            quitMenu(game, actionMenu);
        });

        action(move, game, actionMenu, stringMoves, 'M');
        action(attack, game, actionMenu, stringMoves, 'A');
        action(inventory, game, actionMenu, stringMoves, 'U');
        action(openChest, game, actionMenu, stringMoves, 'O');
        action(disarmTrap, game, actionMenu, stringMoves, 'D');

        actionMenu.getChildren().addAll(
            move, attack, inventory, openChest, disarmTrap, save, quit
        );
        return actionMenu;
    }
}
