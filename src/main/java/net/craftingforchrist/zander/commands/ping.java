package net.craftingforchrist.zander.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ping extends Command {
    public ping() {
        super("ping");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) commandSender;
            int ping = player.getPing();

            player.sendMessage(new TextComponent("Your ping is: " + ChatColor.GREEN + ping));
            return;
        }
    }
}
