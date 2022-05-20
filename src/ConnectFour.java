public class ConnectFour implements BoardGame{
    private int[][] board; // game board for playing ConnectFour
    private int currentPlayer; // stores the current player's turn
    private Position[] winningPositions; //stores row+colum coordinates when someone wins
    private int winner; // stores which player wins (0 == draw)
    private final int numRows = 6, numColumns = 7;

    public ConnectFour(){
        newGame();
    }

    public ConnectFour(ConnectFour cf){
        board = new int[numRows][numColumns];
        for(int r = 0; r < cf.board.length; r++)
            for(int c = 0; c < cf.board[0].length; c++)
                board[r][c] = cf.board[r][c];
        currentPlayer = cf.currentPlayer;
        winningPositions = cf.winningPositions;
        winner = cf.winner;
    }

    public void newGame() {
        board = new int[numRows][numColumns];
        for(int r = 0; r < numRows; r++){
            for(int c = 0; c < numColumns; c++){
                board[r][c] = 0;
            }
        }
        currentPlayer = (int) (Math.random()*2+1);
        winner = 0;
        winningPositions = new Position[4];
        for(int i = 0; i < 4; i++){
            winningPositions[i] = new Position();
        }
    }

    private boolean ascendingDiagCheck(int r, int c){
        int count = 0;
        while(r + count >= 3 && c + count >= 3){
            if(board[r][c] == currentPlayer){
                count++;
                if (count == 4) {
                    for(int i = 3; i >= 0; i--) {
                        winningPositions[i] = new Position(r, c);
                        r++;
                        c++;
                    }
                    return true;
                }
            }else
                count = 0;
            r--;
            c--;
        }
        return false;
    }

    private boolean descendingDiagCheck(int r, int c){
        int count = 0;
        while(r + count >= 3 && 6 - c + count >= 3){

            if(board[r][c] == currentPlayer){
                count++;
                if (count == 4) {
                    for(int i = 3; i >= 0; i--) {
                        winningPositions[i] = new Position(r, c);
                        r++;
                        c--;
                    }
                    return true;
                }
            }else
                count = 0;
            r--;
            c++;
        }
        return false;
    }

    public boolean gameOver() {
        int count = 0;
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
        // Horizontal Check
        for(int r = numRows-1; r >=0; r--) {
            for(int c = numColumns - 1; c + count >= 3; c--){
                if(board[r][c] == currentPlayer){
                    count++;
                    if (count == 4) {
                        for(int i = 0; i < 4; i++) {
                            winningPositions[i] = new Position(r, c);
                            c++;
                        }
                        winner = currentPlayer;
                        currentPlayer = (currentPlayer == 1) ? 2 : 1;
                        return true;
                    }
                }else
                    count = 0;
            }
        }
        // Vertical Check
        for(int c = 0; c < numColumns; c++) {
            for(int r = numRows - 1; r + count >= 3; r--){
                if(board[r][c] == currentPlayer){
                    count++;
                    if (count == 4) {
                        for(int i = 3; i >= 0; i--) {
                            winningPositions[i] = new Position(r, c);
                            r++;
                        }
                        winner = currentPlayer;
                        currentPlayer = (currentPlayer == 1) ? 2 : 1;
                        return true;
                    }
                }else
                    count = 0;
            }
        }

        // Ascending Diagonal
        for(int r = numRows - 1; r >= 3; r--){
            if(ascendingDiagCheck(r, numColumns-1)){
                winner = currentPlayer;
                currentPlayer = (currentPlayer == 1) ? 2 : 1;
                return true;
            }
        }
        for(int c = numColumns - 1; c >= 3; c--){
            if(ascendingDiagCheck(numRows - 1, c)) {
                winner = currentPlayer;
                currentPlayer = (currentPlayer == 1) ? 2 : 1;
                return true;
            }
        }

        // Descending Diagonal
        for(int r = numRows - 1; r >= 3; r--){
            if(descendingDiagCheck(r, 0)) {
                winner = currentPlayer;
                currentPlayer = (currentPlayer == 1) ? 2 : 1;
                return true;
            }
        }
        for(int c = 0; c <= 4; c++){
            if(descendingDiagCheck(numRows - 1, c)) {
                winner = currentPlayer;
                currentPlayer = (currentPlayer == 1) ? 2 : 1;
                return true;
            }
        }
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
        for(int i = 0 ; i < numColumns; i++)
            if (!columnFull(i)) {
                winningPositions = new Position[]{new Position(-1, -1), new Position(-1, -1), new Position(-1, -1), new Position(-1, -1)};
                return false;
            }
        return true;
    }

    @Override
    public int getWinner() {
        return winner;
    }

    @Override
    public Position[] getWinningPositions() {
        return winningPositions;
    }

    @Override
    public boolean columnFull(int column) {
        return board[0][column] != 0;
    }

    @Override
    public void play(int column) {
        if (columnFull(column) || column < 0 || column > 6)
            return;
        int r = numRows-1;
        while(board[r][column] != 0){
            r--;
        }
        board[r][column] = currentPlayer;
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
    }

    @Override
    public int[][] getBoard() {
        return board;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumColumns() {
        return numColumns;
    }
}
