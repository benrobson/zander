package net.craftingforchrist.zander.commands;

import net.craftingforchrist.zander.Variables;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class guides extends Command {
    public guides() {
        super("guides");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) commandSender;
            TextComponent message = new TextComponent("Little stuck? Need some help? Our guides site and resources may be of assistance to you. Check it out here: " + ChatColor.GOLD + Variables.guideaddress);
            message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, Variables.guideaddress));
            player.sendMessage(message);
            return;
        }
    }
}
