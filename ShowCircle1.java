/* To Change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 *  * and open the template in the editor.
 *   */
package showcircle1;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 *  *
 *   * @author Anandi
 *    */
public class ShowCircle1 extends Application {
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    // Create a circle and set its properties
         Circle circle = new Circle();
             circle.setCenterX(100);
             circle.setCenterY(100);
             circle.setRadius(50);
             circle.setStroke(Color.BLACK);
             circle.setFill(null);     
    //                                 // Create a pane to hold the circle 
             Pane pane = new Pane();
             pane.getChildren().add(circle);                       
    //                                                 // Create a scene and place it in the stage
             Scene scene = new Scene(pane, 200, 200);      
             primaryStage.setTitle("ShowCircle"); // Set the stage title
             primaryStage.setScene(scene); // Place the scene in the stage
             primaryStage.show(); // Display the stage                                                                 
   }                                                                                                                                            /**
    //                                                                          * The main method is only needed for the IDE with limited
    //                                                                             * JavaFX support. Not needed for running from the command line.
    //                                                                              */
  public static void main(String[] args) {
    launch(args);
  }
}
