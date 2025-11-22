package entity;

import spritemanager.ResourceManager;

import java.awt.*;

public class Elf extends Player {

    public Elf(int x, int y, ResourceManager resourceManager) {
        super(x, y, resourceManager);

        loadImages();
    }

    public void loadImages() {
        images = resourceManager.getSpritesheet("elf");
    }

}
