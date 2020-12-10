package net.craftingforchrist.zander.discord.commands;

import net.craftingforchrist.zander.Variables;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.md_5.bungee.api.ProxyServer;

import java.awt.*;

public class status extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(Variables.discordprefix + "status")) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Network Status");
            embed.setColor(Color.ORANGE);

            ProxyServer.getInstance().getServers().forEach((name, serverInfo) -> {
                String servername = serverInfo.getName();
                servername = servername.substring(0,1).toUpperCase() + servername.substring(1).toLowerCase();

                embed.addField(servername, "Online: " + serverInfo.getPlayers().size(), true);
            });

            event.getChannel().sendMessage(embed.build()).queue();
        }
    }
}
