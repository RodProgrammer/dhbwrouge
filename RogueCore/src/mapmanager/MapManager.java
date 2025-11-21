package mapmanager;

import mapmanager.maps.Map;
import mapmanager.maps.SimpleTestMap;
import spritemanager.ResourceManager;

import java.util.HashMap;

public class MapManager {
    public HashMap<String, Map> maps;
    public ResourceManager resourceManager;

    public MapManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
        maps = new HashMap<>();

        loadAllMaps();
    }

    public Map getMap(String name) {
        return maps.get(name);
    }

    private void loadAllMaps() {
        maps.put("TestMap", new SimpleTestMap(32, 32, resourceManager));
    }
}
