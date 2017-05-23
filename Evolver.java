/**
 * @(#)Evolver.java
 *
 *
 * @author 
 * @version 1.00 2017/5/19
 */

import java.util.Arrays;
import java.util.Comparator;

public class Evolver {
	int	   populationSize;
	double maxWeightChange;
	double funcMutateProb;
	int numTests;
	ArrayComparator comparator = new ArrayComparator(0, false);
	
	
    public Evolver(int populationSize, double maxWeightChange, double funcMutateProb, int numTests){
    	this.populationSize = populationSize;
    	this.maxWeightChange= maxWeightChange;
    	this.funcMutateProb = funcMutateProb;
    	this.numTests = numTests;
    }
    
    public Network evolve(Network network, int maxGenerations){
    	Network inputNetwork = network.clone();
    	Network bestNetwork = inputNetwork.clone();
    	for(int i=0; i<maxGenerations; i++){
    		Network[] population = createChildren(bestNetwork);
    		bestNetwork = findMostFit(population).clone();
    		if(i%(maxGenerations/23)==0){
    			System.out.print("#");
    		}
    	}
    	System.out.println();
    	return bestNetwork;
    }
    
    public Network[] createChildren(Network parent){
    	Network[] output = new Network[populationSize];
    	
    	output[0] = parent.clone(); //Best network is always preserved as is
    	for(int i=1; i<populationSize; i++){
    		output[i] = parent.clone();
    		output[i].mutateNodes(maxWeightChange, funcMutateProb);
    	}
    	return output;
    }
    
    public Network findMostFit(Network population[]){
    	int index = 0;
    	double maxFitness = calcFitness(population[0])[0];
    	for(int i=1; i<populationSize; i++){
    		double fitness = calcFitness(population[i])[0];
    		if(fitness>maxFitness){
    			maxFitness = fitness;
    			index = i;
    		}
    	}
//		System.out.print("Fitness = ");
//    	System.out.printf("%10.5e",maxFitness);
//    	if(index!=0){
//			System.out.print(" (Improved!)");
//    	}
//    	System.out.println();
    	return population[index];
    }
    
    public double[] calcFitness(Network network){
    	double avgFitness = 0.0;
    	double xWins = 0.0;
    	double oWins = 0.0;
    	double ties  = 0.0;
		for(int i = 0; i<numTests; i++){
			int winner = playGame(network);
			switch(winner){
        	case -1:
        		avgFitness -= 5.0;
        		oWins+=1.0;
        		break;
        	case 0:
        		avgFitness += 1.0;
        		ties+=1.0;
        		break;
        	case 1:
        		avgFitness += 2.0;
        		xWins+=1.0;
        		break;
        	}
        	//Wins are worth 1, ties are worth 0, losses are -1
		}
		double[] out = {avgFitness, xWins, oWins, ties};
    	return out;
    }
    
    public int playGame(Network network){
    	TTTGame game = new TTTGame();
        int side = 1;
        int move = 0;
        while(!game.isGameOver()){
        	if(side == 1){ //NN turn
        		nnMakeMove(network, 1, game);
        	} else {
        		game.makeMove(-1, game.noPlanAI(-1, game.getBoard()));
        	}
        	side*=-1;
        }
        return game.getWinner();        
    }
    public void nnMakeMove(Network network, int side, TTTGame game){
    	double[] inputs = genInputs(game);
		Double[][] output = sortOutputs(network.calc(inputs));
		Arrays.sort(output, comparator);
		for(int j = 0; j<9 && !game.makeMove(side, output[j][1].intValue()); j++){
		}
    }
    public double[] genInputs(TTTGame game){
    	double[] inputs = new double[18];
		int[][] gameBoard = game.getBoard();
		for(int j = 0; j < 9; j++){
			inputs[j] = ((gameBoard[j/3][j%3] == 1) ? 1.0 : 0.0); //Tile is occupied by X
			inputs[j+9]=((gameBoard[j/3][j%3] ==-1) ? 1.0 : 0.0); //Tile is occupied by O
		}
		return inputs;
    }
    public Double[][] sortOutputs(double[] nnout){
    	Double[][] output = new Double[9][2];
		for(int j = 0; j < 9; j++){
			output[j][0] = nnout[j];
			output[j][1] = (double) j;
		}
		return output;
    }
    public void halveLearningRate(){
    	maxWeightChange/=2.0;
    	funcMutateProb/=2.0;
    }
}