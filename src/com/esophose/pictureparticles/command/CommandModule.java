package com.esophose.pictureparticles.command;

import java.text.MessageFormat;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.esophose.pictureparticles.manager.LangManager;
import com.esophose.pictureparticles.manager.LangManager.Lang;

public interface CommandModule {

    /**
     * Called when this command gets executed
     * 
     * @param player The Player who executed this command
     * @param args The arguments to this command
     */
    public void onCommandExecute(Player player, String[] args);

    /**
     * Called when a player tries to tab complete this command
     * 
     * @param player The Player who is tab completing this command
     * @param args Arguments typed so far
     * @return A list of possible argument values
     */
    public List<String> onTabComplete(Player player, String[] args);

    /**
     * Gets the name of this command
     * 
     * @return The name of this command
     */
    public String getName();

    /**
     * Gets the Lang description of this command
     * 
     * @return The description of this command
     */
    public Lang getDescription();

    /**
     * Gets any arguments this command has
     * 
     * @return The arguments this command has
     */
    public String getArguments();
    
    /**
     * Displays a command's usage to the player
     * 
     * @param player The Player to display the command usage to
     * @param command The command to display usage for
     */
    public static void printUsage(Player player, CommandModule command) {
        Object[] args = new Object[] { command.getName(), command.getArguments() };
        LangManager.sendCustomMessage(player, new MessageFormat(ChatColor.YELLOW + "/pip {0} {1}").format(args));
    }
    
    /**
     * Displays a command's usage (with its description) to the player
     * 
     * @param player The Player to display the command usage to
     * @param command The command to display usage for
     */
    public static void printUsageWithDescription(Player player, CommandModule command) {
        if (command.getArguments().length() == 0) {
            Object[] args = new Object[] { command.getName(), LangManager.getText(command.getDescription()) };
            LangManager.sendCustomMessage(player, new MessageFormat(ChatColor.YELLOW + "/pip {0} - {1}").format(args));
        } else {
            Object[] args = new Object[] { command.getName(), command.getArguments(), LangManager.getText(command.getDescription()) };
            LangManager.sendCustomMessage(player, new MessageFormat(ChatColor.YELLOW + "/pip {0} {1} - {2}").format(args));
        }
    }
    
    /**
     * Displays a command's sub-command usage to the player
     * 
     * @param player The Player to display the command usage to
     * @param command The command to display usage for
     * @param subCommandName The name of the command's sub-command to display usage for
     * @param subCommandArgs The sub-command's arguments
     */
    public static void printSubcommandUsage(Player pplayer, CommandModule command, String subCommandName, String subCommandArgs) {
        Object[] args = new Object[] { command.getName(), subCommandName, subCommandArgs };
        LangManager.sendCustomMessage(pplayer, new MessageFormat(ChatColor.YELLOW + "/pip {0} {1} {2}").format(args));
    }

}
