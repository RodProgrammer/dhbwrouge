package effects;

import spritemanager.ResourceManager;

import java.awt.*;
import java.io.Serializable;

public class CirclerBasic extends Effect implements Serializable {

    private int currImageX;
    private int currImageY;
    private int currTick;

    public CirclerBasic(int x, int y, ResourceManager resourceManager) {
        super(x, y, resourceManager);

        currImageX = 0;
        currImageY = 0;
        currTick = 0;

        loadEffect();
    }

    @Override
    public void render(Graphics2D g, int x, int y) {

        //g.drawRect(x - 25, y - 25, 100, 100);

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
    }
}
