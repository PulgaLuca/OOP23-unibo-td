package it.unibo.model.entities;
/**
 * Player implementation.
 */
public class PlayerImpl implements Player {

    private int money; //money of player
    private static final int maxLives = 3; //max life of player
    private int lives;

    /**
     * Instructor method, constructs the player object with start game values.
     */
    public PlayerImpl() {
        lives = 1; //lives to star game
        money = 1; //money get player to start
    }

    /**
     * @return Returns the maximum number of lives the player can have
     */
    public int getMaxLives() {
        return maxLives;
    }

    /**
     * @return Number of lives the player has left
     */
    public int getRemainingLives() {
        return lives;
    }

    /**
     * Damage the player's screw.
     * @param damage Number of lives the player has lost
     */
    public void loseLives(final int damage) {
        lives = lives - damage;
        return;
    }

    /**
     * Restoring the player's screw. I use the damage method by passing a negative value (see subtraction).
     * @param numberLives Number of lives to be restored
     */
    public void restoreLives(final int numberLives) {
        loseLives(-numberLives);
        return;
    }

    /**
     * @return money currently held
     */
    public int getMoney() {
        return money;
    }

    /**
     * the cash will be added to the money.
     * @param cash in the case of a reward the value stored in the money will increase, 
     * - however in the case of a construction cost the past value will be negative so it will decrease
     */
    public void setMoney(final int cash) {
        money = money + cash;
        return;
    }
}
