import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PacMan extends JFrame{
    public PacMan() {
        setTitle("PacMan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(new GamePanel());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static void main(String[] args){
        new PacMan();
    }
}

class GamePanel extends JPanel implements ActionListener, KeyListener {
    private final int TILE_SIZE = 24;
    private final int COOKIE_SIZE = 4;
    private final int ROWS = 40;
    private final int COLS = 40;
    private final Timer timer;

    private int pacmanX = 1;
    private int pacmanY = 1;
    private int dirX = 0;
    private int dirY = 0;

    private final int[][] map = {
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1},
        {1,2,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,2,1},
        {1,2,1,0,0,0,0,0,1,0,1,0,0,0,0,0,1,2,1},
        {1,2,1,0,1,1,1,0,1,0,1,0,1,1,1,0,1,2,1},
        {1,2,1,0,0,0,1,0,0,0,0,0,1,0,0,0,1,2,1},
        {1,2,1,0,0,0,1,1,1,1,1,1,1,0,0,0,1,2,1},
        {1,2,2,2,0,0,0,0,0,0,0,0,0,0,0,2,2,2,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
    };
    
    public GamePanel() {
        setPreferredSize(new Dimension(COLS * TILE_SIZE, ROWS * TILE_SIZE));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        timer = new Timer(100, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(int row = 0; row < map.length; row++){
            for(int col = 0; col < map[row].length; col++){
                if(map[row][col] == 1){
                    g.setColor(Color.BLUE);
                    g.fillRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
                else if(map[row][col] == 2){
                    g.setColor(Color.ORANGE);
                    g.fillRect(col * TILE_SIZE + (TILE_SIZE - COOKIE_SIZE) / 2,
                               row * TILE_SIZE + (TILE_SIZE - COOKIE_SIZE) / 2,
                               COOKIE_SIZE, COOKIE_SIZE);
                }
            }
        }

        g.setColor(Color.YELLOW);
        g.fillOval(pacmanX * TILE_SIZE, pacmanY * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(map[pacmanY + dirY][pacmanX + dirX] != 1){
            pacmanX += dirX;
            pacmanY += dirY;
        }
        if(map[pacmanY][pacmanX] == 2){
            map[pacmanY][pacmanX] = 0;
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e){
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP -> {dirX = 0; dirY = -1; }
            case KeyEvent.VK_DOWN -> { dirX = 0; dirY = 1; }
            case KeyEvent.VK_LEFT -> { dirX = -1; dirY = 0; }
            case KeyEvent.VK_RIGHT -> { dirX = 1; dirY = 0; }
        }
    }

    @Override public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP :
                if(dirY == -1){
                    dirX = 0;
                    dirY = 0;
                    break;
                }
            case KeyEvent.VK_DOWN :
                if(dirY == 1){
                    dirX = 0;
                    dirY = 0;
                    break;
                }
            case KeyEvent.VK_LEFT :
                if(dirX == -1){
                    dirX = 0;
                    dirY = 0;
                    break;
                }
            case KeyEvent.VK_RIGHT :
                if(dirX == 1){
                    dirX = 0;
                    dirY = 0;
                    break;
                }
        }
        
    }
    @Override public void keyTyped(KeyEvent e) {}
}

class Actor {
    private int x, y;
    private int dirX, dirY;

    public Actor(){
    }
}

class Pac_man extends Actor {
    private int score;

    /*public void move(int[][] map){
        if(map[y + dirY][x + dirX] != 1){
            x += dirX;
            y += dirY;
        }
    }*/

    public void eatCookie(){
        score++;
    }

    public int getScore(){
        return score;
    }
}

class Ghost extends Actor {

}

class Cookie {

}

class PowerCookie extends Cookie {

}