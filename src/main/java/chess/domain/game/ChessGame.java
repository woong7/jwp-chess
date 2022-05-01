package chess.domain.game;

import chess.domain.board.Board;
import chess.domain.board.BoardGenerator;
import chess.domain.position.Square;

import java.util.Map;
import java.util.stream.Collectors;

public final class ChessGame {
    private static final String ERROR_MESSAGE_TURN = "순서 지키시지?!";

    private Board board;
    private GameTurn turn;

    public ChessGame(BoardGenerator boardGenerator, GameTurn gameTurn) {
        this.board = new Board(boardGenerator);
        this.turn = gameTurn;
    }

    public void startGame() {
        if (turn.equals(GameTurn.READY)) {
            this.turn = GameTurn.WHITE;
        }
    }

    public boolean isCastable(Square source, Square target) {
        return board.isRightTurn(source, turn.getColor())
                && board.isRightTurn(target, turn.getColor())
                && board.isCastable(source, target);
    }

    public void move(Square source, Square target) {
        checkTurn(source);
        board.checkCanMove(source, target);
        turn = turn.switchColor();
        checkKingDie(target);
        board = board.move(source, target);
    }

    public void doCastling(Square source, Square target) {
        board = board.castle(source, target);
        turn = turn.switchColor();
    }

    private void checkTurn(Square source) {
        if (!board.isRightTurn(source, turn.getColor())) {
            throw new IllegalArgumentException(ERROR_MESSAGE_TURN);
        }
    }

    private void checkKingDie(Square target) {
        if (board.isTargetKing(target)) {
            turn = GameTurn.FINISHED;
        }
    }

    public boolean isKingDie() {
        return turn == GameTurn.FINISHED;
    }

    public boolean isInGame() {
        return turn != GameTurn.READY;
    }

    public Map<String, String> getEmojis() {
        return board.getBoard().entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> entry.getKey().getName(), entry -> entry.getValue().getEmoji()));
    }

    public Board getBoard() {
        return board;
    }

    public GameTurn getTurn() {
        return turn;
    }

    public void doPromotion(Square target) {
        board = board.doPromotion(target);
    }

    public boolean isPromotionAvailable(Square target) {
        return board.getBoard().get(target).isPawn() && target.isEndRank();
    }
}
