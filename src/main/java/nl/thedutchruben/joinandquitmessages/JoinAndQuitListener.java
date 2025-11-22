package nl.thedutchruben.joinandquitmessages;

import me.clip.placeholderapi.PlaceholderAPI;
import nl.thedutchruben.mccore.spigot.listeners.TDRListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.md_5.bungee.api.ChatColor.COLOR_CHAR;

@TDRListener
public class JoinAndQuitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        String joinText = JoinAndQuitMessages.getInstance().getJoinMessage(event.getPlayer())
                .replace('&','§')
                .replace("%player%", event.getPlayer().getName())
                .replace("%world%", event.getPlayer().getWorld().getName())
                .replace("%playercount_online%", String.valueOf(Bukkit.getOnlinePlayers().size()))
                .replace("%playercount_max%", String.valueOf(Bukkit.getMaxPlayers()));
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            joinText = PlaceholderAPI.setPlaceholders(event.getPlayer(), joinText);
        }
        String[] split = Bukkit.getBukkitVersion().split("-")[0].split("\\.");
        if(Integer.parseInt(split[1]) >= 16) {
            joinText = translateHexColorCodes("<",">", joinText);
        }

        event.setJoinMessage(null);

        Bukkit.getLogger().log(Level.INFO, joinText);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.canSee(event.getPlayer())) {
                onlinePlayer.sendMessage(joinText);
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        String quitMessage = JoinAndQuitMessages.getInstance().getQuitMessage(event.getPlayer())
                .replace('&','§')
                .replace("%player%", event.getPlayer().getName())
                .replace("%world%", event.getPlayer().getWorld().getName())
                .replace("%playercount_online%", String.valueOf(Bukkit.getOnlinePlayers().size() - 1))
                .replace("%playercount_max%", String.valueOf(Bukkit.getMaxPlayers()));
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            quitMessage = PlaceholderAPI.setPlaceholders(event.getPlayer(), quitMessage);
        }
        String[] split = Bukkit.getBukkitVersion().split("-")[0].split("\\.");
        if(Integer.parseInt(split[1]) >= 16) {
            quitMessage = translateHexColorCodes("<",">", quitMessage);
        }

        event.setQuitMessage(null);

        Bukkit.getLogger().log(Level.INFO, quitMessage);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.canSee(event.getPlayer())) {
                onlinePlayer.sendMessage(quitMessage);
            }
        }
    }

    public String translateHexColorCodes(String startTag, String endTag, String message)
    {
        final Pattern hexPattern = Pattern.compile(startTag + "#?([A-Fa-f0-9]{6})" + endTag);
        Matcher matcher = hexPattern.matcher(message);
        StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);
        while (matcher.find())
        {
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
