package dev.esophose.pictureparticles.image;

import java.awt.image.BufferedImage;
import org.bukkit.Location;

public class PngParticleImage extends ParticleImage {

    private BufferedImage image;

    public PngParticleImage(BufferedImage image, Location location, float scale, float pixelSize) {
        super(location, scale, pixelSize);
        this.image = image;
    }

    @Override
    protected BufferedImage getImage() {
        return this.image;
    }
}
