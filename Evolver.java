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
	int    numTests;
	int    initialSide;
	
	
    public Evolver(int populationSize, double maxWeightChange, double funcMutateProb, int numTests, int side){
    	this.populationSize = populationSize;
    	this.maxWeightChange= maxWeightChange;
    	this.funcMutateProb = funcMutateProb;
    	this.numTests = numTests;
    	this.initialSide = side;
    }
    
    public Network evolve(Network network, Network vsNet, int maxGenerations){
    	Network inputNetwork = network.clone();
    	Network bestNetwork = inputNetwork.clone();
    	for(int i=0; i<maxGenerations; i++){
    		Network[] population = createChildren(bestNetwork);
    		bestNetwork = findMostFit(population, vsNet).clone();
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
    
    public Network findMostFit(Network population[], Network vsNet){
    	int index = 0;
    	double maxFitness = calcFitness(population[0], vsNet);
    	for(int i=1; i<populationSize; i++){
    		double fitness = calcFitness(population[i], vsNet);
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
    
    public double calcFitness(Network network, Network vsNet){
    	double avgFitness = 0.0;
		for(int i = 0; i<numTests; i++){
			TTTGame game = new TTTGame();
	        int side = initialSide;
	        int move = 0;
	        while(!game.isGameOver()){
        		double[] inputs = new double[9];
        		int[][] gameBoard = game.getBoard();
        		for(int j = 0; j < 9; j++){
        			inputs[j] = (double) gameBoard[j/3][j%3];
        		}
				Double[][] output = new Double[9][2];
				double[] nnout;
				if(side == 1){ //X turn
					nnout = (initialSide == 1) ? network.calc(inputs) : vsNet.calc(inputs);
				} else { //O turn
					nnout = (initialSide == -1) ? network.calc(inputs) : vsNet.calc(inputs);
				}
        		for(int j = 0; j < 9; j++){
        			output[j][0] = new Double(nnout[j]);
        			output[j][1] = new Double(j);
        		}
        		Arrays.sort(output, new ArrayComparator(0, false));
        		for(int j = 0; j<9 && !game.makeMove(side, output[j][1].intValue()); j++){
        		}
	        	side*=-1;
	        }
	        avgFitness+=game.getWinner()*initialSide;
		}
    	return avgFitness;
    }
    public void multiplyLearningRate(double factor){
    	maxWeightChange*=factor;
    	funcMutateProb*=factor;
    }
}