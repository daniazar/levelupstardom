/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame.model;

import java.io.Serializable;

/**
 *
 * @author matiaspan
 */
public class Score implements Serializable {

    public String name;
    public float score;

    public Score() {

    }
    
    public Score(String name, float score) {
        this.name = name;
        this.score = score;
    }
}
