package effects;

import spritemanager.ResourceManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public abstract class Effect implements Serializable {

    protected transient BufferedImage[][] images;

    protected transient ResourceManager resourceManager;

    protected transient BufferedImage effectIcon;

    public Effect(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    public abstract void render(Graphics2D g, int x, int y);

    public abstract void tick();

    public abstract void loadEffect();

    public BufferedImage getEffectIcon() {
        return effectIcon;
    }

    public void setResourceManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

}
