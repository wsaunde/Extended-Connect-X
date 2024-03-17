package cpsc2150.extendedConnectX.models;

/*
Josh Bailey - jtbly12
Jackson Graham - jgrhm
Will Saunders - wsaunde
Grace Johnson - gracemarieee
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @invariant MIN_COL <= NUM_COLUMNS <= MAX_COL AND MIN_ROW <= NUM_ROWS <= MAX_ROW AND
 *            MIN_NUM_TO_WIN <= NUM_TO_WIN <= MAX_NUM_TO_WIN AND
 *            NUM_TO_WIN <= NUM_COLUMNS AND NUM_TO_WIN <= NUM_ROWS
 *
 * @invariant [There are NUM_ROWS x NUM_COLUMNS or less player moves stored in Map]
 *
 * @correspondence number_of_rows = NUM_ROWS
 * @correspondence number_of_columns = NUM_COLUMNS
 * @correspondence number_to_win = NUM_TO_WIN
 * @correspondence self = Map<Character, List<BoardPosition>> playerMoves
 */
public class GameBoardMem extends AbsGameBoard
{
    private final int NUM_TO_WIN;
    private final int NUM_ROWS;
    private final int NUM_COLUMNS;
    private Map<Character, List<BoardPosition>> playerMoves;

    /**
     * Constructs a GameBoardMem object
     *
     * @pre MIN_ROW <= inRow <= MAX_ROW AND MIN_COL <= inColumn <= MAX_COL AND
     * MIN_NUM_TO_WIN <= inNumToWin <= MAX_NUM_TO_WIN AND inNumToWin <= inRow AND inNumToWin <= inColumn
     *
     * @post NUM_ROWS = inRows AND NUM_COLUMNS = inColumns AND NUM_TO_WIN = inNumToWin
     * AND self = new HashMap<>()
     */
    public GameBoardMem(int inRows, int inColumns, int inNumToWin)
    {
        NUM_ROWS = inRows;
        NUM_COLUMNS = inColumns;
        NUM_TO_WIN = inNumToWin;
        playerMoves = new HashMap<>();
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

    public char whatsAtPos(BoardPosition pos)
    {
        // step through each key in the map
        for (char key : playerMoves.keySet())
        {
            // for each key, step through BoardPosition objects in list corresponding to key
            for (BoardPosition currentPos : playerMoves.get(key))
            {
                // if pos param is found in current list, return character that is at the position
                if (currentPos.equals(pos))
                {
                    return key;
                }
            }
        }
        // if it isn't found in the list, the BoardPosition is empty and ' ' is returned
        return ' ';
    }

    public void dropToken(char p, int c)
    {
        int currentRow = 0;
        BoardPosition currentPos = new BoardPosition(currentRow,c);

        // start at bottom row of the column, iterate up the column until there is an empty space for a token
        while (currentRow < NUM_ROWS && whatsAtPos(currentPos) != ' ')
        {
            currentPos = new BoardPosition(currentRow,c);
            currentRow++;
        }

        // if the player token key already exists in map, add currentPos to list corresponding to key
        if (playerMoves.containsKey(p))
        {
            playerMoves.get(p).add(currentPos);
        }
        // if the player token key does not exist, create an empty list, add the position, and add the pair to the map
        else
        {
            List<BoardPosition> newList = new ArrayList<>();
            newList.add(currentPos);
            playerMoves.put(p, newList);
        }
    }

    public boolean isPlayerAtPos(BoardPosition pos, char player)
    {
        // if the player key exists, return true if the BoardPosition is in the list corresponding to the key, false ow
        if (playerMoves.containsKey(player))
        {
            return playerMoves.get(player).contains(pos);
        }
        // if the key does not exist, return false
        return false;
    }
}
