package net.craftingforchrist.zander.commands;

import net.craftingforchrist.zander.ZanderBungeeMain;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class link extends Command {
    public link() {
        super("link");
    }
    private static ZanderBungeeMain plugin;

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) commandSender;

            if (args.length == 0) {
                player.sendMessage(new TextComponent(ChatColor.RED + "Please provide a link code."));
                return;
            } else {
                //
                // Database Query
                // Check if the player can be verified.
                //
                try {
                    PreparedStatement findstatement = plugin.getInstance().getConnection().prepareStatement("SELECT * FROM webaccounts where playerid = (select id from playerdata where username=?) AND registered = false;");
                    findstatement.setString(1, player.getDisplayName());
                    ResultSet results = findstatement.executeQuery();
                    if (results.next()) {
                        String registrationtoken = results.getString("registrationtoken");

                        if (args[0].equals(registrationtoken)) {
                            //
                            // Database Query
                            // Update the registration as successful and linked.
                            //
                            try {
                                PreparedStatement linkverifydonestatement = plugin.getInstance().getConnection().prepareStatement("UPDATE webaccounts SET registered=1 WHERE playerid = (select id from playerdata where username=?);");
                                linkverifydonestatement.setString(1, player.getDisplayName());
                                linkverifydonestatement.executeUpdate();
                                player.sendMessage(new TextComponent(ChatColor.GREEN.toString() + ChatColor.BOLD + "You are now registered as " + ChatColor.YELLOW + ChatColor.BOLD + player.getDisplayName()));
                            } catch (SQLException e) {
                                e.getMessage();
                            }
                        } else {
                            player.sendMessage(new TextComponent(ChatColor.RED + "This token is incorrect. Please check the registration email and try again."));
                        }
                    } else {
                        player.sendMessage(new TextComponent(ChatColor.RED + "You are already registered or have not started registering."));
                    }
                } catch (SQLException e) {
                    e.getMessage();
                }
            }
            return;
        }
    }
}
