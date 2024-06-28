package it.unibo.model.entities.defense.tower.attack;

import java.util.Optional;

import it.unibo.model.entities.defense.bullet.Bullet;
import it.unibo.model.entities.defense.bullet.BulletImpl;
import it.unibo.model.entities.defense.tower.Tower;
import it.unibo.model.entities.defense.tower.target.TargetSelectionStrategy;
import it.unibo.model.entities.enemies.Enemy;
import it.unibo.model.utilities.Vector2D;

/**
 * Strategy pattern implmentation. Attack based on a single target.
 */
public class SingleTargetAttack implements AttackStrategy {

    private static final int SLOW_FREQ = 5000;
    private static final double BULLET_SPEED = 0.15;
    private static final int BULLET_DAMAGE = 25;

    /**
     * {@link Tower}'s attack method to attack target {@link Enemy}. Attacking
     *
     * @param tower Target @param enemy chosen by the {@link Tower} depending on
     * the {@link TargetSelectionStrategy}.
     */
    @Override
    public void attack(final Tower tower, final Optional<Enemy> enemy) {
        enemy.ifPresent(e -> {
            long lastShotTime = tower.getCurrentWeapon().getLastShotTime();
            long currentTime = System.currentTimeMillis();
            long timeSinceLastShot = currentTime - lastShotTime;
            long fireRateInMilliseconds = (long) (SLOW_FREQ / tower.getCurrentWeapon().getFrequency());

            if (timeSinceLastShot >= fireRateInMilliseconds) {
                System.out.println(tower.getName() + " attacco a " + e.getName());
                Vector2D direction = Vector2D.calculateDirection(tower.getPosition(), e.getPosition());
                Bullet bullet = new BulletImpl(1, "bullet", "base", "bullet/img/bullet.png", tower.getPosition(),
                        direction, BULLET_SPEED, BULLET_DAMAGE, e);

                tower.getBullets().add(bullet);
                tower.getCurrentWeapon().setLastShotTime(currentTime);
            }
        });
    }
}
