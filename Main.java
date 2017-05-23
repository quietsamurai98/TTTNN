/**
 * @(#)Main.java
 *
 *
 * @author 
 * @version 1.00 2017/5/18
 */
import java.util.Arrays;
import java.util.Scanner;
public class Main {
        
    /**
     * Creates a new instance of <code>Main</code>.
     */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int[] numpadToTile = {-1,6,7,8,3,4,5,0,1,2};
        int epoch = 100;
        int[] layerCounts = {18,18,18,9,9,9};
        Network nn = new Network(layerCounts);
        Evolver darwin = new Evolver(25, 1, 0.5, 1000);
        
        //nn = darwin.evolve(nn, 1000);
        
        Scanner kb = new Scanner(System.in);
		System.out.println("Training... please wait.");
		nn = darwin.evolve(nn, epoch);
        while(true){
        	System.out.println(nn);
        	double[] fitArr = darwin.calcFitness(nn);
        	System.out.println("Current fitness: " + fitArr[0]);
        	System.out.println("    Wins for X: " + ((int) (fitArr[1]+0.5)));
        	System.out.println("    Wins for O: " + ((int) (fitArr[2]+0.5)));
        	System.out.println("    Tied games: " + ((int) (fitArr[3]+0.5)));
        	System.out.println("Play a game, then train for "+ epoch +" generations.");
        	System.out.println("You are playing as O. Enter a number 0-8 to put an O at that tile.");
        	System.out.println("Tile Reference:\n789\n456\n123\n");
        	
        	TTTGame game = new TTTGame();
	        int side = 1;
	        int move = 0;
	        while(!game.isGameOver()){
	        	if(side == 1){ //NN turn
	        		double[] inputs = new double[18];
	        		int[][] gameBoard = game.getBoard();
	        		for(int j = 0; j < 9; j++){
	        			inputs[j] = ((gameBoard[j/3][j%3] == 1) ? 1.0 : 0.0); //Tile is occupied by X
	        			inputs[j+9]=((gameBoard[j/3][j%3] ==-1) ? 1.0 : 0.0); //Tile is occupied by O
	        		}
					Double[][] output = new Double[9][2];
	        		double[] nnout = nn.calc(inputs);
	        		for(int j = 0; j < 9; j++){
	        			output[j][0] = new Double(nnout[j]);
	        			output[j][1] = new Double(j);
	        		}
	        		Arrays.sort(output, new ArrayComparator(0, false));
	        		for(int j = 0; j<9 && !game.makeMove(side, output[j][1].intValue()); j++){
	        		}
	        	} else {
	        		move = numpadToTile[kb.nextInt()];
		        	while(!game.makeMove(side, move)){
		        		move = numpadToTile[kb.nextInt()];
		        	}
	        	}
	        	System.out.println(game);
	        	side*=-1;
	        }
			System.out.println("Training... please wait.");
			darwin.halveLearningRate();
			nn = darwin.evolve(nn, epoch);
        }
    }
    
    public static boolean isNumeric(String str){  
		try{  
			double d = Double.parseDouble(str);  
		}  
		catch(NumberFormatException nfe){  
			return false;  
		}  
		return true;  
	}
}
