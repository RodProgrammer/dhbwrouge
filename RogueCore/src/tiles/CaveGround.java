package tiles;

import spritemanager.ResourceManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CaveGround extends Tile {

    private final ResourceManager resourceManager;

    public CaveGround(int x, int y, ResourceManager resourceManager) {
        super(x, y);
        this.resourceManager = resourceManager;
        loadResources();
    }

    @Override
    public void draw(Graphics2D g, int x, int y) {
        g.drawImage(image, x, y, null);
    }

    @Override
    public void tick() {

    }

    public BufferedImage getBufferedImage() {
        return image;
    }

    private void loadResources() {
        image =  resourceManager.getSpritesheet("cave")[6][0];
    }
}
