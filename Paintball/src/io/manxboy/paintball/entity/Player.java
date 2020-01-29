/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.manxboy.paintball.entity;

import io.manxboy.paintball.Environment;
import io.manxboy.paintball.Input;
import io.manxboy.paintball.Resources;
import io.manxboy.paintball.graphics.Renderer;
import io.manxboy.paintball.math.Vec3f;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

/**
 *
 * @author manxboy
 */
public class Player extends Entity {
    //stores the color of the player/ the color of the players paintballs
    private Color color;
    
    //stores the current cooldown for the player fire action
    private double fireCooldown = 0;
    
    public Player(Color color, Vec3f pos) {
        super(pos);
        this.color = color;
    }
    
    /**
     * get the current player/paintball color
     * @return the color
     */
    public Color getColor() {
        return color;
    }
    
    /**
     * set the current player/paintball color
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void tick(Environment e, double delta) {
        
        this.vel = new Vec3f(
                Input.X * Math.cos(Math.PI/4) - Input.Y * Math.sin(Math.PI/4),
                (Input.Y * Math.cos(Math.PI/4) + Input.X * Math.sin(Math.PI/4)),
                0
        ).mult(SPEED);
        
        
        if (fireCooldown > FIRE_COOLDOWN_LENGTH) {
            
            
            if (Input.FIRE) {
                Paintball ball = new Paintball(pos.add(0, 0, .5), new Vec3f(0, 1, 0).mult(Paintball.SPEED), color);
                
                e.addEntity(ball);
                
                fireCooldown = 0;
            }
        } else {
            fireCooldown = fireCooldown + delta;
            
        }
        
        
        super.tick(e, delta);
    }
    
    
    private static final int SPEED = 4;
    private static final double FIRE_COOLDOWN_LENGTH = 0.25;

    @Override
    public void render(Graphics g, Environment e, Renderer r) {
        Vec3f p_location = r.worldSpaceToDrawSpace(r.getGame().getPlayer().getPos());

        Image i = Resources.getImage("player");
        
        Vec3f image_location = p_location.sub((((double)i.getWidth(null)) * r.getScale() / 2) - 9 * r.getScale(), ((double)i.getHeight(null)) * r.getScale(), 0);
        
        g.drawImage( i, (int) image_location.x, (int) image_location.y, i.getWidth(null) * r.getScale(), i.getHeight(null) * r.getScale(), null);
        
        /*
        g.setColor(Color.cyan);
                
        g.fillRect((int) p_location.x, (int) p_location.y, 8, 8);
        
        g.setColor(Color.magenta);
                
        g.fillRect((int) p_location.x - 8, (int) p_location.y, 8, 8);
        
        g.setColor(Color.green);
                
        g.fillRect((int) p_location.x, (int) p_location.y -8, 8, 8);
        
        g.setColor(Color.yellow);
                
        g.fillRect((int) p_location.x - 8, (int) p_location.y - 8, 8, 8);
        
        Vec3f p_head_location = r.worldSpaceToDrawSpace(r.getGame().getPlayer().getPos().add(0, 0, 1));

        g.setColor(Color.cyan);
                
        g.fillRect((int) p_head_location.x, (int) p_head_location.y, 8, 8);
        
        g.setColor(Color.magenta);
                
        g.fillRect((int) p_head_location.x - 8, (int) p_head_location.y, 8, 8);
        
        g.setColor(Color.green);
                
        g.fillRect((int) p_head_location.x, (int) p_head_location.y -8, 8, 8);
        
        g.setColor(Color.yellow);
                
        g.fillRect((int) p_head_location.x - 8, (int) p_head_location.y - 8, 8, 8);
        */
    }
}
