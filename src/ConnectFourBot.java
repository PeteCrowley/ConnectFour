import java.util.ArrayList;
import java.util.Collections;

public class ConnectFourBot {
    private int depth;

    public ConnectFourBot(){
        depth = 5;
    }

    private static ArrayList<Integer> getPossibleMoves(ConnectFour cf){
        ArrayList<Integer> moves = new ArrayList<>();
        if (cf.gameOver())
            return moves;
        for(int i = 0; i < cf.getNumColumns(); i++){
            if(!cf.columnFull(i))
                moves.add(i);
        }
        return moves;
    }

    private static ArrayList<ConnectFour> genNextStates(ConnectFour cf){
        ArrayList<ConnectFour> boards = new ArrayList<>();
        for(int move : getPossibleMoves(cf)){
            ConnectFour b = new ConnectFour(cf);
            b.play(move);
            boards.add(b);
        }
        return boards;
    }
    public static int randomMove(ConnectFour cf) {
        ArrayList<Integer> possibleMoves = ConnectFourBot.getPossibleMoves(cf);
        if(possibleMoves.isEmpty())
            return -1;
        return possibleMoves.get((int) (Math.random()*possibleMoves.size()));

    }

    public static int miniMax(ConnectFour board, int alpha, int beta, int depth){
        if (depth == 0)
            return evaluate(board);
        else if(board.getCurrentPlayer() == 1){
            int maxEval = -100;
//            if(depth <= 2)
//                System.out.println();
            ArrayList<ConnectFour> children = genNextStates(board);
//            Collections.sort(children);
//            System.out.println(children);
            for(ConnectFour b : children){
                int eval = miniMax(b, alpha, beta, depth-1);
                if(eval > maxEval) {
                    maxEval = eval;
                }
                alpha = Math.max(alpha, eval);
                if (beta <= alpha)
                    break;
            }
            if(maxEval < -30)
                return maxEval + 1;
            return maxEval;
        }
        else{
            int minEval = 100;
            ArrayList<ConnectFour> children = genNextStates(board);
//            Collections.sort(children);
            for(ConnectFour b : children){
                int eval = miniMax(b, alpha, beta, depth-1);
                if(eval < minEval) {
                    minEval = eval;
                }
                beta = Math.min(beta, eval);
                if (beta <= alpha)
                    break;
            }
            if(minEval > 30)
                return minEval - 1;
            return minEval;
        }
    }

    public static ConnectFour chooseBestMove(ConnectFour board, int depth){
        ArrayList<ConnectFour> boards = genNextStates(board);
        if(boards.isEmpty())
            return board;
        Collections.shuffle(boards);
        ConnectFour bestBoard = boards.get(0);
        int bestEval = miniMax(bestBoard,-100, 100, depth);
        for(ConnectFour b : boards){
            int eval = miniMax(b,-100, 100, depth);
            if (board.getCurrentPlayer() == 1 && eval > bestEval){
                bestEval = eval;
                bestBoard = b;
            }
            else if(board.getCurrentPlayer() == 2 && eval < bestEval){
                bestEval = eval;
                bestBoard = b;
            }
        }

        return bestBoard;
    }

    public static int evaluate(ConnectFour cf){
        if(cf.getWinner() == 1)
            return 100;
        else if(cf.getWinner() == 2)
            return -100;
        return 0;
    }


}

