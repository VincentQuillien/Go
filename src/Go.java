//imports
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

//class defnition for reversi game
public class Go extends Application {
    // overridden init method
    public void init() {
        sp_mainlayout = new StackPane();
        go_control = new GoControl();
        sp_mainlayout.getChildren().add(go_control);
    }

    // overridden start method
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Go");
        primaryStage.setScene(new Scene(sp_mainlayout, 800, 800));
        primaryStage.show();
    }

    // overridden stop method
    public void stop() {

    }

    // entry point into our program for launching our javafx applicaton
    public static void main(String[] args) {
        launch(args);
    }

    // private fields for a stack pane and a reversi control
    private StackPane sp_mainlayout;
    private GoControl go_control;

}


