package game.component;

import game.BubbleFrame;
import game.Moveable;
import game.service.BackgroundPlayerService;
import game.state.PlayerDirecton;
import lombok.Getter;
import lombok.Setter;


import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Player extends JLabel implements Moveable {

    private BubbleFrame mContext;
    private List<Bubble> bubbleList;

    // 위치 상태
    private int x;
    private int y;

    // 플레이어의 방향
    private PlayerDirecton playerDirecton;

    // 움직임 상태
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    // 벽에 충돌한 상태
    private boolean leftWallCrash;
    private boolean rightWallCrash;

    // 플레이어 속도 상태
    private final int SPEED = 5;
    private final int JUMPSPEED = 4;

    private ImageIcon playerR, playerL;

    public Player(BubbleFrame mContext) {
        this.mContext = mContext;
        initObject();
        initSetting();
        initBackgroundPlayerService();
    }

    private void initObject() {
        playerR = new ImageIcon("Image/playerR.png");
        playerL = new ImageIcon("Image/playerL.png");
        bubbleList = new ArrayList<>();
    }

    private void initSetting() {
        x = 70;
        y = 535;

        left = false;
        right = false;
        up = false;
        down = false;

        leftWallCrash = false;
        rightWallCrash = false;

        playerDirecton = PlayerDirecton.RIGHT;
        setIcon(playerR);
        setSize(50, 50);
        setLocation(x, y);
    }

    private void initBackgroundPlayerService() {
        new Thread(new BackgroundPlayerService(this)).start();
    }


    @Override
    public void attack() {
        new Thread(()->{
            Bubble bubble = new Bubble(mContext);
            mContext.add(bubble);
            bubbleList.add(bubble);
            if (playerDirecton == PlayerDirecton.LEFT) {
                bubble.left();
            } else{
                bubble.right();
            }
        }).start();
    }

    @Override
    public void right() {
        playerDirecton = PlayerDirecton.RIGHT;
        // System.out.println("right");
        right = true;
        new Thread(()->{
            while (right) {
                setIcon(playerR);
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
        playerDirecton = PlayerDirecton.LEFT;
        // System.out.println("left");
        left = true;
        new Thread(()->{
            while (left) {
                setIcon(playerL);
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
        // System.out.println("up");
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
        // System.out.println("down");
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
