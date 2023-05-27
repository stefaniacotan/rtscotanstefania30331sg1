package laboratory6;

import java.util.ArrayList;

public class Supervisor extends Thread {
    public P_user ps_o1, ps_i2;
    public P_list ps_1;
    public P_ext ps_i1;
    public P ps_2, ps_3;
    public volatile P ps_o2;

    public Supervisor() {
        ps_o1 = new P_user(0);
        ps_i2 = new P_user(0);
        ps_1 = new P_list();
        ps_2 = new P();
        ps_3 = new P();
        ps_2.x = 0;
        ps_3.x = 0;

        ps_o2 = new P();
        ps_o2.x = 0;
    }

    @Override
    public void run() {
        while (true) {
            if (ps_i1 == null && ps_1.list.isEmpty()) {
                try {
                    Thread.sleep(200); // sleep so we can read some values
                } catch (Exception e) {
                }
            }

            if (ps_i1 != null) {
                ps_1.list.add(ps_i1);
                ps_i1 = null;
            }

            if (!ps_1.list.isEmpty()) {
                if (ps_2 != null && ps_1.list.get(0).r != ps_2.x) {
                    ps_o1.x = ps_1.list.get(0).r;
                    ps_3.x = ps_1.list.get(0).e;
                    ps_2 = null;
                    try {
                        ps_o1.tryAcquire();
                        ps_o1.release();
                    } catch (Exception e) {
                    }
                } else if (ps_2 != null && ps_1.list.get(0).r == ps_2.x) {
                    ps_1.list.remove(0); // remove element after getting position
                }
            }

            if (ps_i2 != null) {
                ps_2 = new P();
                ps_2.x = ps_i2.x;
                ps_o2.x = ps_i2.x;
            }

        }
    }
}

class P_list {
    ArrayList<P_ext> list;

    public P_list() {
        list = new ArrayList<P_ext>();
    }
}

class P_ext {
    public int r, e, l;

    public P_ext(int r, int e, int l) {
        this.r = r;// LOCATION
        this.e = e;// EARLIEST EXECUTION TIME
        this.l = l;// LATEST EXECUTION TIME
    }
}