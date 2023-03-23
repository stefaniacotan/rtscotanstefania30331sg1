package laboratory2.app2;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class Window extends JProgressBar implements Observer {

    private static final long serialVersionUID = 1L;

    public Window() {
        setMinimum(0);
        setMaximum(100);
    }

    @Override
    public void update(Observable o, Object arg) {
        int value = getValue() + 1;
        if (value > 100) {
            value = 0;
        }
        setValue(value);
    }
}
