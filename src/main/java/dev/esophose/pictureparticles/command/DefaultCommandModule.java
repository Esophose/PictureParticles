package dev.esophose.pictureparticles.command;

import dev.esophose.pictureparticles.manager.LangManager.Lang;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;

public class DefaultCommandModule implements CommandModule {

    public void onCommandExecute(Player player, String[] args) {

    }

    public List<String> onTabComplete(Player player, String[] args) {
        return new ArrayList<String>();
    }

    public String getName() {
        return "";
    }

    public Lang getDescription() {
        return Lang.COMMAND_DESCRIPTIONS_USAGE;
    }

    public String getArguments() {
        return "";
    }

}
