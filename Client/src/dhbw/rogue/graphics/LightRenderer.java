package dhbw.rogue.graphics;

import tiles.Tile;
import utility.Settings;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LightRenderer {

    private BufferedImage lightMap;

    public LightRenderer(Tile[][] tile) {

        if (tile == null) return;

        lightMap = new BufferedImage(tile.length * Settings.SCALED_TILE_SIZE, tile[0].length * Settings.SCALED_TILE_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) lightMap.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, lightMap.getWidth(), lightMap.getHeight());

        drawLight(tile);
    }

    public void renderLight(Graphics2D g, int discrepancyX, int discrepancyY) {
        g.drawImage(lightMap, (Settings.SCREEN_WIDTH / 2) - discrepancyX, (Settings.SCREEN_HEIGHT / 2) - discrepancyY, null);
    }

    private void drawLight(Tile[][] allTiles) {
        Graphics2D g = (Graphics2D) lightMap.getGraphics();
        for (Tile[] tiles : allTiles) {
            for (Tile tile : tiles) {
                if (tile.getLight() != null) {
                    //tile.getLight().drawLight(g);
                    System.out.println("Light " + tile.getLight() + "x: " + tile.getRectangle().getX() + ", y:" + tile.getRectangle().getY());
                }
            }
        }
    }

}
