package entity;

import effects.Effect;
import spritemanager.ResourceManager;
import utility.Settings;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Player extends Entity implements Serializable {

    private final Set<Direction> dirs;
    protected transient BufferedImage[][] images;

    private transient int animTick;
    protected int currImage;
    protected int currDirectionImage;

    public Player(int x, int y, ResourceManager resourceManager) {
        super(x, y, 100, 100, resourceManager);
        dirs = ConcurrentHashMap.newKeySet(); //haha it will crash without it... I wanna commit not alive anymore
        name = String.valueOf(hashCode());

        animTick = 0;
        currImage = 0;
        currDirectionImage = 0;

        loadImages();
    }

    public void drawPlayer(Graphics2D g, int discrepancyX, int discrepancyY) {
        g.drawImage(images[currImage][currDirectionImage], x - discrepancyX + (Settings.SCREEN_WIDTH / 2), y - discrepancyY + (Settings.SCREEN_HEIGHT / 2), null);
        g.setColor(Color.RED);
        g.drawString(name, x - discrepancyX + (Settings.SCREEN_WIDTH / 2) - (name.length() * 2), y - discrepancyY + (Settings.SCREEN_HEIGHT / 2) - 24);

        for (Effect effect : effects) {
            effect.render(g, x - discrepancyX + (Settings.SCREEN_WIDTH / 2), y - discrepancyY + (Settings.SCREEN_HEIGHT / 2));
        }

        g.setColor(Color.GREEN);
        g.fillRect(x - discrepancyX + (Settings.SCREEN_WIDTH / 2), y - discrepancyY + (Settings.SCREEN_HEIGHT / 2) - 16, Settings.SCALED_TILE_SIZE, 8);
        g.setColor(Color.BLUE);
        g.fillRect(x - discrepancyX + (Settings.SCREEN_WIDTH / 2), y - discrepancyY + (Settings.SCREEN_HEIGHT / 2) - 8, Settings.SCALED_TILE_SIZE, 8);

    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(images[currImage][currDirectionImage], Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2, null);

        for(Effect effect : effects) {
            effect.render(g, Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2);
        }

        g.setColor(Color.RED);
        g.drawString(name, (Settings.SCREEN_WIDTH / 2) - (name.length() * 2), (Settings.SCREEN_HEIGHT / 2) - 8);

    }

    @Override
    public void tick() {

        for (Effect effect : effects) {
            effect.tick();
        }

        if (dirs.contains(Direction.UP)) {
            this.y -= speed;
            currDirectionImage = Direction.UP.value;
        }
        if (dirs.contains(Direction.DOWN)) {
            this.y += speed;
            currDirectionImage = Direction.DOWN.value;
        }
        if (dirs.contains(Direction.LEFT)) {
            this.x -= speed;
            currDirectionImage = Direction.LEFT.value;
        }
        if (dirs.contains(Direction.RIGHT)) {
            this.x += speed;
            currDirectionImage = Direction.RIGHT.value;
        }
        if (dirs.contains(Direction.UP) && dirs.contains(Direction.DOWN) || dirs.contains(Direction.LEFT) && dirs.contains(Direction.RIGHT)) {
            currImage = 0;
            return;
        }

        if (dirs.isEmpty()) {
            currImage = 0;
            return;
        }

        animTick++;
        if (animTick >= 15) {
            animTick = 0;
            currImage++;

            if (currImage >= images.length) {
                currImage = 0;
            }
        }
    }

    public void updatePlayer(Player player) {
        this.x = player.x;
        this.y = player.y;
        this.currImage = player.currImage;
        this.currDirectionImage = player.currDirectionImage;
        this.speed = player.speed;
        this.effects = player.effects;
        for(Effect effect : effects) {
            effect.setResourceManager(resourceManager);
            effect.loadEffect();
        }
    }

    public void addDirection(Direction dir) {
        dirs.add(dir);
    }

    public void removeDirection(Direction dir) {
        dirs.remove(dir);
    }

    @Override
    public String toString() {
        return "Player(name: " + name +  ", x: " + x + ", y:" + y + ")";
    }

    public void loadImages() {
        return;
    }

    public int getCurrImage() {
        return currImage;
    }

    public void setCurrImage(int currImage) {
        this.currImage = currImage;
    }

    public int getCurrDirectionImage() {
        return currDirectionImage;
    }

    public void setCurrDirectionImage(int currDirectionImage) {
        this.currDirectionImage = currDirectionImage;
    }
}
