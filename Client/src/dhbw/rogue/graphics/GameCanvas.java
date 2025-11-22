package dhbw.rogue.graphics;

import data.Message;
import dhbw.rogue.functionality.Chat;
import dhbw.rogue.functionality.RogueKeyListener;
import dhbw.rogue.connection.ServerConnection;
import effects.Effect;
import entity.Dwarf;
import entity.Entity;
import entity.Player;
import mapmanager.MapManager;
import spritemanager.ResourceManager;
import utility.Settings;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.*;
import java.util.List;

public class GameCanvas extends Canvas implements Runnable {

    private boolean running;
    private int fps;
    private int tps;
    private final List<String> informationMessages;

    private final Player player;

    private ServerConnection serverConnection;
    private final RogueKeyListener listener;
    private final Chat chat;

    private final List<Entity> entities;
    private final List<Player> players;

    private final MapRenderer mapRenderer;

    private final ResourceManager resourceManager;
    private final LightRenderer lightRenderer;

    public GameCanvas(ResourceManager resourceManager, MapManager mapManager) {
        running = true;

        informationMessages = Collections.synchronizedList(new ArrayList<>());
        players = Collections.synchronizedList(new ArrayList<>());
        entities = Collections.synchronizedList(new ArrayList<>());
        chat = new Chat(this);

        this.resourceManager = resourceManager;

        player = new Dwarf(0, 0, resourceManager);
        listener = new RogueKeyListener(player, chat);
        addKeyListener(listener);

        mapRenderer = new MapRenderer(resourceManager, mapManager);
        lightRenderer = new LightRenderer(mapRenderer.getMap());

    }

    public void startThread() {
        new Thread(this).start();
        deleteMessages();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double unprocessed = 0;
        double nsPerTick = 1000000000.0 / Settings.TPS;
        int ticks = 0;
        int frames = 0;
        long lastTimer = System.currentTimeMillis();

        while (running) {
            long now = System.nanoTime();
            unprocessed += (now - lastTime) / nsPerTick;
            lastTime = now;

            while (unprocessed >= 1) {
                ticks++;
                player.tick();
                mapRenderer.tick(); // maybe animations for maps?

                synchronized (player) { //ConcurrentModificationException without it :)
                    if (serverConnection != null) {
                        serverConnection.sendObject(player);
                    }
                }
                unprocessed--;
            }

            frames++;
            render();

            if (System.currentTimeMillis() - lastTimer > 1000) {
                lastTimer += 1000;
                fps = frames;
                tps = ticks;
                frames = 0;
                ticks = 0;
            }
        }
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            requestFocus();
            return;
        }
        Toolkit.getDefaultToolkit().sync();

        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED); //For MacOS, since I have stuttering
        g.setColor(Color.BLACK);
        g.fillRect(0,0, getWidth(), getHeight());

        int height = 0;

        mapRenderer.render(g, player.getX(), player.getY());

        synchronized (informationMessages) {
            for (String message : informationMessages) {
                g.drawString(message, 20, height + 80);
                height += 15;
            }
        }

        synchronized (entities) {
            for (Entity entity : entities) {
                entity.draw(g);
            }
        }

        synchronized (players) {
            for (Player player : players) {
                player.drawPlayer(g, this.player.getX(), this.player.getY());
            }
        }

        player.draw(g);

        lightRenderer.renderLight(g, player.getX(), player.getY());

        for (Effect effect : player.getEffects()) {
            g.drawImage(effect.getEffectIcon(), 48, Settings.SCREEN_HEIGHT-96, null);
        }

        g.setColor(Color.GREEN);
        g.fillRect(20, 48, Settings.SCALED_TILE_SIZE, 16);
        g.setColor(Color.BLUE);
        g.fillRect(20, 64, Settings.SCALED_TILE_SIZE, 16);

        g.setColor(Color.WHITE);
        g.drawString("FPS: " + fps, 20, 20);
        g.drawString("TPS: " + tps, 20, 40);

        chat.renderChat(g);

        g.dispose();
        bs.show();
    }

    public synchronized void addPlayer(Player player) {
        if (this.player.getName().equals(player.getName())) {
            this.player.updatePlayer(player);
            return;
        }

        synchronized (players) {
            for (Player p : players) {
                if (p.getName().equals(player.getName())) {
                    p.updatePlayer(player);
                   return;
                }
            }
            player.setResourceManager(resourceManager);
            player.loadImages();
            for(Effect p : player.getEffects()) {
                p.setResourceManager(resourceManager);
                p.loadEffect();
            }
            players.add(player);
        }
    }

    public void addEntity(Entity entity) {
        if (!entities.contains(entity)) {
            entities.add(entity);
        } else {
            entities.remove(entity);
            entities.add(entity);
        }
    }

    public void removePlayer(Player player) {
        synchronized (players) {
            players.removeIf(p -> p.getName().equals(player.getName()));
        }
    }

    private void deleteMessages() {
        new Thread(() -> {
            while(running) {
                if (!informationMessages.isEmpty()) {
                    try {
                        Thread.sleep(1300);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    synchronized (informationMessages) {
                        informationMessages.remove(informationMessages.getFirst());
                    }
                } else {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void setServerConnection(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public void addInformationMessage(String message) {
        informationMessages.add(message);
    }

    public void addChatMessage(Message message) {
        chat.addMessage(message);
    }

    public void sendMessageToServer(Message message) {
        serverConnection.sendObject(message);
    }
}
