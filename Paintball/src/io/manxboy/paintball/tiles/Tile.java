/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.manxboy.paintball.tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author manxboy
 */
public abstract class Tile {
    
    public static final int WIDTH = 60;
    public static final int HEIGHT = 31;

    private BufferedImage splatter;
    
    public Tile() {
        splatter = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
    }
    
    public abstract Image getSprite();
    
    public abstract boolean isTraversable();
    
    public Image getSplatter() {
        return splatter;
    }
    
}
