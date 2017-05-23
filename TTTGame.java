/**
 * @(#)TTTGame.java
 *
 *
 * @author 
 * @version 1.00 2017/5/22
 */

import java.util.ArrayList;
public class TTTGame {
	int[][] gameBoard;
    public TTTGame() {
    	gameBoard = new int[3][3];
    }
    public boolean makeMove(int side, int tile){
    	if (gameBoard[tile/3][tile%3] == 0){
    		gameBoard[tile/3][tile%3] = side;
    		return true; //legal move returns true
    	}
    	return false;
    }
    public int[][] getBoard(){
    	return gameBoard;
    }
    public boolean isGameOver(){
    	for(int r = 0; r<3; r++){
    		if(gameBoard[r][0] == gameBoard[r][1] && gameBoard[r][1] == gameBoard[r][2] && gameBoard[r][2]!= 0){
    			return true; //vertical
    		}
    		if(gameBoard[0][r] == gameBoard[1][r] && gameBoard[1][r] == gameBoard[2][r] && gameBoard[2][r]!= 0){
    			return true; //horizontal
    		}
    	}
    	if(gameBoard[0][0] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][2] && gameBoard[0][0] != 0){
    		return true; //diagonal
    	}
    	if(gameBoard[0][2] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][0] && gameBoard[0][2] != 0){
    		return true; //diagonal
    	}
    	for(int[] row : gameBoard){
    		for(int tile : row){
    			if(tile==0){
    				return false; //tie
    			}
    		}
    	}
    	return true;//
    }
    public int getWinner(){
    	for(int r = 0; r<3; r++){
    		if(gameBoard[r][0] == gameBoard[r][1] && gameBoard[r][1] == gameBoard[r][2] && gameBoard[r][2]!= 0){
    			return gameBoard[r][2]; //vertical
    		}
    		if(gameBoard[0][r] == gameBoard[1][r] && gameBoard[1][r] == gameBoard[2][r] && gameBoard[2][r]!= 0){
    			return gameBoard[2][r]; //horizontal
    		}
    	}
    	if(gameBoard[0][0] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][2] && gameBoard[0][0] != 0){
    		return gameBoard[1][1]; //diagonal
    	}
    	if(gameBoard[0][2] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][0] && gameBoard[0][2] != 0){
    		return gameBoard[1][1]; //diagonal
    	}
    	return 0; //tie
    }
    public String toString(){
    	String out = "";
    	int[][] arr = gameBoard;
    	for(int row[]:arr){
    		for(int elem:row){
    			out+=(elem==0) ? "_" : ((elem==1) ? "X" : "O");
    		}
    		out+="\n";
    	}
    	return out;
    }
    
    public int noPlanAI(int side, int[][] board){ //If it's about to win, it wins. If it's about to lose, it blocks.
    	ArrayList<Integer> moves = new ArrayList<Integer>();
    	for(int r = 0; r<3; r++){
	    	if(board[r][1] == side && board[r][2] == side && board[r][0] == 0)
	    		moves.add(r*3);
	    	if(board[r][0] == side && board[r][2] == side && board[r][1] == 0)
	    		moves.add(r*3+1);
	    	if(board[r][0] == side && board[r][1] == side && board[r][2] == 0)
	    		moves.add(r*3+2);
    	}
    	for(int c = 0; c<3; c++){
	    	if(board[1][c] == side && board[2][c] == side && board[0][c] == 0)
	    		moves.add(c);
	    	if(board[0][c] == side && board[2][c] == side && board[1][c] == 0)
	    		moves.add(3+c);
	    	if(board[0][c] == side && board[1][c] == side && board[2][c] == 0)
	    		moves.add(6+c);
    	}
    	if(board[1][1] == side && board[2][2] == side && board[0][0] == 0)
    		moves.add(0);
    	if(board[0][0] == side && board[2][2] == side && board[1][1] == 0)
    		moves.add(4);
    	if(board[0][0] == side && board[1][1] == side && board[2][2] == 0)
    		moves.add(8);
    	if(board[1][1] == side && board[0][2] == side && board[2][0] == 0)
    		moves.add(6);
    	if(board[2][0] == side && board[0][2] == side && board[1][1] == 0)
    		moves.add(4);
    	if(board[2][0] == side && board[1][1] == side && board[0][2] == 0)
    		moves.add(2);
    	if(moves.size()>0){
    		return moves.get((int)(Math.random()*moves.size())); //win
    	}
    	side*=-1;
    	for(int r = 0; r<3; r++){
	    	if(board[r][1] == side && board[r][2] == side && board[r][0] == 0)
	    		moves.add(r*3);
	    	if(board[r][0] == side && board[r][2] == side && board[r][1] == 0)
	    		moves.add(r*3+1);
	    	if(board[r][0] == side && board[r][1] == side && board[r][2] == 0)
	    		moves.add(r*3+2);
    	}
    	for(int c = 0; c<3; c++){
	    	if(board[1][c] == side && board[2][c] == side && board[0][c] == 0)
	    		moves.add(c);
	    	if(board[0][c] == side && board[2][c] == side && board[1][c] == 0)
	    		moves.add(3+c);
	    	if(board[0][c] == side && board[1][c] == side && board[2][c] == 0)
	    		moves.add(6+c);
    	}
    	if(board[1][1] == side && board[2][2] == side && board[0][0] == 0)
    		moves.add(0);
    	if(board[0][0] == side && board[2][2] == side && board[1][1] == 0)
    		moves.add(4);
    	if(board[0][0] == side && board[1][1] == side && board[2][2] == 0)
    		moves.add(8);
    	if(board[1][1] == side && board[0][2] == side && board[2][0] == 0)
    		moves.add(6);
    	if(board[2][0] == side && board[0][2] == side && board[1][1] == 0)
    		moves.add(4);
    	if(board[2][0] == side && board[1][1] == side && board[0][2] == 0)
    		moves.add(2);
    	if(moves.size()>0){
    		return moves.get((int)(Math.random()*moves.size())); //block opponent from winning
    	}
    	
    	for(int i = 0; i < 9; i++){
	    	if(board[i/3][i%3]==0){
	    		moves.add(i);
	    	}
    	}
    	return moves.get((int)(Math.random()*moves.size())); //make a random legal move
    }
}