package mapmanager.maps;

import spritemanager.ResourceManager;
import tiles.CaveGround;
import tiles.TestLightTile;
import utility.Settings;

public class SimpleTestMap extends Map {

    public SimpleTestMap(int width, int height, ResourceManager resourceManager) {
        super(width, height, resourceManager);

    }

    @Override
    public void loadMap() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (i % 8 == 0 && j % 8 == 0) {
                    map[i][j] = new TestLightTile(i* Settings.SCALED_TILE_SIZE, j*Settings.SCALED_TILE_SIZE);
                    continue;
                }
                map[i][j] = new CaveGround(i*Settings.SCALED_TILE_SIZE, j*Settings.SCALED_TILE_SIZE, resourceManager);
            }
        }
        map[0][0] = new TestLightTile(0, 0);
    }
}
