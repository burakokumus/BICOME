package bicome.controllers;

import bicome.logic.environment.Environment;
import bicome.logic.feature.Feature;
import bicome.logic.feature.FeatureList;
import bicome.logic.manager.GameManager;
import bicome.logic.world.Organism;
import bicome.logic.world.Tile;
import bicome.logic.world.World;
import bicome.utils.PageNavigator;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSlider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Please add javadoc Cerag
 * Please only send compilable source next time because I have to test our app
 * I have commented all lines that prevents complitation
 */
public class GameController implements Initializable{


    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView imageOfAnimal;
    @FXML
    private ImageView backgroundPicture;
    @FXML
    private Label animalName;
    @FXML
    private Label environmentName;
    @FXML
    private Label timeData;
    @FXML
    private Label survivalRate;
    @FXML
    private JFXButton homeButton;
    @FXML
    private JFXButton pauseButton;
    @FXML
    private JFXButton speedButton;
    @FXML
    private Label environmentConditionsLabel;
    @FXML
    private JFXListView animalList;
    @FXML
    private Label speedLabel;
    @FXML
    private JFXSlider speedSlider;
    @FXML
    private GridPane grid;

    public ObservableList<Feature> featuresList;

    private GameManager gameManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) { //This is the function that will called during creation of the page
        featuresList = FXCollections.observableArrayList();
        animalList.setItems(featuresList);
        // 25 -> 1x
        updateTime(0);

        speedSlider.valueProperty().addListener( e -> {
           try {
            int value = (int) Math.floor((speedSlider.getValue() - 1) / 25) + 1;
            speedLabel.setText("Speed: " + value + "x");
            gameManager.setDurationOfTurns(GameManager.INITIAL_DURATION_OF_TURNS / value);
           }
           catch ( ArithmeticException ex )
           {
              speedSlider.setValue( 1 );
              gameManager.setDurationOfTurns( GameManager.INITIAL_DURATION_OF_TURNS );
           }
        });
        speedSlider.setValue(25);
    }

    public static class MyNode extends Rectangle {
        private static final int SIZE = 15;
        private final int x, y;
        private final Tile tile;

        public MyNode( Tile tile, int x, int y) {
            super( SIZE, SIZE);
//            if(!tile.getColor().toString().contains("0x00000000") )
//                System.out.println(tile.getColor().toString());
            setFill(tile.getColor());
            this.tile = tile;
            this.x = x;
            this.y = y;

            /*try {
                //Set the background color of the button
                setStyle("-fx-background-color: #" + tile.getColor().toString().substring(2, 8));
            }
            catch (NullPointerException e) {
                System.out.println("color is null");
                setStyle("-fx-background-color: #ffffff");
            }*/
        }

        public int getRow()
        {
            return x;
        }

        public int getCol()
        {
            return y;
        }

        public Tile getTile() {
            return tile;
        }
    }
    
    @FXML
    private void goHome(ActionEvent event) {
        Scene currentScene = ((Node) event.getSource()).getScene();
        Stage currentStage = (Stage) currentScene.getWindow();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Resources/Views/StartStage.fxml"));
            currentStage.setScene(new Scene(root, currentScene.getWidth(), currentScene.getHeight()));
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't load startStage");
        }

    }

    @FXML
    private void pauseAndPlayGame(ActionEvent event){
        Button button = (Button) event.getSource();
        ImageView imageView = (ImageView) button.getGraphic();
        if(button.getText().equals("playing")) {
            gameManager.pause();
            button.setText("paused");
            imageView.setImage(new Image("/Resources/Images/playWhite.png"));
        }
        else {
            gameManager.start();
            button.setText("playing");
            imageView.setImage(new Image("/Resources/Images/pauseWhite.png"));
        }
    }

    private void setAnimalList(Tile tile){
        Optional<Organism> organism = Optional.ofNullable(tile.getOrganism());

        organism.ifPresent( o -> {
            animalList.getItems().addAll(o.getFeatures());
        });
    }

    @FXML
    protected void onGridClicked(MouseEvent event)
    {
        for(Node node : grid.getChildren()) {
            if(node instanceof MyNode) {
                if(node.getBoundsInParent().contains(event.getSceneX(), event.getSceneY())) {
                    if(node == null)
                        return;
                    MyNode currentNode = (MyNode) node;
                    animalList.getItems().clear();
                    animalList.getItems().addAll(currentNode.getTile().getOrganism().getFeatures());
                    //Set the name of the animal and the image
                }
            }
        }
    }

    @FXML
    private void updateTime( long time) {
        timeData.setText( time + " years" );
    }

    @FXML
    private void updateSurvivalRate( int rateOfSurvival ) {
        survivalRate.setText( rateOfSurvival + "%" );
    }

    @FXML
    private void setAnimalPic( Image image) {
        imageOfAnimal.setImage(image);
    }

    public void updateGameStage( long time)
    {
        updateTime(time);
        //updateSurvivalRate(rateOfSurvival);

        World world = gameManager.getWorld();
        Tile[][] tiles = world.getGrid();

        /*for(Tile[] arr : tiles) {
            for(Tile tile : arr) {
                System.out.print(tile.getOrganism() != null ? 1 : 0);
            }
            System.out.println();
        } */ //Test for tiles

        grid.getChildren().retainAll(grid.getChildren().get(0)); //retainAll is for the grid lines
        for(int i = 0; i < 30; ++i) {
            for(int j = 0; j  < 30; ++j) {
                MyNode node = new MyNode(tiles[i][j], i, j);
                grid.add(node , j, i);
            }
        }
    }

    public void init()
    {
        environmentConditionsLabel.setText(gameManager.getWorld().getEnvironment().getConditionsForGUI());
        //Initialize the game grid
        Tile[][] tiles = gameManager.getWorld().getGrid();
        for(int i = 0; i < 30; ++i) {
            for(int j = 0; j  < 30; ++j) {
                grid.add(new MyNode(tiles[i][j], i, j), j, i);
            }
        }

        gameManager.start();
    }

    public void setManager(Environment env, FeatureList list)
    {
        World world = new World(list, env);
        gameManager = new GameManager(world, this);
    }

    public void finishGame()
    {
        Scene currentScene = anchorPane.getScene();
        Stage currentStage = (Stage) currentScene.getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/Views/ReflectionStage.fxml"));
            ReflectionController controller = loader.getController();
            AnchorPane root = loader.load();
            controller.setGameManager(gameManager);
            controller.init();
            currentStage.setScene(new Scene(root, currentScene.getWidth(), currentScene.getHeight()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
