package utility;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Utility {

    private Utility() {}

    /**
     * This exists so the renderer doesn't have to rescale each time it draws the picture,
     * but we will resize the Picture beforehand and then we gucci
     */
    public static BufferedImage scaleImage(BufferedImage originalImage) {

        BufferedImage scaledImage = new BufferedImage(Settings.SCALED_TILE_SIZE, Settings.SCALED_TILE_SIZE, 2);
        Graphics2D graphics2D = scaledImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, Settings.SCALED_TILE_SIZE, Settings.SCALED_TILE_SIZE, null);
        graphics2D.dispose();

        return scaledImage;
    }

    /**
     * This exists so the renderer doesn't have to rescale each time it draws the picture,
     * but we will resize the Picture beforehand and then we gucci
     */
    public static BufferedImage scaleImage(BufferedImage originalImage, int tileSize) {

        BufferedImage scaledImage = new BufferedImage(tileSize, tileSize, 2);
        Graphics2D graphics2D = scaledImage.createGraphics();

        int size = Settings.SCALED_TILE_SIZE;

        if(tileSize >= Settings.SCALED_TILE_SIZE) {
            size = tileSize;
        }

        graphics2D.drawImage(originalImage, 0, 0, size, size, null);
        graphics2D.dispose();

        return scaledImage;
    }

    /**
     * We split the PNG into multiple BufferedImages, so we got a SpriteSheet ::)))
     * also we basically load everything into RAM, so we go vroom vroom
     */
    public static BufferedImage[][] getImages(BufferedImage originalImage, int width, int height, int tileSize) {
        int imageWidth = originalImage.getWidth() / width;
        int imageHeight = originalImage.getHeight() / height;

        BufferedImage[][] images = new BufferedImage[imageWidth][imageHeight];

        int i = 0;
        int j = 0;
        for (int x = 0; x < images.length; x++) {
            for (int y = 0; y < images[x].length; y++) {
                BufferedImage image = originalImage.getSubimage(i, j, width, height);
                if (!isEmpty(image)) {
                    images[x][y] = scaleImage(image, tileSize);
                }
                j += width;
            }
            j = 0;
            i += height;
        }

        return images;
    }

    private static boolean isEmpty(BufferedImage image) {
        if (image.getTransparency() == Transparency.OPAQUE) {
            return false;
        }

        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                int alpha = (pixel >> 24) & 0xff;
                if (alpha != 0) {
                    return false;
                }
            }
        }

        return true;
    }


}
