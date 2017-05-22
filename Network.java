/**
 * @(#)Network.java
 *
 *
 * @Kenneth Palmer 
 * @version 1.00 2017/5/18
 * 
 * Object containing the actual neural network
 */

import java.util.Arrays;
public class Network {
	Node[][] networkNodes; //Jagged array of nodes. First index is layer, second index is node within that layer.
	double[][] networkValues; //Jagged array of node values
    public Network(int[] layerCounts){ 
    	//layerCounts is an array of how many nodes there are per layer. 
    	//For example [2,3,3,1] would make a neural network with a 2 node input layer, two hidden layers with three nodes each, and a one node output layer
    	networkNodes = new Node[layerCounts.length][];
    	networkValues = new double[layerCounts.length][];
    	for(int i=0; i<layerCounts.length; i++){
    		networkNodes[i] = new Node[layerCounts[i]];
    		networkValues[i] = new double[layerCounts[i]];
    		for(int j=0; j<layerCounts[i]; j++){
    			networkValues[i][j]=0.0;
    			if(i==0){
					networkNodes[i][j] = new Node(); //Input nodes have no weights
    			} else {
    				networkNodes[i][j] = new Node(layerCounts[i-1]); //Create node with layerCounts[i-1] input weights
    			}
    		}
    	}
    }
    
    public Network(double[][][] networkWeights){ //Creates network with pre-defined weights.
    
    	//-----networkWeights structure-----
    	//networkWeights 		  = whole network
    	//networkWeights[i] 	  = the layer i of the network
    	//networkWeights[i][j]    = the jth node in layer i of the network
    	//networkWeights[i][j][k] = the kth input weight of node j in layer i of the network
    	
    	
    	networkNodes = new Node[networkWeights.length][];
    	networkValues = new double[networkWeights.length][];
    	for(int i=0; i<networkWeights.length; i++){
    		networkNodes[i] = new Node[networkWeights[i].length];
    		networkValues[i] = new double[networkWeights[i].length];
    		for(int j=0; j<networkWeights[i].length; j++){
    			networkValues[i][j]=0.0;
    			if(i==0){
					networkNodes[i][j] = new Node(); //Input nodes have no weights
    			} else {
    				networkNodes[i][j] = new Node(networkWeights[i][j]); //Create node with layerCounts[i-1] input weights
    			}
    		}
    	}
    }
    public Network(double[][][] networkWeights, int[][] activeFunc){ //Creates network with pre-defined weights and activation functions.
    
    	//-----networkWeights structure-----
    	//networkWeights 		  = whole network
    	//networkWeights[i] 	  = the layer i of the network
    	//networkWeights[i][j]    = the jth node in layer i of the network
    	//networkWeights[i][j][k] = the kth input weight of node j in layer i of the network
    	
    	
    	networkNodes = new Node[networkWeights.length][];
    	networkValues = new double[networkWeights.length][];
    	for(int i=0; i<networkWeights.length; i++){
    		networkNodes[i] = new Node[networkWeights[i].length];
    		networkValues[i] = new double[networkWeights[i].length];
    		for(int j=0; j<networkWeights[i].length; j++){
    			networkValues[i][j]=0.0;
    			if(i==0){
					networkNodes[i][j] = new Node(); //Input nodes have no weights
    			} else {
    				networkNodes[i][j] = new Node(networkWeights[i][j], activeFunc[i][j]); //Create node with layerCounts[i-1] input weights
    			}
    		}
    	}
    }
    public double[] calc(double inputs[]){
    	
    	if(inputs.length!=networkValues[0].length){
    		System.out.println("Number of inputs did not match expected number of inputs!");
    		System.out.println("    Number of inputs: "+inputs.length);
    		System.out.println("    Expected number : "+networkValues[0].length); 
    	}
    	for(int i=0; i<inputs.length; i++){
    		networkValues[0][i] = inputs[i];
    	}
    	for(int i=1; i<networkValues.length; i++){
    		for(int j=0; j<networkValues[i].length; j++){
    			networkValues[i][j] = networkNodes[i][j].calc(networkValues[i-1]);
    		}
    	}
    	return networkValues[networkValues.length-1];
    }
    
    public Network clone(){ //returns a deep copy of this instance
    	return new Network(getNetworkWeights(), getNetworkFuncs());
    }
    
    public void mutateNodes(double maxChange, double funcMutateProb){ //Change the weights of all neurons by a random amount between -maxChange and +maxChange
    	for(Node[] layer : networkNodes){
    		for(Node node : layer){
    			node.mutateWeights(maxChange);
    			if(Math.random()<funcMutateProb){
    				node.randomizeActivationFunc();
    			}
    		}
    	}
    }
    
    public double[][][] getNetworkWeights(){
    	//-----networkWeights structure-----
    	//networkWeights 		  = whole network
    	//networkWeights[i] 	  = the layer i of the network
    	//networkWeights[i][j]    = the jth node in layer i of the network
    	//networkWeights[i][j][k] = the kth input weight of node j in layer i of the network
    	double [][][] networkWeights = new double[networkNodes.length][][];
    	for(int i = 0; i < networkNodes.length; i++){
    		networkWeights[i] = new double[networkNodes[i].length][];
    		for(int j = 0; j < networkNodes[i].length; j++){
    			networkWeights[i][j] = networkNodes[i][j].getWeights();
    		}
    	}
    	return networkWeights;
    }
    
    public int[][] getNetworkFuncs(){
    	int [][] networkFuncs = new int[networkNodes.length][];
    	for(int i = 0; i < networkNodes.length; i++){
    		networkFuncs[i] = new int[networkNodes[i].length];
    		for(int j = 0; j < networkNodes[i].length; j++){
    			networkFuncs[i][j] = networkNodes[i][j].getActivationFunc();
    		}
    	}
    	return networkFuncs;
    }
    
    public String toString(){
    	String out = "";
    	int[][] arr = getNetworkFuncs();
    	for(int row[]:arr){
    		for(int elem:row){
    			out+=elem+" ";
    		}
    		out+="\n";
    	}
    	return out;
    }
}