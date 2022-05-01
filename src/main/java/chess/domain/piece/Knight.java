package chess.domain.piece;

import chess.domain.position.UnitDirection;

import java.util.List;

import static chess.domain.position.UnitDirection.*;

public final class Knight extends MovingUnitPiece {
    private final static String BUG_MESSAGE_COLOR = "[BUG] 나이트는 색상을 가져야합니다.";
    private static final String BLACK_KNIGHT = "black_knight";
    private static final String WHITE_KNIGHT = "white_knight";
    private static final List<UnitDirection> MOVABLE_DIRECTIONS;

    static {
        MOVABLE_DIRECTIONS = List.of(ENN, ESS, WNN, WSS, EEN, EES, WWN, WWS);
    }

    Knight(Color color, int moveCount) {
        super(color, 2.5, moveCount, MOVABLE_DIRECTIONS);
    }

    @Override
    public String getEmoji() {
        if (color == Color.NONE) {
            throw new IllegalArgumentException(BUG_MESSAGE_COLOR);
        }

        if (color == Color.BLACK) {
            return BLACK_KNIGHT;
        }

        return WHITE_KNIGHT;
    }

    @Override
    public boolean isPawn() {
        return false;
    }

    @Override
    public boolean isKing() {
        return false;
    }
}
