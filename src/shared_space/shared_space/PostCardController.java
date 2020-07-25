package shared_space;

import java.io.IOException;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PostCardController extends Pane {

    @FXML
    private Label imgbox;

    @FXML
    private Label titlebox;

    @FXML
    private Label bodybox;

    @FXML
    private Label authorbox;

    public PostCardController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "postCard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setImgbox(String value) {
        String syntax = "-fx-background-image: url('" + value + "');-fx-border-radius: 20; -fx-background-size: cover; -fx-shape: 'M0 13 C0 5 5 0 13 0 L86 0 C94 0 99 5 99 13 L99 86 C99 94 94 99 86 99 L13 99 C5 99 0 94 0 86Z'; -fx-background-color: transparent;";
        this.imgbox.setStyle(syntax);
    }

    public void setTitlebox(String value) {
        this.titlebox.setText(value);
    }

    public void setBodybox(String value) {
        this.bodybox.setText(value);
    }

    public void setAuthorbox(String value) {
        this.authorbox.setText(value);
    }

}
