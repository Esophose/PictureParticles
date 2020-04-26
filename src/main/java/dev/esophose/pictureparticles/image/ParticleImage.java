package dev.esophose.pictureparticles.image;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import org.bukkit.Color;
import org.bukkit.Location;

public abstract class ParticleImage {

    private Location location;
    private float scale;
    private float pixelSize;
    private int width, height;
    private Color[] pixels;

    public ParticleImage(Location location, float scale, float pixelSize) {
        this.location = location;
        this.scale = scale;
        this.pixelSize = pixelSize;
    }

    protected abstract BufferedImage getImage();

    private void parseImage() {
        if (this.pixels != null)
            return;

        BufferedImage image = this.getImage();

        byte[] imagePixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        this.width = image.getWidth();
        this.height = image.getHeight();

        this.pixels = new Color[this.width * this.height];
        boolean hasAlpha = image.getColorModel().hasAlpha();
        int pixelLength = hasAlpha ? 4 : 3;
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                int pixelIndex = y * this.width + x;
                int pos = (y * pixelLength * this.width) + (x * pixelLength);
                int argb = 0;

                if (hasAlpha && (imagePixels[pos++] & 0xff) < 128) // Ignore transparent pixels
                    continue;

                argb += ((int) imagePixels[pos++] & 0xff); // blue
                argb += (((int) imagePixels[pos++] & 0xff) << 8); // green
                argb += (((int) imagePixels[pos++] & 0xff) << 16); // red

                this.pixels[pixelIndex] = Color.fromRGB(argb);
            }
        }
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

    public int getWidth() {
        this.parseImage();
        return this.width;
    }

    public int getHeight() {
        this.parseImage();
        return this.height;
    }

    public Color getColorAt(int x, int y) {
        this.parseImage();
        return this.pixels[y * this.width + x];
    }

}
