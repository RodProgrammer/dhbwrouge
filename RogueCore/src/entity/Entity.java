package entity;

import effects.Effect;
import spritemanager.ResourceManager;
import utility.Settings;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Entity implements Serializable {

    protected int x;
    protected int y;
    protected final Rectangle rectangle;

    protected int health;
    protected int maxHealth;
    protected int mana;
    protected int maxMana;

    protected int speed;

    protected String name;

    protected transient ResourceManager resourceManager;
    protected List<Effect> effects;

    public Entity(int x, int y, int maxHealth, int maxMana, ResourceManager resourceManager) {
        this.x = x;
        this.y = y;
        this.rectangle = new Rectangle(Settings.SCALED_TILE_SIZE, Settings.SCALED_TILE_SIZE);

        this.rectangle.x = x;
        this.rectangle.y = y;
        this.rectangle.width = Settings.SCALED_TILE_SIZE;
        this.rectangle.height = Settings.SCALED_TILE_SIZE;

        this.maxHealth = maxHealth;
        this.maxMana = maxMana;
        this.health = maxHealth;
        this.mana = maxMana;
        this.speed = 5;

        this.resourceManager = resourceManager;

        this.effects = new ArrayList<>();
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(x, y, rectangle.width, rectangle.height);
        g.setColor(Color.RED);
        g.drawString(name, x - (name.length() * 2), y - 8);
    }

    public abstract void tick();

    public boolean intersect(Rectangle rect) {
        return rectangle.intersects(rect);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        rectangle.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        rectangle.y = y;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public String toString() {
        return "Entity{" + "x=" + x + ", y=" + y + '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    public void setResourceManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    public List<Effect> getEffects() {
        return effects;
    }
}
