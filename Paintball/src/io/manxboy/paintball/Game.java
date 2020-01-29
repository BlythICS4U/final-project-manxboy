/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.manxboy.paintball;

import io.manxboy.paintball.entity.Entity;
import io.manxboy.paintball.entity.Player;
import io.manxboy.paintball.graphics.Overlay;
import io.manxboy.paintball.graphics.Renderer;
import io.manxboy.paintball.math.Vec3f;
import java.awt.Color;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manxboy
 */
public class Game {
    
    //stores weather the game is running. when set to false, the game stops
    private boolean running;
    
    //the renderer for this game
    private Renderer renderer;
    
    //the current environment that the game is using
    private Environment currentEnvironment;
    
    //the games player
    private Player player;
    
    /*DEBUG_VALUE: delta is copyed here for use by the debug overlay*/
    public double DEBUG_tick_delta;
    
    
    public Game() {
        //set the game running
        running = true;
        
        //create a new renderer
        renderer = new Renderer(this);        
        
        //create a new Player and set the color
        player = new Player(Color.GREEN, new Vec3f(1,1,0));
        
        setEnvironment(new Environment());
    }
    
    
    public void loop() {

        //Global time store, for last and current time of loop
        long currentTime = System.nanoTime();
        long lastTime = currentTime;
        
        //global time delta
        long deltaTime = 0;

        //stores the last time of tick/render update
        long lastFrameTime = currentTime;
        long lastTickTime = currentTime;
        
        //counts up time frame/tick
        long timeSinceLastFrame = 0;
        long timeSinceLastTick = 0;
        
        //stores delta of time since last frame/tick
        double frameDelta = 0;
        double tickDelta = 0;
        
        
        while (running) {
            //get the current time
            currentTime = System.nanoTime();
            
            //get the delta since the last loop
            deltaTime = currentTime - lastTime;
            
            //add the delta to the counters
            timeSinceLastFrame += deltaTime;
            timeSinceLastTick  += deltaTime;
            
            //check if enough time has elapsed for a new frame
            if (timeSinceLastFrame >= FRAMETIME_NS) {
                
                //get the delta (in seconds) between this frame and the last
                frameDelta = ((currentTime - lastFrameTime)) / NANOSECOND;
                
                //call the renderer
                render(frameDelta);
                
                //reset the time since last frame
                timeSinceLastFrame = 0;
                lastFrameTime = currentTime;
            }
            
            //check of enough time has elapsed for a new tick
            if (timeSinceLastTick >= TICKTIME_NS) {
                
                //get the delta (in seconds) between this tick and the last
                tickDelta = ((currentTime - lastTickTime)) / NANOSECOND;
                
                //debug
                DEBUG_tick_delta = tickDelta;
                
                //call a physics tick
                tick(tickDelta);
                
                
                //reset the time since last tick
                timeSinceLastTick = 0;
                lastTickTime = currentTime;
            }
            
            lastTime = currentTime;
        }
        
        //release all resources used by the renderer
        renderer.dispose();
    }
    
    public void render(double delta) {
        //poll user input
        Input.poll();
        //render a new frame
        renderer.render(delta);
    }
    
    public void tick(double delta) {
                
        //loop over all entities in the environment
        for (Entity e : currentEnvironment.getEntities()) {
            //tick each entity
            e.tick(currentEnvironment, delta);
        }
    }
    
    /**
     * Stops the game, by setting the running flag to false
     */
    public void stop() {
        running = false;
    }
    
    /**
     * Get the currentEnvironment
     * @return the currentEnvironment
     */
    public Environment getEnvironment() {
        return currentEnvironment;
    }
    
    /**
     * Sets the current environment, and update all concerned parties. also removes
     * player from the old environment, and adds it to the new
     * @param e the new environment
     * @return the old environment
     */
    public Environment setEnvironment(Environment e) {
        //get the old environment
        Environment old = currentEnvironment;
        
        //remove the player from the old environment
        if (old != null) old.removeEntity(player);
        
        //set the current environment to the new one
        currentEnvironment = e;
        
        //add the player to the new environment
        currentEnvironment.addEntity(player);
        
        //set the renderer to use the new environment
        renderer.setEnvironmnent(currentEnvironment);
        
        //return the old environment
        return old;
    }
    
    /**
     * Gets the current player
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }
    
    
    public static void main(String[] args) {
        
        //try to load all resources from the resources package
        try {
            Resources.load();
        } catch (IOException | URISyntaxException ex) {
            
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            //on fail, exit
            System.exit(0);
        }
        
        //create a new game
        Game game = new Game();
        
        /*====DEBUG===========================================================*/
        Thread t = new Thread(() -> {
            Scanner in = new Scanner(System.in);
            
            
            while (game.running) {
                try {
                    String command = in.nextLine();
                
                    String split[] = command.split(" ");
                    
                    double x = 0, y = 0, z = 0;
                    
                    
                     if ("toggle".equals(split[0])) {
                        if ("debug".equals(split[1])) {
                            Overlay.DEBUG.setEnabled(!Overlay.DEBUG.isEnabled());
                            continue;
                        }
                    } else if (command.startsWith("#")) {
                        game.getPlayer().setColor(Color.decode(command));
                    }
                    
                    if (split.length > 1) x = Double.parseDouble(split[1]);
                    if (split.length > 2) y = Double.parseDouble(split[2]);
                    if (split.length > 3) z = Double.parseDouble(split[3]);
                        
                    
                    if ("tp".equals(split[0])) {
                        System.out.println("teleporting");
                        game.player.setPos(new Vec3f(x, y, z));
                    } else if ("cam".equals(split[0])) {
                        System.out.println("setting camera");
                        game.renderer.setCamera(new Vec3f(x, y, z));
                    } else if ("scale".equals(split[0])) {
                        System.out.println("setting scale: " + split[1]);
                        game.renderer.setScale((int)x);
                    }
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
            }
            
        });
        
        /*====END=DEBUG=======================================================*/
        
        t.start();
        //start the game
        game.loop();
    }
    
    //the amount of nanoseconds in a second, as a double
    public static final double NANOSECOND   = 1_000_000_000;
    //the target framerate
    public static final int    FRAMERATE    = 60;
    //the target tickrate
    public static final int    TICKRATE     = 100;
    //the amount of time need to wait for the next frame
    public static final long   FRAMETIME_NS = (long) ((1d / FRAMERATE) * NANOSECOND);
    //the amount of time need to wait for the next tick
    public static final long   TICKTIME_NS  = (long) ((1d / TICKRATE) * NANOSECOND);
}
