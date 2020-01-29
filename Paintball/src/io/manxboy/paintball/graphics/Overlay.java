/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.manxboy.paintball.graphics;

import io.manxboy.paintball.Game;
import io.manxboy.paintball.Input;
import java.awt.Color;
import java.awt.Graphics;

/**
 * a overlay is a GUI layer that is rendered over the environment
 * @author manxboy
 */
public abstract class Overlay {
    
    private boolean enabled = true;
    
    /**
     * draw this overlay onto the graphics
     * @param delta the delta since the last frame
     */
    public void draw(Graphics g, double delta, Game game) {
        draw(g, delta, game, null);
    }
    
    public abstract void draw(Graphics g, double delta, Game game, Renderer renderer);
    
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean b) {
        this.enabled = b;
    }
    
    /**
     * Debug Overlay. draws Frame-rate to screen
     */
    public static final Overlay DEBUG = new Overlay() {
        private boolean enabled = false;
        
        @Override
        public void draw(Graphics g, double delta, Game game, Renderer renderer) {
                g.setXORMode(Color.white);
                g.setColor(Color.BLACK);
                g.drawString("FPS: " + Math.round(1 / delta), 0, 16);
                g.drawString("∆  : " + game.DEBUG_tick_delta, 0, 32);
                g.drawString("PLA: " + game.getPlayer().getPos().toString(), 0, 48);
                if (renderer != null) g.drawString("CAM: " + renderer.getCamera(), 0, 64);
                
                g.drawString("BUTTONS: " + Input.FIRE + "", 0, 80);
        }
    };
}
