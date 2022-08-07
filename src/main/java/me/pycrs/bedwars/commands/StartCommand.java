package me.pycrs.bedwars.commands;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.tasks.LobbyLoop;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.block.data.type.Bed;
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
        if (sender instanceof Player player) {
            if (!player.hasPermission("bedwars.start")) {
                player.sendMessage(Component.text("You must be admin or higher to use this command!", NamedTextColor.RED));
                return true;
            }
        }

        if (LobbyLoop.isCountingDown() || Bedwars.isGameInProgress()) {
            sender.sendMessage(Component.text("The game is starting already or is already in progress.", NamedTextColor.RED));
            return true;
        } else if (Bedwars.isGameFinished()) {
            sender.sendMessage(Component.text("The game has finished, waiting for server restart.", NamedTextColor.RED));
            return true;
        } else if (Bukkit.getOnlinePlayers().size() < 2) {
            sender.sendMessage(Component.text("A minimum of 2 players is needed to forcefully start the game.", NamedTextColor.RED));
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
