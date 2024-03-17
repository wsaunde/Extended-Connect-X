package cpsc2150.extendedConnectX.tests;

/*
Josh Bailey - jtbly12
Jackson Graham - jgrhm
Will Saunders - wsaunde
Grace Johnson - gracemarieee
*/

import org.junit.Test;
import static org.junit.Assert.*;
import cpsc2150.extendedConnectX.models.*;

public class TestGameBoardMem {

    private IGameBoard IGameBoardFactory(int inRow, int inColumn, int inNumToWin) {
        return new GameBoardMem(inRow, inColumn, inNumToWin);
    }

    private String boardToString(char[][] expected, int numRows, int numColumns) {
        String boardString = "";

        for (int i = 0; i < numColumns; i++) {
            if (i < 10)
                boardString += "| " + Integer.toString(i);
            else
                boardString += "|" + Integer.toString(i);
        }
        boardString += "|\n";

        for (int row = numRows - 1; row >= 0; row--) {
            for (int column = 0; column < numColumns; column++) {
                if (expected[row][column] == 0)
                    boardString += "|" + "  ";
                else
                    boardString += "|" + expected[row][column] + " ";
            }
            boardString += "|\n";
        }
        return boardString;
    }

    // Tests the constructor is able to correctly initialize an empty board to the minimum dimensions
    @Test
    public void testConstructor_3_3_3_min_dimensions()
    {
        String expectedString;
        char[][] expectedBoard = new char[3][3];

        IGameBoard actualBoard = IGameBoardFactory(3,3,3);

        String actualString = actualBoard.toString();
        expectedString = boardToString(expectedBoard,3,3);

        assertEquals(expectedString,actualString);
    }

    // Tests the constructor is able to correctly initialize an empty board to the maximum dimensions
    @Test
    public void testConstructor_100_100_25_max_dimensions()
    {
        String expectedString;
        char[][] expectedBoard = new char[100][100];

        IGameBoard actualBoard = IGameBoardFactory(100,100,25);

        String actualString = actualBoard.toString();
        expectedString = boardToString(expectedBoard,100,100);

        assertEquals(expectedString,actualString);
    }
    // Tests the constructor is able to correctly initialize an empty board of differing row and col dimensions
    @Test
    public void testConstructor_9_7_5_diff_dimensions()
    {
        String expectedString;
        char[][] expectedBoard = new char[9][7];

        IGameBoard actualBoard = IGameBoardFactory(9,7,5);

        String actualString = actualBoard.toString();
        expectedString = boardToString(expectedBoard,9,7);

        assertEquals(expectedString,actualString);
    }
    // Tests that checkIfFree() recognizes empty cell in top left even with other cols full
    @Test
    public void testCheckIFFree_board_full_except_left_column()
    {
        int input1 = 0;
        char inputToken = 'a';

        String expectedString;
        char[][] expectedBoard = new char[5][5];

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        for (int row = 0; row < 5; row++)
        {
            for (int column = 0; column < 5; column++)
            {
                if (!(row == 4 && column == 0))
                {
                    expectedBoard[row][column] = inputToken;
                    actualBoard.dropToken(inputToken, column);
                    inputToken++;
                }
            }
        }
        assertTrue(actualBoard.checkIfFree(input1));

        String actualString = actualBoard.toString();
        expectedString = boardToString(expectedBoard,5,5);
        assertEquals(expectedString, actualString);
    }
    // Tests that checkIfFree() correctly recognizes a column is full with other cols all empty
    @Test
    public void testCheckIfFree_column_full_false()
    {
        int input1 = 4;
        char inputToken = 'a';

        String expectedString;
        char[][] expectedBoard = new char[5][5];

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        for (int row = 0; row < 5; row++)
        {
            expectedBoard[row][4] = inputToken;
            actualBoard.dropToken(inputToken,4);
            inputToken++;
        }
        assertFalse(actualBoard.checkIfFree(input1));

        String actualString = actualBoard.toString();
        expectedString = boardToString(expectedBoard,5,5);
        assertEquals(expectedString, actualString);
    }
    // Tests that checkIfFree() correctly recognizes empty cell in top right even with other cols full
    @Test
    public void testCheckIfFree_board_full_except_right_column()
    {
        int input1 = 4;
        char inputToken = 'a';

        String expectedString;
        char[][] expectedBoard = new char[5][5];

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        for (int row = 0; row < 5; row++)
        {
            for (int column = 0; column < 5; column++)
            {
                if (!(row == 4 && column == 4))
                {
                    expectedBoard[row][column] = inputToken;
                    actualBoard.dropToken(inputToken, column);
                    inputToken++;
                }
            }
        }
        assertTrue(actualBoard.checkIfFree(input1));

        String actualString = actualBoard.toString();
        expectedString = boardToString(expectedBoard,5,5);
        assertEquals(expectedString, actualString);
    }
    // Tests that checkHorizWin() works properly in top row of board
    @Test
    public void testCheckHorizWin_last_placed_top_left_true()
    {
        BoardPosition input = new BoardPosition(4,0);
        char inputToken = 'a';

        String expectedString;
        char[][] expectedBoard = new char[5][5];

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        for (int row = 0; row < 5; row++)
        {
            for (int col = 0; col < 4; col++)
            {
                if(row != 4)
                {
                    expectedBoard[row][col] = inputToken;
                    actualBoard.dropToken(inputToken,col);
                    inputToken++;
                }
                else
                {
                    actualBoard.dropToken('x',col);
                    expectedBoard[row][col] = 'x';
                }
            }
        }
        assertTrue(actualBoard.checkHorizWin(input, 'x'));

        String actualString = actualBoard.toString();
        expectedString = boardToString(expectedBoard,5,5);
        assertEquals(expectedString, actualString);
    }
    // Tests that checkHorizWin() works properly when token is placed in middle of string of characters
    @Test
    public void testCheckHorizWin_last_placed_middle_true()
    {
        BoardPosition input = new BoardPosition(2,2);
        char inputToken = 'a';

        String expectedString;
        char[][] expectedBoard = new char[5][5];

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 4; col++)
            {
                if (row != 2)
                {
                    expectedBoard[row][col] = inputToken;
                    actualBoard.dropToken(inputToken,col);
                    inputToken++;
                }
                else
                {
                    actualBoard.dropToken('x',col);
                    expectedBoard[row][col] = 'x';
                }
            }
        }
        assertTrue(actualBoard.checkHorizWin(input, 'x'));

        String actualString = actualBoard.toString();
        expectedString = boardToString(expectedBoard,5,5);
        assertEquals(expectedString, actualString);
    }
    // Tests that checkHorizWin() can count to the left and stop when it recognizes an 'o' character
    @Test
    public void testCheckHorizWin_last_placed_bottom_right_false()
    {
        BoardPosition input = new BoardPosition(0,4);

        String expectedString;
        char[][] expectedBoard = new char[5][5];

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        actualBoard.dropToken('x',0);
        actualBoard.dropToken('o',1);
        actualBoard.dropToken('x',2);
        actualBoard.dropToken('x',3);
        actualBoard.dropToken('x',4);

        expectedBoard[0][0] = 'x';
        expectedBoard[0][1] = 'o';
        expectedBoard[0][2] = 'x';
        expectedBoard[0][3] = 'x';
        expectedBoard[0][4] = 'x';

        assertFalse(actualBoard.checkHorizWin(input, 'x'));

        String actualString = actualBoard.toString();
        expectedString = boardToString(expectedBoard,5,5);
        assertEquals(expectedString, actualString);
    }
    // Tests that checkHorizWin() can count to the right and stop when it recognizes the end of the board
    @Test
    public void testCheckHorizWin_last_placed_left_false()
    {
        BoardPosition input = new BoardPosition(1,2);
        char inputToken = 'a';

        String expectedString;
        char[][] expectedBoard = new char[5][5];

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        for (int row = 0; row < 2; row++)
        {
            for (int column = 0; column < 5; column++)
            {
                if (row == 1 && column > 1)
                {
                    actualBoard.dropToken('x',column);
                    expectedBoard[row][column] = 'x';
                }
                else
                {
                    actualBoard.dropToken(inputToken,column);
                    expectedBoard[row][column] = inputToken;
                    inputToken++;
                }
            }
        }
        assertFalse(actualBoard.checkHorizWin(input, 'x'));

        String actualString = actualBoard.toString();
        expectedString = boardToString(expectedBoard,5,5);
        assertEquals(expectedString, actualString);
    }
    // Tests that checkVertWin() works properly with last token placed in top row
    @Test
    public void testCheckVertWin_middle_last_placed_in_top_row_true()
    {
        BoardPosition input = new BoardPosition(4,2);

        String expectedString;
        char[][] expectedBoard = new char[5][5];

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        actualBoard.dropToken('o', 2);
        actualBoard.dropToken('x', 2);
        actualBoard.dropToken('x', 2);
        actualBoard.dropToken('x', 2);
        actualBoard.dropToken('x', 2);

        expectedBoard[0][2] = 'o';
        expectedBoard[1][2] = 'x';
        expectedBoard[2][2] = 'x';
        expectedBoard[3][2] = 'x';
        expectedBoard[4][2] = 'x';

        assertTrue(actualBoard.checkVertWin(input, 'x'));

        String actualString = actualBoard.toString();
        expectedString = boardToString(expectedBoard,5,5);
        assertEquals(expectedString, actualString);
    }
    // Tests that checkVertWin() works properly with common case of number to win x's in a row vertically
    @Test
    public void testCheckVertWin_left_last_not_in_top_row_true()
    {
        BoardPosition input = new BoardPosition(3,0);

        String expectedString;
        char[][] expectedBoard = new char[5][5];

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        actualBoard.dropToken('x', 0);
        actualBoard.dropToken('x', 0);
        actualBoard.dropToken('x', 0);
        actualBoard.dropToken('x', 0);

        expectedBoard[0][0] = 'x';
        expectedBoard[1][0] = 'x';
        expectedBoard[2][0] = 'x';
        expectedBoard[3][0] = 'x';

        assertTrue(actualBoard.checkVertWin(input, 'x'));

        String actualString = actualBoard.toString();
        expectedString = boardToString(expectedBoard,5,5);
        assertEquals(expectedString, actualString);
    }
    // Tests that checkVertWin() counts down from last placed token properly/stops when it reaches bottom of board
    @Test
    public void testCheckVertWin_right_false()
    {
        BoardPosition input = new BoardPosition(2,4);

        String expectedString;
        char[][] expectedBoard = new char[5][5];

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        actualBoard.dropToken('x', 4);
        actualBoard.dropToken('x', 4);
        actualBoard.dropToken('x', 4);

        expectedBoard[0][4] = 'x';
        expectedBoard[1][4] = 'x';
        expectedBoard[2][4] = 'x';

        assertFalse(actualBoard.checkVertWin(input, 'x'));

        String actualString = actualBoard.toString();
        expectedString = boardToString(expectedBoard,5,5);
        assertEquals(expectedString, actualString);
    }
    // Tests that checkVertWin() counts down from last placed token properly/stops when it reaches an 'o'
    @Test
    public void testCheckVertWin_left_last_placed_in_top_row_false()
    {
        BoardPosition input = new BoardPosition(4,0);

        String expectedString;
        char[][] expectedBoard = new char[5][5];

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        actualBoard.dropToken('x', 0);
        actualBoard.dropToken('o', 0);
        actualBoard.dropToken('x', 0);
        actualBoard.dropToken('x', 0);
        actualBoard.dropToken('x', 0);

        expectedBoard[0][0] = 'x';
        expectedBoard[1][0] = 'o';
        expectedBoard[2][0] = 'x';
        expectedBoard[3][0] = 'x';
        expectedBoard[4][0] = 'x';

        assertFalse(actualBoard.checkVertWin(input, 'x'));

        String actualString = actualBoard.toString();
        expectedString = boardToString(expectedBoard,5,5);
        assertEquals(expectedString, actualString);
    }
    // Tests that checkDiagWin() works properly on positive diagonal with token placed in middle of string of x's
    @Test
    public void testCheckDiagWin_last_placed_in_middle_pos_diag()
    {
        BoardPosition input = new BoardPosition(1,1);
        char inputToken = 'a';

        String expectedString;
        char[][] expectedBoard = new char[5][5];

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        for (int row = 0; row < 4; row++)
        {
            for (int col = row; col < 4; col++)
            {
                if (col == row)
                {
                    actualBoard.dropToken('x',col);
                    expectedBoard[row][col] = 'x';
                }
                else
                {
                    actualBoard.dropToken(inputToken,col);
                    expectedBoard[row][col] = inputToken;
                    inputToken++;
                }
            }
        }
        assertTrue(actualBoard.checkDiagWin(input, 'x'));

        String actualString = actualBoard.toString();
        expectedString = boardToString(expectedBoard,5,5);
        assertEquals(expectedString, actualString);
    }
    // Tests that checkDiagWin() works properly on negative diagonal with token placed in middle of string of x's
    @Test
    public void testCheckDiagWin_last_placed_in_middle_neg_diag()
    {
        BoardPosition input = new BoardPosition(2,2);
        char inputToken = 'a';

        String expectedString;
        char[][] expectedBoard = new char[5][5];

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        for (int row = 0; row < 4; row++)
        {
            for (int col = 4 - row; col > 0; col--)
            {
                if (col == 4 - row)
                {
                    actualBoard.dropToken('x',col);
                    expectedBoard[row][col] = 'x';
                }
                else
                {
                    actualBoard.dropToken(inputToken,col);
                    expectedBoard[row][col] = inputToken;
                    inputToken++;
                }
            }
        }
        assertTrue(actualBoard.checkDiagWin(input, 'x'));

        String actualString = actualBoard.toString();
        expectedString = boardToString(expectedBoard,5,5);
        assertEquals(expectedString, actualString);
    }
    // Tests that checkDiagWin() counts down and to the left properly with last token placed in top right of board
    @Test
    public void testCheckDiagWin_last_placed_in_top_right()
    {
        BoardPosition input = new BoardPosition(4,4);
        char inputToken = 'a';

        char[][] expectedBoard = new char[5][5];
        String expectedString;

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        for (int row = 0; row < 5; row++)
        {
            for (int col = row; col < 5; col++)
            {
                if (col == row && col != 0)
                {
                    actualBoard.dropToken('x',col);
                    expectedBoard[row][col] = 'x';
                }
                else
                {
                    actualBoard.dropToken(inputToken,col);
                    expectedBoard[row][col] = inputToken;
                    inputToken++;
                }
            }
        }
        assertTrue(actualBoard.checkDiagWin(input, 'x'));

        expectedString = boardToString(expectedBoard, 5, 5);
        String actualString = actualBoard.toString();
        assertEquals(expectedString, actualString);
    }
    // Tests that checkDiagWin() counts down and to the right properly with last token placed in top left of board
    @Test
    public void testCheckDiagWin_last_placed_in_top_left()
    {
        BoardPosition input = new BoardPosition(4,0);
        char inputToken = 'a';

        char[][] expectedBoard = new char[5][5];
        String expectedString;

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        for (int row = 0; row < 5; row++)
        {
            for (int col = 4 - row; col >= 0; col--)
            {
                if (col == 4 - row && col != 4)
                {
                    actualBoard.dropToken('x',col);
                    expectedBoard[row][col] = 'x';
                }
                else
                {
                    actualBoard.dropToken(inputToken,col);
                    expectedBoard[row][col] = inputToken;
                    inputToken++;
                }
            }
        }
        assertTrue(actualBoard.checkDiagWin(input, 'x'));

        expectedString = boardToString(expectedBoard, 5, 5);
        String actualString = actualBoard.toString();
        assertEquals(expectedString, actualString);
    }
    // Tests that checkDiagWin() counts up and to the right properly with last token placed in bottom left corner
    @Test
    public void testCheckDiagWin_last_placed_in_bottom_left()
    {
        BoardPosition input = new BoardPosition(0,0);
        char inputToken = 'a';

        char[][] expectedBoard = new char[5][5];
        String expectedString;

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        for (int row = 0; row < 4; row++)
        {
            for (int col = row; col < 4; col++)
            {
                if (col == row)
                {
                    actualBoard.dropToken('x',col);
                    expectedBoard[row][col] = 'x';
                }
                else
                {
                    actualBoard.dropToken(inputToken,col);
                    expectedBoard[row][col] = inputToken;
                    inputToken++;
                }
            }
        }
        assertTrue(actualBoard.checkDiagWin(input, 'x'));

        expectedString = boardToString(expectedBoard, 5, 5);
        String actualString = actualBoard.toString();
        assertEquals(expectedString, actualString);
    }
    // Tests that checkDiagWin() counts up and to the left properly with last token placed in bottom right corner
    @Test
    public void testCheckDiagWin_last_placed_in_bottom_right()
    {
        BoardPosition input = new BoardPosition(0,4);
        char inputToken = 'a';

        char[][] expectedBoard = new char[5][5];
        String expectedString;

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        for (int row = 0; row < 4; row++)
        {
            for (int col = 4 - row; col > 0; col--)
            {
                if (col == 4 - row)
                {
                    actualBoard.dropToken('x',col);
                    expectedBoard[row][col] = 'x';
                }
                else
                {
                    actualBoard.dropToken(inputToken,col);
                    expectedBoard[row][col] = inputToken;
                    inputToken++;
                }
            }
        }
        assertTrue(actualBoard.checkDiagWin(input, 'x'));

        expectedString = boardToString(expectedBoard, 5, 5);
        String actualString = actualBoard.toString();
        assertEquals(expectedString, actualString);
    }
    // Tests that checkDiagWin() can recognize an opposing char when counting x's down and to the right
    @Test
    public void testCheckDiagWin_last_placed_in_top_left_false()
    {
        BoardPosition input = new BoardPosition(0,4);
        char inputToken = 'a';

        char[][] expectedBoard = new char[5][5];
        String expectedString;

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        for (int row = 0; row < 5; row++)
        {
            for (int col = 4 - row; col >= 0; col--)
            {
                if (col == 4 - row && col < 3)
                {
                    actualBoard.dropToken('x',col);
                    expectedBoard[row][col] = 'x';
                }
                else
                {
                    actualBoard.dropToken(inputToken,col);
                    expectedBoard[row][col] = inputToken;
                    inputToken++;
                }
            }
        }
        assertFalse(actualBoard.checkDiagWin(input, 'x'));

        expectedString = boardToString(expectedBoard, 5, 5);
        String actualString = actualBoard.toString();
        assertEquals(expectedString, actualString);
    }
    // Tests that checkTie() can recognize missing token in top left corner of board when rest of board is full
    @Test
    public void testCheckTie_full_except_top_left_false()
    {
        char inputToken = 'a';

        char[][] expectedBoard = new char[5][5];
        String expectedString;

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        for (int row = 0; row < 5; row++)
        {
            for (int column = 0; column < 5; column++)
            {
                if (!(row == 4 && column == 0))
                {
                    actualBoard.dropToken(inputToken, column);
                    expectedBoard[row][column] = inputToken;
                    inputToken++;
                }
            }
        }
        assertFalse(actualBoard.checkTie());

        expectedString = boardToString(expectedBoard,5,5);
        String actualString = actualBoard.toString();
        assertEquals(expectedString, actualString);
    }
    // Tests that checkTie() can recognize missing token in top right corner of board when rest of board is full
    @Test
    public void testCheckTie_full_except_top_right_false()
    {
        char inputToken = 'a';

        char[][] expectedBoard = new char[5][5];
        String expectedString;

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        for (int row = 0; row < 5; row++)
        {
            for (int column = 0; column < 5; column++)
            {
                if (!(row == 4 && column == 4))
                {
                    actualBoard.dropToken(inputToken, column);
                    expectedBoard[row][column] = inputToken;
                    inputToken++;
                }
            }
        }
        assertFalse(actualBoard.checkTie());

        expectedString = boardToString(expectedBoard,5,5);
        String actualString = actualBoard.toString();
        assertEquals(expectedString, actualString);
    }
    // Tests that checkTie() returns false when no columns are close to being full
    @Test
    public void testCheckTie_no_full_columns_false()
    {
        char inputToken = 'a';

        char[][] expectedBoard = new char[5][5];
        String expectedString;

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        for (int row = 0; row < 2; row++)
        {
            for (int col = 0; col < 5; col++)
            {
                actualBoard.dropToken(inputToken,col);
                expectedBoard[row][col] = inputToken;
                inputToken++;
            }
        }
        assertFalse(actualBoard.checkTie());

        expectedString = boardToString(expectedBoard,5,5);
        String actualString = actualBoard.toString();
        assertEquals(expectedString, actualString);
    }
    // Tests that checkTie() returns true when board is full
    @Test
    public void testCheckTie_full_board_true()
    {
        char inputToken = 'a';

        char[][] expectedBoard = new char[5][5];
        String expectedString;

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        for (int row = 0; row < 5; row++)
        {
            for (int col = 0; col < 5; col++)
            {
                actualBoard.dropToken(inputToken, col);
                expectedBoard[row][col] = inputToken;
                inputToken++;
            }
        }
        assertTrue(actualBoard.checkTie());

        expectedString = boardToString(expectedBoard,5,5);
        String actualString = actualBoard.toString();
        assertEquals(expectedString, actualString);
    }
    // Tests that whatsAtPos() can correctly identify char in bottom left corner of board
    @Test
    public void testWhatsAtPos_char_at_bottom_left_corner()
    {
        BoardPosition input = new BoardPosition(0, 0);
        char inputToken = 'a';

        char[][] expectedBoard = new char[5][5];
        String expectedString;
        char expectedChar = 'a';

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        for (int row = 0; row < 5; row++)
        {
            for (int col = 0; col < 5; col++)
            {
                actualBoard.dropToken(inputToken,col);
                expectedBoard[row][col] = inputToken;
                inputToken++;
            }
        }
        char actualChar = actualBoard.whatsAtPos(input);
        assertTrue(expectedChar == actualChar);

        expectedString = boardToString(expectedBoard,5,5);
        String actualString = actualBoard.toString();
        assertEquals(expectedString, actualString);
    }
    // Tests that whatsAtPos() can correctly identify char in top left corner of board
    @Test
    public void testWhatsAtPos_char_at_top_left_corner()
    {
        BoardPosition input = new BoardPosition(4, 0);
        char inputToken = 'a';

        char[][] expectedBoard = new char[5][5];
        String expectedString;
        char expectedChar = 'u';

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        for (int row = 0; row < 5; row++)
        {
            for (int col = 0; col < 5; col++)
            {
                actualBoard.dropToken(inputToken,col);
                expectedBoard[row][col] = inputToken;
                inputToken++;
            }
        }
        char actualChar = actualBoard.whatsAtPos(input);
        assertTrue(expectedChar == actualChar);

        expectedString = boardToString(expectedBoard,5,5);
        String actualString = actualBoard.toString();
        assertEquals(expectedString, actualString);
    }
    // Tests that whatsAtPos() can correctly identify char in bottom right corner of board
    @Test
    public void testWhatsAtPos_char_at_bottom_right_corner()
    {
        BoardPosition input = new BoardPosition(0, 4);
        char inputToken = 'a';

        char[][] expectedBoard = new char[5][5];
        String expectedString;
        char expectedChar = 'e';

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        for (int row = 0; row < 5; row++)
        {
            for (int col = 0; col < 5; col++)
            {
                actualBoard.dropToken(inputToken,col);
                expectedBoard[row][col] = inputToken;
                inputToken++;
            }
        }
        char actualChar = actualBoard.whatsAtPos(input);
        assertTrue(expectedChar == actualChar);

        expectedString = boardToString(expectedBoard,5,5);
        String actualString = actualBoard.toString();
        assertEquals(expectedString, actualString);
    }
    // Tests that whatsAtPos() can correctly identify char in top right corner of board
    @Test
    public void testWhatsAtPos_char_at_top_right_corner()
    {
        BoardPosition input = new BoardPosition(4, 4);
        char inputToken = 'a';

        char[][] expectedBoard = new char[5][5];
        String expectedString;
        char expectedChar = 'y';

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        for (int row = 0; row < 5; row++)
        {
            for (int col = 0; col < 5; col++)
            {
                actualBoard.dropToken(inputToken,col);
                expectedBoard[row][col] = inputToken;
                inputToken++;
            }
        }
        char actualChar = actualBoard.whatsAtPos(input);
        assertTrue(expectedChar == actualChar);

        expectedString = boardToString(expectedBoard,5,5);
        String actualString = actualBoard.toString();
        assertEquals(expectedString, actualString);
    }
    // Tests that whatsAtPos() correctly returns a ' ' char when no character is in board cell
    @Test
    public void testWhatsAtPos_return_space()
    {
        BoardPosition input = new BoardPosition(2, 2);
        char inputToken = 'a';

        char[][] expectedBoard = new char[5][5];
        String expectedString;
        char expectedChar = ' ';

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 5; col++)
            {
                if(!(row == 2 && col == 2))
                {
                    actualBoard.dropToken(inputToken,col);
                    expectedBoard[row][col] = inputToken;
                    inputToken++;
                }
            }
        }
        char actualChar = actualBoard.whatsAtPos(input);
        assertTrue(expectedChar == actualChar);

        expectedString = boardToString(expectedBoard,5,5);
        String actualString = actualBoard.toString();
        assertEquals(expectedString, actualString);
    }
    // Tests that isPlayerAtPos() correctly identifies char in bottom left corner
    @Test
    public void testIsPlayerAtPos_char_at_bottom_left_corner()
    {
        BoardPosition input = new BoardPosition(0, 0);
        char input2 = 'a';

        char inputToken = 'a';

        char[][] expectedBoard = new char[5][5];
        String expectedString;

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        for (int row = 0; row < 5; row++)
        {
            for (int col = 0; col < 5; col++)
            {
                actualBoard.dropToken(inputToken,col);
                expectedBoard[row][col] = inputToken;
                inputToken++;
            }
        }
        assertTrue(actualBoard.isPlayerAtPos(input, input2));

        expectedString = boardToString(expectedBoard,5,5);
        String actualString = actualBoard.toString();
        assertEquals(expectedString, actualString);
    }
    // Tests that isPlayerAtPos() correctly identifies char in top left corner
    @Test
    public void testIsPlayerAtPos_char_at_top_left_corner()
    {
        BoardPosition input = new BoardPosition(4, 0);
        char input2 = 'u';

        char inputToken = 'a';

        char[][] expectedBoard = new char[5][5];
        String expectedString;

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        for (int row = 0; row < 5; row++)
        {
            for (int col = 0; col < 5; col++)
            {
                actualBoard.dropToken(inputToken,col);
                expectedBoard[row][col] = inputToken;
                inputToken++;
            }
        }
        assertTrue(actualBoard.isPlayerAtPos(input, input2));

        expectedString = boardToString(expectedBoard,5,5);
        String actualString = actualBoard.toString();
        assertEquals(expectedString, actualString);
    }
    // Tests that isPlayerAtPos() correctly identifies char in bottom right corner
    @Test
    public void testIsPlayerAtPos_char_at_bottom_right_corner()
    {
        BoardPosition input = new BoardPosition(0, 4);
        char input2 = 'e';

        char inputToken = 'a';

        char[][] expectedBoard = new char[5][5];
        String expectedString;

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        for (int row = 0; row < 5; row++)
        {
            for (int col = 0; col < 5; col++)
            {
                actualBoard.dropToken(inputToken,col);
                expectedBoard[row][col] = inputToken;
                inputToken++;
            }
        }
        assertTrue(actualBoard.isPlayerAtPos(input, input2));

        expectedString = boardToString(expectedBoard,5,5);
        String actualString = actualBoard.toString();
        assertEquals(expectedString, actualString);
    }
    // Tests that isPlayerAtPos() correctly identifies char in top right corner
    @Test
    public void testIsPlayerAtPos_char_at_top_right_corner()
    {
        BoardPosition input = new BoardPosition(4, 4);
        char input2 = 'y';

        char inputToken = 'a';

        char[][] expectedBoard = new char[5][5];
        String expectedString;

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        for (int row = 0; row < 5; row++)
        {
            for (int col = 0; col < 5; col++)
            {
                actualBoard.dropToken(inputToken,col);
                expectedBoard[row][col] = inputToken;
                inputToken++;
            }
        }
        assertTrue(actualBoard.isPlayerAtPos(input, input2));

        expectedString = boardToString(expectedBoard,5,5);
        String actualString = actualBoard.toString();
        assertEquals(expectedString, actualString);
    }
    // Tests that isPlayerAtPos() correctly returns false when a player is not in the cell
    @Test
    public void testIsPlayerAtPos_middle_of_board_false()
    {
        BoardPosition input = new BoardPosition(2, 2);
        char input2 = 'x';

        char inputToken = 'a';

        char[][] expectedBoard = new char[5][5];
        String expectedString;

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 5; col++)
            {
                if(!(row == 2 && col == 2))
                {
                    actualBoard.dropToken(inputToken,col);
                    expectedBoard[row][col] = inputToken;
                    inputToken++;
                }
            }
        }
        assertFalse(actualBoard.isPlayerAtPos(input, input2));

        expectedString = boardToString(expectedBoard,5,5);
        String actualString = actualBoard.toString();
        assertEquals(expectedString, actualString);
    }
    // Tests that dropToken() properly drops token into bottom left corner of board when board is empty
    @Test
    public void testDropToken_empty_board_bottom_left()
    {
        char input = 'x';
        char input2 = 0;

        char[][] expectedBoard = new char[5][5];
        String expectedString;

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        actualBoard.dropToken(input, input2);
        expectedBoard[0][0] = input;

        expectedString = boardToString(expectedBoard, 5, 5);
        String actualString = actualBoard.toString();
        assertEquals(expectedString, actualString);
    }
    // Tests that dropToken() properly drops token into bottom right corner of board when board is empty
    @Test
    public void testDropToken_empty_board_bottom_right()
    {
        char input = 'x';
        char input2 = 4;

        char[][] expectedBoard = new char[5][5];
        String expectedString;

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        actualBoard.dropToken(input, input2);
        expectedBoard[0][4] = input;

        expectedString = boardToString(expectedBoard, 5, 5);
        String actualString = actualBoard.toString();
        assertEquals(expectedString, actualString);
    }
    // Tests that dropToken() correctly drops token into top left corner of board when column is almost full
    @Test
    public void testDropToken_top_left_corner()
    {
        char inputToken = 'a';

        char[][] expectedBoard = new char[5][5];
        String expectedString;

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        for (int row = 0; row < 5; row++)
        {
            actualBoard.dropToken(inputToken, 0);
            expectedBoard[row][0] = inputToken;
            inputToken++;
        }

        expectedString = boardToString(expectedBoard, 5, 5);
        String actualString = actualBoard.toString();
        assertEquals(expectedString, actualString);
    }
    // Tests that dropToken() correctly drops token into top right corner of board when column is almost full
    @Test
    public void testDropToken_top_right_corner()
    {
        char inputToken = 'a';

        char[][] expectedBoard = new char[5][5];
        String expectedString;

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        for (int row = 0; row < 5; row++)
        {
            actualBoard.dropToken(inputToken, 4);
            expectedBoard[row][4] = inputToken;
            inputToken++;
        }

        expectedString = boardToString(expectedBoard, 5, 5);
        String actualString = actualBoard.toString();
        assertEquals(expectedString, actualString);
    }
    // Tests that dropToken() correctly places a new token on top of previously placed tokens in middle of board
    @Test
    public void testDropToken_middle_column_not_empty()
    {
        char inputToken = 'a';

        char[][] expectedBoard = new char[5][5];
        String expectedString;

        IGameBoard actualBoard = IGameBoardFactory(5,5,4);

        for (int col = 0; col < 5; col++)
        {
            actualBoard.dropToken(inputToken, col);
            expectedBoard[0][col] = inputToken;
            inputToken++;
        }

        actualBoard.dropToken(inputToken, 2);
        expectedBoard[1][2] = 'f';

        expectedString = boardToString(expectedBoard, 5, 5);
        String actualString = actualBoard.toString();
        assertEquals(expectedString, actualString);
    }
}

