package nl.thedutchruben.joinandquitmessages;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinAndQuitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        event.setJoinMessage(JoinAndQuitMessages.getInstance().joinmessage.replace('&','§').replace("%player%",event.getPlayer().getName()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        event.setQuitMessage(JoinAndQuitMessages.getInstance().quitmessage.replace('&','§').replace("%player%",event.getPlayer().getName()));
    }
}
