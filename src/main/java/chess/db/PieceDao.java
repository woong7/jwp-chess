package chess.db;

import chess.domain.piece.Color;
import chess.domain.piece.InitialPositionPieceGenerator;
import chess.domain.piece.Piece;
import chess.domain.position.Column;
import chess.domain.position.Row;
import chess.domain.position.Square;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PieceDao {
    private static final String ERROR_MESSAGE_NO_SAVED_GAME = "헉.. 저장 안한거 아냐? 그런 게임은 없어!";

    private JdbcTemplate jdbcTemplate;

    public PieceDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(String gameID) {
        String sql = "insert into piece (position, type, color, moveCount, gameID) values (?, ?, ?, ?, ?)";

        List<Object[]> data = new ArrayList<>();
        insertPieces(gameID, data);
        jdbcTemplate.batchUpdate(sql, data);
    }

    private void insertPieces(String gameID, List<Object[]> data) {
        for (Column column : Column.values()) {
            insertPiecesInRow(gameID, data, column);
        }
    }

    private void insertPiecesInRow(String gameID, List<Object[]> data, Column column) {
        for (Row row : Row.values()) {
            data.add(new Object[]{new Square(column, row).getName(),
                    InitialPositionPieceGenerator.getType(column, row).name(),
                    InitialPositionPieceGenerator.getColor(row).name(),
                    0,
                    gameID});
        }
    }

    public void deleteByPosition(Square target, String gameID) {
        String sql = "delete from piece where position = ? and gameID = ?";
        jdbcTemplate.update(sql, target.getName(), gameID);
    }

    public void updatePosition(Square source, Square target, String gameID) {
        String sql = "update piece set position = ?, moveCount=moveCount+1 where position = ? and gameID = ?";
        jdbcTemplate.update(sql, target.getName(), source.getName(), gameID);
    }

    public void insertNone(String gameID, Square source) {
        String sql = "insert into piece (position, type, color, moveCount, gameID) values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, source.getName(), InitialPositionPieceGenerator.NONE.name(), Color.NONE.name(), 0, gameID);
    }

    public void deleteAll(String gameID) {
        String sql = "delete from piece where gameID = ?";
        jdbcTemplate.update(sql, gameID);
    }

    public Map<Square, Piece> findByGameID(String gameID) {
        String sql = "select position, type, color, moveCount from piece where gameID = ?";
        Map<Square, Piece> board = new HashMap<>();
        List<Map<String, Object>> data = jdbcTemplate.queryForList(sql, gameID);
        loadBoard(board, data);
        checkGameExist(board);
        return board;

    }

    public void promotePiece(String gameID, String target, String type){
        String sql = "update piece set type = ? where position = ? and gameID = ?";
        jdbcTemplate.update(sql, type, target, gameID);
    }

    private void loadBoard(Map<Square, Piece> board, List<Map<String, Object>> pieces) {
        for (Map<String, Object> piece : pieces) {
            String position = (String) piece.get("position");
            String type = (String) piece.get("type");
            String color = (String) piece.get("color");
            int moveCount = (int) piece.get("moveCount");
            board.put(new Square(position), Piece.createByTypeAndColorAndMoveCount(type, color, moveCount));
        }
    }

    private void checkGameExist(Map<Square, Piece> board) {
        if (board.isEmpty()) {
            throw new IllegalArgumentException(ERROR_MESSAGE_NO_SAVED_GAME);
        }
    }
}
