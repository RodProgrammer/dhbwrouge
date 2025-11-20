package mapmanager;

import mapmanager.maps.Map;

import java.util.HashMap;

public class MapManager {
    public HashMap<String, Map> maps;

    public MapManager() {
        maps = new HashMap<>();
    }

    public Map getMap(String name) {
        return maps.get(name);
    }

    private void loadAllMaps() {

    }
}
