package me.pycrs.bedwars.listeners.bedwars;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.Settings;
import me.pycrs.bedwars.entities.player.level.BedwarsPrestige;
import me.pycrs.bedwars.entities.player.level.HypixelExperienceCalculator;
import me.pycrs.bedwars.entities.team.BedwarsTeam;
import me.pycrs.bedwars.events.BedwarsGameStartEvent;
import me.pycrs.bedwars.generators.Generator;
import me.pycrs.bedwars.listeners.BaseListener;
import me.pycrs.bedwars.tasks.GameLoop;
import me.pycrs.bedwars.tasks.InventoryWatcher;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class BedwarsGameStartListener extends BaseListener<Bedwars> {
    public BedwarsGameStartListener(Bedwars plugin) {
        super(plugin);
    }

    @EventHandler
    public void onGameStart(BedwarsGameStartEvent event) {
        Bedwars.setGameStage(Bedwars.GameStage.GAME_IN_PROGRESS);
        BedwarsTeam.distributePlayers();

        // Initial setup
        plugin.getPlayers().forEach(bedwarsPlayer -> {
            Player player = bedwarsPlayer.getPlayer();
            player.sendMessage(Settings.WELCOME_MESSAGE);
            bedwarsPlayer.setSpectator(false);
            bedwarsPlayer.getEquipment().updateArmor(false);
            bedwarsPlayer.getEquipment().updateEquipment();
            bedwarsPlayer.showLevel();
            bedwarsPlayer.teleportToBase();
        });

        // Initial generator activation
        plugin.getMap().getDiamondGenerators().forEach(generator -> generator.activate(Generator.getProperty("diamondI", true)));
        plugin.getMap().getEmeraldGenerators().forEach(generator -> generator.activate(Generator.getProperty("emeraldI", true)));
        plugin.getTeams().forEach(team -> {
            // These will depend on what map is in use
            team.getForge().activate(20);
        });

        Bedwars.gameLoop = new GameLoop(plugin);
        // This may cause some performance issues, keep an eye on it
        Bedwars.inventoryWatcher = new InventoryWatcher(plugin);

        BedwarsTeam.removeEmptyTeams();
    }
}
