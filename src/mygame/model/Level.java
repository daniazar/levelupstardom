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

    public Level()
    {
        
    }
    public Level(String name, String filename) {
        this.name = name;
        this.filename = filename;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    public boolean equals(Object o)
    {
        if (o.getClass() != Level.class)
            return false;
        if (((Level)o).name.equals(name))
            return true;
        else
            return false;
    }
}
