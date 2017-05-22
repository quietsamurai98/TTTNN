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
        int[] layerCounts = {9,18,18,18,9};
        Network nn = new Network(layerCounts);
        Evolver darwin = new Evolver(25, 1, 0.5, 250);
        
        //nn = darwin.evolve(nn, 1000);
        
        Scanner kb = new Scanner(System.in);
		System.out.println("Training... please wait.");
		nn = darwin.evolve(nn, 100);
        while(true){
        	System.out.println(nn);
        	System.out.println("Current fitness: " + darwin.calcFitness(nn));
        	System.out.println("Play a game, then train for 100 generations.");
        	System.out.println("You are playing as O. Enter a number 0-8 to put an O at that tile.");
        	System.out.println("Tile Reference:\n012\n345\n678\n");
        	
        	TTTGame game = new TTTGame();
	        int side = 1;
	        int move = 0;
	        while(!game.isGameOver()){
	        	if(side == 1){ //NN turn
	        		double[] inputs = new double[9];
	        		int[][] gameBoard = game.getBoard();
	        		for(int j = 0; j < 9; j++){
	        			inputs[j] = (double) gameBoard[j/3][j%3];
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
	        		move = kb.nextInt();
		        	while(!game.makeMove(side, move)){
		        		move = kb.nextInt();
		        	}
	        	}
	        	System.out.println(game);
	        	side*=-1;
	        }
			System.out.println("Training... please wait.");
			darwin.halveLearningRate();
			nn = darwin.evolve(nn, 100);
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
