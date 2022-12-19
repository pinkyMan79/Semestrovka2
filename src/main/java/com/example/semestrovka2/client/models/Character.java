package com.example.semestrovka2.client.models;

import com.example.semestrovka2.client.GUI.AppConroller;
import com.example.semestrovka2.client.GUI.animation.SpriteAnimation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import lombok.Data;

@Data
public class Character extends Pane{

    private ImageView imageView;
    private final int count = 3;
    private final int columns = 3;
    private final int offsetX = 0;
    private final int offsetY = 0;
    private final int width = 32;
    private final int height = 32;
    private int speedBonus = 1;
    private int level = 1;
    private int hp = 100 * level;
    private int damage = 5 * level;
    private int exp = 0;
    public Rectangle remRectangle = null;
    public SpriteAnimation animation;


    public Character(ImageView imageView) {
        this.imageView = imageView;
        this.imageView.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        this.animation = new SpriteAnimation(
                Duration.millis(200),
                imageView,
                count,
                columns,
                offsetX,
                offsetY,
                width,
                height);
        getChildren().add(imageView);
    }
    public void moveX(int x){
        boolean isRightMove = x > 0;
        for (int i = 0; i < Math.abs(x); i++) {
            if (isRightMove){
                this.setTranslateX(this.getTranslateX() + 0.2 + (speedBonus * level));
            }else {
                this.setTranslateX(this.getTranslateX() - 0.2 - (speedBonus * level));
            }
        }
    }

    public void moveY(int y){
        boolean isUpMove = y > 0;
        for (int i = 0; i < Math.abs(y); i++) {
            if (isUpMove) {
                this.setTranslateY(this.getTranslateY() + 0.2 + (speedBonus * level));
            }else{ this.setTranslateY(this.getTranslateY() - 0.2 - (speedBonus * level));}
        }
        bonusAction();
    }

    public void bonusAction(){
        AppConroller.bonuses.forEach((rectangle) -> {
            this.getBoundsInParent().intersects(rectangle.getBoundsInParent());
            remRectangle = rectangle;
        });
        exp++;
        if (exp % 5 == 0){
            level++;
        }
        AppConroller.bonuses.remove(remRectangle);
        AppConroller.root.getChildren().remove(remRectangle);
    }

}
