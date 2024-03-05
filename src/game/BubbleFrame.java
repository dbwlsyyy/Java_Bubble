package game;

import game.component.Enemy;
import game.component.Player;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@Getter
@Setter
// 윈도우 창 상속 (extends)
public class BubbleFrame extends JFrame {

    private BubbleFrame mContext = this;
    private JLabel backgroundMap;
    private Player player;
    private Enemy enemy;

    public BubbleFrame() {
        initObject();
        initSetting();
        initListener();
        setVisible(true);
    }

    public void initObject() {
        backgroundMap = new JLabel(new ImageIcon("Image/backgroundMap.png"));
        setContentPane(backgroundMap);

        player = new Player(mContext);
        add(player);
        enemy = new Enemy(mContext);
        add(enemy);
        // new BGM();
    }

    public void initSetting(){
        setSize(1000, 640);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
    }

    public void initListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // System.out.println(e.getKeyCode());
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if(!player.isLeft() && !player.isLeftWallCrash()) {
                            player.left();
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if(!player.isRight() && !player.isRightWallCrash()) {
                            player.right();
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if(!player.isUp() && !player.isDown()) {
                            player.up();
                        }
                        break;
                    case KeyEvent.VK_SPACE:
                        player.attack();
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()){
                    case KeyEvent.VK_LEFT:
                        player.setLeft(false);
                        break;
                    case KeyEvent.VK_RIGHT:
                        player.setRight(false);
                        break;
                }
            }
        });
    }
    public static void main(String[] args) {
        new BubbleFrame();
    }
}