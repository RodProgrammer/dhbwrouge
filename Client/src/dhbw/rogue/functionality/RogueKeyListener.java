package dhbw.rogue.functionality;

import entity.Direction;
import entity.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class RogueKeyListener implements KeyListener {

    private final Player player;
    private final Chat chat;

    private boolean chatOpened;

    public RogueKeyListener(Player player, Chat chat) {
        this.player = player;
        chatOpened = false;
        this.chat = chat;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!chatOpened) {
            if (KeyEvent.VK_W == e.getKeyCode()) {
                player.addDirection(Direction.UP);
            }

            if (KeyEvent.VK_S == e.getKeyCode()) {
                player.addDirection(Direction.DOWN);
            }

            if (KeyEvent.VK_A == e.getKeyCode()) {
                player.addDirection(Direction.LEFT);
            }

            if (KeyEvent.VK_D == e.getKeyCode()) {
                player.addDirection(Direction.RIGHT);
            }

            if (KeyEvent.VK_T == e.getKeyCode()) {
                chatOpened = !chatOpened;
                player.removeDirection(Direction.UP);
                player.removeDirection(Direction.DOWN);
                player.removeDirection(Direction.LEFT);
                player.removeDirection(Direction.RIGHT);
            }

            return;
        }

        if (KeyEvent.VK_BACK_SPACE == e.getKeyCode()) {
            chat.deleteLetter();
        }

        if (KeyEvent.VK_ESCAPE == e.getKeyCode()) {
            chat.clearLetters();
            chatOpened = !chatOpened;
        }

        if (KeyEvent.VK_ENTER == e.getKeyCode()) {
            chatOpened = !chatOpened;
            chat.sendMessage();
        }

        if (chatOpened) {
            chat.addLetter(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!chatOpened) {
            if (KeyEvent.VK_W == e.getKeyCode()) {
                player.removeDirection(Direction.UP);
            }

            if (KeyEvent.VK_S == e.getKeyCode()) {
                player.removeDirection(Direction.DOWN);
            }

            if (KeyEvent.VK_A == e.getKeyCode()) {
                player.removeDirection(Direction.LEFT);
            }

            if (KeyEvent.VK_D == e.getKeyCode()) {
                player.removeDirection(Direction.RIGHT);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
