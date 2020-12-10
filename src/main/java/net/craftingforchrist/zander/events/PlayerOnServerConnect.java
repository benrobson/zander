package net.craftingforchrist.zander.events;

import net.craftingforchrist.zander.Variables;
import net.craftingforchrist.zander.ZanderBungeeMain;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayerOnServerConnect implements Listener {
    private ZanderBungeeMain plugin = ZanderBungeeMain.getInstance();

    @EventHandler
    public void PlayerServerSwitchEvent(ServerConnectedEvent event) {
        ProxiedPlayer player = event.getPlayer();

        //
        // Database Query
        // Start the players game session.
        //
        try {
            PreparedStatement updatestatement = plugin.getConnection().prepareStatement("UPDATE gamesessions SET server = ? WHERE (select id from playerdata where uuid = ?) AND sessionend IS NULL");
            updatestatement.setString(1, event.getServer().getInfo().getName());
            updatestatement.setString(2, event.getPlayer().getUniqueId().toString());
            updatestatement.executeUpdate();
            plugin.getProxy().getConsole().sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', Variables.developmentprefix + " " + player.getDisplayName() + " has switched to " + event.getServer().getInfo().getName() + ".")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}