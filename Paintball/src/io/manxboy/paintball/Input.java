/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.manxboy.paintball;

import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;
import net.java.games.input.ControllerEnvironment;

/**
 *
 * @author manxboy
 */
public class Input {
    public static double X = 0.0;
    public static double Y = 0.0;
    
    public static boolean FIRE = true;
    public static boolean EXIT = false;
    
    public static void poll() {
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        
        X = 0.0;
        Y = 0.0;
        FIRE = false;
        
        for (Controller controller : controllers) {
            
            controller.poll();
            
            if (controller.getType() == Type.GAMEPAD) {
                
                for (Component component : controller.getComponents()) {
                    if (component.getIdentifier() == Identifier.Axis.X) { 
                        double x = component.getPollData();
                       if (Math.abs(x) > Math.max(component.getDeadZone(), .1)) {
                            X = x;
                        } else {
                       }
                    }
                    
                    if (component.getIdentifier() == Identifier.Axis.Y) { 
                        double y = component.getPollData();
                        if (Math.abs(y) > Math.max(component.getDeadZone(), 0.1)) {
                            Y = y * -1;
                        } else {
                        }
                    }
                }
            } else if (controller.getType() == Type.KEYBOARD) {
                
                
                for (Component component : controller.getComponents()) {
                    Identifier i = component.getIdentifier();
                    
                    
                    
                    if (i == Identifier.Key.W && component.getPollData() == 1f) {
                        Y = 1d;
                    } else if (i == Identifier.Key.A && component.getPollData() == 1f) {
                        X = -1d;
                    } else if (i == Identifier.Key.S && component.getPollData() == 1f) {
                        Y = -1d;
                    } else if (i == Identifier.Key.D && component.getPollData() == 1f) {
                        X = 1d;
                    } else if (i == Identifier.Key.SPACE) {                        
                        if (component.getPollData() > 0) FIRE = true;
                        
                    } else if (i == Identifier.Key.ESCAPE) {
                        if (component.getPollData() == 1f) {
                            EXIT = true;
                        } else {
                            EXIT = false;
                        }
                    }
                }
            }
        }
    }
}
