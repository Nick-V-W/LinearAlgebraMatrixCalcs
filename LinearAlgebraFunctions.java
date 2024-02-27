
public class LinearAlgebraFunctions {

	/**
	 * 
	 * @param vector1
	 * @param vector2
	 * @return 
	 */
	public static float dotProduct(float[] vector1, float[] vector2) {
		assert vector1.length == vector2.length : "Violation of: Vectors are same length";
		
		float finalProduct = 0;
		for (int i = 0; i < vector1.length; i++) {
			float product = vector1[i] * vector2[i];
			finalProduct += product;
		}
		return finalProduct;
	}
	
	public static boolean isInvertible(float[][] matrixM) {
		assert matrixM.length != 0 : "Violation of: Zero dimensional matrix";
		assert matrixM[0].length != 0 : "Violation of: Zero dimensional matrix";
		
		boolean invertible = false;
		if (matrixM.length == matrixM[0].length) {
			float[][] rref = computeRREF(matrixM);
			invertible = isStandardBasis(rref);
		}
		
		return invertible;
	}
	
	public static boolean isStandardBasis(float[][] rref) {
		boolean isBasis = true;
		for (int row = 0; row < rref.length; row++) {
			for (int col = 0; col < rref[row].length; col++) {
				if (row == col) {
					if (rref[row][col] != 1) {
						isBasis = false;
					}
				} else if (rref[row][col] != 0) {	
					isBasis = false;
				}
			}
		}
		return isBasis;
	}
	
	
	public static float[][] computeRREF(float[][] matrix) {
		assert matrix.length != 0 : "Violation of: Zero dimensional matrix";
		assert matrix[0].length != 0 : "Violation of: Zero dimensional matrix";
		
		int rows = matrix.length;
		int cols = matrix[0].length;
		float[][] rref = new float[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				rref[i][j] = matrix[i][j];
			}
		}
		
		int posR = 0;
		int posC = 0;
		while ((posR < rows) && (posC < cols)) {
			for (posC = 0; posC < cols; posC++) {
				boolean found = findPivot(rref, posC, posR);
				// if pivot is found, advance to next row
				if (found) {
					posR++;
				}
			}
		}
		
		return rref;
	}
	
	/**
	 * Helper method for computeRREF. Finds pivot.
	 * @param matrix
	 * @param col
	 * @return
	 */
	public static boolean findPivot(float[][] matrix, int col, int row) {
		boolean foundPivot = true;
		int pivPos = -1;
		int secPiv = -1;
		for (int markOne = row; markOne < matrix.length; markOne++) {
			float point = matrix[markOne][col];
			if ((point == 1) || (point == -1)) {
				pivPos = markOne;
				markOne = matrix.length;
			} else if (matrix[markOne][col] != 0) {
				if (secPiv != row) {
					secPiv = markOne;
				}
			}
		}
		if (pivPos != -1) {
			pivotMath(matrix, row, pivPos, col);
		} else if (secPiv != -1) {
			pivotMath(matrix, row, secPiv, col);
		} else {
			foundPivot = false;
		}
		return foundPivot;
	}
	
	/**
	 * Provides algorithm for isolating rows around pivot.
	 * @param matrix
	 * @param row
	 * @param pivPos
	 * @param col
	 */
	public static void pivotMath(float[][] matrix, int row, int pivPos, int col) {
		if (pivPos != row) {
			// swap rows
			swapRows(matrix, pivPos, row);
		}
		// perform row math to clear col
		for (int i = 0; i < matrix.length; i++) {
			if (i != row) {
				scaleRow(matrix, i, col);
				elimRow(matrix, i, row);
			}
		}
	}
	
	/**
	 * Helper method for pivotMath. Swaps given rows
	 * @param rref
	 * @param row1
	 * @param row2
	 */
	private static void swapRows(float[][] rref, int row1, int row2) {
		int lengthOfRow = rref[row1].length;
		float[] newRow1 = new float[lengthOfRow];
		for (int i = 0; i < lengthOfRow; i++) {
			newRow1[i] = rref[row2][i];
			rref[row2][i] = rref[row1][i];
		}
		for (int i = 0; i < lengthOfRow; i++) {
			rref[row1][i] = newRow1[i];
		}
	}
	
	/**
	 * Helper method for pivotMath. Scales row to make it easy to eliminate 
	 * @param rref
	 * @param row
	 * @param pivCol
	 */
	private static void scaleRow(float[][] rref, int row, int pivCol) {
		float digit = rref[row][pivCol];
		if (digit != 0) {
			if (digit < 0) {
				digit *= -1;
				for (int i = 0; i < rref[row].length; i++) {
					rref[row][i] *= -1;
				}
			}
			for (int i = 0; i < rref[row].length; i++) {
				float value = rref[row][i];
				rref[row][i] = value * (1/digit);
			}	
		}
	}
	
	/**
	 * Helper method for pivotMath. Eliminates digits in the same column as pivot
	 * @param rref
	 * @param rowToElim
	 * @param pivRow
	 */
	private static void elimRow(float[][] rref, int rowToElim, int pivRow) {
		if (rref[rowToElim][pivRow] != 0) {
			for (int i = 0; i < rref[pivRow].length; i++) {
				rref[rowToElim][i] -= rref[pivRow][i];
			}		
		}

	}
	
	/**
	 * Prints matrix
	 * @param matrix
	 */
	public static void printMatrix(float[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			System.out.print("| ");
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println("|");
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		float[] vector1 = {2, 0, 1};
		float[] vector2 = {0, 1, 0};
		float[] vector3 = {1, 0, 0};

		float[][] matrix = {vector1, vector2, vector3};
		printMatrix(matrix);
		System.out.println(isInvertible(matrix));
		printMatrix(computeRREF(matrix));
	}

}
