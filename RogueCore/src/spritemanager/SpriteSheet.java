package spritemanager;

import utility.Settings;
import utility.Utility;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteSheet {
    private BufferedImage[][] spritesheet;

    public SpriteSheet(String path) {

        BufferedImage originalImage = null;
        try {
            originalImage = ImageIO.read(new File(path));
        } catch (IOException ex) {}

        if (originalImage != null) {
            spritesheet = Utility.getImages(originalImage, Settings.TILE_SIZE, Settings.TILE_SIZE, Settings.SCALED_TILE_SIZE);
        }
    }

    public SpriteSheet(String path, int width, int height) {

        BufferedImage originalImage = null;
        try {
            originalImage = ImageIO.read(new File(path));
        } catch (IOException ex) {}

        if (originalImage != null) {
            spritesheet = Utility.getImages(originalImage, width, height, 100);
        }
    }


    public BufferedImage[][] getTileset() {
        return spritesheet;
    }
}
