package com.esophose.pictureparticles;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.esophose.pictureparticles.command.ParticleCommandHandler;
import com.esophose.pictureparticles.manager.LangManager;
import com.esophose.pictureparticles.manager.ParticleManager;
import com.esophose.pictureparticles.manager.SettingManager.PSetting;

public class PictureParticles extends JavaPlugin {
    
    private static Plugin pluginInstance;

    public void onEnable() {
        pluginInstance = Bukkit.getServer().getPluginManager().getPlugin("PictureParticles");
        
        getCommand("pip").setExecutor(new ParticleCommandHandler());
        getCommand("pip").setTabCompleter(new ParticleCommandHandler());
        
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
        
        new ParticleManager().runTaskTimer(this, 0, 10);
    }
    
    public static Plugin getPlugin() {
        return pluginInstance;
    }
    
}
