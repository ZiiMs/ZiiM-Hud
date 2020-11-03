package me.ziim.ziimhud.gui;


public class Vector2D {
    private int x;
    private int y;

    public Vector2D(int scaledX, int scaledY) {
        this.x = scaledX;
        this.y = scaledY;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
