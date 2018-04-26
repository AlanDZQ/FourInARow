package neustt.fourinarow.tests;

import neustt.fourinarow.BoardCellStatus;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardCellStatusTest {

    @Test
    public void shouldGetColorWithPLAYER_ONE() {
        BoardCellStatus boardCellStatus = BoardCellStatus.PLAYER_ONE;
        assertEquals(boardCellStatus.getColor(), "#00f");
    }

    @Test
    public void shouldGetColorWithPLAYER_TWO() {
        BoardCellStatus boardCellStatus = BoardCellStatus.PLAYER_TWO;
        assertEquals(boardCellStatus.getColor(), "#f00");
    }

    @Test
    public void shouldGetColorWithEMPTY() {
        BoardCellStatus boardCellStatus = BoardCellStatus.EMPTY;
        assertEquals(boardCellStatus.getColor(), "#eaeaea");
    }

    @Test
    public void isEmpty() {
        BoardCellStatus boardCellStatus = BoardCellStatus.EMPTY;
        assertTrue(boardCellStatus.isEmpty());
    }
}
