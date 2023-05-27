package laboratory6;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException{
        P_move p_move = new P_move(0);
        p_move.move = 0;
        P_arrived p_arrived = new P_arrived(0);
        Supervisor supervisor=new Supervisor();

        // create plant and controller
        Plant p = new Plant();
        p.init(p_move, p_arrived);


        P_user userdemand= supervisor.ps_o1;
        P_user userdisplay= supervisor.ps_i2;
        Controller c = new Controller();
        c.init(userdemand, userdisplay, p_move, p_arrived);

        p.start();
        c.start();
        supervisor.start();

        // create message printer
        MessagePrinter printer = new MessagePrinter();
        printer.display = supervisor.ps_o2;
        printer.supervisor = supervisor;
        printer.start();


        // listen for requests
        Scanner in = new Scanner(System.in);
        System.out.print("enter places to go: ");
        while (true) {
            int demand = in.nextInt();
            supervisor.ps_i1= new P_ext(demand,2,5);

        }
    }
}

class MessagePrinter extends Thread{ // this is the user display listener
    P display;
    Supervisor supervisor;
    public void run() {
        int prev;
        prev = 0;
        while (true) {
            try {
                // display.acquire();
                // the if statement is too slow to detect all the moves
                // if (prev != supervisor.ps_o2.x)
                //
                try {
                    Thread.sleep(3000); // print position every 3 seconds
                } catch (Exception e) {}
                System.out.println("arrived at: " + supervisor.ps_o2.x);
                // prev = display.x;
                prev = supervisor.ps_o2.x;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}