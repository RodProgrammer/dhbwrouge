package dhbw.rogue.graphics;

import spritemanager.ResourceManager;
import tiles.CaveGround;
import tiles.TestLightTile;
import tiles.Tile;
import utility.Settings;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class MapRenderer {

    private final Tile[][] map;

    public MapRenderer(ResourceManager resourceManager) {
        map = new Tile[32][32];

        Random rand = new Random();

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = new CaveGround(i*Settings.SCALED_TILE_SIZE, j*Settings.SCALED_TILE_SIZE, resourceManager);
            }
        }
        map[0][0] = new TestLightTile(0, 0);
    }

    public void render(Graphics2D g, int discrepancyX, int discrepancyY) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                int x = (i * Settings.SCALED_TILE_SIZE) + (Settings.SCREEN_WIDTH / 2) - discrepancyX;
                int y = (j * Settings.SCALED_TILE_SIZE) + (Settings.SCREEN_HEIGHT / 2) - discrepancyY;
                if (!(x >= Settings.SCREEN_WIDTH || x <= -1 * Settings.SCALED_TILE_SIZE) && !(y >= Settings.SCREEN_HEIGHT || y <= -1 * Settings.SCALED_TILE_SIZE)) {
                    map[i][j].draw(g, x, y);
                }
            }
        }
        g.setColor(Color.WHITE);
    }

    public void tick() {
        for (Tile[] tiles : map) {
            for (Tile tile : tiles) {
                tile.tick();
            }
        }
    }

    public Tile[][] getMap() {
        return map;
    }


}
