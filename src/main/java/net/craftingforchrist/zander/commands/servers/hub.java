package net.craftingforchrist.zander.commands.servers;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class hub extends Command {
    public hub() {
        super("hub");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) commandSender;

            // Check if the Player is already connected to the Hub
            if (player.getServer().getInfo().getName().equalsIgnoreCase("hub")) {
                player.sendMessage(new TextComponent(ChatColor.RED + "You are already connected to the Hub!"));
                return;
            }

            ServerInfo target = ProxyServer.getInstance().getServerInfo("Hub");
            player.connect(target);
            return;
        }
    }
}
