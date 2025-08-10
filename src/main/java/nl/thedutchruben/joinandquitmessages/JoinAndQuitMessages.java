package nl.thedutchruben.joinandquitmessages;

import nl.thedutchruben.mccore.Mccore;
import nl.thedutchruben.mccore.config.UpdateCheckerConfig;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

public final class JoinAndQuitMessages extends JavaPlugin {
    private static JoinAndQuitMessages instance;
    public String defaultJoinMessage,defaultQuitMessage;
    public boolean randomMessages;

    @Override
    public void onEnable() {
        instance =this;

        if(!getConfig().contains("joinmessage")){
            getConfig().set("joinmessage", "&7[&2+&7]&4%player%");
            getConfig().set("quitmessage", "&7[&4-&7]&4%player%");
            saveConfig();
        }

        if(!getConfig().contains("version")){
            getConfig().set("version", "1.1");
            getConfig().set("update_check", true);
            getConfig().set("random.enabled", false);
            getConfig().set("random.messages.join", Arrays.asList("&7[&2+&7]&4%player%", "&4%player% &7has joined the server"));
            getConfig().set("random.messages.leave", Arrays.asList("&7[&4-&7]&4%player%", "&4%player% &7has left the server"));
            saveConfig();
        }

        // Add permission-based messages configuration
        if(!getConfig().contains("permission_messages.enabled")){
            getConfig().set("permission_messages.enabled", false);
            getConfig().set("permission_messages.join.joinandquitmessages.vip", "&7[&6VIP+&7]&6%player%");
            getConfig().set("permission_messages.join.joinandquitmessages.premium", "&7[&5PREMIUM+&7]&5%player%");
            getConfig().set("permission_messages.leave.joinandquitmessages.vip", "&7[&6VIP-&7]&6%player%");
            getConfig().set("permission_messages.leave.joinandquitmessages.premium", "&7[&5PREMIUM-&7]&5%player%");
            saveConfig();
        }

        defaultJoinMessage = getConfig().getString("joinmessage");
        defaultQuitMessage = getConfig().getString("quitmessage");
        randomMessages = getConfig().getBoolean("random.enabled");

        Metrics metrics = new Metrics(this, 15516);
        metrics.addCustomChart(new SimplePie("random_messages", () -> String.valueOf(randomMessages)));
        metrics.addCustomChart(new SimplePie("download_source", DownloadSource.GITHUB::name));
        metrics.addCustomChart(new SimplePie("update_checker", () -> String.valueOf(getConfig().getBoolean("update_check", true))));

        Mccore mccore = new Mccore(this,"joinandquitmessages","63681520254bd4f432001af8", Mccore.PluginType.SPIGOT);
        // Start the update checker if enabled.
        if (getConfig().getBoolean("update_check", true)) {
            mccore.startUpdateChecker(new UpdateCheckerConfig("joinandquitmessages.checkupdate",20*60*5));
        }
    }

    @Override
    public void onDisable() {

    }

    public static JoinAndQuitMessages getInstance(){
        return instance;
    }

    public String getQuitMessage() {
        if (randomMessages) {
            List<String> array = getConfig().getStringList("random.messages.leave");
            Random generator = new Random();
            int randomIndex = generator.nextInt(array.size());
            return array.get(randomIndex);
        }

        return defaultQuitMessage;
    }

    public String getQuitMessage(Player player) {
        // Check if permission-based messages are enabled
        if (getConfig().getBoolean("permission_messages.enabled", false)) {
            Set<String> permissionKeys = getConfig().getConfigurationSection("permission_messages.leave").getKeys(false);
            
            // Find the highest priority permission (check in reverse order)
            for (String permission : permissionKeys) {
                if (player.hasPermission(permission)) {
                    return getConfig().getString("permission_messages.leave." + permission);
                }
            }
        }

        // Fall back to existing logic (random or default)
        return getQuitMessage();
    }

    public String getJoinMessage() {
        if (randomMessages) {
            List<String> array = getConfig().getStringList("random.messages.join");
            Random generator = new Random();
            int randomIndex = generator.nextInt(array.size());
            return array.get(randomIndex);
        }

        return defaultJoinMessage;
    }

    public String getJoinMessage(Player player) {
        // Check if permission-based messages are enabled
        if (getConfig().getBoolean("permission_messages.enabled", false)) {
            Set<String> permissionKeys = getConfig().getConfigurationSection("permission_messages.join").getKeys(false);
            
            // Find the highest priority permission (check in reverse order)
            for (String permission : permissionKeys) {
                if (player.hasPermission(permission)) {
                    return getConfig().getString("permission_messages.join." + permission);
                }
            }
        }

        // Fall back to existing logic (random or default)
        return getJoinMessage();
    }

    public void reloadConfigCache() {
        reloadConfig();
        defaultJoinMessage = getConfig().getString("joinmessage");
        defaultQuitMessage = getConfig().getString("quitmessage");
        randomMessages = getConfig().getBoolean("random.enabled");
    }

    enum DownloadSource {
        SPIGOT,
        BUKKIT,
        GITHUB,
        MODRINTH
    }
}
