package chess.domain.piece;

import chess.domain.position.UnitDirection;

import java.util.List;

import static chess.domain.position.UnitDirection.*;

public final class Bishop extends MovingMultipleUnitPiece {
    private final static String BUG_MESSAGE_COLOR = "[BUG] 비숍은 색상을 가져야합니다.";
    private static final String BLACK_BISHOP = "black_bishop";
    private static final String WHITE_BISHOP = "white_bishop";
    private static final List<UnitDirection> MOVABLE_DIRECTIONS;

    static {
        MOVABLE_DIRECTIONS = List.of(EN, ES, WS, WN);
    }

    Bishop(Color color, int moveCount) {
        super(color, 3, moveCount, MOVABLE_DIRECTIONS);
    }

    @Override
    public String getEmoji() {
        if (color == Color.NONE) {
            throw new IllegalArgumentException(BUG_MESSAGE_COLOR);
        }

        if (color == Color.BLACK) {
            return BLACK_BISHOP;
        }

        return WHITE_BISHOP;
    }

    @Override
    public boolean isRook(){
        return false;
    };
}
