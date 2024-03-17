package cpsc2150.extendedConnectX.models;

/*
Josh Bailey - jtbly12
Jackson Graham - jgrhm
Will Saunders - wsaunde
Grace Johnson - gracemarieee
 */

/**
 * Game board that represents a 2-dimensional game board used to play extendedConnectX.
 * Bottom left corner of game board is of index (0,0)
 *
 * @initialization ensures: Empty gameboard, and gameboard is of size number_of_rows x number_of_columns or smaller.
 *                          number_to_win in a horizontal, vertical, or diagonal sequence are needed in order to win.
 *
 * @defines: number_of_rows: Z
 *           number_of_columns: Z
 *           number_to_win: Z
 *
 * @constraints: MIN_ROW <= number_of_rows <= MAX_ROW AND
 *               MIN_COL <= number_of_columns <= MAX_COL AND
 *               MIN_NUM_TO_WIN <= number_to_win <= MAX_NUM_TO_WIN AND
 *               number_to_win <= number_of_rows AND
 *               number_to_win <= number_of_columns AND
 */
public interface IGameBoard
{
    static final int MIN_NUM_TO_WIN = 3;
    static final int MAX_NUM_TO_WIN = 25;
    static final int MIN_COL = 3;
    static final int MAX_COL = 100;
    static final int MIN_ROW = 3;
    static final int MAX_ROW = 100;

    /**
     * Checks if a specified column is free by checking if the top row of the column is empty (' ')
     *
     * @param c is the int representing the column index
     *
     * @return true IFF the top row of the column does not contain some token (' '), false OW
     *
     * @pre 0 <= c < number_of_columns
     *
     * @post checkIfFree = [true IFF column c is not fully occupied by tokens, false OW as the column is full]
     *          AND self = #self AND number_of_columns = #number_of_columns AND number_of_rows = #number_of_rows
     *          AND number_to_win = #number_to_win
     */
    default public boolean checkIfFree(int c)
    {
        // the column is free if its top slot does not contain a token
        return whatsAtPos(new BoardPosition(getNumRows() - 1, c)) == ' ';
    }

    /**
     * Allows a player to drop a token into a specific column, placing it at the lowest available row
     *
     * @param p the character representing player's token to be dropped in the column
     * @param c the index for the column in which the player wants to drop their token
     *
     * @pre 0 <= c < number_of_columns
     * @pre checkIfFree(c) = true AND p != ' '
     *
     * @post  self = [#self except the player's token is placed in the lowest available row of the given column]
     *          AND number_of_columns = #number_of_columns AND number_of_rows = #number_of_rows
     *          AND number_to_win = #number_to_win
     */
    public void dropToken(char p, int c);

    /**
     * Checks if a player has won the game based on the most recent marker in a specified column
     *
     * @param c the index of the column in which the last token was placed
     *
     * @return true IFF a player wins the game with a vertical, horizontal, or diagonal sequence of number_to_win
     *          char p's, false OW
     *
     * @pre 0 <= c < number_of_columns
     *
     * @post checkForWin = [true IFF the most recent play in the given column results in checkHorizWin OR checkVertWin
     *          OR checkDiagWin returning true, false OW] AND self = #self AND number_of_rows = #number_of_rows
     *          AND number_of_columns = #number_of_columns AND number_to_win = #number_to_win
     */
    default public boolean checkForWin(int c)
    {
        // currentRow is set to top row of column
        int currentRow = getNumRows() - 1;
        // create BoardPosition object, so that whatsAtPos may be called to check if a spot is empty
        BoardPosition currentPos = new BoardPosition(currentRow,c);

        // iterate down the given column from the top row
        // loop breaks if a non-empty spot is found or the bottom bound of the board is reached
        while (currentRow > 0 && whatsAtPos(currentPos) == ' ')
        {
            currentRow--;
            currentPos = new BoardPosition(currentRow,c);
        }
        // if the column was empty, return false
        if (whatsAtPos(currentPos) == ' ')
        {
            return false;
        }
        // if column was not empty, currentPos references the last move made in the column, check if that move resulted
        // in a horizontal win, a vertical win, or a diagonal win. If it resulted in one of these wins, return true,
        // ow return false
        return (checkHorizWin(currentPos, whatsAtPos(currentPos)) || checkVertWin(currentPos, whatsAtPos(currentPos))
            || checkDiagWin(currentPos, whatsAtPos(currentPos)));
    }

    /**
     * Checks if the game board is completely full and the game has ended in a tie
     *
     * @return true IFF the game board is completely full and game has ended in a tie, false ow
     *
     * @pre None
     *
     * @post checkTie = [true if all columns on the board are full of tokens, false OW as there is an available slot]
     *          AND self = #self AND number_of_rows = #number_of_rows AND number_of_columns = #number_of_columns
     *          AND number_to_win = #number_to_win
     */
    default public boolean checkTie()
    {
        // iterate through each column
        for (int i = 0; i < getNumColumns(); i++)
        {
            // if checkIfFree(i), the column i is free and the board is not full, so there is no tie
            if (checkIfFree(i))
            {
                return false;
            }
        }
        // if no columns are free and the board is full, return true, ow return false
        return true;
    }

    /**
     * Checks for a win resulting from a horizontal sequence of number_to_win tokens by a given player starting at a
     * given position.
     *
     * @param pos position of the token that was just dropped
     * @param p player who dropped the most recent token
     *
     * @return true IFF if the last token by player p results in a horizontal streak of number_to_win tokens, false OW
     *
     * @pre p != ' '
     *
     * @post [checkHorizWin = true if the last token by player p results in a horizontal row of number_to_win tokens,
     *          checkHorizWin = false if the recent move by player p did not result in number_to_win tokens in a row
     *          horizontally] AND
     *          self = #self AND number_of_rows = #number_of_rows AND number_of_columns = #number_of_columns AND
     *          number_to_win = #number_to_win
     */
    default public boolean checkHorizWin(BoardPosition pos, char p)
    {
        // numP represents amount of char 'p' found. If it is equal to number_to_win, player p has won
        int numP = 0;
        int currentColumn = pos.getColumn();
        BoardPosition currentPosition = new BoardPosition(pos.getRow(), currentColumn);

        // LEFT of play (including given position):
        // Iterates across row to the left counting number of char p that are in consecutive positions.
        // The loop breaks if left bound is reached, if the char is not p, or if number_to_win p are found consecutively.
        while (currentColumn >= 0 && whatsAtPos(currentPosition) == p && numP != getNumToWin())
        {
            numP++;
            currentColumn--;
            // Runs to ensure BoardPosition object is not created with invalid column that is out of game board's bounds
            if (currentColumn >= 0)
            {
                currentPosition = new BoardPosition(pos.getRow(),currentColumn);
            }
        }
        // If number_to_win consecutive char p were counted horizontally, a win is found and true is returned
        if (numP == getNumToWin())
        {
            return true;
        }

        // RIGHT of play:
        // If a winning sequence was not found, reset to the right of the token position horizontally and continue on
        currentColumn = pos.getColumn() + 1;
        // Runs to ensure BoardPosition object is not created with invalid column that is out of game board's bounds
        if (currentColumn < getNumColumns())
        {
            currentPosition = new BoardPosition(pos.getRow(),currentColumn);
        }

        // Iterates across row to the right counting number of char p that are in consecutive positions.
        // The loop breaks if right bound is reached, if any char is not p, or if number_to_win p are found consecutively.
        while (currentColumn < getNumColumns() && whatsAtPos(currentPosition) == p && numP != getNumToWin())
        {
            numP++;
            currentColumn++;

            // Runs to ensure BoardPosition object is not created with invalid column that is out of game board's bounds
            if (currentColumn < getNumColumns())
            {
                currentPosition = new BoardPosition(pos.getRow(),currentColumn);
            }
        }

        return numP == getNumToWin();
    }

    /**
     * Checks for a win resulting from a vertical sequence of number_to_win tokens by a given player starting at a given
     * position.
     *
     * @param pos position of the token that was just dropped
     * @param p player who dropped the most recent token
     *
     * @return true IFF if the last token by player p results in a vertical streak of number_to_win tokens, false OW
     *
     * @pre p != ' '
     *
     * @post [checkVertWin = true if the last token by player p results in a vertical streak of number_to_win tokens,
     *          checkVertWin = false if the last token placed by player p did not result in number_to_win tokens in a
     *          sequence vertically] AND
     *          self = #self AND number_of_rows = #number_of_rows AND number_of_columns = #number_of_columns AND
     *          number_to_win = #number_to_win
     */
    default public boolean checkVertWin(BoardPosition pos, char p)
    {
        // represents amount of char 'p' found. If it is equal to number_to_win, player p has won
        int numP = 0;
        int currentRow = pos.getRow();
        BoardPosition currentPosition = new BoardPosition(currentRow,pos.getColumn());

        // Iterates down the column counting the number of char p that are in consecutive positions,
        // Loop breaks if the bottom bound is reached, the char is not p, or number_to_win p are counted consecutively
        while (currentRow >= 0 && whatsAtPos(currentPosition) == p && numP != getNumToWin())
        {
            numP++;
            currentRow--;

            // Runs to ensure BoardPosition object is not created with invalid row that is out of game board's bounds
            if (currentRow >= 0)
            {
                currentPosition = new BoardPosition(currentRow,pos.getColumn());
            }
        }

        // If number_to_win consecutive char p were counted vertically, a win is found and true is returned
        return numP == getNumToWin();
    }

    /**
     * Checks for a win resulting from a diagonal sequence of number_to_win tokens by a given player starting at a given
     * position.
     *
     * @param pos position of the token that was just dropped
     * @param p player who dropped the most recent token
     *
     * @return true IFF if the last token by player p results in a diagonal streak of number_to_win tokens, false OW
     *
     * @pre p != ' '
     *
     * @post [checkDiagWin = true if the last token by player p results in a diagonal streak of number_to_win tokens,
     *          checkDiagWin = false if the last token placed by player p did not result in number_to_win tokens in a
     *          sequence on either diagonal] AND
     *          self = #self AND number_of_rows = #number_of_rows AND number_of_columns = #number_of_columns AND
     *          number_to_win = #number_to_win
     */
    default public boolean checkDiagWin(BoardPosition pos, char p)
    {
        // represents amount of char 'p' found. If it is equal to getNumToWin, player p has won
        int numP = 0;
        int currentRow = pos.getRow();
        int currentColumn = pos.getColumn();
        BoardPosition currentPos = new BoardPosition(currentRow, currentColumn);

        boolean validMove = currentRow < getNumRows() && currentColumn < getNumColumns();

        // FORWARD DIAGONAL (/):
        // Iterates up and to the right along the forward diagonal counting the number of consecutive char p's
        // Loop breaks if the upper bound of the rows/columns is reached, if the char found is not p, or if
        // number_to_win char p are counted in consecutive positions
        while (validMove && whatsAtPos(currentPos) == p && numP != getNumToWin())
        {
            numP++;
            currentRow++;
            currentColumn++;

            validMove = currentRow < getNumRows() && currentColumn < getNumColumns();

            // Runs to ensure BoardPosition object is not created with invalid params out of the game board's bounds
            if (validMove)
            {
                currentPos = new BoardPosition(currentRow,currentColumn);
            }
        }

        // Reset values to check the other direction of the forward diagonal
        // This doesn't include pos, so assign currentRow and currentColumn values 1 move down and 1 move left from pos
        currentRow = pos.getRow() - 1;
        currentColumn = pos.getColumn() - 1;

        validMove = currentRow >= 0 && currentColumn >= 0;

        // Runs to ensure BoardPosition object is not created with invalid params out of the game board's bounds
        if (validMove)
        {
            currentPos = new BoardPosition(currentRow,currentColumn);
        }

        // Iterates down and to the left along the forward diagonal counting the number of consecutive char p's
        // Loop breaks if the lower bound of the rows/columns is reached, if the char found is not p, or if
        // number_to_win char p are counted in consecutive positions
        while (validMove && whatsAtPos(currentPos) == p && numP != getNumToWin())
        {
            numP++;
            currentRow--;
            currentColumn--;

            validMove = currentRow >= 0 && currentColumn >= 0;

            // Runs to ensure BoardPosition object is not created with invalid params out of the game board's bounds
            if (validMove)
            {
                currentPos = new BoardPosition(currentRow,currentColumn);
            }
        }

        // If number_to_win char p were counted consecutively on the forward diagonal, go ahead and return true
        if (numP == getNumToWin())
        {
            return true;
        }


        // BACKWARD DIAGONAL (\):
        // If there were not number_to_win char p consecutively on the forward diagonal, reset for the backward diagonal
        numP = 0;
        currentRow = pos.getRow();
        currentColumn = pos.getColumn();
        currentPos = new BoardPosition(currentRow,currentColumn);

        validMove = currentRow < getNumRows() && currentColumn >= 0;

        // Iterates up and to the left along the backward diagonal counting the number of consecutive char p's
        // Loop breaks if the bounds of the rows/columns is reached, if the char found is not p, or if
        // number_to_win char p are counted in consecutive positions
        while (validMove && whatsAtPos(currentPos) == p && numP != getNumToWin())
        {
            numP++;
            currentRow++;
            currentColumn--;

            validMove = currentRow < getNumRows() && currentColumn >= 0;

            // Runs to ensure BoardPosition object is not created with invalid params out of the game board's bounds
            if (validMove)
            {
                currentPos = new BoardPosition(currentRow,currentColumn);
            }
        }

        // Reset values to check the other direction of the backward diagonal
        // This doesn't include pos, so assign currentRow and currentColumn values 1 move down and 1 move right from pos
        currentRow = pos.getRow() - 1;
        currentColumn = pos.getColumn() + 1;

        validMove = currentRow >= 0 && currentColumn < getNumColumns();

        // Runs to ensure BoardPosition object is not created with invalid params out of the game board's bounds
        if (validMove)
        {
            currentPos = new BoardPosition(currentRow,currentColumn);
        }

        // Iterates down and to the right along the backward diagonal counting the number of consecutive char p's
        // Loop breaks if the bounds of the rows/columns is reached, if the char found is not p, or if
        // number_to_win char p are counted in consecutive positions
        while (validMove && whatsAtPos(currentPos) == p && numP != getNumToWin())
        {
            numP++;
            currentRow--;
            currentColumn++;

            validMove = currentRow >= 0 && currentColumn < getNumColumns();

            // Runs to ensure BoardPosition object is not created with invalid params out of the game board's bounds
            if (validMove)
            {
                currentPos = new BoardPosition(currentRow,currentColumn);
            }
        }

        // If number of char p counted consecutively on the backward diagonal, return true. OW return false
        return numP == getNumToWin();
    }

    /**
     * Checks and returns the char marker of the player at a given BoardPosition, or a ' ' if that position is empty
     *
     * @param pos the desired BoardPosition to get the marker of
     *
     * @return the marker of the player at the specified BoardPosition as a char IFF there is one, OW returns ' '
     *
     * @pre None
     *
     * @post [whatsAtPos = the marker at the specified BoardPosition IFF there is one, OW returns ' '] AND
     *          self = #self AND number_of_rows = #number_of_rows AND number_of_columns = #number_of_columns AND
     *          number_to_win = #number_to_win
     *
     */
    public char whatsAtPos(BoardPosition pos);

    /**
     * Checks if a particular player token is at a particular position on board
     *
     * @param pos the BoardPosition of the most recent token placed
     * @param player the char token expected at pos
     *
     * @return true IFF player char is located on board at BoardPosition pos, false OW
     *
     * @pre p != ' '
     *
     * @post [isPlayerAtPos = true IFF player char is located at BoardPosition pos, false OW] AND
     *          self = #self AND number_of_rows = #number_of_rows AND number_of_columns = #number_of_columns AND
     *          number_to_win = #number_to_win
     */
    default public boolean isPlayerAtPos(BoardPosition pos, char player)
    {
        return whatsAtPos(pos) == player;
    }

    /**
     * Returns number of rows in the game board
     *
     * @return number of rows in self as an int
     *
     * @pre None
     *
     * @post getNumRows = number_of_rows AND self = #self AND number_of_rows = #number_of_rows AND
     *          number_of_columns = #number_of_columns AND number_to_win = #number_to_win
     */
    public int getNumRows();

    /**
     * Returns number of columns present in board
     *
     * @return number of columns in self as an int
     *
     * @pre None
     *
     * @post getNumColumns = number_of_columns self = #self AND number_of_rows = #number_of_rows AND
     *           number_of_columns = #number_of_columns AND number_to_win = #number_to_win
     */
    public int getNumColumns();

    /**
     * Returns number of tokens in a row needed to win game
     *
     * @return number of tokens in a row needed to win as an int
     *
     * @pre None
     *
     * @post getNumToWin = number_to_win AND self = #self AND number_of_rows = #number_of_rows AND
     *          number_of_columns = #number_of_columns AND number_to_win = #number_to_win
     */
    public int getNumToWin();
}
