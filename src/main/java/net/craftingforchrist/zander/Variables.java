package net.craftingforchrist.zander;

public class Variables {
    // Prefixes
    public static String pluginprefix = ConfigurationManager.getConfig().getString("prefix.plugin");
    public static String developmentprefix = ConfigurationManager.getConfig().getString("prefix.development");
    public static String reportprefix = ConfigurationManager.getConfig().getString("prefix.report");
    public static String voteprefix = ConfigurationManager.getConfig().getString("prefix.vote");

    // Database
    public static String host = ConfigurationManager.getDatabase().getString("database.host");
    public static String port = ConfigurationManager.getDatabase().getString("database.port");
    public static String database = ConfigurationManager.getDatabase().getString("database.database");
    public static String username = ConfigurationManager.getDatabase().getString("database.username");
    public static String password = ConfigurationManager.getDatabase().getString("database.password");
    public static String useSSL = ConfigurationManager.getDatabase().getString("database.usessl");
    public static String autoReconnect = ConfigurationManager.getDatabase().getString("database.autoreconnect");

    // Website Information
    public static String siteaddress = ConfigurationManager.getConfig().getString("web.siteaddress");
    public static String guideaddress = ConfigurationManager.getConfig().getString("web.guideaddress");

    // Discord
    public static String discordtoken = ConfigurationManager.getConfig().getString("discord.token");
    public static String discordprefix = ConfigurationManager.getConfig().getString("discord.prefix");
}
