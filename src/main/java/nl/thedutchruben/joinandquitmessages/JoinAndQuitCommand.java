package nl.thedutchruben.joinandquitmessages;

import me.clip.placeholderapi.PlaceholderAPI;
import nl.thedutchruben.mccore.spigot.commands.Command;
import nl.thedutchruben.mccore.spigot.commands.Default;
import nl.thedutchruben.mccore.spigot.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.md_5.bungee.api.ChatColor.COLOR_CHAR;

@Command(
        command = "joinandquitmessages",
        description = "JoinAndQuitMessages plugin commands",
        permission = "joinandquitmessages.use",
        aliases = {"jqm"})
public class JoinAndQuitCommand {

    @Default
    public void defaultCommand(CommandSender sender, List<String> args) {
        sender.sendMessage(ChatColor.RED + "Usage: /joinandquitmessages <test [message]|reload>");
    }

    @SubCommand(
            subCommand = "test",
            description = "Test a join message (current config or custom message)",
            permission = "joinandquitmessages.test",
            maxParams = 2)
    public void testCommand(CommandSender sender, List<String> args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players!");
            return;
        }
        
        Player player = (Player) sender;
        String joinMessage;
        
        // Check if custom message is provided
        if (args.size() > 0) {
            // Join all arguments to form the custom message
            joinMessage = String.join(" ", args);
            sender.sendMessage(ChatColor.YELLOW + "Testing custom message:");
        } else {
            // Use the configured message
            joinMessage = JoinAndQuitMessages.getInstance().getJoinMessage(player);
            sender.sendMessage(ChatColor.YELLOW + "Testing configured join message:");
        }
        
        // Apply formatting
        joinMessage = joinMessage.replace('&', '§').replace("%player%", player.getName());
        
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            joinMessage = PlaceholderAPI.setPlaceholders(player, joinMessage);
        }
        
        String[] split = Bukkit.getBukkitVersion().split("-")[0].split("\\.");
        if (Integer.parseInt(split[1]) >= 16) {
            joinMessage = translateHexColorCodes("<", ">", joinMessage);
        }
        
        sender.sendMessage(ChatColor.GREEN + "Result: " + joinMessage);
    }

    @SubCommand(subCommand = "reload", description = "Reload the plugin configuration", permission = "joinandquitmessages.reload")
    public void reloadCommand(CommandSender sender, List<String> args) {
        JoinAndQuitMessages.getInstance().reloadConfigCache();
        sender.sendMessage(ChatColor.GREEN + "Configuration reloaded successfully!");
    }

    private String translateHexColorCodes(String startTag, String endTag, String message) {
        final Pattern hexPattern = Pattern.compile(startTag + "#?([A-Fa-f0-9]{6})" + endTag);
        Matcher matcher = hexPattern.matcher(message);
        StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);
        while (matcher.find()) {
            String group = matcher.group(1).replace("#", "");
            matcher.appendReplacement(buffer, COLOR_CHAR + "x"
                    + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
                    + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                    + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
            );
        }
        return matcher.appendTail(buffer).toString();
    }
}