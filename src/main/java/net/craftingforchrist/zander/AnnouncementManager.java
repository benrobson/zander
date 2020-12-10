package net.craftingforchrist.zander;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AnnouncementManager {
    public static void schedule(ZanderBungeeMain plugin) {
        int interval = plugin.configurationManager.getConfig().getInt("announcement.interval");
        ProxyServer.getInstance().getScheduler().schedule(plugin, new Runnable() {
            @Override
            public void run() {
                Random random = new Random();

                int get = random.nextInt(plugin.configurationManager.getConfig().getInt("announcement.max"));
                String announcement = ChatColor.translateAlternateColorCodes('&', Variables.pluginprefix + " " + plugin.configurationManager.getConfig().getString("announcement." + get));
                ProxyServer.getInstance().broadcast(announcement); // Broadcast to all Servers
            }
        }, 2, interval, TimeUnit.MINUTES);
        return;
    }
}