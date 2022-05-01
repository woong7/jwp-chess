package chess.domain.piece;

import chess.domain.position.Column;
import chess.domain.position.Row;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

public enum InitialPositionPieceGenerator {
    KING((column, row) -> new InitialPiecePositionChecker(column, row).isKing(), row -> new King(getColor(row), 0)),
    QUEEN((column, row) -> new InitialPiecePositionChecker(column, row).isQueen(), row -> new Queen(getColor(row), 0)),
    ROOK((column, row) -> new InitialPiecePositionChecker(column, row).isRook(), row -> new Rook(getColor(row), 0)),
    BISHOP((column, row) -> new InitialPiecePositionChecker(column, row).isBishop(), row -> new Bishop(getColor(row), 0)),
    KNIGHT((column, row) -> new InitialPiecePositionChecker(column, row).isKnight(), row -> new Knight(getColor(row), 0)),
    PAWN((column, row) -> new InitialPiecePositionChecker(column, row).isPawn(), row -> new Pawn(getColor(row), 0)),
    NONE((column, row) -> new InitialPiecePositionChecker(column, row).isNone(), row -> new None(getColor(row), 0));

    private final BiPredicate<Column, Row> condition;
    private final Function<Row, Piece> colorOf;

    InitialPositionPieceGenerator(BiPredicate<Column, Row> condition, Function<Row, Piece> colorOf) {
        this.condition = condition;
        this.colorOf = colorOf;
    }

    public static Piece generatePiece(Column column, Row row) {
        return Arrays.stream(InitialPositionPieceGenerator.values())
                .filter(piece -> piece.condition.test(column, row))
                .map(piece -> piece.colorOf.apply(row))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static InitialPositionPieceGenerator getType(Column column, Row row) {
        return Arrays.stream(InitialPositionPieceGenerator.values())
                .filter(piece -> piece.condition.test(column, row))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static Color getColor(Row row) {
        if (row == Row.SEVEN || row == Row.EIGHT) {
            return Color.BLACK;
        }
        if (row == Row.ONE || row == Row.TWO) {
            return Color.WHITE;
        }
        return Color.NONE;
    }
}
