import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static javafx.scene.input.KeyCode.SPACE;

//class definition for a custom reversi control
class GoControl extends Control {
    // constructor for the class
    public GoControl() {

        setSkin(new GoControlSkin(this));
        resetGame = new Button("Reset");
        rules = new Button("Rules");
        pass = new Button("Skip");
        blackscore = new Label("Black Score: 0");
        whitescore = new Label("White Score: 0");
        currentplayer = new Label("Current Player = Black");
        blackscore.setPrefSize(250,80);
        whitescore.setPrefSize(250,80);
        currentplayer.setPrefSize(250,80);
        blackscore.setFont(new Font("Arial", 18));
        whitescore.setFont(new Font("Arial", 18));
        currentplayer.setFont(new Font("Arial", 18));
        resetGame.setPrefSize(80, Integer.MAX_VALUE);
        rules.setPrefSize(80, Integer.MAX_VALUE);
        pass.setPrefSize(80, Integer.MAX_VALUE);
        go_board = new GoBoard();
        logic = new GoLogic(go_board, (scores, playerColor) -> {
            blackscore.setText("Black Score: " + scores[0]);
            whitescore.setText("White Score: " + scores[1]);
            currentplayer.setText("Current Player = " + playerColor);
        });
        getChildren().add(go_board);

        // mouse clicked event handler that will try to place a piece on the board
        setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                int x = (int) (event.getX() / go_board.getCell_width());
                int y = (int) (event.getY() / go_board.getCell_height());
                if (event.isControlDown())
                    logic.debug(x, y);
                else
                    logic.placePiece(x, y);
            }
        });

        // key stroke event handler which will call the restGame method when the space bar is pressed
        setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case SPACE: logic.resetGame();break;
                    case P: logic.swapPlayer();break;
                }
            }
        });

        resetGame.setOnAction(event -> logic.resetGame());
        pass.setOnAction(event -> logic.swapPlayer());
    }

    // overridden version of the resize method
    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        go_board.resize(width, height);
    }
    GoBoard go_board;
    GoLogic logic;
    public Button resetGame, rules, pass;
    public Label blackscore, whitescore, currentplayer;

}

class GoControlSkin extends SkinBase<GoControl> implements Skin<GoControl> {

    public GoControlSkin(GoControl cc) {
        // call the super class constructor
        super(cc);
    }

}
