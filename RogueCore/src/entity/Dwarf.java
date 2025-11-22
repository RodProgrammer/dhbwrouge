package entity;

import effects.CirclerBasic;
import spritemanager.ResourceManager;
import java.awt.*;
import java.io.Serializable;

public class Dwarf extends Player implements Serializable {

    public Dwarf(int x, int y, ResourceManager resourceManager) {
        super(x, y, resourceManager);

        effects.add(new CirclerBasic(resourceManager));

        loadImages();
    }

    public void loadImages() {
        images = resourceManager.getSpritesheet("dwarf");
    }
}
