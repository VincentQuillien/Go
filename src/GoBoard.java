import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

class GoBoard extends Pane {

    // default constructor for the class
    public GoBoard() {
        render = new GoPiece[SIZE][SIZE];
        horizontal = new Line[SIZE];
        vertical = new Line[SIZE];
        horizontal_t = new Translate[SIZE];
        vertical_t = new Translate[SIZE];
        initialiseLinesBackground();
        initialiseRender();
    }

    public void render(GoPiece.Colour[][] newState) {
        if (newState == null)
            return;
        for (int i = 0; i < newState.length; i++) {
            for (int j = 0; j < newState.length; j++) {
                render[i][j].setPiece(newState[i][j]);
            }
        }
    }

    public double getCell_width() {
        return this.cell_width;
    }

    public double getCell_height() {
        return this.cell_height;
    }

    // overridden version of the resize method to give the board the correct size
    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        cell_width = width / SIZE;
        cell_height = height / SIZE;
        background.setWidth(width);
        background.setHeight(height);
        horizontalResizeRelocate(width);
        verticalResizeRelocate(height);
        pieceResizeRelocate();
    }

    //Public method for counting the score
    /*public void updateScore(){
        for(int x = 0; x < SIZE; x++){
            for(int y = 0; y < SIZE; y++){
                if(render[x][y].getPiece() == GoPiece.Colour.BLACK){
                    black_score = black_score + 1;
                }
                else if(render[x][y].getPiece() == GoPiece.Colour.WHITE){
                    white_score += 1;
                }
            }
        }
        System.out.println("Black's score is: " + black_score);
        System.out.println("White's score is: " + white_score);
    }*/



    // private method that will initialise the background and the lines
    private void initialiseLinesBackground() {
        // initialize backkground, set colour and add to reversiBoard
        System.out.println(cell_height);
        background = new Rectangle();
        background.setFill(Color.SANDYBROWN);
        getChildren().add(background);


        for (int i = 0; i < SIZE; i++) {
            horizontal[i] = new Line();
            horizontal_t[i] = new Translate();
            horizontal[i].getTransforms().add(horizontal_t[i]);
            horizontal[i].setStartX(0);
            horizontal[i].setStartY(0);
            horizontal[i].setEndY(0);
            getChildren().add(horizontal[i]);
        }

        for (int i = 0; i < SIZE; i++) {
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
        for (int x = 0; x < SIZE; x++) {
            horizontal[x].setEndX(width - cell_width / 2);
            horizontal[x].setStartX(cell_width / 2);
            horizontal_t[x].setY((x + 1) * cell_height - cell_height / 2);
        }
    }

    // private method for resizing and relocating the vertical lines
    private void verticalResizeRelocate(final double height) {
        for (int y = 0; y < SIZE; y++) {
            vertical[y].setEndY(height - cell_height / 2);
            vertical[y].setStartY(cell_height / 2);
            vertical_t[y].setX((y + 1) * cell_width - cell_width / 2);
        }
    }

    // private method for resizing and relocating all the pieces
    private void pieceResizeRelocate() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                render[i][j].resize(cell_width, cell_height);
                render[i][j].relocate(i * cell_width, j * cell_height);
            }
        }
    }

    // private method that will initialise everything in the render array
    private void initialiseRender() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                render[i][j] = new GoPiece(GoPiece.Colour.EMPTY);
                this.getChildren().add(render[i][j]);
            }
        }
    }

    private final int SIZE = 7;
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
    // the width and height of a cell in the board
    private double cell_width;
    private double cell_height;
    private double white_score = 0.5;
    private double black_score = 0;
}


