package tiles;

import utility.Settings;

import java.awt.*;
import java.util.Random;

public class TestLightTile extends Tile {
    public TestLightTile(int x, int y) {
        super(x, y);

        hasCollision = true;

        light = new Light(x, y, Settings.SCALED_TILE_SIZE * 2, new Random().nextInt(10, 101));
    }

    @Override
    public void draw(Graphics2D g, int x, int y) {
        g.setColor(Color.MAGENTA);
        g.fillRect(x, y, Settings.SCALED_TILE_SIZE, Settings.SCALED_TILE_SIZE);
    }

    @Override
    public void tick() {

    }

    public void reloadLight() {
        light = new Light(x, y, Settings.SCALED_TILE_SIZE * 2, new Random().nextInt(10, 101));
    }
}
