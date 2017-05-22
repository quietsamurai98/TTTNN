/**
 * @(#)TTTGame.java
 *
 *
 * @author 
 * @version 1.00 2017/5/22
 */


public class TTTGame {
	int[][] gameBoard;
    public TTTGame() {
    	gameBoard = new int[3][3];
    }
    public boolean makeMove(int side, int tile){
    	if (gameBoard[tile/3][tile%3] == 0){
    		gameBoard[tile/3][tile%3] = side;
    		return true;
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
    			out+=(elem==0) ? "_" : ((elem==1) ? "X" : "0");
    		}
    		out+="\n";
    	}
    	return out;
    }
}