package nl.thedutchruben.joinandquitmessages;

import org.bukkit.plugin.java.JavaPlugin;

public final class JoinAndQuitMessages extends JavaPlugin {
    private static JoinAndQuitMessages instance;
    public String joinmessage,quitmessage;
    @Override
    public void onEnable() {
        instance =this;

        if(!getConfig().contains("joinmessage")){
            getConfig().set("joinmessage","&7[&2+&7]&4%player%");
            getConfig().set("quitmessage","&7[&4-&7]&4%player%");
            saveConfig();
        }
        joinmessage = getConfig().getString("joinmessage");
        quitmessage = getConfig().getString("quitmessage");
        getServer().getPluginManager().registerEvents(new JoinAndQuitListener(),this);
    }

    @Override
    public void onDisable() {

    }

    public static JoinAndQuitMessages getInstance(){
        return instance;
    }
}
