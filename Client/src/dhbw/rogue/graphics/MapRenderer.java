package dhbw.rogue.graphics;

import mapmanager.MapManager;
import spritemanager.ResourceManager;
import tiles.Tile;
import utility.Settings;

import java.awt.*;

public class MapRenderer {


    private ResourceManager resourceManager;
    private MapManager mapManager;
    private Tile[][] map;

    public MapRenderer(ResourceManager resourceManager, MapManager mapManager) {
        this.mapManager = mapManager;
        this.resourceManager = resourceManager;
        map = mapManager.getMap("TestMap").getMap();
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

    public void reloadMap(String mapName) {
        var newMap = mapManager.getMap(mapName);
        newMap.loadMap();

    }

}
