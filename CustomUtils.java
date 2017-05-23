/**
 * @(#)CustomUtils.java
 *
 *
 * @author 
 * @version 1.00 2017/5/19
 */
import java.util.Arrays;
import java.util.Comparator;
import java.util.ArrayList;

public class CustomUtils {

    public CustomUtils() {
    }
    
    public static double[] deepCopy(double[] in){
    	int l = in.length;
    	double[] out = new double[l];
    	for(int i = 0; i<l; i++){
    		out[i] = in[i];
    	}
    	return out;
    }
    public static double sigmoid(double x){
    	return (1.0 / (1.0 + Math.exp(-x)));
    }
    
    public static void main(String[] args){
    	double[] nnout = {0.5433,0.9264,0.9795,0.3439,0.9898,0.5597,0.6421,0.4981,0.3981};
    	System.out.println(sortOutputsList(nnout));
    	Double[][] output = sortOutputs(nnout);
    	System.out.print("[ ");
    	for(int i = 0; i < 9; i++){
    		System.out.print(((int)(output[i][1] + 0.5)) + " ");
    	}
    	System.out.println("]");
    }
    public static Double[][] sortOutputs(double[] nnout){
    	ArrayComparator comparator = new ArrayComparator(0, false);
    	Double[][] output = new Double[9][2];
		for(int j = 0; j < 9; j++){
			output[j][0] = nnout[j];
			output[j][1] = (double) j;
		}
		Arrays.sort(output, comparator);
		return output;
    }
    public static ArrayList<Integer> sortOutputsList(double[] nnout){
    	ArrayList<Integer> moves = new ArrayList<Integer>();
    	ArrayList<Double> values = new ArrayList<Double>();
    	moves.add(0);
    	values.add(nnout[0]);
    	int l = 1;
    	for(int i = 1; i < 9; i++){
    		for(int j = 0; j < i && i==l; j++){
    			if(values.get(j)<nnout[i]){
    				values.add(j, nnout[i]);
    				moves.add(j, i);
    				l++;
    			}
    		}
    		if(i==l){
    			values.add(nnout[i]);
				moves.add(i);
				l++;
    		}
    	}
    	return moves;
    }
}