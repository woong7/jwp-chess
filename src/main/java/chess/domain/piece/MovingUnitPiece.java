package chess.domain.piece;

import chess.domain.position.Movement;
import chess.domain.position.UnitDirection;

import java.util.List;

public abstract class MovingUnitPiece extends Piece {
    List<UnitDirection> movableDirections;

    MovingUnitPiece(Color color, double score, int moveCount, List<UnitDirection> directions) {
        super(color, score, moveCount);
        this.movableDirections = directions;
    }

    @Override
    public boolean canMove(Movement movement, Piece target) {
        return movement.hasSame(movableDirections);
    }

    @Override
    public boolean isNone() {
        return false;
    }

    @Override
    abstract public boolean isPawn();

    @Override
    abstract public boolean isKing();

    @Override
    public boolean isRook(){
        return false;
    };
}
