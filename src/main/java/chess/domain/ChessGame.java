package chess.domain;

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
        if (turn.equals(GameTurn.READY)) {
            this.turn = GameTurn.WHITE;
        }
    }

    public void move(Square source, Square target) {
        checkTurn(source);
        board.checkCanMove(source, target);
        turn = turn.switchColor();
        checkKingDie(target);
        board = board.move(source, target);
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
}
