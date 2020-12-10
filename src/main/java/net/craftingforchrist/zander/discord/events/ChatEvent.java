package net.craftingforchrist.zander.discord.events;

import net.craftingforchrist.zander.ZanderBungeeMain;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.List;

public class ChatEvent extends ListenerAdapter {
    private ZanderBungeeMain plugin = ZanderBungeeMain.getInstance();

    @Override
    public void onGuildMessageReceived (GuildMessageReceivedEvent event) {
        String GuildMessage = event.getMessage().getContentRaw();
        String[] words = GuildMessage.split(" ");

        //
        // Swear Filter
        //
        List<String> bannedWords = plugin.configurationManager.getConfig().getStringList("bannedWords");

        boolean bannedshouldBreak = false;
        for (String word : words) {
            if(bannedshouldBreak) break;
            for (String badWord : bannedWords) {
                if (event.getMessage().getAuthor().isBot()) return;
                if (word.toLowerCase().equals(badWord.toLowerCase())) {
                    event.getMessage().delete().queue();

                    System.out.println(badWord + " is a banned word.");

                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle("No Swearing!", null);
                    embed.setColor(Color.red);
                    embed.setDescription(event.getAuthor().getName() + " swearing was detected in your message, swearing is not allowed on this Server.\nContinuing to do so will result in punishment.");
                    event.getChannel().sendMessage(embed.build()).complete();
                    bannedshouldBreak = true;
                    break;
                }
            }
        }

        //
        // Link Filter
        //
        List<String> filteredLinks = plugin.configurationManager.getConfig().getStringList("filteredLinks");

        boolean linksshouldBreak = false;
        for (String word : words) {
            if (linksshouldBreak) break;

            for (String filterLink : filteredLinks) {
                if (event.getMessage().getAuthor().isBot()) return;
                if (word.toLowerCase().contains(filterLink.toLowerCase())) {
                    if (word.toLowerCase().contains(plugin.configurationManager.getConfig().getString("web.siteaddress"))) return;
                    event.getMessage().delete().queue();

                    System.out.println(filterLink + " is a part of or is an advertising link.");

                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle("No Advertising!", null);
                    embed.setColor(Color.red);
                    embed.setDescription(event.getAuthor().getName() + " advertising was detected in your message, advertising is not allowed on this Server.\nContinuing to do so will result in punishment.");
                    event.getChannel().sendMessage(embed.build()).complete();
                    linksshouldBreak = true;
                    break;
                }
            }
        }
    }
}
