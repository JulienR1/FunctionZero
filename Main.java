package main;

import java.util.Scanner;

public class Main {
	
	static final double PRECISION = Math.pow(10, -7); 
	static final int MAX_NUM_OF_ITERATIONS = 100;
	
	public static void main(String[] args) {
		// Get the range of the function
		double[] functionRange = GetFunctionZeroRange();
		VerifyRange(functionRange);
		
		// Solve for the function's zero
		double answer = SolveFunction(functionRange);
		System.out.println("Answer: " + answer);
	}

	static double[] GetFunctionZeroRange() {
		// Temporary variables used to get the inputs
		double[] knownValues = new double[2];
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		
		// Get the range of the known function's zero.
		System.out.println("Enter the range of the function's zero");
		for (int i = 0; i < 2; i++) {
			System.out.print(">");		
			knownValues[i] = input.nextInt();
		}
		
		// Sort the values ascending
		if (knownValues[0] > knownValues[1]) {
			double tempVar = knownValues[1];
			knownValues[1] = knownValues[0];
			knownValues[0] = tempVar;
		}
		
		// return the sorted range
		return knownValues;
	}
	
	// Make sure that the entered values are a-okay
	static boolean VerifyRange(double[] range) {
		return (Math.signum(GetYAt(range[0])) != Math.signum(GetYAt(range[1])));
	}
	
	// Get the function's zero
	static double SolveFunction(double[] range) {
		double xOnePicture, xTwoPicture;
		double lastX = 0;
		
		// Make sure that the entered values are not the solutions
		xOnePicture = GetYAt(range[0]);
		if (xOnePicture == 0)
			return range[0];
		xTwoPicture = GetYAt(range[1]);
		if(xTwoPicture == 0)
			return range[1];
		
		int negativeSide = (xOnePicture < 0) ? 0 : 1;
		int positiveSide = 1 - negativeSide;
		
		// Converge toward the answer using bissection
		for (int i = 0; i < MAX_NUM_OF_ITERATIONS;i++) {
			// Get the center value
			double xCenter = (range[0] + range[1]) / 2;
			double centerPicture = GetYAt(xCenter);
			
			// Replace the correct value in the range
			if (Math.signum(centerPicture) == -1) {
				range[negativeSide] = xCenter;
			} else if (Math.signum(centerPicture) == 1) {
				range[positiveSide] = xCenter;
			} else if(centerPicture == 0) {
				// Is exactly the answer
				return xCenter;
			}
			
			// Define if it is in the precision range
			if (Math.abs(lastX - xCenter) <= PRECISION) {
				return xCenter;
			}
			lastX = xCenter;
		}
			
		System.out.println("No answer found in " + MAX_NUM_OF_ITERATIONS + " iterations.");
		return 0;
	}
	
	// Returns the value of y at a certain x value
	static double GetYAt(double x) {
		return Math.pow(Math.E, x) - (x + 5);
	}
}