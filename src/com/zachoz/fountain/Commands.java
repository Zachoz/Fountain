package com.zachoz.fountain;

import com.sk89q.minecraft.util.commands.*;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands {

    FountainPlugin plugin;

    public Commands(FountainPlugin pl) {
        plugin = pl;
    }

    @Command(aliases = {"fountain", "fountains"},
            usage = "<params>",
            desc = "Fountain commands",
            min = 1)
    @CommandPermissions({"fountain.manage"})
    public void fountain(CommandContext args, CommandSender sender) throws CommandException {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can execute this command!");
            return;
        }

        if (args.getString(0).equals("create") && args.argsLength() >= 2) {
            if (args.getString(1).matches("[A-Za-z0-9]+")) {
                if (FountainPlugin.fountains.containsKey(args.getString(1))) {
                    sender.sendMessage(ChatColor.RED + "A fountain with that name already exists!");
                    return;
                }

                new Fountain(args.getString(1), ((Player) sender).getLocation(), true);
                sender.sendMessage(ChatColor.DARK_AQUA + "Created new fountain at your location called " + ChatColor.AQUA + args.getString(1));

            } else {
                sender.sendMessage(ChatColor.RED + "Fountain names must be alphanumeric!");
            }
        }

        if (args.getString(0).equals("remove") && args.argsLength() >= 2) {
            if (FountainPlugin.fountains.containsKey(args.getString(1))) {
                FountainPlugin.fountains.get(args.getString(1)).remove(true);
                sender.sendMessage(ChatColor.DARK_AQUA + "Removed fountain " + ChatColor.AQUA + args.getString(1));
            } else {
                sender.sendMessage(ChatColor.DARK_AQUA + "No fountain could be found under that name! Type /fountain list " +
                        "for a list of active fountains");
            }
        }

        if (args.getString(0).equals("list")) {
            sender.sendMessage(ChatColor.DARK_AQUA + "Active fountains: " + ChatColor.AQUA
                    + StringUtils.join(FountainPlugin.fountains.keySet(), ChatColor.DARK_AQUA + ", " + ChatColor.AQUA));
        }

        if (args.getString(0).equals("teleport") && args.argsLength() >= 2) {
            if (FountainPlugin.fountains.containsKey(args.getString(1))) {
                ((Player) sender).teleport(FountainPlugin.fountains.get(args.getString(1)).getLocation());
                sender.sendMessage(ChatColor.DARK_AQUA + "Teleported to fountain " + ChatColor.AQUA + args.getString(1));
            } else {
                sender.sendMessage(ChatColor.DARK_AQUA + "No fountain could be found under that name! Type /fountain list " +
                        "for a list of active fountains");
            }
        }

    }
}
