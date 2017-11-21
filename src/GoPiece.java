import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.transform.Translate;
//class definition for a reversi piece
class GoPiece extends Group {
    enum Colour{
        EMPTY,
        BLACK,
        WHITE
    }
    public GoPiece(Colour currentplayer) {
        piece = new Ellipse();
        t = new Translate();
        piece.getTransforms().add(t);

        if(currentplayer == Colour.WHITE){
            piece.setFill(Color.WHITE);
        }
        else if(currentplayer == Colour.BLACK) {
            piece.setFill(Color.BLACK);
        }
        else if(currentplayer == Colour.EMPTY){
            piece.setFill(Color.TRANSPARENT);
        }
        getChildren().addAll(piece);
    }
    // overridden version of the resize method to give the piece the correct size
    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        if(player == Colour.WHITE){
            piece.setCenterX(width / 2); piece.setCenterY(height / 2);
            piece.setRadiusX(width / 2); piece.setRadiusY(height / 2);
        }
        else{
            piece.setCenterX(width / 2); piece.setCenterY(height / 2);
            piece.setRadiusX(width / 2); piece.setRadiusY(height / 2);
        }
    }
    // overridden version of the relocate method to position the piece correctly
    @Override
    public void relocate(double x, double y) {
        super.relocate(x, y);
        t.setX(x);
        t.setY(y);
    }
    // public method that will swap the colour and type of this piece
    public void swapPiece() {
        if(player == Colour.WHITE){
            player = Colour.BLACK;
            piece.setFill(Color.BLACK);
        }
        else if (player == Colour.BLACK){
            player = Colour.WHITE;
            piece.setFill(Color.WHITE);
        }
    }
    // method that will set the piece type
    public void setPiece(final Colour type) {
        if(type == Colour.EMPTY){
            piece.setVisible(false);
        }
        else if(type == Colour.WHITE){
            piece.setVisible(true);
            piece.setFill(Color.WHITE);
        }
        else if(type == Colour.BLACK){
            piece.setVisible(true);
            piece.setFill(Color.BLACK);
        }
        player = type;
    }

    // returns the type of this piece
    public Colour getPiece() {
        return player;
    }

    // private fields
    private Colour player;		// the player that this piece belongs to
    private Ellipse piece;	// ellipse representing the player's piece
    private Translate t;	// translation for the player piece
}
