package mapmanager.maps;

import spritemanager.ResourceManager;
import tiles.Tile;

public abstract class Map {

    protected final Tile[][] map;
    protected transient ResourceManager resourceManager;

    public Map(int width, int height, ResourceManager resourceManager) {
        this.resourceManager = resourceManager;

        map = new Tile[width][height];

        loadMap();
    }

    public Tile[][] getMap() {
        return map;
    }

    public abstract void loadMap();

    public void setResourceManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }
}
