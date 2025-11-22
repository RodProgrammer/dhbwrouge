package effects;

import spritemanager.ResourceManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public abstract class Effect implements Serializable {

    protected transient BufferedImage[][] images;

    protected transient ResourceManager resourceManager;

    public Effect(int x, int y, ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    public abstract void render(Graphics2D g, int x, int y);

    public abstract void tick();

    public abstract void loadEffect();

    public void setResourceManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

}
