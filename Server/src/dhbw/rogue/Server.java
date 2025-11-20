package dhbw.rogue;

import data.Message;
import entity.Entity;
import entity.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {

    private ServerSocket serverSocket;

    private final List<ClientConnection> connections;

    private final List<Entity> monster; //for possible Monsters in the future

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("[ERROR] Server couldn't create ServerSocket");
        }
        connections = Collections.synchronizedList(new ArrayList<>());
        monster = Collections.synchronizedList(new ArrayList<>());

        System.out.println("[INFO] Server has been started.");
        //pingClients();
    }

    public synchronized void removeClient(ClientConnection clientConnection) {
        connections.remove(clientConnection);
        System.out.println("Client " + clientConnection.getUsername() + " has disconnected.");
        for (ClientConnection connection : connections) {
            connection.sendInformation("Disconnected: " + clientConnection.getUsername());
            connection.sendMessage(new Message("Disconnected Player", clientConnection.getLastPlayerState()));
        }
    }

    public synchronized void sendMessage(ClientConnection clientConnection , Message message) {
        synchronized (connections) {
            for (ClientConnection client : connections) {
                client.sendMessage(new Message(message, clientConnection.getUsername()));
            }
        }
    }

    public synchronized void sendInformation(ClientConnection clientConnection, String information) {
        for (ClientConnection client : connections) {
            if (client != clientConnection) {
                client.sendInformation(information);
            }
        }
    }

    public synchronized void sendEntity(ClientConnection clientConnection, Entity entity) {
        synchronized (connections) {
            for (ClientConnection client : connections) {
                if (client != clientConnection) {
                    client.sendEntity(entity);
                }
            }
        }
    }

    public synchronized void sendPlayer(ClientConnection clientConnection, Player player) {
        synchronized (connections) {
            for (ClientConnection client : connections) {
                if (client != clientConnection) {
                    client.sendPlayer(player);
                }
            }
        }
    }

    public synchronized void updatePlayer(ClientConnection clientConnection, Player player) {
        synchronized (connections) {
            for (ClientConnection client : connections) {
                if (client == clientConnection) {
                    client.sendPlayer(player);
                    break;
                }
            }
        }
    }

    public void startServer() {
        runTPS();
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                ClientConnection client = new ClientConnection(socket, this);
                connections.add(client);
                client.start();
            } catch (IOException e) {
                System.out.println("[ERROR] Client Connecting error");
            }
        }
    }

    private void runTPS() {
        new Thread(() -> {
            long lastTime = System.nanoTime();
            final double ticks = 60D;
            double ns = 1000000000 / ticks;
            double delta = 0;

            int tps = 0;

            while (true) {

                if (connections.isEmpty()) continue;

                long now = System.nanoTime();
                delta += (now - lastTime) / ns;
                lastTime = now;
                if (delta >= 1) {
                    tick();
                    delta--;
                    tps++;
                }

                if (tps >= 60) {
                    tps = 0;
                }
            }
        }).start();
    }

    private void tick() {
        for (ClientConnection client : connections) {
            Player player = client.getLastPlayerState();
            if (player != null) {
                if (player.getX() < 0) {
                    player.setX(0);
                    updatePlayer(client, player);
                } else if(player.getY() < 0) {
                    player.setY(0);
                    updatePlayer(client, player);
                }
            }
        }
    }

    private void pingClients() {
        new Thread(() -> {
            while(true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {}

                for (ClientConnection clientConnection : connections) {
                    clientConnection.sendInformation("Ping from Server!");
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        new Server(4000).startServer();
    }
}
