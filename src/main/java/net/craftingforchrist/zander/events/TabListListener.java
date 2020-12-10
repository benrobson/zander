package net.craftingforchrist.zander.events;

import net.craftingforchrist.zander.ConfigurationManager;
import net.craftingforchrist.zander.ZanderBungeeMain;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.concurrent.TimeUnit;

public class TabListListener implements Listener {
    private ZanderBungeeMain plugin = ZanderBungeeMain.getInstance();

    // Called for when player is connected, send them the tablist.
    @EventHandler
    public void onPlayerJoin(ServerConnectedEvent event) {
        plugin.getProxy().getScheduler().schedule(plugin, new Runnable() {
            @Override
            public void run() {
                ProxiedPlayer player = event.getPlayer();

                player.resetTabHeader();
                player.setTabHeader(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', ConfigurationManager.getConfig().getString("tablist.footer"))).create(),
                        new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', ConfigurationManager.getConfig().getString("tablist.footer"))).create());
            }
        }, 3, TimeUnit.SECONDS);
    }

    // Called for when the player has switched servers, send them the updated tablist.
    @EventHandler
    public void onPlayerJoin(ServerSwitchEvent event) {
        plugin.getProxy().getScheduler().schedule(plugin, new Runnable() {
            @Override
            public void run() {
                ProxiedPlayer player = event.getPlayer();

                player.resetTabHeader();
                player.setTabHeader(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', ConfigurationManager.getConfig().getString("tablist.header"))).create(),
                        new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', ConfigurationManager.getConfig().getString("tablist.footer"))).create());
            }
        }, 3, TimeUnit.SECONDS);
    }
}
