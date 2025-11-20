package spritemanager;

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
            spritesheet = Utility.getImages(originalImage, 16,16);
        }
    }

    public BufferedImage[][] getTileset() {
        return spritesheet;
    }
}
