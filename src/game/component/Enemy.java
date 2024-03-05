package game.component;

import game.BubbleFrame;
import game.Moveable;
import game.service.BackgroundEnemyService;
import game.state.EnemyDirecton;
import lombok.Getter;
import lombok.Setter;


import javax.swing.*;

@Getter
@Setter
public class Enemy extends JLabel implements Moveable {

    private BubbleFrame mContext;

    // 위치 상태
    private int x;
    private int y;

    // 플레이어의 방향
    private EnemyDirecton enemyDirecton;

    // 움직임 상태
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    private int state; // 0(살아있을 때), 1(물방울에 맞았을 때)

    // 플레이어 속도 상태
    private final int SPEED = 3;
    private final int JUMPSPEED = 2;

    private ImageIcon enemyR, enemyL;

    public Enemy(BubbleFrame mContext) {
        this.mContext = mContext;
        initObject();
        initSetting();
        initBackgroundEnemyService();
        right();
    }

    private void initObject() {
        enemyL = new ImageIcon("Image/enemyL.png");
        enemyR = new ImageIcon("Image/enemyR.png");
    }

    private void initSetting() {
        x = 480;
        y = 178;

        left = false;
        right = false;
        up = false;
        down = false;

        state = 0;

        enemyDirecton = EnemyDirecton.RIGHT;
        setIcon(enemyR);
        setSize(50, 50);
        setLocation(x, y);
    }

    private void initBackgroundEnemyService() {
         new Thread(new BackgroundEnemyService(this)).start();
    }


    @Override
    public void right() {
        enemyDirecton = EnemyDirecton.RIGHT;
        System.out.println("right");
        right = true;
        new Thread(()->{
            while (right) {
                setIcon(enemyR);
                x += SPEED;
                setLocation(x, y);
                try {
                    Thread.sleep(10); // 0.01초
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    @Override
    public void left() {
        enemyDirecton = EnemyDirecton.LEFT;
        System.out.println("left");
        left = true;
        new Thread(()->{
            while (left) {
                setIcon(enemyL);
                x -= SPEED ;
                setLocation(x, y);
                try {
                    Thread.sleep(10); // 0.01초
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    @Override
    // left + up, right + up 이 가능해짐 (thread 사용을 통해 )
    public void up() {
        System.out.println("up");
        up = true;
        new Thread(()->{
            for (int i = 0; i < 130/JUMPSPEED; i++) {
                y -= JUMPSPEED;
                setLocation(x, y);
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            up = false;
            down();
        }).start();
    }

    @Override
    public void down() {
        System.out.println("down");
        down = true;
        new Thread(()->{
            while (down) {
                y += JUMPSPEED;
                setLocation(x, y);
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            down = false;
        }).start();
    }
}
