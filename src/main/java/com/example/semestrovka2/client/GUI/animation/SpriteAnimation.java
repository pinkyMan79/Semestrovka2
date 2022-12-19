package com.example.semestrovka2.client.GUI.animation;

import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SpriteAnimation extends Transition {

    private final ImageView imageView;
    private final int count;
    private final int columns;

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    private int offsetX;
    private int offsetY;
    private final int width;
    private final int height;

    public SpriteAnimation(Duration duration, ImageView imageView, int count, int columns, int offsetX, int offsetY, int width, int height) {
        this.imageView = imageView;
        this.count = count;
        this.columns = columns;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
        setCycleDuration(duration);
    }


    @Override
    protected void interpolate(double v) {
        final int index = Math.min((int)Math.floor(v * count), count - 1);
        final int x = (index % columns) * width + offsetX;
        final int y = (index / columns) * width + offsetY;
        imageView.setViewport(new Rectangle2D(x, y, width, height));
    }

}
