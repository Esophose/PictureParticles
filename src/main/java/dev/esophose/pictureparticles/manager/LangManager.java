package dev.esophose.pictureparticles.manager;

import dev.esophose.pictureparticles.PictureParticles;
import dev.esophose.pictureparticles.manager.SettingManager.PSetting;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class LangManager {

    /**
     * Contains the location in the .lang file of every chat message
     */
    public static enum Lang { // @formatter:off

        // Command Errors
        COMMAND_ERROR_NO_PERMISSION,
        COMMAND_ERROR_UNKNOWN,

        // Command Descriptions
        COMMAND_DESCRIPTIONS_USAGE,
        COMMAND_DESCRIPTIONS_HELP_1,
        COMMAND_DESCRIPTIONS_HELP_2,
        COMMAND_DESCRIPTION_CREATE; // @formatter:on

        private String message;

        private String getConfigName() {
            return this.name().toLowerCase().replaceAll("_", "-");
        }

        /**
         * Sets the message from the lang file
         *
         * @param langFile The lang file to pull the message from
         */
        private void setMessage(YamlConfiguration langFile) {
            String fileLocation = this.getConfigName();
            String langMessage = langFile.getString(fileLocation);
            if (langMessage == null) {
                langMessage = "&cMissing message in " + langFileName + ": " + fileLocation + ". Contact a server administrator.";
                PictureParticles.getPlugin().getLogger().warning("Missing message in " + langFileName + ": " + fileLocation);
            }
            this.message = parseColors(langMessage);
        }

        /**
         * Gets the message this enum represents
         *
         * @param replacements The replacements for the message
         * @return The message with the replacements applied
         */
        private String get(Object... replacements) {
            return new MessageFormat(this.message).format(replacements);
        }
    }

    /**
     * The current lang file name
     */
    private static String langFileName;

    private LangManager() {

    }

    /**
     * Used to set up the LangManager
     * This should only get called once by the PlayerParticles class, however
     * calling it multiple times wont affect anything negatively
     */
    public static void reload(boolean resetLangFile) {
        YamlConfiguration lang = configureLangFile(resetLangFile);
        for (Lang messageType : Lang.values())
            messageType.setMessage(lang);
    }

    /**
     * Loads the target .lang file as defined in the config and grabs its YamlConfiguration
     * If it doesn't exist, default to default.lang
     * If default.lang doesn't exist, copy the file from this .jar to the target directory
     *
     * @return The YamlConfiguration of the target .lang file
     */
    private static YamlConfiguration configureLangFile(boolean resetLangFile) {
        File pluginDataFolder = PictureParticles.getPlugin().getDataFolder();
        langFileName = PSetting.LANG_FILE.getString();
        File targetLangFile = new File(pluginDataFolder.getAbsolutePath() + "/lang/" + langFileName);

        if (resetLangFile) {
            File defaultLangFile = new File(pluginDataFolder.getAbsolutePath() + "/lang/default.lang");
            if (defaultLangFile.exists()) {
                defaultLangFile.delete();
                PictureParticles.getPlugin().getLogger().warning("default.lang has been reset!");
            }
        }

        if (!targetLangFile.exists()) { // Target .lang file didn't exist, default to default.lang
            if (!langFileName.equals("default.lang")) {
                PictureParticles.getPlugin().getLogger().warning("Couldn't find lang file '" + langFileName + "', defaulting to default.lang");
            }
            langFileName = "default.lang";

            targetLangFile = new File(pluginDataFolder.getAbsolutePath() + "/lang/" + langFileName);
            if (!targetLangFile.exists()) { // default.lang didn't exist, create it
                try (InputStream stream = PictureParticles.getPlugin().getResource("lang/default.lang")) {
                    targetLangFile.getParentFile().mkdir(); // Make sure the directory always exists
                    Files.copy(stream, Paths.get(targetLangFile.getAbsolutePath()));
                    return YamlConfiguration.loadConfiguration(targetLangFile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    PictureParticles.getPlugin().getLogger().severe("Unable to write default.lang to disk! You and your players will be seeing lots of error messages!");
                    return null;
                }
            }
        }

        return YamlConfiguration.loadConfiguration(targetLangFile);
    }

    /**
     * Gets a formatted and replaced message
     *
     * @param messageType  The message type to get
     * @param replacements The replacements fot the message
     * @return The Lang in text form with its replacements applied
     */
    public static String getText(Lang messageType, Object... replacements) {
        return messageType.get(replacements);
    }

    /**
     * Sends a message to the given player
     *
     * @param player       The player to send the message to
     * @param messageType  The message to send to the player
     * @param replacements The replacements for the message
     */
    public static void sendMessage(Player player, Lang messageType, Object... replacements) {
        if (!PSetting.MESSAGES_ENABLED.getBoolean()) return;

        String message = messageType.get(replacements);

        if (message.length() == 0) return;

        if (PSetting.USE_MESSAGE_PREFIX.getBoolean()) {
            message = parseColors(PSetting.MESSAGE_PREFIX.getString()) + " " + message;
        }

        if (message.trim().equals("")) return;

        player.sendMessage(message);
    }

    /**
     * Sends a custom message to a player
     * Used in cases of string building
     *
     * @param player  The player to send the message to
     * @param message The message to send to the player
     */
    public static void sendCustomMessage(Player player, String message) {
        if (!PSetting.MESSAGES_ENABLED.getBoolean()) return;

        if (message.trim().length() == 0) return;

        if (PSetting.USE_MESSAGE_PREFIX.getBoolean()) {
            message = parseColors(PSetting.MESSAGE_PREFIX.getString()) + " " + message;
        }

        player.sendMessage(message);
    }

    /**
     * Sends a message to a Player without the prefix
     *
     * @param player       The player to send the message to
     * @param messageType  The message type to send the player
     * @param replacements The replacements for the message
     */
    public static void sendSimpleMessage(Player player, Lang messageType, Object... replacements) {
        if (!PSetting.MESSAGES_ENABLED.getBoolean()) return;

        String message = messageType.get(replacements);

        if (message.length() == 0) return;

        if (message.trim().equals("")) return;

        player.sendMessage(message);
    }

    /**
     * Translates all ampersand symbols into the Minecraft chat color symbol
     *
     * @param message The input string
     * @return The output string, parsed
     */
    public static String parseColors(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
