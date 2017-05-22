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
    	double maxFitness = calcFitness(population[0]);
    	for(int i=1; i<populationSize; i++){
    		double fitness = calcFitness(population[i]);
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
    
    public double calcFitness(Network network){
    	double avgFitness = 0.0;
		for(int i = 0; i<numTests; i++){
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
	        		double[] nnout = network.calc(inputs);
	        		for(int j = 0; j < 9; j++){
	        			output[j][0] = new Double(nnout[j]);
	        			output[j][1] = new Double(j);
	        		}
	        		Arrays.sort(output, new ArrayComparator(0, false));
	        		for(int j = 0; j<9 && !game.makeMove(side, output[j][1].intValue()); j++){
	        		}
	        	} else {
	        		move = (int) (Math.random()*9);
		        	while(!game.makeMove(side, move)){
		        		move = (int) (Math.random()*9);
		        	}
	        	}
	        	side*=-1;
	        }
	        avgFitness+=game.getWinner();
		}
    	return avgFitness;
    }
    public void halveLearningRate(){
    	maxWeightChange/=2.0;
    	funcMutateProb/=2.0;
    }
}