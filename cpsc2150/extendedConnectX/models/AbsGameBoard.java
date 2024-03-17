package cpsc2150.extendedConnectX.models;

/*
Josh Bailey - jtbly12
Jackson Graham - jgrhm
Will Saunders - wsaunde
Grace Johnson - gracemarieee
 */

/**
 * This abstract class implements IGameBoard and provides an override to the toString() function for use by IGameBoard
 * implementations.
 *
 * @invariant None
 */
public abstract class AbsGameBoard implements IGameBoard
{
    /**
     * Returns a String representation of the current state of the game board
     *
     * @pre None
     *
     * @post self = #self AND
     *          [toString = String representation of the game board in its current state. The columns are numbered
     *          0 to (getNumColumns() - 1) and tokens for each player will be visible on the board in their positions.]
     */
    // @Override
    public String toString()
    {
        String boardString = "";

        // fills boardString with column numbers so columns are numbered when board is printed
        for (int i = 0; i < getNumColumns(); i++)
        {
            boardString += "|" + String.format("%2d", i);
        }
        boardString += "|\n";

        // concatenates current state of board to string, if there is a marker, it is added, ow a space is added
        for (int i = getNumRows() - 1; i >= 0; i--)
        {
            for (int j = 0; j < getNumColumns(); j++)
            {
                boardString += "|" + whatsAtPos(new BoardPosition(i,j)) + " ";
            }
            boardString += "|\n";
        }

        return boardString;
    }
}
