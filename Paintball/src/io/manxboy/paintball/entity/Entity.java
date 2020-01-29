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
import java.awt.Graphics;

/**
 *
 * @author manxboy
 */
public abstract class Entity {
    /**
     * the position vector of the entity
     */
    protected Vec3f pos = Vec3f.ZERO;
    
    /**
     * the velocity of the entity
     */
    protected Vec3f vel = Vec3f.ZERO;
    
    public Entity(Vec3f pos) {
        this.pos = pos;
    }
    
    /**
     * get the current position of the entity
     * @return the position
     */
    public Vec3f getPos() {
        return pos;
    }
    
    /**
     * set the current position of the entity
     * @param v the new position
     */
    public void setPos(Vec3f v) {
        this.pos = v;
    }
    
    /**
     * Gets the Velocity of the entity
     * @return 
     */
    public Vec3f getVelocity() {
        return this.vel;
    }
    
    /**
     * Sets the Velocity of the entity
     * @param v the new velocity
     */
    public void setVelocity(Vec3f v) {
        this.vel = v;
    }
    
    /**
     * Ticks this entity. calculates and applies physics
     * @param e
     * @param delta 
     */
    public void tick(Environment e, double delta) {
        
        Vec3f destPos = pos.add(vel.mult(delta));
        Tile destTile = e.getTile(destPos);
        
        if (destTile != null && destTile.isTraversable()) {
            pos = destPos;
        } else {
            this.onCollision(e, destPos, delta);
        }
        
        //pos = pos.add(vel.mult(delta));

    }
    
    /**
     * Called whenever the entity collides with terrain
     * @param e
     * @param tile_pos
     * @param delta 
     */
    public void onCollision(Environment e, Vec3f tile_pos, double delta){};
    
    public abstract void render(Graphics g, Environment e, Renderer r);
}
