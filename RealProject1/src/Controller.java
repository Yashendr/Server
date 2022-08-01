import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private Button exitBtn;

    @FXML
    private TextArea messages;

    @FXML
    private Button sendBtn;

    @FXML
    private TextField sendText;

    @FXML
    private TextArea users;

    @FXML
    void onEnterPressed(ActionEvent event) {

    }

    @FXML
    void onExitClick(ActionEvent event) {

    }

    @FXML
    void onSendClick(ActionEvent event) {
        System.out.println("aaaaaa");
    }

}

