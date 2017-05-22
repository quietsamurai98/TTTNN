/**
 * @(#)CustomUtils.java
 *
 *
 * @author 
 * @version 1.00 2017/5/19
 */


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
}