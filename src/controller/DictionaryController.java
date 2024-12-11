package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dictionary.Dictionary;
import dictionary.DictionaryManagement;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class DictionaryController implements Initializable {
    protected int point_Max = 0;
    protected static Dictionary dictionary;
    protected static DictionaryManagement dm = new DictionaryManagement();
    static {
        dm.insertFromFile("dictionaries.txt");
        dictionary = dm.getDictionary();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchWordBtn.setOnAction(new EventHandler<ActionEvent>() {     //ấn vào giao diện searchWord
            @Override
            public void handle(ActionEvent event) {
                showComponent("/views/SearcherGui.fxml");
                System.out.println("SEARCH_GUI");
            }
        });

        addWordBtn.setOnAction(new EventHandler<ActionEvent>() {        //ấn vào giao diện addWord
            @Override
            public void handle(ActionEvent event) {
                showComponent("/views/AdditionGui.fxml");
                System.out.println("ADDITION_GUI");
            }
        });

        translateBtn.setOnAction(new EventHandler<ActionEvent>() {      //ấn vào giao diện translate
            @Override
            public void handle(ActionEvent event) {
                showComponent("/views/TranslationGui.fxml");
                System.out.println("TRANSLATE_GUI");
            }
        });

        gameBtn.setOnAction(new EventHandler<ActionEvent>() {           //ấn vào giao diện game
            @Override
            public void handle(ActionEvent event) {
                showComponent("/views/GameGui.fxml");
                System.out.println("GAME_GUI");
            }
        });

        tooltip1.setShowDelay(Duration.seconds(0.5));
        tooltip2.setShowDelay(Duration.seconds(0.5));
        tooltip3.setShowDelay(Duration.seconds(0.5));
        tooltip4.setShowDelay(Duration.seconds(0.5));

        closeBtn.setOnMouseClicked(e -> {
            System.exit(0);
        });
    }

    private void setNode(Node node) {
        container.getChildren().clear();
        container.getChildren().add(node);
    }

    @FXML
    private void showComponent(String path) {
        try {
            AnchorPane component = FXMLLoader.load(getClass().getResource(path));
            setNode(component);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Tooltip tooltip1, tooltip2, tooltip3, tooltip4;

    @FXML
    private Button addWordBtn, translateBtn, searchWordBtn, closeBtn, gameBtn;

    @FXML
    private AnchorPane container;
}
