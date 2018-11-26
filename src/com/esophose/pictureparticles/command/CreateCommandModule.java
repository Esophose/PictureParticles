package com.esophose.pictureparticles.command;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.esophose.pictureparticles.PictureParticles;
import com.esophose.pictureparticles.image.RenderableParticleImage;
import com.esophose.pictureparticles.manager.LangManager.Lang;
import com.esophose.pictureparticles.manager.ParticleManager;

public class CreateCommandModule implements CommandModule {

    public void onCommandExecute(Player player, String[] args) {
        String fileName = args[0];

        try (InputStream inputStream = new FileInputStream(PictureParticles.getPlugin().getDataFolder().getAbsolutePath() + "/images/" + fileName)) {
            if (inputStream != null) {
                BufferedImage image = ImageIO.read(inputStream);
                RenderableParticleImage particleImage = new RenderableParticleImage(image, player.getLocation().clone().add(0, 1, 0), 0.1f, 1f);
                ParticleManager.getImages().add(particleImage);
            }
        } catch (IOException e) {
            e.printStackTrace();
            
            if (e instanceof FileNotFoundException) {
                player.sendMessage(ChatColor.RED + "File does not exist");
            }
        }
    }

    public List<String> onTabComplete(Player pplayer, String[] args) {
        return new ArrayList<String>(); // TODO: Implement
    }

    public String getName() {
        return "create";
    }

    public Lang getDescription() {
        return Lang.COMMAND_DESCRIPTION_CREATE;
    }

    public String getArguments() {
        return "";
    }

}
