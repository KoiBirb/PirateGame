package entity;

import Main.GamePanel;
import Main.KeyInput;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{

    GamePanel gp;
    KeyInput keyI;

    public final int screenX;
    public final int screenY;
    public int keyAmount = 0;

    public Player(GamePanel gp, KeyInput keyI) {

        this.gp = gp;
        this.keyI = keyI;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }

    // set spawn speed and direction
    public void setDefaultValues() {
        worldX = gp.tileSize * 7;
        worldY = gp.tileSize * 4;
        speed = 4;
        direction = "down";
    }

    // load the player sprites
    public void getPlayerImage(){

        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/Walk/player_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/Walk/player_up_2.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/player/Walk/player_up_3.png"));
            up4 = ImageIO.read(getClass().getResourceAsStream("/player/Walk/player_up_4.png"));
            up5 = ImageIO.read(getClass().getResourceAsStream("/player/Walk/player_up_5.png"));
            up6 = ImageIO.read(getClass().getResourceAsStream("/player/Walk/player_up_6.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/Walk/player_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/Walk/player_down_2.png"));
            down3 = ImageIO.read(getClass().getResourceAsStream("/player/Walk/player_down_3.png"));
            down4 = ImageIO.read(getClass().getResourceAsStream("/player/Walk/player_down_4.png"));
            down5 = ImageIO.read(getClass().getResourceAsStream("/player/Walk/player_down_5.png"));
            down6 = ImageIO.read(getClass().getResourceAsStream("/player/Walk/player_down_6.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/Walk/player_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/Walk/player_left_2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/player/Walk/player_left_3.png"));
            left4 = ImageIO.read(getClass().getResourceAsStream("/player/Walk/player_left_4.png"));
            left5 = ImageIO.read(getClass().getResourceAsStream("/player/Walk/player_left_5.png"));
            left6 = ImageIO.read(getClass().getResourceAsStream("/player/Walk/player_left_6.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/Walk/player_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/Walk/player_right_2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/player/Walk/player_right_3.png"));
            right4 = ImageIO.read(getClass().getResourceAsStream("/player/Walk/player_right_4.png"));
            right5 = ImageIO.read(getClass().getResourceAsStream("/player/Walk/player_right_5.png"));
            right6 = ImageIO.read(getClass().getResourceAsStream("/player/Walk/player_right_6.png"));

        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void update() {


        if (keyI.upPressed || keyI.downPressed ||
                keyI.leftPressed || keyI.rightPressed){
            if(keyI.upPressed) {
                direction = "up";
            }
            else if(keyI.downPressed){
                direction = "down";
            }
            else if(keyI.leftPressed){
                direction = "left";
            }
            else if(keyI.rightPressed){
                direction = "right";
            }


            // collision check
            collisionOn = false;
            gp.cCheck.checkTile(this);
            int objIndex = gp.cCheck.checkObject(this, true);
            pickUpObject(objIndex);

            if(!collisionOn) {

                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 4) {
                if(spriteNum == 1){
                    spriteNum = 2;
                }
                else if (spriteNum == 2){
                    spriteNum = 3;
                }
                else if (spriteNum == 3){
                    spriteNum = 4;
                }
                else if (spriteNum == 4){
                    spriteNum = 5;
                }
                else if (spriteNum == 5){
                    spriteNum = 6;
                }
                else if (spriteNum == 6){
                    spriteNum = 1;
                }
                spriteCounter = 0;
        }

        } else {
            spriteCounter = 0;
            spriteNum = 1;
        }
    }
    public void pickUpObject(int i) {
        if(i != 999) {
            String objectName = gp.obj[i].name;
            switch (objectName) {
                case "Key":
                        gp.playSoundEffect(5);
                        keyAmount++;
                        gp.obj[i] = null;
                        gp.ui.showMessage("You got a key!");

                    break;
                case "Chest":
                    if(keyAmount > 0){
                        gp.obj[i] = null;
                        keyAmount--;
                    }
                    else{
                        gp.ui.showMessage("You need a key!");
                    }
                break;
            }
        }
    }
    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch (direction) {
        case "up":
            if (spriteNum == 1){
                image = up1;
            }
            if (spriteNum == 2){
                image = up2;
            }
            if (spriteNum == 3){
                image = up3;
            }
            if (spriteNum == 4){
                image = up4;
            }
            if (spriteNum == 5){
                image = up5;
            }
            if (spriteNum == 6){
                image = up6;
            }
            break;
        case "down":
            if (spriteNum == 1){
                image = down1;
            }
            if (spriteNum == 2){
                image = down2;
            }
            if (spriteNum == 3){
                image = down3;
            }
            if (spriteNum == 4){
                image = down4;
            }
            if (spriteNum == 5){
                image = down5;
            }
            if (spriteNum == 6){
                image = down6;
            }
            break;
        case "left":
            if (spriteNum == 1){
                image = left1;
            }
            if (spriteNum == 2){
                image = left2;
            }
            if (spriteNum == 3){
                image = left3;
            }
            if (spriteNum == 4){
                image = left4;
            }
            if (spriteNum == 5){
                image = left5;
            }
            if (spriteNum == 6){
                image = left6;
            }
            break;
        case "right":
            if (spriteNum == 1){
                image = right1;
            }
            if (spriteNum == 2){
                image = right2;
            }
            if (spriteNum == 3){
                image = right3;
            }
            if (spriteNum == 4){
                image = right4;
            }
            if (spriteNum == 5){
                image = right5;
            }
            if (spriteNum == 6){
                image = right6;
            }
            break;
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
