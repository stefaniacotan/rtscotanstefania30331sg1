package laboratory2.app1;

public class Fir extends Thread {
    int id;
    Window win;
    int processorLoad;
    Fir(int id,int priority,Window win, int procLoad){
        this.id=id;
        this.win=win;
        this.processorLoad=procLoad;
        this.setPriority(priority);
    }
    public void run(){
        int c=0;
        while(c<1000){
            for(int j=0;j<this.processorLoad;j++){
                j++;j--;
            }
            c++;
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.win.setProgressValue(id, c);
        }
    }
}