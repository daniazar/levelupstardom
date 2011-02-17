/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame.model;

/**
 *
 * @author matiaspan
 */
public class PickupGameInstanceManager implements GameInstanceManager {

    private int energy;
    private float score;

    public PickupGameInstanceManager() {
        energy = 100;
        score = 0;
    }
    
    public boolean isGameOver() {
        return energy <= 0;
    }

    public float getScore() {
        return score;
    }

    public int getEnergyLeft() {
        return energy;
    }

    public void damageCharacter(float damage) {
        energy -= damage;
    }

    public void addPoints(float points) {
        score += points;
    }

}
