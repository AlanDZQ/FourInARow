package neustt.fourinarow;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the main user interface. This will respond to the
 * user interface events, e.g. button presses.
 *
 * @author Neil Taylor (nst@aber.ac.uk)
 */
public class FourInARowController implements Initializable {

//    /**
//     * Represents a board that is a 2-dimensional array
//     * of BoardCellStatus items.
//     */
//    private BoardCellStatus[][] board = null;

    private FourInARowLogic fourInARowLogic = new FourInARowLogic();

    /**
     * The current player for the game.
     */
//    private Player player = Player.ONE;

    /**
     * A link to the text area that is shown at the bottom of the main window.
     */
    @FXML
    private TextArea messageArea;

    /**
     * Link to the undo menu item.
     */
    @FXML
    private MenuItem undoMenuItem;

    /**
     * Link to the redo menu item.
     */
    @FXML
    private MenuItem redoMenuItem;

    /**
     * A link to the grid that shows the different squares that represent
     * the game.
     */
    @FXML
    private GridPane boardGrid;


    /**
     * Displays the current state of the board. This will redraw the
     * entire board on the screen.
     */
    private void displayBoard() {
        for (int row = 0; row < fourInARowLogic.getBoard().length; row++) {
            for (int column = 0; column < fourInARowLogic.getBoard()[0].length; column++) {

                Node node = getNodeByRowColumnIndex(row + 1, column);

                //refactor
                String colour = fourInARowLogic.getBoard()[row][column].getColor();

                if (node instanceof Pane) {
                    node.setStyle("-fx-background-color: " + colour);
                } else {
                    System.out.println("Found something unexpected for " + (row + 1) + " - " + column);
                    System.out.println(node);
                }

            }
        }
    }

    /**
     * Handles a button press from one of the buttons at the top of the
     * screen. When the button is pressed, it checks if there is a space in
     * the same column as the button. If there is a space, the space is
     * set to the value for the current player. The board is then redisplayed.
     * <p>
     * This fuction has included story 5.
     *
     * @param event The event that specifies which button was pressed.
     */
    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (fourInARowLogic.isTwoPlayMode()) {
            twoPlayerGame(event);
        } else {
            onePlayerGame(event);
        }
    }

    public void twoPlayerGame(ActionEvent event) {
        int selectedColumn = 0;
        // determine which column the button is in.
        GridPosition position = fourInARowLogic.getGridPositionForNode((Node) event.getSource());
        selectedColumn = position.getColumn();
        messageArea.setText("Column " + (selectedColumn + 1));

        if (fourInARowLogic.getBoard() != null) {
            if (fourInARowLogic.checkEmptyRowNumber(selectedColumn) != -1) {
                // there is an empty row - update the turn and redisplay the board
                BoardCellStatus turn = fourInARowLogic.takeTurn();
                fourInARowLogic.getBoard()[fourInARowLogic.checkEmptyRowNumber(selectedColumn)][selectedColumn] = turn;
                fourInARowLogic.addLog(position);
                displayBoard();
                if (fourInARowLogic.checkVertically() || fourInARowLogic.checkDiagonally() || fourInARowLogic.checkHorizontally()) {
                    messageArea.setText(turn.name() + " win the game!\n" + fourInARowLogic.showAllMoves());
                    endGame();
                }
            } else {
                messageArea.setText("No available positions in this column.");
            }
        }
    }

    public void onePlayerGame(ActionEvent event) {
        twoPlayerGame(event);
        if (fourInARowLogic.checkVertically() || fourInARowLogic.checkDiagonally() || fourInARowLogic.checkHorizontally()) {
            return;
        }
        int computerColumn = fourInARowLogic.findAColumn4Computer();
        int computerRow;
        for (int row = 5; row >= 0; row--) {
            //refactor
            if (fourInARowLogic.getBoard()[row][computerColumn].isEmpty()) {
                computerRow = row;
                BoardCellStatus computerTurn = fourInARowLogic.takeTurn();
                fourInARowLogic.getBoard()[computerRow][computerColumn] = computerTurn;
                GridPosition gridPosition = new GridPosition();
                gridPosition.setColumn(computerColumn);
                fourInARowLogic.addLog(gridPosition);

                displayBoard();
                //story1 judgment for win player
                if (fourInARowLogic.checkVertically() || fourInARowLogic.checkDiagonally() || fourInARowLogic.checkHorizontally()) {
                    messageArea.setText("Computer" + " win the game! \n" + fourInARowLogic.showAllMoves());
                    System.out.println(computerTurn + " win the game!");
                    endGame();
                    return;
                }
                break;
            }
        }
    }

    /**
     * Given a row and column pair, this method identifies the Node that is held within the
     * grid pane that shows the board. A node is a visual component. For this application,
     * it is a Button if it is in the top column. Otherwise, this is a Pane, which is rectangular
     * block that we are setting to a colour to represent a move played.
     *
     * @param row    The row.
     * @param column The column.
     * @return If a node is found it is returned. Otherwise, null is returned.
     */
    public Node getNodeByRowColumnIndex(int row, int column) {
        Node result = null;
        ObservableList<Node> children = boardGrid.getChildren();

        for (Node node : children) {
            //refactor
            GridPosition position = fourInARowLogic.getGridPositionForNode(node);

            //refactor
            if (position.equals(row, column)) {
                result = node;
                break;
            }
        }
        return result;
    }

    /**
     * Shows an About dialog.
     *
     * @param event The event that started the action.
     */
    @FXML
    private void handleAboutDialogAction(ActionEvent event) {
        FourInARowDialogController dialogController = new FourInARowDialogController();
        dialogController.showDialog(boardGrid.getScene().getWindow());
    }

    /**
     * Starts a game with two human players. This will initialise an empty board and
     * display that to the screen. The default situation is that the top level buttons
     * are not enabled. This method will enable them so that the players can select
     * which column to make a move with.
     * <p>
     * this is story 8
     *
     * @param event The event that sarted the action.
     */
    @FXML
    private void handleStartTwoPlayerGameAction(ActionEvent event) {
        fourInARowLogic.setTwoPlayMode(true);
        fourInARowLogic.initialiseBoard();
        displayBoard();
        fourInARowLogic.setPlayer(Player.ONE);

        for (int column = 0; column < 7; column++) {
            Node node = getNodeByRowColumnIndex(0, column);
            if (node instanceof Button) {
                ((Button) node).setDisable(false);
            } else {
                System.err.println("Unexpected item found:" + node);
            }
        }

        messageArea.setText("Starting a two player game.");
    }

    /**
     * A place where you could add support for a single person game. You would use this
     * to start a game where one of the players is your computer player.
     *
     * @param event The event that generated this action.
     *              <p>
     *              this is story 8
     */
    @FXML
    private void handleStartOnePlayerGameAction(ActionEvent event) {
        fourInARowLogic.setTwoPlayMode(false);
        fourInARowLogic.initialiseBoard();
        displayBoard();
        fourInARowLogic.setPlayer(Player.ONE);

        for (int column = 0; column < 7; column++) {
            Node node = getNodeByRowColumnIndex(0, column);
            if (node instanceof Button) {
                ((Button) node).setDisable(false);
            } else {
                System.err.println("Unexpected item found : " + node);
            }
        }
        messageArea.setText("Starting a single player game.");

    }

    /**
     * Quits the application by calling System.exit(0);
     *
     * @param event The event that specifies which user interface item generated
     *              the action. In this case, it is a menu item.
     */
    @FXML
    private void handleQuitAction(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Responds to the Undo action being called from the menu.
     */
    @FXML
    private void handleUndoAction(ActionEvent event) {
        fourInARowLogic.undo();
        displayBoard();
    }

    /**
     * Responds to the Redo action being called from the menu.
     */
    @FXML
    private void handleRedoAction(ActionEvent event) {
        fourInARowLogic.redo();
        displayBoard();

    }

    /**
     * Required as part of the Initializable interface. Not used in this class.
     *
     * @param url Not used.
     * @param rb  Not used.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private void endGame() {

        for (int column = 0; column < 7; column++) {
            Node node = getNodeByRowColumnIndex(0, column);
            if (node instanceof Button) {
                ((Button) node).setDisable(true);
            } else {
                System.err.println("Unexpected item found: " + node);
            }
        }

    }
}
