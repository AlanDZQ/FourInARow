package neustt.fourinarow.tests;

import neustt.fourinarow.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import javafx.scene.Node;

import static org.junit.Assert.*;

/**
 * FourInARowLogic Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Apr 14, 2018</pre>
 */
public class FourInARowLogicTest {
    BoardCellStatus[][] testBoard;

    @Before
    public void before() throws Exception {
        testBoard = new BoardCellStatus[6][7];
        for (int row = 0; row < testBoard.length; row++) {
            for (int column = 0; column < testBoard[0].length; column++) {
                testBoard[row][column] = BoardCellStatus.EMPTY;
            }
        }
    }


    @Test
    public void shouldCreateFourInARowLogicCorrectly() {
        FourInARowLogic fourInARowLogic = new FourInARowLogic();

        assertSame(fourInARowLogic.getBoard(), null);
        assertEquals(Player.ONE, fourInARowLogic.getPlayer());
    }

    /**
     * Method: getBoard()
     */
    @Test
    public void shouldGetBoard() {

        FourInARowLogic fourInARowLogic = new FourInARowLogic();

        assertNull(fourInARowLogic.getBoard());

    }

    /**
     * Method: isTwoPlayMode()
     */
    @Test
    public void shouldIsTwoPlayModeInitializeWithFalse() {

        FourInARowLogic fourInARowLogic = new FourInARowLogic();

        assertFalse(fourInARowLogic.isTwoPlayMode());
    }

    /**
     * Method: setTwoPlayMode(boolean twoPlayMode)
     */
    @Test
    public void shouldSetTwoPlayModeCorrectly() {

        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.setTwoPlayMode(true);

        assertTrue(fourInARowLogic.isTwoPlayMode());
    }

    /**
     * Method: getPlayer()
     */
    @Test
    public void shouldGetPlayerCorrectly() {
        FourInARowLogic fourInARowLogic = new FourInARowLogic();

        assertEquals(Player.ONE, fourInARowLogic.getPlayer());
    }

    /**
     * Method: setPlayer(Player player)
     */
    @Test
    public void shouldSetPlayerCorrectly() {
        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.setPlayer(Player.TWO);


        assertEquals(Player.TWO, fourInARowLogic.getPlayer());
    }

    /**
     * Method: takeTurn()
     */
    @Test
    public void shouldTakeTurnWork() {
        FourInARowLogic fourInARowLogic = new FourInARowLogic();

        assertEquals(BoardCellStatus.PLAYER_ONE, fourInARowLogic.takeTurn());
        assertEquals(Player.TWO, fourInARowLogic.getPlayer());
    }

    /**
     * Method: initialiseBoard()
     */
    @Test
    public void shouldInitialiseBoardWork() {
        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();

        for (int row = 0; row < 6; row++) {
            for (int column = 0; column < 7; column++) {
                assertSame(fourInARowLogic.getBoard()[row][column], BoardCellStatus.EMPTY);
            }
        }
    }

    /**
     * Method: getGridPositionForNode(Node node)
     */
    @Test
    public void testGetGridPositionForNode() {

    }

    /**
     * Method: addLog(GridPosition gridPosition)
     */
    @Test
    public void testAddLog() {
        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();
        GridPosition gridPosition = new GridPosition();
        gridPosition.setColumn(0);
        gridPosition.setRow(0);
        fourInARowLogic.addLog(gridPosition);
        assertEquals(fourInARowLogic.getGridPositions().get(0), gridPosition);
    }

    /**
     * Method: undo()
     */
    @Test
    public void shouldUndoWorkCorrectlyWhenCompareWithPreviousSituation() {

        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();

        BoardCellStatus turn = fourInARowLogic.takeTurn();
        GridPosition position = new GridPosition();
        position.setColumn(1);
        fourInARowLogic.getBoard()[5][position.getColumn()] = turn;
        fourInARowLogic.addLog(position);

        fourInARowLogic.undo();
        assertTrue(fourInARowLogic.equal(testBoard));
    }

    @Test
    public void shouldUndoWorkCorrectlyWhenCompareWithNewSituation() {

        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();

        BoardCellStatus turn = fourInARowLogic.takeTurn();
        GridPosition position = new GridPosition();
        position.setColumn(1);
        fourInARowLogic.getBoard()[5][position.getColumn()] = turn;
        testBoard[5][position.getColumn()] = turn;
        fourInARowLogic.addLog(position);

        fourInARowLogic.undo();
        assertFalse(fourInARowLogic.equal(testBoard));
    }

    @Test(expected = NullPointerException.class)
    public void shouldUndoWorkCorrectlyWhenCompareWhenColumnIsEmpty() {

        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();

        BoardCellStatus turn = fourInARowLogic.takeTurn();
        GridPosition position = new GridPosition();
        position.setColumn(0);
        fourInARowLogic.getBoard()[5][position.getColumn()] = turn;
        fourInARowLogic.addLog(position);

        fourInARowLogic.getBoard()[0][0]=BoardCellStatus.EMPTY;
        fourInARowLogic.getBoard()[1][0]=BoardCellStatus.EMPTY;
        fourInARowLogic.getBoard()[2][0]=BoardCellStatus.EMPTY;
        fourInARowLogic.getBoard()[3][0]=BoardCellStatus.EMPTY;
        fourInARowLogic.getBoard()[4][0]=BoardCellStatus.EMPTY;
        fourInARowLogic.getBoard()[5][0]=BoardCellStatus.EMPTY;

        fourInARowLogic.undo();
    }

    /**
     * Method: redo()
     */
    @Test
    public void shouldRedoWorkCorrectlyInNormalSituation() {

        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();

        BoardCellStatus turn = fourInARowLogic.takeTurn();
        GridPosition position = new GridPosition();
        position.setColumn(1);
        fourInARowLogic.getBoard()[5][position.getColumn()] = turn;
        testBoard[5][position.getColumn()] = turn;
        fourInARowLogic.addLog(position);

        fourInARowLogic.undo();
        fourInARowLogic.redo();

        assertTrue(fourInARowLogic.equal(testBoard));
    }

    @Test(expected = NullPointerException.class)
    public void shouldRedoWorkCorrectlyWhenUndoIsNull() {

        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();

        BoardCellStatus turn = fourInARowLogic.takeTurn();
        GridPosition position = new GridPosition();
        position.setColumn(1);
        fourInARowLogic.getBoard()[5][position.getColumn()] = turn;
        fourInARowLogic.addLog(position);

        fourInARowLogic.redo();
    }

    @Test(expected = NullPointerException.class)
    public void shouldRedoWorkCorrectlyWhenColumnIsFull() {

        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();

        BoardCellStatus turn = fourInARowLogic.takeTurn();
        GridPosition position = new GridPosition();
        position.setColumn(1);
        fourInARowLogic.getBoard()[5][position.getColumn()] = turn;
        fourInARowLogic.addLog(position);
        fourInARowLogic.undo();
        fourInARowLogic.getBoard()[0][1] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[1][1] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[2][1] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[3][1] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[4][1] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[5][1] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.redo();
    }

    /**
     * Method: findAColumn4Computer()
     */
    @Test
    public void shouldFindAColumn4ComputerWork() {
        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();
        assertTrue(fourInARowLogic.findAColumn4Computer() >= 0 && fourInARowLogic.findAColumn4Computer() <= 6);
    }

    @Test(expected = NullPointerException.class)
    public void shouldFindAColumn4ComputerThrowExceptionWhenTheBoardIsFull() {
        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();
        for (int column = 6; column >= 0; column--) {
            for (int row = 5; row >= 0; row--) {
                fourInARowLogic.getBoard()[row][column] = BoardCellStatus.PLAYER_ONE;
            }
        }
        fourInARowLogic.findAColumn4Computer();
    }

    /**
     * Method: checkVertically()
     */
    @Test
    public void shouldNotWinWhenBoardIsEmptyWhenCheckVertically() {
        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();
        assertFalse(fourInARowLogic.checkVertically());
    }

    @Test
    public void shouldWinWhenFourInARowVertically() {
        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();
        fourInARowLogic.getBoard()[0][0] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[1][0] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[2][0] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[3][0] = BoardCellStatus.PLAYER_ONE;
        assertTrue(fourInARowLogic.checkVertically());
    }

    @Test
    public void shouldWinWhenFiveInARowVertically() {
        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();
        fourInARowLogic.getBoard()[0][0] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[1][0] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[2][0] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[3][0] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[4][0] = BoardCellStatus.PLAYER_ONE;
        assertTrue(fourInARowLogic.checkVertically());
    }

    @Test
    public void shouldNotWinWhenThreeInARowVertically() {
        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();
        fourInARowLogic.getBoard()[0][0] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[1][0] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[2][0] = BoardCellStatus.PLAYER_ONE;
        assertFalse(fourInARowLogic.checkVertically());
    }

    @Test
    public void shouldNotWinWhenDiscreteFourInARowVertically() {
        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();
        fourInARowLogic.getBoard()[0][0] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[1][0] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[2][0] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[4][0] = BoardCellStatus.PLAYER_ONE;
        assertFalse(fourInARowLogic.checkVertically());
    }

    /**
     * Method: checkDiagonally()
     */
    @Test
    public void shouldNotWinWhenBoardIsEmptyWhenCheckDiagonally() {
        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();
        assertFalse(fourInARowLogic.checkDiagonally());
    }

    @Test
    public void shouldWinWhenFourInARowFromLeftHighToRightLowDiagonally() {
        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();
        fourInARowLogic.getBoard()[0][0] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[1][1] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[2][2] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[3][3] = BoardCellStatus.PLAYER_ONE;
        assertTrue(fourInARowLogic.checkDiagonally());
    }

    @Test
    public void shouldWinWhenFiveInARowFromLeftHighToRightLowDiagonally() {
        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();
        fourInARowLogic.getBoard()[0][0] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[1][1] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[2][2] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[3][3] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[4][4] = BoardCellStatus.PLAYER_ONE;
        assertTrue(fourInARowLogic.checkDiagonally());
    }

    @Test
    public void shouldNotWinWhenThreeInARowFromLeftHighToRightLowDiagonally() {
        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();
        fourInARowLogic.getBoard()[0][0] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[1][1] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[2][2] = BoardCellStatus.PLAYER_ONE;
        assertFalse(fourInARowLogic.checkDiagonally());
    }

    @Test
    public void shouldNotWinWhenDiscreteFourInARowFromLeftHighToRightLowDiagonally() {
        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();
        fourInARowLogic.getBoard()[0][0] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[1][1] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[2][2] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[4][4] = BoardCellStatus.PLAYER_ONE;
        assertFalse(fourInARowLogic.checkDiagonally());
    }

    @Test
    public void shouldNotWinWhenDiscreteFourInARowFromLeftHighToRightLowDiagonally2() {
        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();
        fourInARowLogic.getBoard()[0][0] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[1][1] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[3][3] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[4][4] = BoardCellStatus.PLAYER_ONE;
        assertFalse(fourInARowLogic.checkDiagonally());
    }

    @Test
    public void shouldWinWhenFourInARowFromLeftLowToRightHighDiagonally() {
        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();
        fourInARowLogic.getBoard()[4][0] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[3][1] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[2][2] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[1][3] = BoardCellStatus.PLAYER_ONE;
        assertTrue(fourInARowLogic.checkDiagonally());
    }

    @Test
    public void shouldWinWhenFiveInARowFromLeftLowToRightHighDiagonally() {
        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();
        fourInARowLogic.getBoard()[4][0] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[3][1] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[2][2] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[1][3] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[0][4] = BoardCellStatus.PLAYER_ONE;
        assertTrue(fourInARowLogic.checkDiagonally());
    }

    @Test
    public void shouldNotWinWhenThreeInARowFromLeftLowToRightHighDiagonally() {
        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();
        fourInARowLogic.getBoard()[4][0] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[3][1] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[2][2] = BoardCellStatus.PLAYER_ONE;
        assertFalse(fourInARowLogic.checkDiagonally());
    }

    @Test
    public void shouldNotWinWhenDiscreteFourInARowFromLeftLowToRightHighDiagonally() {
        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();
        fourInARowLogic.getBoard()[4][0] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[3][1] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[2][2] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[0][4] = BoardCellStatus.PLAYER_ONE;
        assertFalse(fourInARowLogic.checkDiagonally());
    }

    /**
     * Method: checkHorizontally()
     */
    @Test
    public void shouldNotWinWhenBoardIsEmptyWhenCheckHorizontally() {
        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();
        assertFalse(fourInARowLogic.checkHorizontally());
    }

    @Test
    public void shouldWinWhenFourInARowHorizontally() {
        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();
        fourInARowLogic.getBoard()[0][0] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[0][1] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[0][2] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[0][3] = BoardCellStatus.PLAYER_ONE;
        assertTrue(fourInARowLogic.checkHorizontally());
    }

    @Test
    public void shouldWinWhenFiveInARowHorizontally() {
        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();
        fourInARowLogic.getBoard()[0][0] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[0][1] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[0][2] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[0][3] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[0][4] = BoardCellStatus.PLAYER_ONE;
        assertTrue(fourInARowLogic.checkHorizontally());
    }

    @Test
    public void shouldNotWinWhenThreeInARowHorizontally() {
        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();
        fourInARowLogic.getBoard()[0][0] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[0][1] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[0][2] = BoardCellStatus.PLAYER_ONE;
        assertFalse(fourInARowLogic.checkHorizontally());
    }

    @Test
    public void shouldNotWinWhenDiscreteFourInARowHorizontally() {
        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();
        fourInARowLogic.getBoard()[0][0] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[0][1] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[0][2] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[0][4] = BoardCellStatus.PLAYER_ONE;
        assertFalse(fourInARowLogic.checkHorizontally());
    }

    /**
     * Method: showAllMoves()
     */
    @Test
    public void shouldShowAllMovesByPlayer1() {

        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();

        BoardCellStatus turn = fourInARowLogic.takeTurn();
        GridPosition position = new GridPosition();
        position.setColumn(1);
        position.setRow(1);
        fourInARowLogic.getBoard()[5][position.getColumn()] = turn;
        fourInARowLogic.addLog(position);

        assertEquals("Step 1- Player 1: Column 2, Row 1\n", fourInARowLogic.showAllMoves());
    }

    @Test
    public void shouldShowAllMovesByPlayer2() {

        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();

        BoardCellStatus turn = fourInARowLogic.takeTurn();
        GridPosition position = new GridPosition();
        position.setColumn(1);
        fourInARowLogic.getBoard()[5][position.getColumn()] = turn;
        fourInARowLogic.addLog(position);
        fourInARowLogic.getBoard()[5][position.getColumn()] = turn;
        fourInARowLogic.addLog(position);
        assertEquals("Step 1- Player 1: Column 2, Row 1\nStep 2- Player 2: Column 2, Row 2\n", fourInARowLogic.showAllMoves());
    }

    /**
     * Method: checkEmptyRowNumber()
     */
    @Test
    public void shouldCheckEmptyRowNumberReturnTheFirstEmptyRowNumberInSelectedColumn() {
        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();

        assertEquals(5, fourInARowLogic.checkEmptyRowNumber(0));

    }

    @Test(expected = NullPointerException.class)
    public void shouldCheckEmptyRowNumberThrowTheExceptionWhenTheBoardIsFull() {
        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();
        fourInARowLogic.getBoard()[0][0] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[1][0] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[2][0] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[3][0] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[4][0] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.getBoard()[5][0] = BoardCellStatus.PLAYER_ONE;
        fourInARowLogic.checkEmptyRowNumber(0);

    }

    /**
     * Method: equal()
     */
    @Test
    public void shouldEqualWithTwoNullBoard() {
        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        testBoard=null;
        assertTrue(fourInARowLogic.equal(testBoard));
    }

    @Test
    public void shouldNotEqualWithOneNullBoard() {
        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        fourInARowLogic.initialiseBoard();
        testBoard = null;
        assertFalse(fourInARowLogic.equal(testBoard));
    }

    @Test
    public void shouldNotEqualWithOneNullBoard2() {
        FourInARowLogic fourInARowLogic = new FourInARowLogic();
        assertFalse(fourInARowLogic.equal(testBoard));
    }

}
