package physix3d.math;

/**
 * Created by oyvind on 26.02.2016.
 */
public class Matrix {

    private int rows;
    private int columns;
    private double [][] matrix;

    public Matrix(double[][] array) {
        rows = array.length;
        columns = array[0].length;
        matrix = new double[rows][columns];
        matrix = array.clone();
    }

    public Matrix(int rows, int columns, double[] elements) {
        this.rows = rows;
        this.columns = columns;
        matrix = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = elements[i * columns + j];
            }
        }
    }

    public void multiplyRowByScalar(int row, double scalar) {
        for (int j = 0; j < columns; j++) {
            matrix[row][j] *= scalar;
        }
    }

    public void switchTwoRows(int row, int secondRow) {
        for (int j = 0; j < columns; j++) {
            double element = matrix[row][j];
            matrix[row][j] = matrix[secondRow][j];
            matrix[secondRow][j] = element;
        }
    }

    public void addMultipleOfRowToRow(double scalar, int row, int secondRow) {
        for (int j = 0; j < columns; j++) {
            matrix[secondRow][j] += scalar * matrix[row][j];
        }
    }

    public Matrix performGaussJordan() {
        double[][] GJ = new double[rows][columns];
        GJ = matrix.clone();
        int currentRow = 0;
        for (int j = 0; j < columns && currentRow < rows; j++) {
            cleanMatrix();
            double pivot = GJ[currentRow][j];
            int maxRow = currentRow;
            for (int i = currentRow; i < rows; i++) {
                if (Math.abs(pivot) < Math.abs(GJ[i][j])) {
                    pivot = GJ[i][j];
                    maxRow = i;
                }
            }
            if (pivot == 0) {
                continue; // Next column. Row number not increased
            }
            switchTwoRows(currentRow, maxRow);
            multiplyRowByScalar(currentRow, 1 / pivot);
            for (int i = 0; i < currentRow; i++) {
                addMultipleOfRowToRow(-matrix[i][j], currentRow, i);
            }
            for (int i = currentRow + 1; i < rows; i++) {
                addMultipleOfRowToRow(-matrix[i][j], currentRow, i);
            }
            currentRow++;
        }
        Matrix GaussJordan = new Matrix(GJ);
        GaussJordan.cleanMatrix();
        return GaussJordan;
    }

    public void cleanMatrix() {
        double tolerance = 0.000001;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (Math.abs(((int) matrix[i][j]) - matrix[i][j]) < tolerance) {
                    matrix[i][j] = (int) matrix[i][j];
                } else if (Math.abs(((int) matrix[i][j] - 1)
                        - matrix[i][j]) < tolerance) {
                    matrix[i][j] = -1 + (int) matrix[i][j];
                } else if (Math.abs(((int) matrix[i][j] + 1)
                        - matrix[i][j]) < tolerance) {
                    matrix[i][j] = 1 + (int) matrix[i][j];
                }
            }
        }
    }

    public void multiplyMatrixByScalar(double scalar) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] *= scalar;
            }
        }
    }

    public double[] multiplyMatrixByVector(double[] vector) {
        double returnVector[] = new double[vector.length];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                returnVector[i] += matrix[i][j] * vector[j];
            }
        }
        return returnVector;
    }

    public double[] normalizeVector(double[] vector) {
        double value = 0.0;
        for (int i = 0; i < vector.length; i++) {
            value += vector[i] * vector[i];
        }
        value = Math.sqrt(value);
        for (int i = 0; i < vector.length; i++) {
            vector[i] = vector[i] / value;
        }

        return vector;
    }

    public double vectorLength(double[] vector) {
        double vlength = 0.0;
        for (int i = 0; i < vector.length; i++) {
            vlength += Math.pow(vector[i], 2);
        }
        vlength = Math.sqrt(vlength);
        return vlength;
    }

    public double[] powerMatrix() {
        double[] x = new double[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            x[i] = Math.random() * 10;
        }
        x = normalizeVector(x);

        double[] Ax = new double[x.length];
        Ax = multiplyMatrixByVector(x);
        Ax = normalizeVector(Ax);

        for (int i = 0; i < 1000; i++) {
            x = Ax;
            Ax = normalizeVector(multiplyMatrixByVector(x));
        }

        double[] y = multiplyMatrixByVector(x);
        return y;
    }

    @Override
    public String toString() {
        String matrixString = "";
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrixString += String.format("%6.3f ", matrix[i][j]);
            }
        }
        return matrixString;
    }
}
