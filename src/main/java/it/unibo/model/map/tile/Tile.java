package it.unibo.model.map.tile;

import java.util.Set;

import it.unibo.model.entities.defense.tower.Tower;
import it.unibo.model.utilities.Position2D;

/**
 * Represents a square 2-dimensional tile that forms the
 * {@link it.unibo.model.map.GameMap GameMap}.
 */
public interface Tile {

    /**
     * @return The set of {@link TileFeature}
     */
    Set<TileFeature> getTileFeatures();

    /**
     * @return the path of the file representing the {@link Tile}
     */
    String getSprite();

    /**
     * @return {@code true} if the {@link Tile} is empty and allows buildings,
     * {@code false} otherwise
     */
    boolean canBuild();

    /**
     * Occupies the current {@link Tile} with a {@link Tower}.
     *
     * @param tower The {@link Tower} to build
     */
    void buildTower(Tower tower);

    /**
     * @return tile's position.
     */
    Position2D getPosition();

    /**
     * Set the tile's {@link Position2D}.
     *
     * @param position2D
     */
    void setPosition(Position2D position2D);
}
