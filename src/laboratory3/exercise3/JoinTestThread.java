package laboratory3.exercise3;


class JoinTestThread extends Thread {
    Thread t;
    int n;
    long sum = 0;

    JoinTestThread(int n, Thread t) {
        this.n = n;
        this.t = t;
    }

    public void run() {
        for (int i = 1; i <= n / 2; i++) {
            if (n % i == 0) {
                sum = sum + i;
            }
        }
        sum = sum + n; // add the number itself

        try {
            if (t != null) t.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Main.sum += sum;
    }
}
