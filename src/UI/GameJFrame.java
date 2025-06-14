package UI;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.Random;


public class GameJFrame extends JFrame implements KeyListener, ActionListener {

    private static final int VICTORY_IMAGE_SIZE = 512;
    private static final int IMAGE_SIZE = 150;

    //The objects with two options above the menu
    JMenu functionJMenu = new JMenu("功能");
    JMenu aboutJMenu = new JMenu("我们");
    JMenu imageSubMenu = new JMenu("切换图片");
    //The object of the entry under the options
    JMenuItem imageItem1 = new JMenuItem("图片1");
    JMenuItem imageItem2 = new JMenuItem("图片2");
    JMenuItem imageItem3 = new JMenuItem("图片3");
    JMenuItem imageItem4 = new JMenuItem("图片4");
    JMenuItem replayItem = new JMenuItem("重新游戏");
    JMenuItem reLoginItem = new JMenuItem("重新登录");
    JMenuItem closeItem = new JMenuItem("关闭游戏");

    JMenuItem accountItem = new JMenuItem("微信");

    //initial a 2D array
    //Objective: In order to manage data
    int[][] data = new int[4][4];

    //Record the position of the blank square in the 2D array
    int x = 0;
    int y = 0;

    int imageNum = 0;

    //count step
    int step = 0;

    int[][] win = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };

    public GameJFrame(){

        //initialize the frame
        initJFrame();

        //initialize the menu
        initJMenuBar();
        
        //initialize the data
        initData();

        //initialize the image
        initImage();

        this.setVisible(true);

    }

    //initialize data
    private void initData() {
        int[] tempArr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        Random r = new Random();
        for (int i = 0; i < tempArr.length; i++) {
            int index = r.nextInt(tempArr.length);
            int temp = tempArr[i];
            tempArr[i] = tempArr[index];
            tempArr[index] = temp;
        }

        for (int i = 0; i < tempArr.length; i++) {
            if(tempArr[i] == 0){
                x = i / 4;
                y = i % 4;
            }
            data[i / 4][i % 4] = tempArr[i];
        }
    }

    //initialize the image
    private void initImage() {

        this.getContentPane().removeAll();

        // 获取窗口尺寸
        int frameWidth = this.getWidth();
        int frameHeight = this.getHeight();
        // 计算拼图区域的尺寸
        int puzzleWidth = IMAGE_SIZE * 4;
        int puzzleHeight = IMAGE_SIZE * 4;
        // 计算居中位置
        int CENTER_X = (frameWidth - puzzleWidth) / 2;
        int CENTER_Y = (frameHeight - puzzleHeight) / 2;
        int VICTORY_X = (frameWidth - VICTORY_IMAGE_SIZE) / 2;
        int VICTORY_Y = (frameHeight - VICTORY_IMAGE_SIZE) / 2;

        if(victory()){
            JLabel winJLabel = new JLabel(new ImageIcon(GameJFrame.class.getResource("/Image/victory.png")));
            winJLabel.setBounds(VICTORY_X,VICTORY_Y,VICTORY_IMAGE_SIZE,VICTORY_IMAGE_SIZE);
            this.getContentPane().add(winJLabel);
        }

        JLabel stepCount = new JLabel("步数："+ step);
        stepCount.setBounds(50,30,100,20);
        this.getContentPane().add(stepCount);

        JLabel imageNo = new JLabel("当前：图片"+(imageNum+1));
        imageNo.setBounds(130,30,100,20);
        this.getContentPane().add(imageNo);

        JPanel puzzlePanel = new JPanel(new GridLayout(4, 4));
        puzzlePanel.setBounds(CENTER_X, CENTER_Y, puzzleWidth, puzzleHeight);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int number = data[i][j];
                // 创建一个带有图像的对象
                // 创建一个JLabel对象
                URL oneImage = null;
                String imagePath = String.format("/Image/%04d/%d.jpg", imageNum, number);
                oneImage = GameJFrame.class.getResource(imagePath);
                JLabel jLabel;
                if (oneImage != null) {
                    jLabel = new JLabel(new ImageIcon(oneImage));
                } else {
                    // 当图像资源不存在时，创建一个默认的JLabel
                    jLabel = new JLabel();
                    jLabel.setOpaque(true);
                    jLabel.setBackground(Color.LIGHT_GRAY);
                }
                // 指定图像的位置
//        jLabel.setBounds(IMAGE_SIZE * j  + CENTER_X,IMAGE_SIZE * i + CENTER_Y,IMAGE_SIZE,IMAGE_SIZE);
                jLabel.setBorder(new BevelBorder(BevelBorder.RAISED));

                // 将容器添加到界面
                puzzlePanel.add(jLabel);
            }
        }

        this.getContentPane().add(puzzlePanel);

        this.getContentPane().repaint();
        this.getContentPane().revalidate();
    }

    //initialize the menu
    private void initJMenuBar() {
        JMenuBar jMenuBar = new JMenuBar();

        imageSubMenu.add(imageItem1);
        imageSubMenu.add(imageItem2);
        imageSubMenu.add(imageItem3);
        imageSubMenu.add(imageItem4);

        //Associate the entries under each option with the options themselves
        functionJMenu.add(imageSubMenu);
        functionJMenu.add(replayItem);
        functionJMenu.add(reLoginItem);
        functionJMenu.add(closeItem);
        aboutJMenu.add(accountItem);


        replayItem.addActionListener(this);
        reLoginItem.addActionListener(this);
        closeItem.addActionListener(this);
        accountItem.addActionListener(this);
        imageItem1.addActionListener(this);
        imageItem2.addActionListener(this);
        imageItem3.addActionListener(this);
        imageItem4.addActionListener(this);


        //Add two options with submenus to the top-menu
        jMenuBar.add(functionJMenu);
        jMenuBar.add(aboutJMenu);

        //set top-menu
        this.setJMenuBar(jMenuBar);
    }

    //initialize the frame
    private void initJFrame() {
        this.setSize(900,900);
        this.setTitle("拼图 v1.0");
        //set the frame to the top
        this.setAlwaysOnTop(true);
        //set the frame to the center
        this.setLocationRelativeTo(null);
        //set exit mode
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(null);
        //add a keyboard listening event
        this.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(victory()) return;
        if (code == KeyEvent.VK_Q) {
            this.getContentPane().removeAll();

            URL fullImage = null;
            String imagePath = String.format("/Image/%04d/preview.jpg", imageNum);
            fullImage = GameJFrame.class.getResource(imagePath);
            JLabel all = new JLabel(new ImageIcon(fullImage));

            all.setBounds(150, 140, 600, 600);
            this.getContentPane().add(all);
            this.getContentPane().repaint();
        } 
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //judgment on up, down, left and right
        int code = e.getKeyCode();
        int newX = x;
        int newY = y;

        if(victory()) return;
        switch (code){
            //up
            case KeyEvent.VK_W :
                newX = x + 1;
                break;
            //down
            case KeyEvent.VK_S:
                newX = x - 1;
                break;
            case KeyEvent.VK_A:
                newY = y + 1;
                break;
            case KeyEvent.VK_D:
                newY = y - 1;
                break;
            case KeyEvent.VK_Q:
                initImage();
                return;
            case  KeyEvent.VK_P:
                data = new int[][]{
                        {1, 2, 3, 4},
                        {5, 6, 7, 8},
                        {9, 10, 11, 12},
                        {13, 14, 15, 0}
                };
                x = 3;
                y = 3;
                initImage();
                return;
            default:return;
        }

        if(newX < 0 || newX >3 || newY < 0 || newY > 3) return;

        data[x][y] = data[newX][newY];
        data[newX][newY] = 0;
        x = newX;
        y = newY;
        step++;

        initImage();
    }

    //check if two arrays are completely identical
    public boolean victory(){
        for (int i = 0; i < data.length; i++) {
            for (int j = 0;  j< data[i].length; j++) {
                if (data[i][j] != win[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();

        if (obj == replayItem){
            System.out.println("重新游戏");
            replayGame();
        } else if (obj == reLoginItem) {
            System.out.println("重新登录");
            this.setVisible(false);
            new LoginJFrame();
        } else if (obj == closeItem) {
            System.out.println("关闭游戏");
            System.exit(0);
        } else if (obj == accountItem) {
            System.out.println("微信");
            wxJLabel();
        } else if (obj == imageItem1) {
            imageNum = 0;
            replayGame();
        } else if (obj == imageItem2) {
            imageNum = 1;
            replayGame();
        } else if (obj == imageItem3) {
            imageNum = 2;
            replayGame();
        } else  if (obj == imageItem4){
            imageNum = 3;
            replayGame();
        }
    }

    private static void wxJLabel() {
        JDialog jDialog = new JDialog();
        JLabel jLabel = new JLabel(new ImageIcon(GameJFrame.class.getResource("/Image/wx.jpg")));
        jLabel.setBounds(0,0,570,777);
        jDialog.getContentPane().add(jLabel);
        jDialog.setSize(670,877);
        jDialog.setAlwaysOnTop(true);
        jDialog.setLocationRelativeTo(null);
        jDialog.setModal(true);
        jDialog.setVisible(true);
    }

    private void replayGame() {
        step = 0;
        initData();
        initImage();
    }
}
