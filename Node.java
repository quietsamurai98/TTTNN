/**
 * @(#)Node.java
 *
 *
 * @Kenneth Palmer 
 * @version 1.00 2017/5/18
 *
 * Object describing an individual node in neural network
 */

import java.util.Arrays;
public class Node {
	double[] weights;
	boolean isInput;
	int activeFuncID;
    public Node() {
    	//No parameters = input node
    	weights = new double[1];
    	weights[0] = 1.0;
    	isInput = true;
    	this.activeFuncID = 0;
    }
    public Node(int numWeights){
    	//Creates node with numWeights input weights, plus 1 bias weight
    	isInput = false;
    	this.activeFuncID = (int) (Math.random()*activeFunc.length);
    	weights = new double[numWeights + 1];
    	double weightRange = 1; //Initial value of weights will be some random value from -weightRange to + weightRange
    	for(int i=0; i<numWeights; i++){
    		weights[i] = (Math.random()*2.0-1.0)*weightRange; //Initial weight is random number between -sqrt(2/(n+1)) and sqrt(2/(n+1))
    	}
    }
    public Node(double[] givenWeights){
    	//Creates node with provided weights
    	isInput = false;
    	weights = new double[givenWeights.length];
    	this.activeFuncID = (int) (Math.random()*activeFunc.length);
    	for(int i=0; i<givenWeights.length; i++){
    		weights[i] = givenWeights[i];
    	}
    }
    public Node(double[] givenWeights, int activeFuncID){
    	//Creates node with provided weights
    	isInput = false;
    	this.activeFuncID = activeFuncID;
    	weights = new double[givenWeights.length];
    	for(int i=0; i<givenWeights.length; i++){
    		weights[i] = givenWeights[i];
    	}
    }
    public double calc(double[] inputs){ //Returns the neuron output given a list of inputs
    	return activationFunc(sumInputs(inputs));
    }
    
    private double sumInputs(double[] inputs){ //Returns sum of weighted inputs+bias
    	if(inputs.length+1!=weights.length){
    		System.out.println("Number of inputs did not match number of weights!");
    		System.out.println("    Number of inputs : "+inputs.length+" ("+ inputs.length+1 + " including bias neuron)");
    		System.out.println("    Number of weights: "+weights.length); 
    	}
    	double sum = 0.0;
    	for(int i=0; i<inputs.length; i++){
    		sum += inputs[i] * weights[i];
    	}
    	sum += 1 * weights[weights.length-1]; //add weighted bias
    	return sum;
    }
    private double activationFunc(double sum){ //Applys activation function to sum
    	return (1.0 / (1.0 + Math.exp(-sum))); //Sigmoid
    	//return activeFunc[activeFuncID].f(sum);
    }
    public int getActivationFunc(){
    	return activeFuncID;
    }
    public void randomizeActivationFunc(){
    	if(!isInput)
    		this.activeFuncID = (int) (Math.random()*activeFunc.length);
    }
    public double[] getWeights(){
    	return CustomUtils.deepCopy(weights);
    }
    public void setWeights(double[] inputs){
    	for(int i=0; i<inputs.length; i++){
    		weights[i] = inputs[i];
    	}
    }
    public void mutateWeights(double maxChange){
    	for(int i=0; i<weights.length; i++){
    		weights[i]+=(Math.random()*2.0-1.0)*maxChange;
    	}
    }
    
    interface ActivationFunction{
    	double f(double x);
    }
    private ActivationFunction[] activeFunc = new ActivationFunction[]{
    	new ActivationFunction(){
    		public double f(double x){
    			return (1.0 / (1.0 + Math.exp(-x))); //Sigmoid, index 0
    		}
    	}
//    	,new ActivationFunction(){
//    		public double f(double x){
//    			return x; //Identity, index 1
//    		}
//    	}
//    	,new ActivationFunction(){
//    		public double f(double x){
//    			return Math.tanh(x); //Tanh, index 2
//    		}
//    	}
//    	,new ActivationFunction(){
//    		public double f(double x){
//    			return (x>0) ? x : 0; //ReLU, index 3
//    		}
//    	}
//    	,new ActivationFunction(){
//    		public double f(double x){
//    			return Math.signum(x); //Sign, index 4
//    		}
//    	}
//    	,new ActivationFunction(){
//    		public double f(double x){
//    			return (x>0) ? 1.0 : 0.0; //Step, index 5
//    		}
//    	}
//    	,new ActivationFunction(){
//    		public double f(double x){
//    			return Math.sin(x); //Sine, index 6
//    		}
//    	}
    };
}