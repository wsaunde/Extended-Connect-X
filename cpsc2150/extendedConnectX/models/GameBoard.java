package cpsc2150.extendedConnectX.models;

/*
Josh Bailey - jtbly12
Jackson Graham - jgrhm
Will Saunders - wsaunde
Grace Johnson - gracemarieee
 */

/**
 * @invariant MIN_COL <= NUM_COLUMNS <= MAX_COL AND MIN_ROW <= NUM_ROWS <= MAX_ROW AND
 *            MIN_NUM_TO_WIN <= NUM_TO_WIN <= MAX_NUM_TO_WIN AND
 *            NUM_TO_WIN <= NUM_COLUMNS AND NUM_TO_WIN <= NUM_ROWS
 *
 * @invariant [game board 2-dimensional char array is of size board[NUM_ROWS][NUM_COLUMNS]]
 *
 * @correspondence number_of_rows = NUM_ROWS
 * @correspondence number_of_columns = NUM_COLUMNS
 * @correspondence number_to_win = NUM_TO_WIN
 * @correspondence self = board[NUM_ROWS][NUM_COLUMNS]
 *
 */
public class GameBoard extends AbsGameBoard
{
    private final int NUM_ROWS;
    private final int NUM_COLUMNS;
    private final int NUM_TO_WIN;
    private char[][] board;

    /**
     * Constructs a GameBoard object
     *
     * @param inRow the number of rows the board should have as an int
     * @param inColumn the number of columns the board should have as an int
     * @param inNumToWin the number of consecutive tokens that should be required to win as an int
     *
     * @pre MIN_ROW <= inRow <= MAX_ROW AND MIN_COL <= inColumn <= MAX_COL AND
     *          MIN_NUM_TO_WIN <= inNumToWin <= MAX_NUM_TO_WIN AND inNumToWin <= inRow AND inNumToWin <= inColumn
     *
     * @post NUM_ROWS = inRow AND NUM_COLUMNS = inColumn AND NUM_TO_WIN = inNumToWin AND
     *          self = new char[NUM_ROWS][NUM_COLUMNS]
     */
    public GameBoard(int inRow, int inColumn, int inNumToWin)
    {
        NUM_ROWS = inRow;
        NUM_COLUMNS = inColumn;
        NUM_TO_WIN = inNumToWin;
        board = new char[NUM_ROWS][NUM_COLUMNS];
    }

    public int getNumRows()
    {
        return NUM_ROWS;
    }

    public int getNumColumns()
    {                               
        return NUM_COLUMNS;
    }

    public int getNumToWin()
    {
        return NUM_TO_WIN;
    }

    public void dropToken(char p, int c)
    {
        int index = 0;

        // start at bottom row of column, iterate up the column until there is an empty space
        while (board[index][c] != 0)
        {
            index++;
        }

        // place token in the lowest available move location in column
        board[index][c] = p;
    }

    public char whatsAtPos(BoardPosition pos)
    {
        // if at board position pos there is a marker, return the respective marker
        if (board[pos.getRow()][pos.getColumn()] != 0)
        {
            return board[pos.getRow()][pos.getColumn()];
        }

        // ow return space
        return ' ';
    }
}
