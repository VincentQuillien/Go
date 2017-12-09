//imports
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

//class defnition for reversi game
public class Go extends Application {
    // overridden init method
    public void init() {
        sp_mainlayout = new StackPane();
        go_control = new GoControl();
        bp = new BorderPane();
        hb = new HBox();
        vb = new VBox();
        bp.setTop(hb);
        bp.setCenter(sp_mainlayout);
        bp.setRight(vb);
        vb.getChildren().addAll(go_control.resetGame, go_control.rules, go_control.pass);
        hb.getChildren().addAll(go_control.blackscore, go_control.whitescore, go_control.currentplayer);
        sp_mainlayout.getChildren().add(go_control);
    }

    // overridden start method
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Go");
        primaryStage.setScene(new Scene(bp, 750, 750));
        primaryStage.show();

        // Pop Up Dialog Manager
        go_control.rules.setOnAction(event -> {final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(primaryStage);
            VBox dialogVbox = new VBox(20);
            dialogVbox.getChildren().add(new Text("\n" +
                    "    1. The board is empty at the onset of the game (unless players agree to place a handicap).\n" +
                    "\n" +
                    "    2. Black makes the first move, after which White and Black alternate.\n" +
                            "\n" +
                    "    3. A move consists of placing one stone of one's own color on an empty intersection on the board.\n" +
                            "\n" +
                    "    4. A player may pass their turn at any time.\n" +
                            "\n" +
                    "    5. A stone or solidly connected group of stones of one color is captured and removed from the board when" +
                    "     \n        all the intersections directly adjacent to it are occupied by the enemy." +
                    "      \n       (Capture of the enemy takes precedence over self-capture.)\n" +
                            "\n" +
                    "    6. No stone may be played so as to recreate a former board position.\n" +
                            "\n" +
                    "    7. Two consecutive passes end the game.\n" +
                            "\n" +
                    "    8. A player's area consists of all the points the player has either occupied or surrounded.\n" +
                            "\n" +
                    "    9. The player with more area wins.\n"));
            Scene dialogScene = new Scene(dialogVbox, 710, 450);
            dialog.setScene(dialogScene);
            dialog.show();});
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
    private HBox hb;
    private VBox vb;
    private BorderPane bp;
}


