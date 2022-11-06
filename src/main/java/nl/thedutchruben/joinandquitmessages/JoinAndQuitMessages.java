package nl.thedutchruben.joinandquitmessages;

import nl.thedutchruben.mccore.Mccore;
import nl.thedutchruben.mccore.config.UpdateCheckerConfig;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class JoinAndQuitMessages extends JavaPlugin {
    private static JoinAndQuitMessages instance;
    public String defaultJoinMessage,defaultQuitMessage;
    public boolean randomMessages;
    @Override
    public void onEnable() {
        instance =this;

        if(!getConfig().contains("joinmessage")){
            getConfig().set("joinmessage","&7[&2+&7]&4%player%");
            getConfig().set("quitmessage","&7[&4-&7]&4%player%");
            saveConfig();
        }

        if(!getConfig().contains("version")){
            getConfig().set("version","1.1");
            getConfig().set("update_check",true);
            getConfig().set("random.enabled",false);
            getConfig().set("random.messages.join", Arrays.asList("&7[&2+&7]&4%player%", "&4%player% &7has joined the server"));
            getConfig().set("random.messages.leave", Arrays.asList("&7[&4-&7]&4%player%", "&4%player% &7has left the server"));
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

    public String getQuitMessage(Player player){
        if(randomMessages){
            List<String> array = getConfig().getStringList("random.messages.leave");
            Random generator = new Random();
            int randomIndex = generator.nextInt(array.size());
            return array.get(randomIndex);
        }
        return defaultQuitMessage;
    }
    public String getJoinMessage(Player player){
        if(randomMessages){
            List<String> array = getConfig().getStringList("random.messages.join");
            Random generator = new Random();
            int randomIndex = generator.nextInt(array.size());
            return array.get(randomIndex);
        }
        return defaultJoinMessage;
    }

    enum DownloadSource {
        SPIGOT,
        BUKKIT,
        GITHUB
    }
}
