package chess.domain.piece;

import chess.domain.position.UnitDirection;

import java.util.List;

import static chess.domain.position.UnitDirection.*;

public final class Rook extends MovingMultipleUnitPiece {
    private static final String BUG_MESSAGE_COLOR = "[BUG] 룩은 색상을 가져야합니다.";
    private static final String WHITE_ROOK = "white_rook";
    private static final String BLACK_ROOK = "black_rook";
    private static final List<UnitDirection> MOVABLE_DIRECTIONS;

    static {
        MOVABLE_DIRECTIONS = List.of(N, S, W, E);
    }

    Rook(Color color, int moveCount) {
        super(color, 5, moveCount, MOVABLE_DIRECTIONS);
    }

    @Override
    public String getEmoji() {
        if (color == Color.NONE) {
            throw new IllegalArgumentException(BUG_MESSAGE_COLOR);
        }

        if (color == Color.BLACK) {
            return BLACK_ROOK;
        }

        return WHITE_ROOK;
    }

    @Override
    public boolean isRook(){
        return true;
    };
}
