package Test.ex01;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter
@Setter
public class Bubble extends JLabel implements Moveable{

    // 버블의 위치는 플레이어를 참고해야하기 때문에 의존성 컴포지션 필요
    // Composition : 객체 지향 프로그래밍에서 한 클래스가 다른 클래스의 인스턴스를 포함하는 것, 클래스 간의 관계 형성
    private Player player; // 플레이어의 정보 받아옴

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


    public Bubble(Player player) { // 플레이어의 정보 ->  컴포지션 할 때는 생성자로 받아와야 함
        this.player = player; // 얘가 있어야 플레이어 정보 받아올 수 있음, Bubble 객체를 생성할 때 Player 객체를 전달받아서 현재 Bubble 객체 내에 저장하는 역할
        initObject();
        initSetting();
        initThread();
    }

    private void initObject() {
        bubble = new ImageIcon("Image/bubble.png");
        bubbled = new ImageIcon("Image/bubbled.png");
        bomb = new ImageIcon("Image/bomb.png");
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

    public void initThread() {
        // 버블은 스레드가 하나만 필요하다
        new Thread(()->{
            if (player.getPlayerDirecton() == PlayerDirecton.LEFT) {
                left();
            } else{
                right();
            }
        }).start();
    }
    @Override
    public void left() {

    }

    @Override
    public void right() {

    }

    @Override
    public void up() {

    }
}
