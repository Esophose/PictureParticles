package dev.esophose.pictureparticles.manager;

import dev.esophose.pictureparticles.image.ParticleImage;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ParticleManager extends BukkitRunnable {

    private static boolean globalDraw = false;
    private static List<ParticleImage> images = new ArrayList<>();

    /**
     * Gets a List of all ParticleImages
     *
     * @return A List of ParticleImages
     */
    public static List<ParticleImage> getImages() {
        return images;
    }

    /**
     * Ran every tick to display particles for an image
     */
    public void run() {
        for (ParticleImage image : images) {
            Location location = image.getLocation();
            float scale = image.getScale();
            float pixelSize = image.getPixelSize();
            int width = image.getWidth();
            int height = image.getHeight();

            boolean draw = globalDraw;

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    draw = !draw;
                    if (!draw) continue;

                    Color color = image.getColorAt(x, y);
                    if (color != null) {
                        DustOptions dustOptions = new DustOptions(color, pixelSize);
                        double xPos = location.getX() - (width / 2D * scale) + (x * scale);
                        double yPos = location.getY() + (height / 2D * scale) - (y * scale);
                        double zPos = location.getZ();
                        location.getWorld().spawnParticle(Particle.REDSTONE, xPos, yPos, zPos, 1, 0, 0, 0, 0, dustOptions, true);
                    }
                }
            }
        }

        globalDraw = !globalDraw;
    }

}
