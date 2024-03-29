package laboratory2.app2;
import laboratory2.app2.Model.Fir;
import laboratory2.app2.Model.UpdateNotification;
import laboratory2.app2.View.ProgressTracker;
import laboratory2.app2.View.Window;

import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {
    private Window window;

    public void Start(int threadNumber, int processorLoad) {
        window = new Window();

        for (int i = 0; i < threadNumber; i++) {
            ProgressTracker thread = new ProgressTracker(i, 30 * i);
            window.addThread(thread);
        }

        for (int i = 0; i < threadNumber; i++) {
            Fir fir = new Fir(i, processorLoad);
            fir.addObserver(this);

            Thread firThread = new Thread(fir);
            if (i + 2 > Thread.MAX_PRIORITY) {
                System.out.println("Thread " + i + " has a priority of " + (i + 2) + " which is higher than the maximum priority of " + Thread.MAX_PRIORITY);
            } else {
                firThread.setPriority(i + 2);
                firThread.start();
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        // Make sure the arg is of type ThreadUpdateNotification
        if (!(arg instanceof UpdateNotification notification)) {
            return;
        }

        window.setThreadProgress(notification.id, notification.progress);
    }
}