package tiles;

import utility.Settings;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Light {

    private final int x;
    private final int y;
    private int radius;
    private int luminosity;

    private BufferedImage light;

    public Light(int x, int y, int radius, int luminosity) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.luminosity = luminosity;

        light = new BufferedImage(radius * 2, radius * 2, BufferedImage.TYPE_INT_ARGB);
        drawLight();
    }

    public void renderLight(Graphics g, int x, int y) {

    }

    public void drawLight() {
        Graphics2D g = (Graphics2D) light.getGraphics();

        int iterations = radius / Settings.LIGHT_STEP;
        g.setColor(new Color(0, 0, 0, luminosity));
        for (int i = 0; i < iterations; i++) {
            g.fillOval(x + radius - (i*Settings.LIGHT_STEP), y + radius - (i*Settings.LIGHT_STEP), i * Settings.LIGHT_STEP * 2, i * Settings.LIGHT_STEP * 2);
        }
    }
}
