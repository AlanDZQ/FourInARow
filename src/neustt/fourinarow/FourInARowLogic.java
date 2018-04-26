package neustt.fourinarow;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

/**
 * FourInARowLogic refactoring pairwork.
 *
 * @author Alan Dong(20154987), Neil Zhang(20154987)
 * The refactoring of the code
 * @version 1.0
 */


public class FourInARowLogic {

    /**
     * Represents a board that is a 2-dimensional array
     * of BoardCellStatus items.
     */
    private BoardCellStatus[][] board = null;

    /**
     * The current player for the game.
     */

    private Player player = Player.ONE;


    private boolean isTwoPlayMode;

    private GridPosition undoGridPosition;

    private ArrayList<GridPosition> gridPositions = new ArrayList<>();

    public ArrayList<GridPosition> getGridPositions() {
        return gridPositions;
    }

    public BoardCellStatus[][] getBoard() {
        return board;
    }

    public boolean isTwoPlayMode() {
        return isTwoPlayMode;
    }

    public void setTwoPlayMode(boolean twoPlayMode) {
        isTwoPlayMode = twoPlayMode;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


    /**
     * Takes the turn and returns the status that should be recorded on the board.
     * If the current player is ONE, the next player is set to TWO. And, the returned
     * value to be recorded this time is TWO.
     *
     * @return The status (PLAYER_ONE or PLAYER_TWO) that is to be recorded for
     * the cell on the board.
     * <p>
     * This is for story 2.
     */

    public BoardCellStatus takeTurn() {
        if (player == Player.ONE) {
            player = Player.TWO;
            return BoardCellStatus.PLAYER_ONE;
        } else {
            player = Player.ONE;
            return BoardCellStatus.PLAYER_TWO;
        }
    }

    /**
     * Initialises the two dimensional array with empty positions.
     * <p>
     * <p>
     * This is for story 7.
     */
    public void initialiseBoard() {
        gridPositions = new ArrayList<>();
        undoGridPosition = null;
        board = new BoardCellStatus[6][7];
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                board[row][column] = BoardCellStatus.EMPTY;
            }
        }
    }

    /**
     * Gets the position of a node in the GridPane. From the node, the
     * row and column position is extracted. This is then placed into
     * a GridPosition object.
     *
     * @param node The node from the user interface, e.g. a Pane.
     * @return A GridPosition object.
     */
    public GridPosition getGridPositionForNode(Node node) {
        GridPosition position = new GridPosition();
        position.setRow(GridPane.getRowIndex(node));
        position.setColumn(GridPane.getColumnIndex(node));
        return position;
    }

    public void addLog(GridPosition gridPosition) {
        gridPositions.add(gridPosition);
    }

    /**
     * Pairwork.
     *
     * @author Alan Dong(20154987), Neil Zhang(20154987)
     * Story 3
     * @version 1.0
     */
    public void undo() {

        undoGridPosition = gridPositions.get(gridPositions.size() - 1);
        takeTurn();
        gridPositions.remove(gridPositions.size() - 1);
        Boolean find = false;
        for (int row = 0; row <= 5; row++) {
            if (!board[row][undoGridPosition.getColumn()].isEmpty()) {
                find = true;
                board[row][undoGridPosition.getColumn()] = BoardCellStatus.EMPTY;
                break;
            }
        }
        if (!find)
            throw new NullPointerException("There is no step to undo!");
    }

    /**
     * Pairwork.
     *
     * @author Alan Dong(20154987), Neil Zhang(20154987)
     * Story 4
     * @version 1.0
     */
    public void redo() {
        if (undoGridPosition == null) {
            throw new NullPointerException("There is no undo step!");
        }
        Boolean find = false;
        for (int row = 5; row >= 0; row--) {
            if (board[row][undoGridPosition.getColumn()].isEmpty()) {
                find = true;
                BoardCellStatus turn = takeTurn();
                board[row][undoGridPosition.getColumn()] = turn;
                addLog(undoGridPosition);
                undoGridPosition = null;
                break;
            }
        }
        if (!find)
            throw new NullPointerException("The Column has already full, cannot redo!");
    }

    public int findAColumn4Computer() {
        ArrayList<Integer> columns = new ArrayList<>();
        Boolean find = false;
        for (int column = 6; column >= 0; column--) {
            for (int row = 5; row >= 0; row--) {
                //refactor
                if (board[row][column].isEmpty()) {
                    columns.add(column);
                    find = true;
                    break;
                }
            }
        }
        if (!find) {
            throw new NullPointerException("There is no empty place in this board");
        }
        int rand = (int) (Math.random() * (columns.size()));
        return columns.get(rand);
    }


    /**
     * Get the Board form the user interface to check if
     * there is one player win the game.
     * 1. Four consecutive squares arranged vertically.
     * 2. Four consecutive squares arranged diagonally.
     * 3. Four consecutive squares arranged horizontally.
     *
     * @return A Boolean object.
     */

    public Boolean checkVertically() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                if ((j < 3 && board[j][i] != BoardCellStatus.EMPTY && board[j][i] == board[j + 1][i]
                        && board[j][i] == board[j + 2][i] && board[j][i] == board[j + 3][i])) {
                    return true;
                }
            }
        }
        return false;
    }

    public Boolean checkDiagonally() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                if ((i < 4 && j < 3 && board[j][i] != BoardCellStatus.EMPTY && board[j][i] == board[j + 1][i + 1]
                        && board[j][i] == board[j + 2][i + 2] && board[j][i] == board[j + 3][i + 3]) ||
                        (i < 4 && j > 2 && board[j][i] != BoardCellStatus.EMPTY && board[j][i] == board[j - 1][i + 1]
                                && board[j][i] == board[j - 2][i + 2] && board[j][i] == board[j - 3][i + 3])) {
                    return true;
                }
            }
        }
        return false;
    }

    public Boolean checkHorizontally() {

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                if ((i < 4 && board[j][i] != BoardCellStatus.EMPTY && board[j][i] == board[j][i + 1]
                        && board[j][i] == board[j][i + 2] && board[j][i] == board[j][i + 3])) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Pairwork.
     *
     * @author Alan Dong(20154987), Neil Zhang(20154987)
     * Story 10
     * @version 1.0
     */
    public String showAllMoves() {
        StringBuilder moves = new StringBuilder();
        int count = 1;
        ArrayList<Integer> heightOfColumns = new ArrayList<Integer>();
        for (int c = 0; c <= 6; c++) {
            heightOfColumns.add(1);
        }
        for (GridPosition gridPosition : gridPositions) {
            if (count % 2 != 0) {
                moves.append("Step ").append(count).append("- Player 1: Column ").append(gridPosition.getColumn() + 1).append(", Row ").append(heightOfColumns.get(gridPosition.getColumn())).append("\n");
                heightOfColumns.set(gridPosition.getColumn(), heightOfColumns.get(gridPosition.getColumn()) + 1);
                count++;
            } else {
                moves.append("Step ").append(count).append("- Player 2: Column ").append(gridPosition.getColumn() + 1).append(", Row ").append(heightOfColumns.get(gridPosition.getColumn())).append("\n");
                heightOfColumns.set(gridPosition.getColumn(), heightOfColumns.get(gridPosition.getColumn()) + 1);
                count++;
            }
        }
        return moves.toString();
    }

    public boolean equal(BoardCellStatus[][] inBoard) {

        if (this.board == null && inBoard == null) {
            return true;
        } else if (this.board == null || inBoard == null) {
            return false;
        }
        for (int i = 0; i <= 5; i++) {
            for (int j = 0; j <= 6; j++) {
                if (!board[i][j].equals(inBoard[i][j])) {
                    System.out.print(inBoard[i][j] + "\n");
                    System.out.print(board[i][j]);
                    return false;
                }
            }
        }
        return true;
    }


    public int checkEmptyRowNumber(int selectedColumn) {
        int emptyRow = -1;
        // check if there is an empty row
        for (int row = 5; row >= 0; row--) {
            //refactor
            if (getBoard()[row][selectedColumn].isEmpty()) {
                emptyRow = row;
                break;
            }
        }
        if (emptyRow == -1) {
            throw new NullPointerException("There is no empty row in this column");
        }
        return emptyRow;
    }

}
