package mapmanager.maps;

import spritemanager.ResourceManager;
import tiles.CaveGround;
import tiles.Tile;

public class Map {

    private final Tile[][] map;
    private transient ResourceManager resourceManager;

    public Map(int width, int height, ResourceManager resourceManager) {
        this.resourceManager = resourceManager;

        map = new Tile[width][height];

        loadMap();
    }

    public Tile[][] getMap() {
        return map;
    }

    public void loadMap() {
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[x].length; y++) {
                map[x][y] = new CaveGround(x, y, resourceManager);
            }
        }
    }

    public void setResourceManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }
}
