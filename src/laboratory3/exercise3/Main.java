package laboratory3.exercise3;


public class Main {
    public static long sum = 0;

    public static void main(String[] args) {
        JoinTestThread w1 = new JoinTestThread(50001, null);
        JoinTestThread w2 = new JoinTestThread(19999, w1);
        w1.start();
        w2.start();

        try {
            w1.join();
            w2.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(sum);
    }

}
