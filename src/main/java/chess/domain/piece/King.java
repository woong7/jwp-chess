package chess.domain.piece;

import chess.domain.position.UnitDirection;

import java.util.List;

import static chess.domain.position.UnitDirection.*;

public final class King extends MovingUnitPiece {
    private final static String BUG_MESSAGE_COLOR = "[BUG] 킹은 색상을 가져야합니다.";
    private static final String WHITE_KING = "white_king";
    private static final String BLACK_KING = "black_king";
    private static final List<UnitDirection> MOVABLE_DIRECTIONS;

    static {
        MOVABLE_DIRECTIONS = List.of(N, S, E, W, WS, EN, WN, ES);
    }

    King(Color color, int moveCount) {
        super(color, 0, moveCount, MOVABLE_DIRECTIONS);
    }

    @Override
    public String getEmoji() {
        if (color == Color.NONE) {
            throw new IllegalArgumentException(BUG_MESSAGE_COLOR);
        }

        if (color == Color.BLACK) {
            return BLACK_KING;
        }

        return WHITE_KING;
    }

    @Override
    public boolean isPawn() {
        return false;
    }

    @Override
    public boolean isKing() {
        return true;
    }
}
