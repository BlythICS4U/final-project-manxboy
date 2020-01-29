/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.manxboy.paintball;

import io.manxboy.paintball.entity.Entity;
import io.manxboy.paintball.math.Vec3f;
import io.manxboy.paintball.tiles.FloorTile;
import io.manxboy.paintball.tiles.FloorTileWithWall;
import io.manxboy.paintball.tiles.Tile;
import io.manxboy.paintball.tiles.WallTile;
import java.util.ArrayList;

/**
 *
 * @author manxboy
 */
public class Environment {
    private Tile[][][] tiles;
    
    private ArrayList<Entity> entities;
    
    public Environment() {
        tiles = new Tile[3][20][20];
        entities = new ArrayList();
        
        for (int x = 0; x < getSizeX(); x++) {
            for (int y = 0; y < getSizeY(); y++) {
                    if (x == 0 || y == 0 || x == getSizeX() - 1 || y == getSizeY() - 1) {
                        
                        setTile(new WallTile(), x, y, 0);
                        setTile(new FloorTileWithWall(), x, y, 1);
                        
                        continue;
                    }
                    
                    setTile(new FloorTile(), x, y, 0);
            }
        }
        
        setTile(new WallTile(), 4, 4, 0);
        setTile(new FloorTileWithWall(), 4, 4, 1);
    }
    
    public Tile getTile(Vec3f v) {
        return getTile((int)(v.x + .5), (int)(v.y + .5), (int)v.z);
    }
    
    public Tile getTile(int x, int y, int z) {
        
        if (x < 0 || x >= getSizeX()) return null;
        if (y < 0 || y >= getSizeY()) return null;
        if (z < 0 || z >= getSizeZ()) return null;
        
        return tiles[z][x][y];
    }
    
    public Tile setTile(Tile t, Vec3f v) {
        return setTile(t, (int) v.x, (int) v.y, (int) v.z);
    }
    
    public Tile setTile(Tile t, int x, int y, int z) {
        Tile old = getTile(x, y, z);
        
        tiles[z][x][y] = t;
        
        return old;
    }
    
    public int getSizeX() {
        return tiles[0].length;
    }
    
    public int getSizeY() {
        return tiles[0][0].length;
    }
    
    public int getSizeZ() {
        return tiles.length;
    }
    
    public void addEntity(Entity e) {
        entities.add(e);
    }
    
    public void removeEntity(Entity e) {
        entities.remove(e);
    } 
    
    public Entity[] getEntities() {
        return entities.toArray(Entity[]::new);
    }
}
