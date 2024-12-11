package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import game.MultipleChoice;
import game.question.Question;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

public class MultipleChoiceGame extends GameController {
    @FXML
    TextArea contentQuestion;
    @FXML
    Button selectAbtn, selectBbtn, selectCbtn, selectDbtn, nextquestionbtn, replayBtn;
    @FXML
    Label correct, wrong, healthText, scoreText;
    @FXML
    ImageView correct1, wrong1;

    private MultipleChoice game;
    private Question currentQuestion;
    private List<String> currentAnswers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        game = new MultipleChoice();
        game.insertQuestionFromFile();
        displayQuestion();
        replayBtn.setDisable(true);
        selectAbtn.setOnAction(event -> handleAnswer(0));
        selectBbtn.setOnAction(event -> handleAnswer(1));
        selectCbtn.setOnAction(event -> handleAnswer(2));
        selectDbtn.setOnAction(event -> handleAnswer(3));
        nextquestionbtn.setOnAction(event -> displayQuestion());
        replayBtn.setOnAction(event -> handleReplay());
        contentQuestion.setWrapText(true);              //văn bản tự động xuống dòng phù hợp kích thước hiển thị
        contentQuestion.setEditable(false);             //cấm người dùng chỉnh sửa nội dung hiển thị
        correct.setVisible(false);
        correct1.setVisible(false);
        wrong.setVisible(false);
        wrong1.setVisible(false);

        healthText.setText("Health: " + game.getHealth());
        scoreText.setText("Score: " + game.getPoint());

    }

    //nếu nút replay được bấm thì thực hiện
    private void handleReplay() {
        nextquestionbtn.setDisable(false);
        replayBtn.setDisable(true);
        game.setPoint(0);
        game.setHealth(3);
        displayQuestion();
    }

    private void displayQuestion() {
        correct.setVisible(false);
        correct1.setVisible(false);
        wrong.setVisible(false);
        wrong1.setVisible(false);
        selectAbtn.setDisable(false);
        selectBbtn.setDisable(false);
        selectCbtn.setDisable(false);
        selectDbtn.setDisable(false);
        if (game != null) {
            currentQuestion = game.randomQuestion();
            contentQuestion.setText(currentQuestion.getQuestion());                         //set câu hỏi
            currentAnswers = game.randomAnswers(currentQuestion.getCorrectAnswers());       //set 4 đáp án

            // Cập nhật các đáp án cho Button tương ứng ở đây
            selectAbtn.setText(currentAnswers.get(0));
            selectBbtn.setText(currentAnswers.get(1));
            selectCbtn.setText(currentAnswers.get(2));
            selectDbtn.setText(currentAnswers.get(3));
        }
    }

    private void handleAnswer(int selectedAnswerIndex) {
        if (game != null && currentQuestion != null && currentAnswers != null) {
            String selectedAnswer = currentAnswers.get(selectedAnswerIndex);
            selectAbtn.setDisable(true);
            selectBbtn.setDisable(true);
            selectCbtn.setDisable(true);
            selectDbtn.setDisable(true);

            //nếu đáp án được chọn là đúng
            if (currentQuestion.checkAnswers(selectedAnswer)) {
                correct.setVisible(true);
                correct1.setVisible(true);
                wrong.setVisible(false);
                wrong1.setVisible(false);

                game.increasePoint();                               //tăng điểm
                healthText.setText("Health: " + game.getHealth());
                scoreText.setText("Score: " + game.getPoint());
            
            } else {
                correct.setVisible(false);
                correct1.setVisible(false);
                wrong.setVisible(true);
                wrong1.setVisible(true);

                game.decreaseHealth();                              //trừ máu
                healthText.setText("Health: " + game.getHealth());
                scoreText.setText("Score: " + game.getPoint());
            }

            //nếu thua
            if (game.getHealth() == 0) {
                replayBtn.setDisable(false);
                nextquestionbtn.setDisable(true);
                contentQuestion.setText("Your score: " + game.getPoint() + "\nYou lose!");
            }

            //nếu thắng
            if (game.getPoint() == 5) {
                replayBtn.setDisable(false);
                nextquestionbtn.setDisable(true);
                contentQuestion.setText("Your score: " + game.getPoint() + "\nYou win!");
            }
        }
    }
}
