package SP;

/**
 * Connect four
 */
public class Game {
    private final int SIZE_OF_BOARD = 10;
    private final short AMOUNT_OF_PLAYERS = 2;
    private final int[][] DIRECTIONS =
            {{-1,-1} ,/* {-1, 1} - no need to check because there's nothing above added cicle */ {1, -1},
                    {0,-1}, {0,1},
                    {-1,1},{0,1},{1,1}};

    private short[][] gameBoard = new short[SIZE_OF_BOARD][SIZE_OF_BOARD];

    private short[] counter = new short[SIZE_OF_BOARD];

    private short state = 0; // 0 - nothing is going on , 1 - local game, 2 - online game

    private Player[] players = new Player[AMOUNT_OF_PLAYERS]; // More

//    public Game(Player first, Player second){
//
//    }

    // Local game
    public Game(){
        this.state = 1;
        players[0] = new Player("", (short)0);
        players[1] = new Player("", (short)1);
    }

    /**
     * Method adds circle and returns if the player has won
     *
     * @param column column of placed circle
     * @param p player that placed circle
     * @return true if player has won
     */
    public boolean addCircle(short column, Player p){
        short pointer = counter[column];
        counter[column] = ++counter[column];
        this.gameBoard[column][pointer] = p.getNumber();
        return checkVictory(column, pointer , p);
    }

    /**
     * Method checks if there are four circles of the given player
     *
     * @param column column of the placed circle
     * @param pointer row of the placed circle
     * @param p player that might win
     * @return true if player has won, false if he hasn't
     */
    private boolean checkVictory(short column,short pointer, Player p) {
        short possibleWinner = p.getNumber();
        Outer:
        for(int i = 0 ; i < DIRECTIONS.length; i++) {
            short counter = 1;
            int[] dir = this.DIRECTIONS[i];
            for (int j = 1; j < 4; j++) {   // Attempt to Connect four
                int stepX = j*dir[0];
                int stepY = j*dir[1];
                if(column + stepX < 0 || pointer + stepY < 0 ||
                        column + stepX >= SIZE_OF_BOARD || pointer + stepY >= SIZE_OF_BOARD){
                    continue Outer;
                }
                short numberOfPlayer = this.gameBoard[column + stepX][pointer + stepY];
                if (possibleWinner != numberOfPlayer) {
                    continue Outer;
                }
                counter++;
            }
            if(counter == 4){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the game has ended in draw
     *
     * @return true if the game is draw, false if the game can still continue
     */
    public boolean checkDraw(){
        for(int i = 0 ; i < counter.length; i++){
            if(counter[i] != SIZE_OF_BOARD + 1){
                return false;
            }
        }
        return true;
    }

    /**
     * Returns pointer at the top of the column
     *
     * @param column chosen column of the gameboard
     * @return top of the column
     */
    public short getPointer(short column){
        return counter[column];
    }

}
