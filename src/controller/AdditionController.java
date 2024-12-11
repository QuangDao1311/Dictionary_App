package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import word.Word;

public class AdditionController extends DictionaryController {
    @FXML
    private TextField wordTargetInput;          //người dùng nhập từ ở đây
    @FXML
    private TextArea explanationInput;          //người dùng nhập giải nghĩa ở đây
    @FXML
    private Button addBtn;
    @FXML
    private Label successAlert, failAlert;

    private void resetInput() {
        wordTargetInput.setText("");
        explanationInput.setText("");
    }

    private void showSuccessAlert() {
        successAlert.setVisible(true);
        dm.setTimeout(() -> successAlert.setVisible(false), 1500);
    }

    private void showFailAlert() {
        failAlert.setVisible(true);
        dm.setTimeout(() -> failAlert.setVisible(false), 1500);
    }

    @FXML
    private void handleOnClicked() {
        try {
            String englishWord = wordTargetInput.getText().trim(); 
            String meaning = explanationInput.getText().trim(); 

            Word word = new Word(englishWord, meaning);
            if (dictionary.containsKey(word.getWordTarget())) {             //kiểm tra từ đã có trong từ điển hay chưa, nếu có thì thông báo thất bại
                showFailAlert();

            } else {
                dictionary.put(word.getWordTarget(), word);                 //nếu chưa có thì thêm từ đó vào và thông báo thành công
                showSuccessAlert();
            }
            addBtn.setDisable(true);
            resetInput();

        } catch (Exception e) {
            // Xử lý ngoại lệ ở đây.
            e.printStackTrace(); // Hoặc sử dụng log để ghi lại thông tin bị lỗi
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {        //hàm này thực hiện khi giao diện được khởi tạo
        successAlert.setVisible(false);
        failAlert.setVisible(false);
        if (explanationInput.getText().isEmpty() || wordTargetInput.getText().isEmpty())
            addBtn.setDisable(true);

        wordTargetInput.setOnKeyTyped(new EventHandler<KeyEvent>() {        //thiết lập sự kiện cho ô nhập từ
            @Override
            public void handle(KeyEvent event) {
                if (explanationInput.getText().isEmpty() || wordTargetInput.getText().isEmpty())    //1 trong 2 ô trống thì vô hiệu hóa nút add
                    addBtn.setDisable(true);
                else
                    addBtn.setDisable(false);
            }
        });

        explanationInput.setOnKeyTyped(new EventHandler<KeyEvent>() {       //thiết lập sự kiện cho ô giải thích
            @Override
            public void handle(KeyEvent keyEvent) {
                if (explanationInput.getText().isEmpty() || wordTargetInput.getText().isEmpty())    //1 trong 2 ô trống thì vô hiệu hóa nút add
                    addBtn.setDisable(true);
                else
                    addBtn.setDisable(false);
            }
        });
    }
}
