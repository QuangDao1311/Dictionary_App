package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import word.Word;

public class SearcherController extends DictionaryController {
    private ObservableList<String> list = FXCollections.observableArrayList();      //lưu trữ danh sách kết quả tìm kiếm

    @FXML
    private TextField searchTerm;

    @FXML
    private Button cancelBtn, saveBtn;

    @FXML
    private Label englishWord, headerList, notAvailableAlert;

    @FXML
    private TextArea explanation;

    @FXML
    private ListView<String> listResults;

    @FXML
    private Pane headerOfExplanation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {        //hàm này chạy khi giao diện này được khởi tạo
        searchTerm.setOnKeyTyped(new EventHandler<KeyEvent>() {             //lưu những kí tự được nhập vào biến searchTerm
            @Override
            public void handle(KeyEvent keyEvent) {
                if (searchTerm.getText().isEmpty()) {                       //khi ô tìm kiếm trống
                    cancelBtn.setVisible(false);
                    setListDefault(0);
                } else {
                    cancelBtn.setVisible(true);
                    handleOnKeyTyped();
                }
            }
        });
        cancelBtn.setOnAction(new EventHandler<ActionEvent>() {     //khi ấn nút cancle ở ô tìm kiếm thì clear thanh tìm kiếm
            @Override
            public void handle(ActionEvent event) {
                searchTerm.clear();
                cancelBtn.setVisible(false);
                setListDefault(0);
            }
        });
        listResults.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                handleMouseClickAWord(event);
            }
        });

        explanation.setEditable(false);
        saveBtn.setVisible(false);
        cancelBtn.setVisible(false);
        notAvailableAlert.setVisible(false);
    }
    

    @FXML
    private void handleOnKeyTyped() {                       //hàm này chạy khi người dùng nhập từ vào ô tìm kiếm
        list = dm.search(searchTerm.getText().trim());      //tìm trong dm (dictionary management) những từ giống với searchTerm

        if (list.isEmpty()) {
            notAvailableAlert.setVisible(true);       //nếu list trống thì thông báo không khả dụng
        } else {
            notAvailableAlert.setVisible(false);
            headerList.setText("Kết quả");
            listResults.setItems(list);                     //set listResults = list
        }
    }

    @FXML
    private void handleMouseClickAWord(MouseEvent arg0) {       //hàm này chạy khi người dùng nhấp chuột vào một từ trong danh sách tìm kiếm
        String selectedWord = listResults.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            englishWord.setText(dictionary.get(selectedWord).getWordTarget());
            explanation.setText(dictionary.get(selectedWord).getWordExplain());
            headerOfExplanation.setVisible(true);
            explanation.setVisible(true);
            explanation.setEditable(false);
            saveBtn.setVisible(false);
        }
    }

    @FXML
    private void handleClickEditBtn() {         //khi người dùng nhấn nút edit
        explanation.setEditable(true);
        saveBtn.setVisible(true);
    }

    @FXML
    private void handleClickSoundBtn() {        //khi người dùng ấn nút voice
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        Voice voice = VoiceManager.getInstance().getVoice("kevin");
        String selectedWord = listResults.getSelectionModel().getSelectedItem();
        if (voice != null) {
            voice.allocate();
            voice.speak(dictionary.get(selectedWord).getWordTarget());
        } else throw new IllegalStateException("Cannot find voice: kevin");
    }

    @FXML
    private void handleClickSaveBtn() {         //khi người dùng ấn nút save
        Word newWord = new Word(englishWord.getText(), explanation.getText());
        dm.dictionaryUpdate(newWord);

        saveBtn.setVisible(false);
        explanation.setEditable(false);
    }

    @FXML
    private void handleClickDeleteBtn() {       //khi người dùng ấn nút delete
        String selectedWord = listResults.getSelectionModel().getSelectedItem();
        dm.dictionaryDelete(selectedWord);
        refreshAfterDeleting();
    }

    @FXML
    private void refreshAfterDeleting() {       //làm mới sau khi xóa từ
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(englishWord.getText())) {
                list.remove(i);
                break;
            }
        }
        listResults.setItems(list);
        headerOfExplanation.setVisible(false);
        explanation.setVisible(false);
    }

    private void setListDefault(int index) {

    }
}
