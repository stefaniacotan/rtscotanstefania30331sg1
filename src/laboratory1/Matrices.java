package laboratory1;

public class Matrices {

    public static int[][] addMatrices(int[][] a, int[][] b){
        int[][] sum = new int[3][3];
        int i, j;
        for(i = 0; i < 3; i++)
            for(j = 0; j < 3; j++){
                sum[i][j] = a[i][j] + b[i][j];
            }
        return sum;
    }

    public static int[][] multiplyMatrices(int[][] a, int[][] b){
        int[][] product = new int[3][3];
        int i, j, k;
        for(i = 0; i < 3; i++)
            for(j = 0; j < 3; j++){
                int sum = 0;
                for(k = 0; k < 3; k++) {
                    sum += a[i][k] * b[k][j];
                }
                product[i][j] = sum;
            }
        return product;
    }
    public static void printMatrix(int[][] a){
        int i, j;
        for(i = 0; i < 3; i++) {
            for(j = 0; j < 3; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        int[][] matrix1 = {{2, 3, 1},{7, 1, 6}, {9, 2, 4}};
        int[][] matrix2 = {{8, 5, 3},{3, 9, 2}, {2, 7, 3}};
        int[][] product = multiplyMatrices(matrix1, matrix2);
        int[][] sum = addMatrices(matrix1, matrix2);
        System.out.println("The sum of the matrices is: ");
        printMatrix(sum);
        System.out.println("the product of the matrices is: ");
        printMatrix(product);
    }
}
