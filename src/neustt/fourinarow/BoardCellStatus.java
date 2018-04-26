package neustt.fourinarow;

/**
 * Represents the status for a cell in the board.
 *
 * @author Neil Taylor (nst@aber.ac.uk)
 */
public enum BoardCellStatus {
    EMPTY,
    PLAYER_ONE,
    PLAYER_TWO;

    public String getColor() {
        String colour = "#000";
        switch (this) {
            case EMPTY:
                colour = "#eaeaea";
                break;

            case PLAYER_ONE:
                colour = "#00f";
                break;

            case PLAYER_TWO:
                colour = "#f00";
                break;
        }
        return colour;
    }

    public Boolean isEmpty() {
        return this == BoardCellStatus.EMPTY;
    }
}

