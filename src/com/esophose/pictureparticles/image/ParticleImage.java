package com.esophose.pictureparticles.image;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import org.bukkit.Color;

public class ParticleImage {
    
    private int width, height;
    private Color[] pixels;

    protected ParticleImage(BufferedImage image) {
        byte[] imagePixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        this.width = image.getWidth();
        this.height = image.getHeight();
        
        this.pixels = new Color[this.width * this.height];
        boolean hasAlpha = image.getColorModel().hasAlpha();
        int pixelLength = hasAlpha ? 4 : 3;
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                int pixelIndex = y * this.width + x;
                int pos = (y * pixelLength * width) + (x * pixelLength);
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
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public Color getColorAt(int x, int y) {
        return this.pixels[y * width + x];
    }
    
}
