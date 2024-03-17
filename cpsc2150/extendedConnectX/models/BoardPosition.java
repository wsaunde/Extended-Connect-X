package cpsc2150.extendedConnectX.models;

/*
Josh Bailey - jtbly12
Jackson Graham - jgrhm
Will Saunders - wsaunde
Grace Johnson - gracemarieee
 */

/**
 * BoardPosition stores private row and column fields that represents a square on a game board
 *
 * @invariant 0 <= Column < IGameBoard.getNumColumns() AND 0 <= Row < IGameBoard.getNumRows()
 */
public class BoardPosition
{
    private int Row;
    private int Column;

    /**
     * Constructs a BoardPosition object. Sets instance vars to the values passed in via params.
     *
     * @param aRow value to be set as individual cell row
     * @param aColumn value to be set as individual cell column
     *
     * @pre 0 <= aColumn < IGameBoard.getNumColumns() AND 0 <= aRow < IGameBoard.getNumRows()
     *
     * @post Row = aRow AND Column = aColumn
     */
    public BoardPosition(int aRow, int aColumn)
    {
        Row = aRow;
        Column = aColumn;
    }

    /**
     * Returns the row number of the individual cell stored within Row field
     *
     * @return row for this instance of a BoardPosition
     *
     * @pre None
     *
     * @post getRow = Row AND Row = #Row AND Column = #Column
     */
    public int getRow()
    {
        return Row;
    }

    /**
     * Returns the column number of the individual cell stored within Column field
     *
     * @return Column for this instance of a BoardPosition
     *
     * @pre None
     *
     * @post getColumn = Column AND Column = #Column AND Row = #Row
     *
     */
    public int getColumn()
    {
        return Column;
    }

    /**
     * Allows for comparison between instances of object BoardPosition. Two BoardPositions are equal if
     * the Row and Column fields in both instances have the same values
     *
     * @param obj object that is compared to BoardPosition instance making the method call
     * @return true is returned if BoardPosition objects are equal, otherwise false is returned.
     *
     * @pre None
     *
     * @post Column = #Column AND Row = #Row
     * [equals = True if fields Row and Column in compared instances store the same values, equals = False if
     * fields Row and Column in compared instances store different values.]
     */
    @Override
    public boolean equals(Object obj)
    {
        // if obj is of type BoardPosition, compare Column and Row fields of both instances
        if (obj instanceof BoardPosition)
        {
            BoardPosition temp = (BoardPosition) obj;
            return (this.Column == temp.getColumn() && this.Row == temp.getRow());
        }
        // ow return false
        return false;
    }

    /**
     * Allows toString method to be called on a BoardPosition instance and a String in the format "<Row>,<Column>"
     * to be returned.
     *
     * @return a String containing values of calling BoardPosition instance's Row and Column fields
     *
     * @pre None
     *
     * @post Column = #Column AND Row = #Row
     * [toString = String in "<Row>,<Column>" format containing the values of the calling BoardPosition instance's
     * Row and Column fields]
     */
    @Override
    public String toString()
    {
        return Integer.toString(this.Row) + "," + Integer.toString(this.Column);
    }
}