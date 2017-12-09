import java.util.*;

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

    GoLogic(GoBoard board) {
        this.board = board;
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

        System.out.println("captured = " + captured);
        GoPiece.Colour[][] newState = createNewState(captured);

        // Check eternity and suicide rules
        if (Arrays.deepEquals(newState, previousState)
                || isSuicide(x, y, captured)) {
            currentState[x][y] = GoPiece.Colour.EMPTY;
            return;
        }
        currentState[x][y] = GoPiece.Colour.EMPTY;

        // End turn
        // TODO update score
        swapPlayer();
        previousState = currentState;
        currentState = newState;
        board.render(currentState);
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
}
