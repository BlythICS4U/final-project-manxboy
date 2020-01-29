/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.manxboy.paintball.entity;

import io.manxboy.paintball.Environment;
import io.manxboy.paintball.graphics.Renderer;
import io.manxboy.paintball.math.Vec3f;
import io.manxboy.paintball.tiles.Tile;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 * @author manxboy
 */
public class Paintball extends Entity {
    
    private Color color;
    
    public Paintball(Vec3f pos, Vec3f vel, Color c) {
        
        super(pos);
        
        this.color = c;
        
        this.vel = vel;
    }
    
    public Color getColor() {
        return color;
    }
    
    public static final double SPEED = 8;
    
    @Override
    public void tick(Environment e, double delta) {
        super.tick(e, delta);        
    }

    @Override
    public void render(Graphics g, Environment e, Renderer r) {        
        Vec3f render_pos = r.worldSpaceToDrawSpace(pos);
        
        g.setColor(color);
        
        g.fillArc((int)render_pos.x - 1 * r.getScale(), (int)render_pos.y - 1 * r.getScale(), 2 * r.getScale(), 2 * r.getScale(), 0, 360);
    }
    
    @Override
    public void onCollision(Environment e, Vec3f tile_pos, double delta) {
        Vec3f pos_ground = worldSpaceToSplatPixelSpace(tile_pos);
        Vec3f pos_up = worldSpaceToSplatPixelSpace(tile_pos.add(0, 0, 1)).add(0, Tile.HEIGHT, 0);
        
        System.out.println(pos_ground.toStringFixed());
        
        Tile ground = e.getTile(tile_pos);
        Tile up = e.getTile(tile_pos.add(0, 0, 1));
        
        Graphics ground_g = ground.getSplatter().getGraphics();
        
        for (int a = 0; a < 10; a++) {
            double rand_x = ((paint_randomizer.nextDouble() * 2) -1 ) * a * .8;
            double rand_y = ((paint_randomizer.nextDouble() * 2) -1 ) * a * .8;
            
            ground_g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(((paint_randomizer.nextDouble() * 128) + 64) *  (1.1 - (a / 10))) ));
            ground_g.drawRect((int)(pos_ground.x + rand_x), (int)(pos_ground.y + rand_y), 1, 1);
        
            Graphics up_g = up.getSplatter().getGraphics();
        
            up_g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(((paint_randomizer.nextDouble() * 128) + 64) *  (1.1 - (a / 10))) ));
            up_g.drawRect((int)(pos_up.x + rand_y), (int)(pos_up.y + rand_y), 1, 1);
        }
        
        
        
        e.removeEntity(this);
        
    }
    
    public static Vec3f worldSpaceToSplatPixelSpace(double x, double y, double z) {
        return new Vec3f(
                (x * (Tile.WIDTH/2) + y * (Tile.WIDTH/2)) % (Tile.WIDTH / 2.0),
                (y * (Tile.HEIGHT/2) + x * (Tile.HEIGHT/2) + z * (Tile.WIDTH/2)) % (Tile.HEIGHT / 2),
                0);
    }
    
    public static Vec3f worldSpaceToSplatPixelSpace(Vec3f v) {
        return worldSpaceToSplatPixelSpace(v.x, v.y, v.z);
    }
    
    private static Random paint_randomizer = new Random();
}
