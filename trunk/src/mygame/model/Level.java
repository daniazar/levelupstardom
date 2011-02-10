/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame.model;

/**
 *
 * @author matiaspan
 */
public class Level {

    private String name;
    private String filename;

    public String getName() {
        return name;
    }

    public String getFilename() {
        return filename;
    }

    public Level(String name, String filename) {
        this.name = name;
        this.filename = filename;
    }
}
