package whling.knowledge.datastructure;

public class Matrix {

    /**
     * 二维数组顺时针输出
     *
     * @param args
     */
    public static void main(String[] args) {

        int n = 5;
        int[][] matrix = {
                {1, 2, 3, 4, 5},
                {16, 17, 18, 19, 6},
                {15, 24, 25, 20, 7},
                {14, 23, 22, 21, 8},
                {13, 12, 11, 10, 9}
        };

        int left = 0, right = n, top = 0, down = n;

        while (left <= right && top <= down) {
            for (int i = left; i < right; i++) {
                System.out.print(matrix[left][i] + "\t");
            }
            top++;

            for (int i = top; i < down; i++) {
                System.out.print(matrix[i][right - 1] + "\t");
            }
            right--;

            for (int i = right - 1; i >= left; i--) {
                System.out.print(matrix[down - 1][i] + "\t");
            }
            down--;

            for (int i = down - 1; i >= top; i--) {
                System.out.print(matrix[i][left] + "\t");
            }
            left++;
        }
        System.out.println("");

        left = 0;
        right = n;
        top = 0;
        down = n;
        /**
         * 逆时针
         */
        while (left <= right && top <= down) {
            for (int i = top; i < down; i++) {
                System.out.print(matrix[i][left] + "\t");
            }
            left++;

            for (int i = left; i < right; i++) {
                System.out.print(matrix[down - 1][i] + "\t");
            }
            down--;

            for (int i = down - 1; i >= top; i--) {
                System.out.print(matrix[i][right - 1] + "\t");
            }
            right--;

            for (int i = right - 1; i >= left; i--) {
                System.out.print(matrix[top][i] + "\t");
            }
            top++;
        }


    }


}
