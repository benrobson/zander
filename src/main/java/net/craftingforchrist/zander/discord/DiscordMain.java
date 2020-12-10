package net.craftingforchrist.zander.discord;

import lombok.Getter;
import net.craftingforchrist.zander.Variables;
import net.craftingforchrist.zander.ZanderBungeeMain;
import net.craftingforchrist.zander.discord.commands.status;
import net.craftingforchrist.zander.discord.events.ChatEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Listener;

import javax.security.auth.login.LoginException;

import static net.craftingforchrist.zander.Variables.discordtoken;

public class DiscordMain extends ListenerAdapter implements Listener {
    @Getter private static DiscordMain instance;
    public static JDA jda;
    @Getter private ZanderBungeeMain plugin;

    public DiscordMain(ZanderBungeeMain plugin) {
        instance = this;
        this.plugin = plugin;
        plugin.getProxy().getPluginManager().registerListener(plugin, this);

        if (startBot()) {
            registerDiscordEventListeners();
        }
    }

    private void registerDiscordEventListeners() {
        this.jda.addEventListener(this);
        this.jda.addEventListener(new status());
        this.jda.addEventListener(new ChatEvent());
    }

    //
    // Start the Discord side bot
    //
    private boolean startBot() {
        try {
            // Build JDA/bot connection
            jda = JDABuilder.createDefault(discordtoken).build();
            // Show signs of life
            plugin.getProxy().getConsole().sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', Variables.developmentprefix + " zander is now connected to Discord.\nConnected Discord Server Name: " + ChatColor.YELLOW + jda.getGuilds())));
            return true;
        } catch (LoginException e) {
            plugin.getProxy().getConsole().sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', Variables.developmentprefix + " zander has encountered an error and can't login to Discord. The Discord Token may not be set, Discord integrations might not function.")));
        }
        return false;
    }
}
