package cpsc2150.extendedConnects;

/*
Josh Bailey - jtbly12
Jackson Graham - jgrhm
Will Saunders - wsaunde
Grace Johnson - gracemarieee
 */

import cpsc2150.extendedConnectX.models.*;
import java.util.Scanner;


/**
 * The GameScreen class contains the main function and helpers functions necessary to run and play a game of
 * ConnectX. You may choose the number of players, the players' move tokens, size of the board, number in a row
 * needed to win, and whether you want a "Fast" or "Memory Efficient" version of the game to run.
 *
 * @invariant None
 */
public class GameScreen
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        runGame(input);
    }

    /**
     * This method begins and facilitates the game of ConnectX. runGame is responsible for initializing a IGameBoard
     * and taking turns with players making moves onto the board. Necessary functions specified by IGameBoard are called
     * to check the status of the game to respond accordingly. The players may choose to play again after game ends.
     *
     * @param input Scanner instance used to read user's input
     *
     * @pre None
     * @post [The game(s) of ConnectX ended]
     */
    private static void runGame(Scanner input)
    {
        // constants used to validate user's input when asking how many players they wish to play the game with
        final int MIN_NUM_PLAYERS = 2;
        final int MAX_NUM_PLAYERS = 10;

        boolean playGame = true;        // runs initial game and further games
        boolean askPlayAgain;           // checks if they want to play again and have valid input
        boolean gameOver = false;       // checks when each game ends
        boolean validInput = false;     // checks if input is valid

        char[] players;
        int playersSize;

        char playAgain;                 // for players choice in play again or not

        int playersCol;                 // holds players column
        int playerTurn;                 // increments to keep track of whose turn it is

        while (playGame)
        {
            // prompt user to enter number of players
            System.out.println("How many players?");
            playersSize = input.nextInt();

            // validate user input, number of players cannot be less than MIN_NUM_PLAYERS or greater
            // than MAX_NUM_PLAYERS
            while (playersSize < MIN_NUM_PLAYERS || playersSize > MAX_NUM_PLAYERS)
            {
                if (playersSize < MIN_NUM_PLAYERS)
                {
                    System.out.println("Must be at least " + MIN_NUM_PLAYERS + " players");
                }
                else if (playersSize > MAX_NUM_PLAYERS)
                {
                    System.out.println("Must be " + MAX_NUM_PLAYERS + " players or fewer");
                }

                System.out.println("How many players?");
                playersSize = input.nextInt();
            }

            // initialize array that will contain player tokens
            players = new char[playersSize];
            // call fillPlayerArray to prompt player to enter player tokens and fill
            // array with user entered move tokens
            fillPlayerArray(input, players, playersSize);

            playerTurn = 0;
            askPlayAgain = true;

            // creates and prints game board
            IGameBoard board = makeBoard(input);
            System.out.println(board);

            // runs while game is active
            while (!gameOver)
            {
                // Continues running while user enters invalid column/column is full, or if the game is not won/tied
                while (!validInput)
                {
                    // takes in users input for column they wish to move into
                    System.out.println("Player " + players[playerTurn] + ", what column do you want to place your marker in?");
                    playersCol = input.nextInt();

                    // runs if user picks a valid column option
                    if (playersCol >= 0 && playersCol < board.getNumColumns())
                    {
                        // runs if column user picked is not full
                        if (board.checkIfFree(playersCol))
                        {
                            // token is dropped & board is re-printed to screen
                            board.dropToken(players[playerTurn], playersCol);
                            System.out.println(board);

                            // runs if game has been won, code then exits loops and then asks if they wish to play again
                            if (board.checkForWin(playersCol))
                            {
                                System.out.println("Player " + players[playerTurn] + " Won!");
                                validInput = true;
                                gameOver = true;
                            }
                            // runs if game board is full/there is a tie, code then exits loops and asks to play again
                            else if (board.checkTie())
                            {
                                System.out.println("It's a tie!");
                                validInput = true;
                                gameOver = true;
                            }
                            // runs if game hasn't been won, or if there was not a tie
                            else
                            {
                                // increment playerTurn to switch whose turn it is
                                playerTurn++;

                                if (playerTurn > playersSize - 1)
                                {
                                    playerTurn = 0;
                                }
                            }
                        }
                        // runs if column user picked is full of tokens already
                        else
                        {
                            System.out.println("Column is full");
                        }
                    }
                    // runs if user chooses too high of a number
                    else if (playersCol > board.getNumColumns() - 1)
                    {
                        System.out.println("Column cannot be greater than " + (board.getNumColumns() - 1));
                    }
                    // runs if user chooses too low of a number
                    else if (playersCol < 0)
                    {
                        System.out.println("Column cannot be less than 0");
                    }
                }
            }
            // runs after game has resulted in win/tie, loops until user chooses a correct option for continuing
            while (askPlayAgain)
            {
                System.out.println("Would you like to play again? Y/N");
                playAgain = input.next().charAt(0);
                if (playAgain == 'Y' || playAgain == 'y')
                {
                    // needed to exit current while-loop
                    askPlayAgain = false;
                    // needed to keep playing game
                    gameOver = false;
                    // needed to keep validating user input/taking turns in game
                    validInput = false;
                }
                else if (playAgain == 'N' || playAgain == 'n')
                {
                    // needed to exit current while-loop
                    askPlayAgain = false;
                    // needed to terminate the program
                    playGame = false;
                }
            }
        }
    }

    /**
     * Responsible for checking if a char player token is already present in an array of player tokens
     *
     * @param player char player token to check for in players array
     * @param players char array of player tokens
     * @param playersSize size of char array
     *
     * @return true if player char does not exist in players array, false ow
     *
     * @pre None
     * @post validPlayer = [true iff player param is not present in players char array, false ow]
     */
    private static boolean validPlayer (char player, char[] players, int playersSize)
    {
        for (int i = 0; i < playersSize; i++)
        {
            if (players[i] == player)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * This method is responsible for filling an empty char array with player tokens of the user's choosing.
     *
     * @param input Scanner instance used to read user's input
     * @param players empty char array to fill with player tokens
     * @param playersSize size of empty char array
     *
     * @pre None
     * @post players = [char array filled with player tokens of the user's choosing]
     */
    private static void fillPlayerArray(Scanner input, char [] players, int playersSize)
    {
        char currentPlayer;
        for (int i = 0; i < playersSize; i++)
        {
            // prompt user to enter move tokens to represent each player and add tokens to char array.
            // if player attempts to enter duplicate tokens, ask for valid input
            do
            {
                System.out.println("Enter the character to represent player " + (i + 1));
                currentPlayer = input.next().charAt(0);
                currentPlayer = Character.toUpperCase(currentPlayer);

                if (!validPlayer(currentPlayer,players,playersSize))
                {
                    System.out.println(currentPlayer + " is already taken as a player token!");
                }
            }
            while (!validPlayer(currentPlayer, players, playersSize));

            players[i] = Character.toUpperCase(currentPlayer);
        }
    }

    /**
     * This method is responsible for prompting the user for input in order to create a IGameBoard object with
     * characteristics of their choosing. This IGameBoard object is returned.
     *
     * @param input Scanner used to read user's input
     *
     * @return IGameBoard instance created with "settings" specified by the user
     *
     * @pre None
     *
     * @post makeBoard = [IGameBoard with qualities specified by the user. The user chooses the number of rows, columns,
     *          number in a row needed to win, and if the IGameBoard is of dynamic type GameBoard or GameBoardMem. The user
     *          created IGameBoard is returned from the function]
     */
    private static IGameBoard makeBoard(Scanner input)
    {
        boolean validInput;
        char userInput;
        int rows, columns, numToWin;

        IGameBoard board;

        // prompt user to enter number of rows they wish to be on the board.
        // if user's input is less than IGameBoard.MIN_ROW or greater than IGameBoard.MAX_ROW, ask for valid input
        do
        {
            System.out.println("How many rows should be on the board?");
            rows = input.nextInt();

            if(rows < IGameBoard.MIN_ROW)
            {
                System.out.println("Number of rows cannot be less than " + IGameBoard.MIN_ROW + "!");
            }
            else if (rows > IGameBoard.MAX_ROW)
            {
                System.out.println("Number of rows cannot be greater than " + IGameBoard.MAX_ROW + "!");
            }
        } while(rows < IGameBoard.MIN_ROW || rows > IGameBoard.MAX_ROW);

        // prompt user to enter number of columns they wish to be on the board.
        // if user's input is less than IGameBoard.MIN_COLUMN or greater than IGameBoard.MAX_COLUMN, ask for valid input
        do
        {
            System.out.println("How many columns should be on the board?");
            columns = input.nextInt();

            if(columns < IGameBoard.MIN_COL)
            {
                System.out.println("Number of columns cannot be less than " + IGameBoard.MIN_COL + "!");
            }
            else if (columns > IGameBoard.MAX_COL)
            {
                System.out.println("Number of columns cannot be greater than " + IGameBoard.MAX_COL + "!");
            }
        } while(columns < IGameBoard.MIN_COL || columns > IGameBoard.MAX_COL);

        // prompt user to enter number in a row needed to win the game of Extended ConnectX.
        // if user's input is less than MIN_NUM_TO_WIN, greater than MAX_NUM_TO_WIN, or greater than number of rows
        // or columns previously entered, ask for valid input
        do
        {
            System.out.println("How many in a row to win?");
            numToWin = input.nextInt();
            validInput = numToWin <= IGameBoard.MAX_NUM_TO_WIN && numToWin >= IGameBoard.MIN_NUM_TO_WIN &&
                         numToWin <= rows && numToWin <= columns;

            if(numToWin < IGameBoard.MIN_NUM_TO_WIN)
            {
                System.out.println("Number in a row needed to win cannot be less than " + IGameBoard.MIN_NUM_TO_WIN + "!");
            }
            else if (numToWin > IGameBoard.MAX_NUM_TO_WIN)
            {
                System.out.println("Number in a row needed to win cannot be greater than " + IGameBoard.MAX_NUM_TO_WIN + "!");
            }
            else if (numToWin > rows || numToWin > columns)
            {
                System.out.println("Number in a row needed to win cannot be greater than number of rows or columns!");
            }
        }
        while(!validInput);

        // Ask user if they would like a Fast or Memory Efficient Game. If user's input is not 'F', 'f', 'M', or 'm',
        // input is invalid, ask for valid input
        do
        {
            System.out.println("Would you like a Fast Game (F/f) or a Memory Efficient Game (M/m)?");
            userInput = input.next().charAt(0);
            userInput = Character.toUpperCase(userInput);
            validInput = userInput == 'F' || userInput == 'f'|| userInput == 'M' || userInput == 'm';
            if (!validInput)
            {
                System.out.println("Please enter F or M");
            }
        }
        while(!validInput);

        // initialize IGameBoard implementation corresponding to user input and set size of board and number in
        // a row needed to win to user's desired settings. Return the initialized IGameBoard.
        switch (userInput)
        {
            case ('f'):
            case('F'):
                board = new GameBoard(rows, columns, numToWin);
                return board;
            case ('M'):
            case ('m'):
                board = new GameBoardMem(rows, columns, numToWin);
                return board;
            default:
                System.out.println("An error occurred making board...");
                System.out.println("default board implementation initialized.");
                board = new GameBoard(rows, columns, numToWin);
                return board;
        }
    }
}


