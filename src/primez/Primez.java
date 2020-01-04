
package primez;

import java.io.IOException; 
import java.net.URL; 
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Primez extends Application implements Initializable{
    
    @Override
    public void start(Stage primaryStage) throws IOException{
        
        Parent root = FXMLLoader.load(getClass().getResource("fxmlPrime.fxml"));
        primaryStage.getIcons().add(new Image(Primez.class.getResourceAsStream("ikon.png")));
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Prime_Numbers");
        primaryStage.setResizable(false); 
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
