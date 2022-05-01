package chess.domain.piece;

import chess.domain.position.UnitDirection;

import java.util.List;

import static chess.domain.position.UnitDirection.*;

public final class Queen extends MovingMultipleUnitPiece {
    private final static String BUG_MESSAGE_COLOR = "[BUG] 퀸은 색상을 가져야합니다.";
    private static final String WHITE_QUEEN = "white_queen";
    private static final String BLACK_QUEEN = "black_queen";
    private static final List<UnitDirection> MOVABLE_DIRECTIONS;

    static {
        MOVABLE_DIRECTIONS = List.of(N, S, W, E, ES, WN, WS, EN);
    }

    Queen(Color color, int moveCount) {
        super(color, 9, moveCount, MOVABLE_DIRECTIONS);
    }

    @Override
    public String getEmoji() {
        if (color == Color.NONE) {
            throw new IllegalArgumentException(BUG_MESSAGE_COLOR);
        }

        if (color == Color.BLACK) {
            return BLACK_QUEEN;
        }

        return WHITE_QUEEN;
    }

    @Override
    public boolean isRook(){
        return false;
    };
}
