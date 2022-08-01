import javax.swing.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.*;

public class Gui extends Application  {
    public Gui (){

    }

    @Override
    public void start(Stage gui) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("BasicGui.fxml"));
        Scene scene = new Scene(root);

        gui.setScene(scene);
        gui.show();
    }

}