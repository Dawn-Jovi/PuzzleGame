package UI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginJFrame extends JFrame implements ActionListener{



    //set the size of the button
    int buttonWidth =150;
    int buttonHeight =75;

    JButton jbtLogin = new JButton("登录");
    JButton jbtRegister = new JButton("注册");

    public LoginJFrame(){

        initJFrame();

        jbtLogin.setBounds(0, 0, buttonWidth, buttonHeight);
        jbtLogin.addActionListener(this);

        jbtRegister.setBounds(buttonWidth + 10 , 0, buttonWidth, buttonHeight);
        jbtRegister.addActionListener(this);

        this.getContentPane().add(jbtLogin);
        this.getContentPane().add(jbtRegister);

        this.setVisible(true);

    }

    private void initJFrame() {
        this.setSize(488, 430);
        this.setTitle("登录");
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setLayout(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        if(source == jbtLogin){
            System.out.println("登录");
            this.setVisible(false);
            new GameJFrame();
        }else if (source == jbtRegister){
            System.out.println("注册");
            this.setVisible(false);
            new RegisterJFrame();
        }
    }
}
