import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

class GoBoard extends Pane {

    // default constructor for the class
    public GoBoard() {
        render = new GoPiece[7][7];
        horizontal = new Line[7];
        vertical = new Line[7];
        horizontal_t = new Translate[7];
        vertical_t = new Translate[7];

        initialiseLinesBackground();
        initialiseRender();
        resetGame();
    }

    // public method that will try to place a piece in the given x,y coordinate
    public void placePiece(final double x, final double y) {
        int cX = (int) (x / cell_width);
        int cY = (int) (y / cell_height);
        System.out.println(cX + "," + cY);
        if (render[cX][cY].getPiece() == GoPiece.Colour.EMPTY){
            render[cX][cY].setPiece(current_player);
            current_player = current_player == GoPiece.Colour.BLACK ? GoPiece.Colour.WHITE : GoPiece.Colour.BLACK;
        }
    }

    // overridden version of the resize method to give the board the correct size
    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        cell_width = width / 7;
        cell_height = height / 7;
        background.setWidth(width);
        background.setHeight(height);
        horizontalResizeRelocate(width);
        verticalResizeRelocate(height);
        pieceResizeRelocate();
    }

    // public method for resetting the game
    public void resetGame() {
        resetRenders();
        current_player = GoPiece.Colour.BLACK;

    }

    // private method that will reset the renders
    private void resetRenders() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                render[i][j].setPiece(GoPiece.Colour.EMPTY);
            }
        }
    }

    // private method that will initialise the background and the lines
    private void initialiseLinesBackground() {
        // initialize backkground, set colour and add to reversiBoard
        System.out.println(cell_height);
        background = new Rectangle();
        background.setFill(Color.SANDYBROWN);
        getChildren().add(background);


        for (int i = 0; i < 7; i++) {
            horizontal[i] = new Line();
            horizontal_t[i] = new Translate();
            horizontal[i].getTransforms().add(horizontal_t[i]);
            horizontal[i].setStartX(0);
            horizontal[i].setStartY(0);
            horizontal[i].setEndY(0);
            getChildren().add(horizontal[i]);
        }

        for (int i = 0; i < 7; i++) {
            vertical[i] = new Line();
            vertical_t[i] = new Translate();
            vertical[i].getTransforms().add(vertical_t[i]);
            vertical[i].setStartX(0);
            vertical[i].setEndX(0);
            vertical[i].setStartY(0);
            getChildren().add(vertical[i]);
        }
    }

    // private method for resizing and relocating the horizontal lines
    private void horizontalResizeRelocate(final double width) {
        for (int x = 0; x < 7; x++) {
            horizontal[x].setEndX(width - cell_width / 2);
            horizontal[x].setStartX(cell_width / 2);
            horizontal_t[x].setY((x + 1) * cell_height - cell_height / 2);
        }
    }

    // private method for resizing and relocating the vertical lines
    private void verticalResizeRelocate(final double height) {
        for (int y = 0; y < 7; y++) {
            vertical[y].setEndY(height - cell_height / 2);
            vertical[y].setStartY(cell_height / 2);
            vertical_t[y].setX((y + 1) * cell_width - cell_width / 2);
        }
    }

    // private method for resizing and relocating all the pieces
    private void pieceResizeRelocate() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                render[i][j].resize(cell_width, cell_height);
                render[i][j].relocate(i * cell_width, j * cell_height);
            }
        }
    }

    // private method for getting a piece on the board. this will return the board
    // value unless we access an index that doesnt exist. this is to make the code
    // for determing reverse chains much easier
    private GoPiece.Colour getPiece(final int x, final int y) {
        return render[x][y].getPiece();
    }

    // private method that will initialise everything in the render array
    private void initialiseRender() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                render[i][j] = new GoPiece(GoPiece.Colour.EMPTY);
                this.getChildren().add(render[i][j]);
            }
        }
    }

    // rectangle that makes the background of the board
    private Rectangle background;
    // arrays for the lines that makeup the horizontal and vertical grid lines
    private Line[] horizontal;
    private Line[] vertical;
    // arrays holding translate objects for the horizontal and vertical grid lines
    private Translate[] horizontal_t;
    private Translate[] vertical_t;
    // arrays for the internal representation of the board and the pieces that are
    // in place
    private GoPiece[][] render;
    // the current player who is playing and who is his opposition
    private GoPiece.Colour current_player;
    // the width and height of a cell in the board
    private double cell_width;
    private double cell_height;

}


