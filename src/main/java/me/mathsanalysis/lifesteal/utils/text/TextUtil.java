
package me.mathsanalysis.lifesteal.utils.text;

import me.mathsanalysis.lifesteal.Lifesteal;
import me.mathsanalysis.lifesteal.utils.reflection.ReflectionUtil;
import lombok.experimental.UtilityClass;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/*
  Created by CubeCrafter
  Repo https://github.com/PixelStudiosDev/XUtils
*/

@UtilityClass
public class TextUtil {

    private static final Pattern HEX_PATTERN = Pattern.compile("#([A-Fa-f0-9]{6})");

    /**
     * Colorize the given string
     *
     * @param text The text to colorize
     * @return The colorized text
     */
    public static String color(String text) {
        if (ReflectionUtil.supports(16)) {
            Matcher matcher = HEX_PATTERN.matcher(text);
            while (matcher.find()) {
                String color = matcher.group();
                text = text.replace(color, ChatColor.of(color).toString());
                matcher = HEX_PATTERN.matcher(text);
            }
        }
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * Colorize the given list of strings
     *
     * @param text The list of strings to colorize
     * @return The colorized list of strings
     */
    public static List<String> color(List<String> text) {
        return text.stream().map(TextUtil::color).collect(Collectors.toList());
    }

    /**
     * Send a message to a player
     *
     * @param sender The player to send the message to
     * @param message The message to send
     */
    public static void sendMessage(CommandSender sender, String message) {
        if (message == null || message.isEmpty()) return;

        if (message.startsWith("<center>") && message.endsWith("</center>")) {
            message = getCenteredMessage(message);
        }
        message = parsePlaceholders(sender instanceof Player ? (Player) sender : null, message);

        sender.sendMessage(color(message));
    }

    /**
     * Send a messages to a list of players
     *
     * @param players The players to send the messages to
     * @param message The messages to send
     */
    public static void sendMessage(Collection<Player> players, String message) {
        players.forEach(player -> sendMessage(player, message));
    }

    /**
     * Send a list of messages to a player
     *
     * @param sender The player to send the message to
     * @param messages The messages to send
     */
    public static void sendMessages(CommandSender sender, List<String> messages) {
        messages.forEach(message -> sendMessage(sender, message));
    }

    /**
     * Send a list of messages to a list of players
     *
     * @param players The players to send the messages to
     * @param messages The messages to send
     */
    public static void sendMessages(Collection<Player> players, List<String> messages) {
        players.forEach(player -> sendMessages(player, messages));
    }

    public static void broadcast(String message) {
        Bukkit.getOnlinePlayers().forEach(player -> sendMessage(player, message));
    }

    public static void broadcast(List<String> messages) {
        Bukkit.getOnlinePlayers().forEach(player -> sendMessages(player, messages));
    }

    public static void sendMessage(Player player, List<String> message, List<String> hover, ClickEvent click) {
        if (message.isEmpty()) return;

        message.replaceAll(line -> color(parsePlaceholders(player, line)));
        hover.replaceAll(line -> color(parsePlaceholders(player, line)));

        ComponentBuilder builder = new ComponentBuilder(String.join("\n", message))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(String.join("\n", hover)).create()))
                .event(click);

        player.spigot().sendMessage(builder.create());
    }

    /**
     * Parse PlaceholderAPI placeholders
     *
     * @param player The player to parse the placeholders for
     * @param text The text to parse
     * @return The parsed text
     */
    public static String parsePlaceholders(OfflinePlayer player, String text) {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            return PlaceholderAPI.setPlaceholders(player, text);
        }
        return text;
    }

    public static String formatEnumName(String name) {
        StringBuilder builder = new StringBuilder();
        boolean capitalize = true;
        for (char c : name.toCharArray()) {
            if (c == '_') {
                builder.append(' ');
                capitalize = true;
            } else {
                builder.append(capitalize ? Character.toUpperCase(c) : Character.toLowerCase(c));
                capitalize = false;
            }
        }
        return builder.toString();
    }

    public static void info(String message) {
        Lifesteal.get().getPlugin().getLogger().info(message);
    }

    public static void warn(String message) {
        Lifesteal.get().getPlugin().getLogger().warning(message);
    }

    public static void error(String message) {
        Lifesteal.get().getPlugin().getLogger().severe(message);
    }

    public static String stripColor(String text) {
        return ChatColor.stripColor(color(text));
    }

    public static String serializeLocation(Location location) {
        StringBuilder builder = new StringBuilder();
        builder.append(location.getWorld().getName()).append(":").append(location.getX()).append(":").append(location.getY()).append(":").append(location.getZ());
        if (location.getYaw() != 0 && location.getPitch() != 0) {
            builder.append(":").append(location.getYaw()).append(":").append(location.getPitch());
        }
        return builder.toString();
    }

    /**
     * Center a message
     * @param message The message to center
     * @return The centered message
     */
    public static String getCenteredMessage(String message) {
        if (message == null || message.isEmpty()) return "";

        message = TextUtil.color(message.replace("<center>", "").replace("</center>", ""));

        int messageSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for (char c : message.toCharArray()) {
            if (c == '§') {
                previousCode = true;
            } else if (previousCode) {
                previousCode = false;
                isBold = c == 'l' || c == 'L';
            } else {
                FontInfo fontInfo = FontInfo.getFontInfo(c);
                messageSize += isBold ? fontInfo.getBoldLength() : fontInfo.getLength();
                messageSize++;
            }
        }

        int halvedMessageSize = messageSize / 2;
        int toCompensate = 154 - halvedMessageSize;
        int spaceLength = FontInfo.SPACE.getLength() + 1;

        StringBuilder builder = new StringBuilder();
        int compensated = 0;

        while (compensated < toCompensate) {
            builder.append(" ");
            compensated += spaceLength;
        }

        return builder + message;
    }

    /**
     * Parse a color from a string
     * Format: <r>:<g>:<b> or <dye>
     * @param text The serialized color
     * @return The color
     */
    public static Color parseColor(String text) {
        String[] split = text.split(":");

        Color color;

        if (split.length != 3) {
            color = DyeColor.valueOf(text).getColor();
        } else {
            color = Color.fromRGB(
                    Integer.parseInt(split[0]),
                    Integer.parseInt(split[1]),
                    Integer.parseInt(split[2])
            );
        }

        return color;
    }
}
