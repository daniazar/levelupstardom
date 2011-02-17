/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame.model;

import java.util.Timer;

/**
 *
 * @author matiaspan
 */
public class PickupGameInstanceManager implements GameInstanceManager {

    private float energy;
    private float score;

    private static PickupGameInstanceManager instance;

    public static GameInstanceManager getInstance() {
        if (instance == null) {
            instance = new PickupGameInstanceManager();
        }
        return instance;
    }

    public static GameInstanceManager getNewInstance() {
        instance = new PickupGameInstanceManager();
        return instance;
    }
    
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
        return (int)energy;
    }

    public void damageCharacter(float damage) {
        energy -= damage;
    }

    public void addPoints(float points) {
        score += points;
    }

    

}
