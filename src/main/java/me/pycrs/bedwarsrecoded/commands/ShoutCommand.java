package me.pycrs.bedwarsrecoded.commands;

import me.pycrs.bedwarsrecoded.BPlayer;
import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.Mode;
import me.pycrs.bedwarsrecoded.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ShoutCommand implements CommandExecutor {
    private BedWars bedWars;

    public ShoutCommand(BedWars bedWars) {
        this.bedWars = bedWars;
        Objects.requireNonNull(bedWars.getCommand("shout")).setExecutor(this);
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
        if (!BedWars.isGameInProgress()) {
            player.sendMessage(Component.text("You can't use /shout before the game has started.", NamedTextColor.RED));
            return true;
        }
        // Check, if this mode has /shout enabled
        if (bedWars.getMode().equals(Mode.SOLO)) {
            player.sendMessage(Component.text("You can't use /shout in solo mode.", NamedTextColor.RED));
            return true;
        }

        BPlayer bPlayer = bedWars.getPlayer(player.getUniqueId());
        // Check, if the players is on a cooldown
        if (bPlayer.isOnShoutCoolDown().getKey()) {
            player.sendMessage(Component.text(Utils.color("&cYou must wait &e" +
                    bPlayer.isOnShoutCoolDown().getValue() + " &cseconds until you can use /shout again!")));
            return true;
        }

        bedWars.getServer().sendMessage(Component
                .text(Utils.color("&6[SHOUT]&r "))
                .append(Component.text(Utils.getTeamPrefix(null) + " "))
                .append(player.displayName())
                .append(Component.text(Utils.color("&7:&r " + Utils.commandArgsMessage(args, 0)))));
        bPlayer.putOnShoutCoolDown();
        return true;
    }
}
