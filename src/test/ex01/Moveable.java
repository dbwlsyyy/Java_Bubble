package test.ex01;

public interface Moveable {
    public abstract void left();
    public abstract void right();
    public abstract void up();
    default public void down() {}; // 이거 안 쓰면 어댑터 쓸 수 있음
    default public void attack() {}; // 이거 안 쓰면 어댑터 쓸 수 있음


}
