package UI;

import javax.swing.*;

public class RegisterJFrame extends JFrame {

    public RegisterJFrame(){
        initRegister();

        this.setVisible(true);

    }

    private void initRegister() {
        this.setSize(488,500);
        this.setTitle("注册");
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setLayout(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
