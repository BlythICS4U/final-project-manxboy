/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.manxboy.paintball.tiles;

import io.manxboy.paintball.Resources;
import io.manxboy.paintball.math.Vec3f;
import java.awt.Image;

/**
 *
 * @author manxboy
 */
public class FloorTile extends Tile {
    
    @Override
    public Image getSprite() {
        return SPRITE;
    }
    
    
    public static final Image SPRITE = Resources.getImage("test_floor_tile");

    @Override
    public boolean isTraversable() {
        return true;
    }
    
}
