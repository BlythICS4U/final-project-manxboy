/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.manxboy.paintball.graphics;

import io.manxboy.paintball.Environment;
import io.manxboy.paintball.Game;
import io.manxboy.paintball.entity.Entity;
import io.manxboy.paintball.tiles.Tile;
import io.manxboy.paintball.math.Vec3f;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author manxboy
 */
public class Renderer {
    /**
     * the window on which this renderer renders
     */
    private JFrame frame;
    
    /**
     * the canvas on which this renderer renders
     */
    private Canvas canvas;
    
    /**
     * buffer strategy that the renderer uses to prepare then show the frame
     */
    private BufferStrategy buffer;
    
    /**
     * the current Environment to render
     */
    private Environment environment;
    
    /**
     * stores the current camera location
     */
    private Vec3f camera = Vec3f.ZERO;
    
    private Game game;
    
    /**
     * stores the current scale
     */
    private int scale = 2;
    
    private ArrayList<Overlay> overlays;
        
    public Renderer(Game game) {
        
        this.game = game;
        
        frame = new JFrame("paintball");
        canvas = new Canvas();
        
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                System.out.println("closing");
                game.stop();
            }
        });
        
        frame.add(canvas);
        
        frame.setSize(600, 400);
        frame.setVisible(true);
        
        canvas.createBufferStrategy(2);
        
        buffer = canvas.getBufferStrategy();
                        
        overlays = new ArrayList();
        
        overlays.add(Overlay.DEBUG);
    }
    
    /**
     * Tells the renderer to create and show a new frame
     */
    public void render(double delta) {
        
        camera = worldSpaceToCameraVec(game.getPlayer().getPos());
        
        //create snapshots of certain properties
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        
        Graphics g = buffer.getDrawGraphics();
        
        //clear screen
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        
        //render all tiles
        
        Tile t = environment.getTile(game.getPlayer().getPos());
        
        for (int y = environment.getSizeY() - 1; y >= 0; y--) {
            for (int x = 0; x < environment.getSizeX(); x++) {
                for (int z = 0; z < environment.getSizeZ(); z++) {
                    Tile tile = environment.getTile(x, y, z);
                     
                    if (tile != null) {
                        Image image = tile.getSprite();
                    
                        int iwidth = image.getWidth(null);
                        int iheight = image.getHeight(null);
                    
                        Vec3f world_loc = worldSpaceToDrawSpace(x, y, z);
                    
                        Vec3f tile_loc = world_loc.sub(iwidth * scale / 2.0, iheight * scale / 2.0, 0);
                    
                        g.drawImage(
                                image,
                                (int)tile_loc.x, //x
                                (int)tile_loc.y, //y
                                iwidth * scale,  //width
                                iheight *scale,  //height
                                null
                        );
                        
                        Image splatter = tile.getSplatter();
                        int swidth = image.getWidth(null);
                        int sheight = image.getHeight(null);
                        
                        g.drawImage(
                                splatter,
                                (int)tile_loc.x, //x
                                (int)tile_loc.y, //y
                                swidth * scale,  //width
                                sheight *scale,  //height
                                null
                        );
                        
                        g.setColor(Color.black);
                        
                        if (tile == t) g.setColor(Color.red);
                        
                        //g.drawString("(" + x + ", " + y + ", " + z + ")", (int)world_loc.x, (int)world_loc.y);
                        
                    }
                    
                    
                }
            }
        }
        
        for (Entity e : environment.getEntities()) {
            e.render(g, environment, this);
        }
        
        
        //render all overlays
        for (Overlay o : overlays) {
            if (o.isEnabled()) o.draw(g, delta, game, this);
        }
        
        //clean up graphics object and display result
        g.dispose();
        buffer.show();
    }
    
    /**
     * converts from world space (i.e. tile space) to drawSpace (where )
     * @param v
     * @return 
     */
    public Vec3f worldSpaceToDrawSpace(Vec3f v) {
        return worldSpaceToDrawSpace(v.x, v.y, v.z);
    }
    
    /**
     * converts from world space (i.e. tile space) to drawSpace (where )
     * @param v
     * @return 
     */
    public Vec3f worldSpaceToDrawSpace(double x, double y, double z) {        
        return new Vec3f(
                x * (Tile.WIDTH/2) * scale + y * (Tile.WIDTH/2) * scale + canvas.getWidth() / 2.0,
                (y * (Tile.HEIGHT/2) * scale + x * (-Tile.HEIGHT/2) * scale + z * (Tile.WIDTH/2) * scale) * -1.0 + canvas.getHeight() / 2.0,
                0).sub(camera);
    }
    
    
    public Vec3f worldSpaceToCameraVec(Vec3f v) {
        return worldSpaceToCameraVec(v.x, v.y, v.z);
    }
    
    
    public Vec3f worldSpaceToCameraVec(double x, double y, double z) {
        return new Vec3f(
                x * (Tile.WIDTH / 2) * scale + y * (Tile.WIDTH / 2) * scale,
                (y * (Tile.HEIGHT / 2) * scale + x * (-Tile.HEIGHT / 2) * scale + z * (-Tile.WIDTH / 2) * scale) * -1.0,
                0
        );
    }
    
    
    /**
     * gets the current environment
     * @return the environment
     */
    public Environment getEnvironment() {
        return environment;
    }
    
    /**
     * set the current environment
     * @param e the new environment;
     * @return the old environment
     */
    public Environment setEnvironmnent(Environment e) {
        Environment old = environment;
        
        environment = e;
        
        return old;
    }
    
    /**
     * add an overlay to the renderer
     * @param o the overlay to add
     */
    public void addOverlay(Overlay o) {
        overlays.add(o);
    }
    
    /**
     * removes an overlay from the renderer
     * @param o the overlay to remove
     * @return the overlay that was removed
     */
    public Overlay removeOverlay(Overlay o) {
        overlays.remove(o);
        
        return o;
    }
    
    /**
     * get an array of all of the overlays on this renderer
     * @return an array of overlays
     */
    public Overlay[] getOverlays() {
        return overlays.toArray(Overlay[]::new);
    }
    
    /**
     * returns the current camera
     * @return the camera
     */
    public Vec3f getCamera() {
        return this.camera;
    }
    
    public void setCamera(Vec3f v) {
        this.camera = worldSpaceToCameraVec(v);
    }
    
    public Game getGame() {
        return game;
    }
    
    public void dispose() {
        this.frame.dispose();
    }
    
    public int getScale() {
        return scale;
    }
    
    public void setScale(int v) {
        this.scale = v;
    }
}
