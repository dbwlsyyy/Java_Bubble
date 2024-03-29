package test.ex01;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class BackgroundPlayerService implements Runnable {

    private BufferedImage image;
    private Player player;
    private List<Bubble> bubbleList;

    public BackgroundPlayerService(Player player) {
        this.player = player;
        this.bubbleList = player.getBubbleList();

        try {
            image = ImageIO.read(new File("image/backgroundMapService.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void run() {
        while (true) {


            // 버블 충돌 체크
            for (int i = 0; i < bubbleList.size(); i++) {
                if (bubbleList.get(i).getState() == 1) {
                    if ((Math.abs(player.getX() - bubbleList.get(i).getX()) < 10) &&
                            Math.abs(player.getY() - bubbleList.get(i).getY()) > 0 && Math.abs(player.getY() - bubbleList.get(i).getY()) < 50) {
                        System.out.println("적군 사살 완료");
                        bubbleList.get(i).clearBubbled();
                        break;
                    }
                }
            }

            // 벽 충돌 체크

            // 색상 체크
            Color leftColor = new Color(image.getRGB(player.getX() - 10, player.getY() + 25));
            Color rightColor = new Color(image.getRGB(player.getX() + 50 + 10, player.getY() + 25));
            int bottomColor = image.getRGB(player.getX() + 10, player.getY() + 55) // -1
                    + image.getRGB(player.getX() + 50, player.getY() + 55); // -1

            // 바닥 충돌
            if (bottomColor != -2) {
                // System.out.println("바닥에 충돌함");
                // System.out.println("bottom color : " + bottomColor);
                player.setDown(false);
            } else if (bottomColor == -2) {
                if (!player.isDown() && !player.isUp()) {
                    player.down();
                }
            }

            // 외벽 충돌
            if (leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
                // System.out.println("왼쪽 벽에 충돌함");
                player.setLeftWallCrash(true);
                player.setLeft(false);
            } else if (rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
                // System.out.println("오른쪽 벽에 충돌함");
                player.setRightWallCrash(true);
                player.setRight(false);
            } else {
                player.setLeftWallCrash(false);
                player.setRightWallCrash(false);
            }

            try {
                Thread.sleep(10); // 충돌 감지를 미세하게 조절
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

}

