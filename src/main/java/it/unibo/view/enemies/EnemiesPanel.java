package it.unibo.view.enemies;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import it.unibo.model.core.GameState;
import it.unibo.model.entities.enemies.Enemy;
import it.unibo.model.entities.enemies.EnemyState;

/**
 * .
 */
public class EnemiesPanel extends JPanel {

    private ArrayList<Enemy> enemies;
    private int xCellSize;
    private int yCellSize;

    /**
     * .
     * @param enemies
     * @param xCellSize
     * @param yCellSize
     */
    public EnemiesPanel(final ArrayList<Enemy> enemies, final int xCellSize, final int yCellSize) {
        this.enemies = enemies;
        this.xCellSize = xCellSize;
        this.yCellSize = yCellSize;
    }

    @Override
    protected final void paintComponent(final Graphics g) {
        super.paintComponent(g);

        for (Enemy enemy : this.enemies) {
            if (enemy.getState().equals(EnemyState.MOVING)) {
                try {
                    BufferedImage enemyImage = ImageIO.read(new File(enemy.getImagePath()));
                    g.drawImage(enemyImage, (int) (enemy.getPosition().xInt() * this.xCellSize),
                            (int) (enemy.getPosition().yInt() * this.yCellSize), this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * .
     * @param gameState
     * @param xCellSize
     * @param yCellSize
     */
    public void updateView(final GameState gameState, final int xCellSize, final int yCellSize) {
        this.enemies = (ArrayList<Enemy>) gameState.getEnemies().stream().collect(Collectors.toList());
        this.xCellSize = xCellSize;
        this.yCellSize = yCellSize;
        this.revalidate();
        this.repaint();
    }

}
