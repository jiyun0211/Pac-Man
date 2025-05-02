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

    private long lastTime = System.nanoTime();

    Pac_Man pacman = new Pac_Man(1, 1);

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
        timer = new Timer(16, this);
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
        g.fillOval(pacman.getX() * TILE_SIZE, pacman.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        long currentTime = System.nanoTime();
        double deltaTime = (currentTime - lastTime) / 1_000_000_000.0;
        lastTime = currentTime;

        pacman.update(map, deltaTime);

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e){
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP -> pacman.setDirection(0, -1);
            case KeyEvent.VK_DOWN -> pacman.setDirection(0, 1);
            case KeyEvent.VK_LEFT -> pacman.setDirection(-1, 0);
            case KeyEvent.VK_RIGHT -> pacman.setDirection(1, 0);
        }
    }

    @Override public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            default : pacman.setDirection(0, 0);
        }
        
    }
    @Override public void keyTyped(KeyEvent e) {}
}

abstract class Actor {
    protected int x, y;
    protected int dirX, dirY;
    private boolean isMoving = false;

    public Actor(int startX, int startY){
        this.x = startX;
        this.y = startY;
        this.dirX = 0;
        this.dirY = 0;
    }

    public void setDirection(int dirX, int dirY){
        this.dirX = dirX;
        this.dirY = dirY;
        if(this.dirX == 0 && this.dirY == 0) isMoving = true;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public abstract void update(int[][] map, double deltaTime);
}

class Pac_Man extends Actor {
    private int score;
    private final double speed = 5.0;
    private double accumulativeMove = 0.0;

    Pac_Man(int startX, int startY){
        super(startX, startY);
        score = 0;
    }

    @Override
    public void update(int[][] map, double deltaTime){
        accumulativeMove += speed * deltaTime;

        if(map[y + dirY][x + dirX] != 1){
            x += dirX;
            y += dirY;
        }
        eatCookie(map);
        accumulativeMove = 0.0;
    }

    public void eatCookie(int[][] map){
        if(map[y][x] == 2){
            map[y][x] = 0;
            score++;
        }
    }

    public int getScore(){
        return score;
    }
}

class Ghost extends Actor {
    Ghost(int startX, int startY){
        super(startX, startY);
    }

    @Override
    public void update(int[][] map, double deltaTime){

    }
}

class Cookie {

}

class PowerCookie extends Cookie {

}