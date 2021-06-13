package me.pycrs.bedwarsrecoded.commands;

import me.pycrs.bedwarsrecoded.Bedwars;
import me.pycrs.bedwarsrecoded.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StartCommand implements TabExecutor {
    private Bedwars plugin;

    public StartCommand(Bedwars plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("start")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission("bedwars.start")) {
                player.sendMessage(Component.text("You must be admin or higher to use this command!", NamedTextColor.RED));
                return true;
            }
        }
        if (Bukkit.getOnlinePlayers().size() < 1) {
            sender.sendMessage("A minimum of 1 player is needed to forcefully start the game.");
            return true;
        }
        if (Utils.isLobbyCountdownInProgress(plugin) || plugin.isGameInProgress()) {
            sender.sendMessage(Component.text("The game is starting already or is already in progress.", NamedTextColor.RED));
            return true;
        } else {
            plugin.startLobbyCountdown();
            sender.sendMessage(Component.text("You have started the game manually.", NamedTextColor.GREEN));
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return new ArrayList<>();
    }
}
