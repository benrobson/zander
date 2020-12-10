package net.craftingforchrist.zander.commands;

import net.craftingforchrist.zander.Variables;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ranks extends Command {
    public ranks() {
        super("ranks");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) commandSender;
            TextComponent message = new TextComponent("Look at rank perks and purchase ranks at " + ChatColor.BLUE + Variables.siteaddress + "ranks" + ChatColor.RESET);
            message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, Variables.siteaddress + "ranks"));
            player.sendMessage(message);
            return;
        }
    }
}
