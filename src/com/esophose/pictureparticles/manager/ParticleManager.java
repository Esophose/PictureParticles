package com.esophose.pictureparticles.manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.scheduler.BukkitRunnable;

import com.esophose.pictureparticles.image.RenderableParticleImage;

public class ParticleManager extends BukkitRunnable {
    
    private static List<RenderableParticleImage> images = new ArrayList<RenderableParticleImage>();
    
    /**
     * Gets a List of all ParticleImages 
     * 
     * @return A List of ParticleImages
     */
    public static List<RenderableParticleImage> getImages() {
        return images;
    }

    /**
     * Ran every tick to display particles for an image
     */
    public void run() {
        for (RenderableParticleImage image : images) {
            Location location = image.getLocation();
            float scale = image.getScale();
            float pixelSize = image.getPixelSize();
            int width = image.getWidth();
            int height = image.getHeight();
            
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    Color color = image.getColorAt(x, y);
                    if (color != null) {
                        DustOptions dustOptions = new DustOptions(color, pixelSize);
                        location.getWorld().spawnParticle(Particle.REDSTONE, location.getX() - (width / 2 * scale) + (x * scale), location.getY() + (height / 2 * scale) - (y * scale), location.getZ(), 1, 0, 0, 0, dustOptions);
                    }
                }
            }
        }
    }
    
}
