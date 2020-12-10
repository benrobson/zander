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
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerOnJoin implements Listener {
    private ZanderBungeeMain plugin = ZanderBungeeMain.getInstance();

    @EventHandler
    public void PlayerOnJoin(ServerConnectedEvent event) {
        ProxiedPlayer player = event.getPlayer();
        plugin.getLogger().info(player.getDisplayName() + " has joined the server");

        //
        // Database Query
        // Create a new player profile in Database.
        //
        try {
            PreparedStatement findstatement = plugin.getConnection().prepareStatement("SELECT * FROM playerdata WHERE uuid=?");
            findstatement.setString(1, player.getUniqueId().toString());
            ResultSet results = findstatement.executeQuery();
            if (!results.next()) {
                plugin.getProxy().getConsole().sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', Variables.developmentprefix + " " + player.getDisplayName() + " is a new player, creating a player profile.")));
                PreparedStatement insertstatement = plugin.getConnection().prepareStatement("INSERT INTO playerdata (uuid, username) VALUES (?, ?)");

                insertstatement.setString(1, player.getUniqueId().toString());
                insertstatement.setString(2, player.getDisplayName());

                insertstatement.executeUpdate();
                plugin.getProxy().getConsole().sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', Variables.developmentprefix + " Inserted information into " + player.getDisplayName() + "'s profile")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //
        // Database Query
        // Check if player has updated their username
        //
        try {
            PreparedStatement findstatement = plugin.getConnection().prepareStatement("SELECT username FROM playerdata WHERE uuid=?;");
            findstatement.setString(1, player.getUniqueId().toString());
            ResultSet results = findstatement.executeQuery();
            if (results.next()) {
                PreparedStatement updatestatement = plugin.getConnection().prepareStatement("UPDATE playerdata SET username=? WHERE uuid=?;");
                updatestatement.setString(1, player.getDisplayName());
                updatestatement.setString(2, player.getUniqueId().toString());
                updatestatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //
        // Database Query
        // Start the players game session.
        //
        try {
            PreparedStatement insertstatement = plugin.getConnection().prepareStatement("INSERT INTO gamesessions (playerid, ipaddress, server) VALUES ((select id from playerdata where uuid=?), ?, ?)");
            insertstatement.setString(1, player.getUniqueId().toString());
            insertstatement.setString(2, player.getAddress().getAddress().getHostAddress());
            insertstatement.setString(3, "hub");
            insertstatement.executeUpdate();
            plugin.getProxy().getConsole().sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', Variables.developmentprefix + " " + player.getDisplayName() + " has logged in, beginning their session.")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}