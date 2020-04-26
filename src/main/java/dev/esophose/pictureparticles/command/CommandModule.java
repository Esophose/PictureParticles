package dev.esophose.pictureparticles.command;

import dev.esophose.pictureparticles.manager.LangManager;
import dev.esophose.pictureparticles.manager.LangManager.Lang;
import java.util.List;
import org.bukkit.entity.Player;

public interface CommandModule {

    /**
     * Called when this command gets executed
     *
     * @param player The Player who executed this command
     * @param args   The arguments to this command
     */
    void onCommandExecute(Player player, String[] args);

    /**
     * Called when a player tries to tab complete this command
     *
     * @param player The Player who is tab completing this command
     * @param args   Arguments typed so far
     * @return A list of possible argument values
     */
    List<String> onTabComplete(Player player, String[] args);

    /**
     * Gets the name of this command
     *
     * @return The name of this command
     */
    String getName();

    /**
     * Gets the Lang description of this command
     *
     * @return The description of this command
     */
    Lang getDescription();

    /**
     * Gets any arguments this command has
     *
     * @return The arguments this command has
     */
    String getArguments();

    /**
     * Displays a command's usage to the player
     *
     * @param player  The Player to display the command usage to
     * @param command The command to display usage for
     */
    static void printUsage(Player player, CommandModule command) {
        LangManager.sendMessage(player, Lang.COMMAND_DESCRIPTIONS_USAGE, command.getName(), command.getArguments());
    }

    /**
     * Displays a command's usage (with its description) to the player
     *
     * @param player  The Player to display the command usage to
     * @param command The command to display usage for
     */
    static void printUsageWithDescription(Player player, CommandModule command) {
        if (command.getArguments().length() == 0) {
            LangManager.sendSimpleMessage(player, Lang.COMMAND_DESCRIPTIONS_HELP_1, command.getName(), LangManager.getText(command.getDescription()));
        } else {
            LangManager.sendSimpleMessage(player, Lang.COMMAND_DESCRIPTIONS_HELP_2, command.getName(), command.getArguments(), LangManager.getText(command.getDescription()));
        }
    }

}
