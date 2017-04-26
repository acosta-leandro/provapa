/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package provapa;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Leandro Acosta <leandro.acosta292@hotmail.com>
 */
public class ProvaPA extends Application {

    static BorderPane borde;
    public static Stage stage;
    private static ProvaPA instance;

    public ProvaPA() {
        instance = this;
    }

    public static ProvaPA getInstance() {
        return instance;
    }

    @Override
    public void start(Stage stage) throws IOException {

        this.stage = stage;
        iniciarSistema();
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void iniciarSistema() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ProvaPA.class.getResource("view/principal.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            stage.setScene(new Scene(pane));
            stage.setFullScreen(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    

}
