package dhbw.rogue.connection;

import data.Message;
import dhbw.rogue.graphics.Window;
import entity.Entity;
import entity.Player;
import spritemanager.ResourceManager;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class ServerConnection {

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private final Socket socket;
    private final Window gameWindow;;

    public ServerConnection(Socket socket, Window gameWindow) {
        this.socket = socket;
        this.gameWindow = gameWindow;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("[ERROR] Couldn't establish connection.");
            e.printStackTrace();
            System.exit(0);
            return;
        }
        createContinuousConnection();
    }

    private void createContinuousConnection() {
        new Thread(() -> {
            Object msg;
            try {
                while ((msg = in.readObject()) != null) {
                    receiveMessage(msg);
                }
            } catch (IOException e) {
                System.out.println("[INFO] Disconnected from server.");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (ArrayStoreException e) {
                System.err.println("[ERROR] ArrayStoreException while reading object.");
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("[INFO] Disconnected from Server.");
                }
            }
        }).start();
    }

    private synchronized void receiveMessage(Object msg) {
        try {
            switch (msg) {
                case String s -> gameWindow.addInformationMessage(s);
                case Player player -> gameWindow.update_player(player);
                case Entity entity -> gameWindow.update_entity(entity);
                case Message message -> gameWindow.addChatMessage(message);
                default -> {}
            }
        } catch (ClassCastException e) {
            System.out.println("[ERROR] Can't cast Object" + System.lineSeparator());
            e.printStackTrace();
        }
    }

    public synchronized void sendObject(Object o) {
        try {
            synchronized (out) {
                if (out != null && !socket.isClosed()) {
                    out.reset();
                    out.writeObject(o);
                    out.flush();
                } else {
                    System.out.println("[Info] Lost connection.");
                    JOptionPane.showMessageDialog(gameWindow, "Lost connection.", "Server Connection", JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
