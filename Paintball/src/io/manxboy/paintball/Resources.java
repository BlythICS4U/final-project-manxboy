/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.manxboy.paintball;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.stream.Stream;
import javax.imageio.ImageIO;

/**
 *
 * @author manxboy
 */
public class Resources {
    
    public static final HashMap<String, Image> images = new HashMap();
    
    /**
     * tries to load all resources from the resources package
     * @throws IOException on a file error.
     */
    public static void load() throws IOException, URISyntaxException {
        //get the location of the resources folder
        URI uri = Resources.class.getResource("/resources").toURI();
        
        //if inside of a jar, load a virtual file-system for accsess to the jar
        try (FileSystem fileSystem = (uri.getScheme().equals("jar") ? FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap()) : null)) {
            
            //get the path to the location of the resouces folder
            Path dir = Paths.get(uri);
            
            //walk the directory tree and get all files
            Files.walkFileTree(dir, new SimpleFileVisitor<Path>() { 
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                    File file = path.toFile();
                    String name = file.getName();
                    
                    if (name.endsWith("png")) {
                        Image image = ImageIO.read(file);
                        
                        images.put(name.replaceAll(".png", ""), image);
                    }
                    
                    return FileVisitResult.CONTINUE;
                }
            });
        }
        
    }
        
    public static Image getImage(String name) {
        return images.get(name);
    }
}
