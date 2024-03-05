package test.ex01;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class BackgroundEnemyService implements Runnable {

    private BufferedImage image;
    private Enemy enemy;

    public BackgroundEnemyService(Enemy enemy) {
        this.enemy = enemy;

        try {
            image = ImageIO.read(new File("image/backgroundMapService.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void run() {
        while (enemy.getState() == 0) {

            // 색상 체크
            Color leftColor = new Color(image.getRGB(enemy.getX() - 10, enemy.getY() + 25));
            Color rightColor = new Color(image.getRGB(enemy.getX() + 50 + 10, enemy.getY() + 25));
            int bottomColor = image.getRGB(enemy.getX() + 10, enemy.getY() + 55) // -1
                    + image.getRGB(enemy.getX() + 50, enemy.getY() + 55); // -1

            // 바닥 충돌
            if (bottomColor != -2) {
                // System.out.println("바닥에 충돌함");
                // System.out.println("bottom color : " + bottomColor);
                enemy.setDown(false);
            } else if (bottomColor == -2) {
                if (!enemy.isDown() && !enemy.isUp()) {
                    enemy.down();
                }
            }

            // 외벽 충돌
            if (leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
                enemy.setLeft(false);
                if(!enemy.isRight()) enemy.right();
            } else if (rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
                enemy.setRight(false);
                if(!enemy.isLeft()) enemy.left();

            }

            try {
                Thread.sleep(10); // 충돌 감지를 미세하게 조절
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

}

