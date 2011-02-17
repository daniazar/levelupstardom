/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame.model;

/**
 *
 * @author matiaspan
 */
public interface GameInstanceManager {

    boolean isGameOver();
    float getScore();
    int getEnergyLeft();

    void damageCharacter(float damage);
    void addPoints(float points);

}
