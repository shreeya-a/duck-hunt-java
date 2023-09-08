import Dcomponent.dcom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
//project completed
public class Main extends JFrame {
    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setTitle("Duck Hunt");
        frame.setSize(1000,800);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        dcom panel = new dcom();
        frame.add(panel);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                panel.start();
            }
        });
    }
}