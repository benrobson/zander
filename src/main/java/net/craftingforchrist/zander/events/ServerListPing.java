package net.craftingforchrist.zander.events;

import net.craftingforchrist.zander.ZanderBungeeMain;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.util.Random;

public class ServerListPing implements Listener {
    private ZanderBungeeMain plugin = ZanderBungeeMain.getInstance();

    @EventHandler (priority = EventPriority.HIGH)
    public void ServerListPing(ProxyPingEvent event) {
        ServerPing ping = event.getResponse();
        Random random = new Random();

        int get = random.nextInt(plugin.configurationManager.getMotd().getInt("motd.max"));
        String motd = ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getMotd().getString("motd." + get));
        ping.setDescription(motd);
        event.setResponse(ping);
    }
}