package dev.esophose.pictureparticles.command;

import dev.esophose.pictureparticles.PictureParticles;
import dev.esophose.pictureparticles.image.ParticleImage;
import dev.esophose.pictureparticles.image.PngParticleImage;
import dev.esophose.pictureparticles.image.TextParticleImage;
import dev.esophose.pictureparticles.manager.LangManager.Lang;
import dev.esophose.pictureparticles.manager.ParticleManager;
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

public class CreateCommandModule implements CommandModule {

    public void onCommandExecute(Player player, String[] args) {
//        ParticleImage particleImage = new TextParticleImage(String.join(" ", args), player.getLocation().clone().add(0, 1, 0), 0.1f, 1f);
//        ParticleManager.getImages().add(particleImage);

        String fileName = args[0];

        try (InputStream inputStream = new FileInputStream(PictureParticles.getPlugin().getDataFolder().getAbsolutePath() + "/images/" + fileName)) {
            BufferedImage image = ImageIO.read(inputStream);
            ParticleImage particleImage = new PngParticleImage(image, player.getLocation().clone().add(0, 1, 0), 0.1f, 1f);
            ParticleManager.getImages().add(particleImage);
        } catch (IOException e) {
            e.printStackTrace();

            if (e instanceof FileNotFoundException) {
                player.sendMessage(ChatColor.RED + "File does not exist");
            }
        }
    }

    public List<String> onTabComplete(Player pplayer, String[] args) {
        return new ArrayList<>(); // TODO: Implement
    }

    public String getName() {
        return "create";
    }

    public Lang getDescription() {
        return Lang.COMMAND_DESCRIPTION_CREATE;
    }

    public String getArguments() {
        return "<imagePath>";
    }

}
