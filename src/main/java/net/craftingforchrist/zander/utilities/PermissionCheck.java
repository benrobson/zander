package net.craftingforchrist.zander.utilities;

import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.User;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PermissionCheck {
    public static boolean hasPermission(ProxiedPlayer player, String permission) {
        try {
            User user = LuckPerms.getApi().getUserManager().getUser(player.getUniqueId());

//            PermissionData user = (PermissionData) LuckPerms.getApi().getUser(player.getUniqueId());

            if (user == null) return false;

            if (user.getPermissions().contains(permission) == true) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Something went wrong in the permission check.");
            e.printStackTrace();
        }
        return false;
    }
}
