package dev.esophose.pictureparticles;

import dev.esophose.pictureparticles.command.ParticleCommandHandler;
import dev.esophose.pictureparticles.manager.LangManager;
import dev.esophose.pictureparticles.manager.ParticleManager;
import dev.esophose.pictureparticles.manager.SettingManager.PSetting;
import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class PictureParticles extends JavaPlugin {

    private static Plugin pluginInstance;

    public void onEnable() {
        pluginInstance = Bukkit.getServer().getPluginManager().getPlugin("PictureParticles");

        ParticleCommandHandler cmdHandler = new ParticleCommandHandler();
        getCommand("pip").setExecutor(cmdHandler);
        getCommand("pip").setTabCompleter(cmdHandler);

        saveDefaultConfig();
        double configVersion = PSetting.VERSION.getDouble();
        boolean updatePluginSettings = configVersion < Double.parseDouble(getDescription().getVersion());
        if (updatePluginSettings) {
            File configFile = new File(getDataFolder(), "config.yml");
            if (configFile.exists()) {
                configFile.delete();
            }
            saveDefaultConfig();
            reloadConfig();
            getLogger().warning("The config.yml has been updated to v" + getDescription().getVersion() + "!");
            LangManager.reload(true);
        } else {
            LangManager.reload(false);
        }

        new ParticleManager().runTaskTimer(this, 0, 2);
    }

    public static Plugin getPlugin() {
        return pluginInstance;
    }

}
