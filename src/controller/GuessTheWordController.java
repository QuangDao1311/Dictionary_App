package controller;

import java.net.URL;
import java.util.ResourceBundle;

import game.GuessTheWord;
import game.question.WordGuess;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class GuessTheWordController extends GameController {
    @FXML
    TextArea worddisplay;
    @FXML
    TextField answer;
    @FXML
    Label wordcorrect, wordwrong;
    @FXML
    ImageView wordcorrect1, wordwrong1;
    @FXML
    Button nextwordbtn;

    private GuessTheWord game;
    private WordGuess wordGuess;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {        //hàm này được gọi khi giao diện này được khởi tạo
        game = new GuessTheWord();
        game.insertFromFile();
        wordcorrect.setVisible(false);
        wordcorrect1.setVisible(false);
        wordwrong.setVisible(false);
        wordwrong1.setVisible(false);
        displayWord();                                          //hiển thị từ
        setupWordDisplay();
        setupAnswerField();
        nextwordbtn.setOnAction(event -> displayWord());        //nút next word được bấm thì hiển thị từ tiếp theo
    }

    @FXML
    private void displayWord() {
        if (game != null) {
            wordGuess = game.randWord();
            worddisplay.setText(wordGuess.wordGuess());         //hiển thị từ ra
        }
    }

    @FXML
    private void setupWordDisplay() {
        worddisplay.setWrapText(true);                    //tự động xuống dòng phù hợp kích thước hiển thị
        worddisplay.setEditable(false);                   //cấm người dùng chỉnh sửa nội dung hiển thị
    }

    @FXML
    private void setupAnswerField() {
        answer.setEditable(true);
        answer.setOnAction(this::handleAnswer);                 //khi người dùng ấn enter hoặc ấn nút trả lời thì thực hiển hàm handleAnswer
    }

    private void handleAnswer(ActionEvent event) {
        if (wordGuess != null && game != null) {
            String userInput = answer.getText().trim().toLowerCase();
            char userChar = userInput.charAt(0);                    //chỉ lấy 1 kí tự đầu, vì điền từ chỉ dùng 1 kí tự

            if (wordGuess.checkAnswers(userChar)) {                       //checkAnswers trong WordGuess.java
                wordcorrect.setVisible(true);
                wordcorrect1.setVisible(true);
                wordwrong.setVisible(false);
                wordwrong1.setVisible(false);
                
                //getWordTarger và getWordExplain ở file Word.java
                worddisplay.setText("Từ đúng: " + wordGuess.getWordTarget() + "\nNghĩa Tiếng Việt: " + wordGuess.getWordExplain());
            } else {
                wordcorrect.setVisible(false);
                wordcorrect1.setVisible(false);
                wordwrong.setVisible(true);
                wordwrong1.setVisible(true);
            }
        }
    }
}
