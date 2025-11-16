package tiles;

import utility.Settings;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Tile {

    protected final Rectangle rect;

    protected transient BufferedImage image;
    protected transient Light light;

    protected int x;
    protected int y;

    public Tile(int x, int y) {
        rect = new Rectangle(x,y, Settings.SCALED_TILE_SIZE, Settings.SCALED_TILE_SIZE);
        rect.x = x;
        rect.y = y;
        rect.width = Settings.SCALED_TILE_SIZE;
        rect.height = Settings.SCALED_TILE_SIZE;

        this.x = x;
        this.y = y;

        light = null;

        image = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.MAGENTA);
        g2d.drawRect(x, y, rect.width, rect.height);
    }

    public abstract void draw(Graphics2D g, int x, int y);

    public abstract void tick();

    public Rectangle getRectangle() {
        return rect;
    }

    public Light getLight() {
        return light;
    }

}
