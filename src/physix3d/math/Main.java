package physix3d.math;

/**
 * @author �yvind
 *
 */
public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        double[][] matrisen = {{1.0, 3.0, 1.0, 9.0}, {1.0, 1.0, -1.0, 1.0}, {3.0, 11.0, 5.0, 35.0}};
        Matrix matrise = new Matrix(matrisen);

        for (int i = 0; i < matrisen.length; i++) {
            for (int j = 0; j < matrisen[i].length; j++) {
                System.out.printf("%8.2f", matrisen[i][j]);
            }
            System.out.println();
        }
        System.out.printf("%nGjennomfører Gauss Jordan:%n%n");
        matrise = matrise.performGaussJordan();

        for (int i = 0; i < matrisen.length; i++) {
            for (int j = 0; j < matrisen[i].length; j++) {
                System.out.printf("%8.2f", matrisen[i][j]);
            }
            System.out.println();
        }
    }
}
