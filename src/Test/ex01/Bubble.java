package Test.ex01;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter
@Setter
public class Bubble extends JLabel implements Moveable{

    private BubbleFrame mContext;
    // 버블의 위치는 플레이어를 참고해야하기 때문에 의존성 컴포지션 필요
    // Composition : 객체 지향 프로그래밍에서 한 클래스가 다른 클래스의 인스턴스를 포함하는 것, 클래스 간의 관계 형성
    private Player player; // 플레이어의 정보 받아옴
    private Enemy enemy;
    private BackgroundBubbleService backgroundBubbleService;

    // 위치 상태
    private int x;
    private int y;

    // 움직임 상태
    private boolean left;
    private boolean right;
    private boolean up;

    // 물방울 상태
    private int state; // 0(물방울), 1(적군을 맞춘 상태)
    private ImageIcon bubble; // 물방울
    private ImageIcon bubbled; // 적군을 맞춘 물방울
    private ImageIcon bomb; // 터진 물방울


    public Bubble(BubbleFrame mContext) { // 플레이어의 정보 ->  컴포지션 할 때는 생성자로 받아와야 함
        this.mContext = mContext;
        this.player = mContext.getPlayer(); // 얘가 있어야 플레이어 정보 받아올 수 있음, Bubble 객체를 생성할 때 Player 객체를 전달받아서 현재 Bubble 객체 내에 저장하는 역할
        this.enemy = mContext.getEnemy();
        initObject();
        initSetting();
    }

    private void initObject() {
        bubble = new ImageIcon("Image/bubble.png");
        bubbled = new ImageIcon("Image/bubbled.png");
        bomb = new ImageIcon("Image/bomb.png");

        backgroundBubbleService = new BackgroundBubbleService(this);
    }

    private void initSetting() {
        up = false;
        left = false;
        right = false;

        x = player.getX();
        y = player.getY();

        setIcon(bubble);
        setSize(50, 50);

        state = 0;
    }


    @Override
    public void left() {
        left = true;
        for (int i = 0; i < 400; i++) {
            x--;
            setLocation(x, y);

            if (backgroundBubbleService.leftWall()) {
                left = false; // 상태는 행위에 의해 변경 되어야 함 행여 안 쓰이더라도 !
                break;
            }

            // 버블과 적군의 거리가 10
            if ((Math.abs(x - enemy.getX()) < 10) &&
                    Math.abs(y - enemy.getY()) > 0 && Math.abs(y - enemy.getY()) < 50 ) {
                // System.out.println("충돌");
                if(enemy.getState() == 0){
                    attack();
                    break;
                }
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        up();
    }

    @Override
    public void right() {
        right = true;
        for (int i = 0; i < 400; i++) {
            x++;
            setLocation(x, y);

            if (backgroundBubbleService.rightWall()) {
                right = false; // 상태는 행위에 의해 변경 되어야 함
                break;
            }

            // 40과 60의 범위 절댓값
            if ((Math.abs(x - enemy.getX()) < 10) &&
                    Math.abs(y - enemy.getY()) > 0 && Math.abs(y - enemy.getY()) < 50 ) {
                // System.out.println("충돌");
                if(enemy.getState() == 0){
                    attack();
                    break;
                }
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        up();
    }

    @Override
    public void up() {
        up = true;
        while (up) {
            y--;
            setLocation(x, y);

            if (backgroundBubbleService.toptWall()) {
                left = false; // 상태는 행위에 의해 변경 되어야 함
                break; // 없어도 되지만 해주는 게 좀 더 효율적
            }

            try {
                if(state == 0) Thread.sleep(1);
                else Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if(state == 0) clearBubble();
    }

    @Override
    public void attack() {
        state = 1;
        enemy.setState(1);
        setIcon(bubbled);
        mContext.remove(enemy);
        mContext.repaint();
    }

    private void clearBubble() {
        try {
            Thread.sleep(2000);
            setIcon(bomb);
            Thread.sleep(500);
            mContext.remove(this); // BubbleFrame의 bubble이 소멸됨
            mContext.repaint(); // BubbleFrame의 전체를 다시 그 (메모리에 없는 건 그리지 않음)
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
