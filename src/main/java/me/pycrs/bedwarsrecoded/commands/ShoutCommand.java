package me.pycrs.bedwarsrecoded.commands;

import me.pycrs.bedwarsrecoded.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.List;

public class ShoutCommand implements TabExecutor {
    private Bedwars plugin;

    public ShoutCommand(Bedwars plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("shout")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        // Check, if sender was a Player
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command is available to players only.");
            return true;
        }

        Player player = ((Player) sender);
        // Check, if there is a message to shout
        if (args.length < 1) {
            player.sendMessage(Component.text(Utils.color("&cMissing arguments! Usage: " + command.getUsage())));
            return true;
        }
        // Make sure no one shouts before the game starts
        if (!Bedwars.isGameInProgress()) {
            player.sendMessage(Component.text("You can't use /shout before the game has started.", NamedTextColor.RED));
            return true;
        }
        // Check, if this mode has /shout enabled
        if (Bedwars.getMode().equals(Mode.SOLO)) {
            player.sendMessage(Component.text("You can't use /shout in solo mode.", NamedTextColor.RED));
            return true;
        }

        BedwarsPlayer bedwarsPlayer = BedwarsPlayer.toBPlayer(player);
        bedwarsPlayer.shout(Component
                .text(Utils.color("&6[SHOUT]&r "))
                .append(Utils.getTeamPrefix(BedwarsPlayer.getPlayersTeam(player)))
                .append(player.displayName())
                .append(Component.text(Utils.color("&7:&r " + Utils.commandArgsMessage(args, 0)))));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return new ArrayList<>();
    }
}
