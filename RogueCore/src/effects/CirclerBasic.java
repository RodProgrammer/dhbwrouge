package effects;

import spritemanager.ResourceManager;

import java.awt.*;
import java.io.Serializable;

public class CirclerBasic extends Effect implements Serializable {

    private int currImageX;
    private int currImageY;
    private int currTick;

    public CirclerBasic(ResourceManager resourceManager) {
        super(resourceManager);

        currImageX = 0;
        currImageY = 0;
        currTick = 0;

        loadEffect();
    }

    @Override
    public void render(Graphics2D g, int x, int y) {
        g.drawImage(images[currImageX][currImageY], x - 25, y - 25, null);
    }

    @Override
    public void tick() {

        currTick++;

        if (currTick == 2) {
            currImageX++;
            currTick = 0;
        }

        if (currImageX == 5) {
            currImageX = 0;
            currImageY++;
            currTick = 0;
        }

        if (currImageY == 12) {
            currImageY = 0;
            currTick = 0;
        }
    }

    @Override
    public void loadEffect() {
        images = resourceManager.getSpritesheet("basicCircler");
        effectIcon = resourceManager.getSpritesheet("iconSheet")[0][12];
    }
}
