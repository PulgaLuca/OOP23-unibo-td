package it.unibo.model.entities.defense.tower;

import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;

import it.unibo.model.entities.defense.tower.attack.AttackStrategy;
import it.unibo.model.entities.defense.tower.target.TargetSelectionStrategy;
import it.unibo.model.entities.defense.weapon.Weapon;
import it.unibo.model.entities.defense.weapon.WeaponImpl;
import it.unibo.model.entities.enemies.Enemy;
import it.unibo.model.utilities.Position2D;
import it.unibo.model.utilities.Vector2D;

/**
 * Basic Tower class extending {@link AbstractTower}.
 */
public class BasicTower extends AbstractTower {

    /**
     * Basic tower constructor.
     *
     * @param id
     * @param name
     * @param type
     * @param imgPath
     * @param position2d
     * @param direction2d
     * @param cost
     * @param level
     * @param range
     * @param weapons
     * @param currentWeapon
     * @param attackStrategy
     * @param targetSelectionStrategy
     */
    @JsonCreator
    public BasicTower(final int id, final String name, final String type, final String imgPath, final Position2D position2d,
            final Vector2D direction2d, final int cost, final int level, final int range,
            final Set<WeaponImpl> weapons, final Weapon currentWeapon, final AttackStrategy attackStrategy,
            final TargetSelectionStrategy targetSelectionStrategy) {
        super(id, name, type, imgPath, position2d, direction2d, cost, level, range, weapons, currentWeapon,
                attackStrategy, targetSelectionStrategy);
    }

    /**
     * {@link Tower}'s target method to identify the target {@link Enemy}.
     *
     * Target @param enemies available on the map.
     */
    @Override
    public Optional<Enemy> target(final Set<Enemy> enemies) {
        return this.getTargetSelectionStrategy().selectTarget(this, enemies);
    }

    /**
     * {@link Tower}'s attack method to attack {@link Enemy}.
     *
     * @param enemies available on the map.
     */
    @Override
    public void attack(final Set<Enemy> enemies) {
        if (!enemies.isEmpty()) {
            final Optional<Enemy> chosenEnemy = this.target(enemies);
            chosenEnemy.ifPresent(enemy -> {
                this.getAttackStrategy().attack(this, chosenEnemy);
            });
        }
        updateBullets();
    }
}
