package me.pycrs.bedwarsrecoded.commands;

import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

public class ShoutCommand implements CommandExecutor {
    private BedWars bedWars;

    public ShoutCommand(BedWars bedWars) {
        this.bedWars = bedWars;
        Objects.requireNonNull(bedWars.getCommand("shout")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command is available to players only.");
            return true;
        }
        Player player = ((Player) sender);
        if (args.length < 1) {
            player.sendMessage(Component.text(Utils.color("&cMissing arguments! Usage: " + command.getUsage())));
            return true;
        }
        // TODO: 4/6/2021 Check if on cooldown
        bedWars.getServer().sendMessage(Component
                .text(Utils.color("&6[SHOUT]&r "))
                .append(Component.text(Utils.getTeamPrefix(null) + " "))
                .append(player.displayName())
                .append(Component.text(Utils.color("&7:&r " + Utils.commandArgsMessage(args, 0)))));
        return true;
    }
}
