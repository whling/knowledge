package whling.knowledge.other;

/**
 * cpu缓存介绍，以及缓存使用注意
 *
 * https://www.cnblogs.com/techyc/p/3607085.html
 */
public class L1CacheMiss {


    private static final int RUNS = 10;
    private static final int DIMENSION_1 = 1024 * 1024;
    private static final int DIMENSION_2 = 6;

    private static long[][] longs;

    public static void main(String[] args) throws Exception {
        Thread.sleep(10000);
        longs = new long[DIMENSION_1][];
        for (int i = 0; i < DIMENSION_1; i++) {
            longs[i] = new long[DIMENSION_2];
            for (int j = 0; j < DIMENSION_2; j++) {
                longs[i][j] = 0L;
            }
        }
        System.out.println("starting....");

        long sum = 0L;
        for (int r = 0; r < RUNS; r++) {

            final long start = System.nanoTime();

            //slow
            for (int j = 0; j < DIMENSION_2; j++) {
                for (int i = 0; i < DIMENSION_1; i++) {
                    sum += longs[i][j];
                }
            }
            /**
             * 25817917
             * 24691455
             * 27329677
             * 28480908
             * 28712818
             * 29212989
             * 28624472
             * 39875536
             * 31153459
             * 29506226
             */

            //fast
//            for (int i = 0; i < DIMENSION_1; i++) {
//                for (int j = 0; j < DIMENSION_2; j++) {
//                    sum += longs[i][j];
//                }
//            }
            /**
             * 15409634
             * 13041591
             * 8809932
             * 16075900
             * 9277125
             * 9248281
             * 9009215
             * 8659385
             * 8440792
             * 8315346
             */

            System.out.println((System.nanoTime() - start));
        }

    }

}
