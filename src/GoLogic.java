import java.util.*;
import java.util.function.BiConsumer;

class Coord {
    int x;
    int y;

    Coord(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Coord))
            return false;
        Coord other = (Coord) obj;
        return other.x == this.x && other.y == this.y;
    }

    @Override
    public int hashCode() {
        return x * 7 + y;
    }
}

public class GoLogic {
    private GoPiece.Colour[][] currentState;
    private GoPiece.Colour[][] previousState;
    private GoPiece.Colour currentPlayer;
    private final Coord[] positions = new Coord[]{
            new Coord(-1, 0),
            new Coord(1, 0),
            new Coord(0, -1),
            new Coord(0, 1)
    };
    private int boardSize = 7;
    private GoBoard board;
    private BiConsumer<Double[], GoPiece.Colour> updateUI;

    GoLogic(GoBoard board, BiConsumer<Double[], GoPiece.Colour> updateUI) {
        this.board = board;
        this.updateUI = updateUI;
        resetGame();
    }

    // TODO remove
    void debug(int x, int y) {
        currentState[x][y] = currentPlayer;
        board.render(currentState);
    }

    void placePiece(final int x, final int y) {
        if (currentState[x][y] != GoPiece.Colour.EMPTY)
            return;

        Set<Coord> captured = new HashSet<>();
        currentState[x][y] = currentPlayer;
        //call capture for each direction
        for (Coord pos : positions) {
            Set<Coord> group = new HashSet<>();
            if (capture(getOpposite(currentPlayer), x + pos.x, y + pos.y, group))
                captured.addAll(group);
        }

        // System.out.println("captured = " + captured);  Debugging
        GoPiece.Colour[][] newState = createNewState(captured);

        // Check eternity and suicide rules
        if (Arrays.deepEquals(newState, previousState)
                || isSuicide(x, y, captured)) {
            currentState[x][y] = GoPiece.Colour.EMPTY;
            return;
        }
        currentState[x][y] = GoPiece.Colour.EMPTY;

        // End turn
        if(currentPlayer == GoPiece.Colour.WHITE){
            captured_black += captured.size();
        }
        else if ( currentPlayer == GoPiece.Colour.BLACK){
            captured_white += captured.size();
        }

        swapPlayer();
        previousState = currentState;
        currentState = newState;
        board.render(currentState);
        updateScore();
    }

    void resetGame() {
        currentPlayer = GoPiece.Colour.BLACK;
        currentState = new GoPiece.Colour[boardSize][boardSize];
        previousState = new GoPiece.Colour[boardSize][boardSize];
        for (int x = 0; x < boardSize; x++)
            for (int y = 0; y < boardSize; y++) {
                currentState[x][y] = GoPiece.Colour.EMPTY;
                previousState[x][y] = GoPiece.Colour.EMPTY;
            }
        board.render(currentState);
    }

    void swapPlayer() {
        currentPlayer = getOpposite(currentPlayer);
    }

    // Capture a group of the specified color starting at the point x,y
    private boolean capture(GoPiece.Colour colour, int x, int y, Set<Coord> captured) {
        // array out of bound check
        if (x < 0 || x >= currentState.length || y < 0 || y >= currentState.length)
            return true;
        // group boundaries check
        if (currentState[x][y] == getOpposite(colour) || captured.contains(new Coord(x, y)))
            return true;
        // liberty check
        if (currentState[x][y] == GoPiece.Colour.EMPTY)
            return false;

        captured.add(new Coord(x, y));
        for (Coord pos : positions)
            if (!capture(colour, x + pos.x, y + pos.y, captured))
                return false;
        return true;
    }

    private GoPiece.Colour[][] createNewState(Set<Coord> captured) {
        GoPiece.Colour[][] newState = currentState.clone();
        for (int x = 0; x < currentState.length; x++)
            newState[x] = currentState[x].clone();
        for (Coord pos : captured)
            newState[pos.x][pos.y] = GoPiece.Colour.EMPTY;
        return newState;
    }

    private boolean isSuicide(int x, int y, Set<Coord> captured) {
        return captured.isEmpty() && capture(currentPlayer, x, y, new HashSet<>());
    }

    private GoPiece.Colour getOpposite(GoPiece.Colour colour) {
        return colour == GoPiece.Colour.BLACK ? GoPiece.Colour.WHITE : GoPiece.Colour.BLACK;
    }

    //Public method for counting the score
    public void updateScore(){
        black_score = 0;
        white_score = 0.5;
        for(int x = 0; x < 7; x++){
            for(int y = 0; y < 7; y++){
                if(currentState[x][y] == GoPiece.Colour.BLACK){
                    black_score ++;
                }
                else if(currentState[x][y] == GoPiece.Colour.WHITE){
                    white_score ++;
                }
            }
        }

        System.out.println("Black's score is: " + (black_score + captured_white));
        System.out.println("White's score is: " + (white_score + captured_black));
        updateUI.accept(new Double[]{black_score, white_score}, currentPlayer);
    }

    private int captured_black;
    private int captured_white;
    public double white_score;   // white gets extra .5 for going second and to avoid a tie
    public double black_score;
}
