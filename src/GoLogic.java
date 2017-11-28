import java.awt.*;
import java.util.HashSet;
import java.util.Set;

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

/*
public class GoLogic {
    private GoPiece.Colour player;

    public Set<Coord> getGroups(int x, int y, GoPiece[][] board) {
        return hasLiberties(x, y, board, new HashSet<>());
    }

    private Set<Coord> hasLiberties(int x, int y, GoPiece[][] board, Set<Coord> groups) {
        GoPiece.Colour piece = board[x][y].getPiece();
        if (piece == GoPiece.Colour.EMPTY)
            return null;
        if (piece != player)
            return groups;
        groups.add(new Coord(x, y));
        for (int i : new int[]{-1, 1}) {
            hasLiberties(x + i, y, board, groups);
            hasLiberties(x, y + i, board, groups);
        }
    }
}
*/
