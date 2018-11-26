package com.esophose.pictureparticles.image;

import java.awt.image.BufferedImage;

import org.bukkit.Location;

public class RenderableParticleImage extends ParticleImage {

    private Location location;
    private float scale;
    private float pixelSize;
    
    public RenderableParticleImage(BufferedImage image, Location location, float scale, float pixelSize) {
        super(image);
        
        this.location = location;
        this.scale = scale;
        this.pixelSize = pixelSize;
    }
    
    public Location getLocation() {
        return this.location;
    }
    
    public float getScale() {
        return this.scale;
    }
    
    public float getPixelSize() {
        return this.pixelSize;
    }
    
}
