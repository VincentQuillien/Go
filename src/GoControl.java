import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.control.SkinBase;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Skin;

import static javafx.scene.input.KeyCode.SPACE;

//class definition for a custom reversi control
class GoControl extends Control {
    // constructor for the class
    public GoControl() {

        setSkin(new GoControlSkin(this));
        go_board = new GoBoard();
        logic = new GoLogic(go_board);
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
    }

    // overridden version of the resize method
    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        go_board.resize(width, height);
    }
    GoBoard go_board;
    GoLogic logic;
}

class GoControlSkin extends SkinBase<GoControl> implements Skin<GoControl> {

    public GoControlSkin(GoControl cc) {
        // call the super class constructor
        super(cc);
    }

}
