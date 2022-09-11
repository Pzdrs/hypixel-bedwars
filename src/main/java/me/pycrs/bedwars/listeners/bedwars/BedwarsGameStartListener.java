package me.pycrs.bedwars.listeners.bedwars;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.Settings;
import me.pycrs.bedwars.entities.team.BedwarsTeam;
import me.pycrs.bedwars.entities.team.BedwarsTeamList;
import me.pycrs.bedwars.events.BedwarsGameStartEvent;
import me.pycrs.bedwars.generators.Generator;
import me.pycrs.bedwars.listeners.BaseListener;
import me.pycrs.bedwars.scoreboard.LobbyScoreboard;
import me.pycrs.bedwars.tasks.GameLoop;
import me.pycrs.bedwars.tasks.InventoryWatcher;
import me.pycrs.bedwars.entities.player.BedwarsPlayerList;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class BedwarsGameStartListener extends BaseListener<Bedwars> {
    public BedwarsGameStartListener(Bedwars plugin) {
        super(plugin);
    }

    @EventHandler
    public void onGameStart(BedwarsGameStartEvent event) {
        Bedwars.setGameStage(Bedwars.GameStage.GAME_IN_PROGRESS);
        BedwarsPlayerList.set(new BedwarsPlayerList(BedwarsTeam.distributePlayers()));

        // Initial setup
        BedwarsPlayerList.getList().forEach(bedwarsPlayer -> {
            Player player = bedwarsPlayer.getPlayer();
            LobbyScoreboard.get().removePlayer(player);
            player.sendMessage(Settings.WELCOME_MESSAGE);
            bedwarsPlayer.setSpectator(false);
            bedwarsPlayer.getEquipment().updateArmor(false);
            bedwarsPlayer.getEquipment().updateEquipment();
            bedwarsPlayer.showLevel();
            bedwarsPlayer.teleportToBase();
        });

        // Initial generator activation
        Bedwars.getMap().getDiamondGenerators().forEach(generator -> generator.activate(Generator.getProperty("diamondI", true)));
        Bedwars.getMap().getEmeraldGenerators().forEach(generator -> generator.activate(Generator.getProperty("emeraldI", true)));
        BedwarsTeamList.getList().forEach(team -> {
            // These will depend on what map is in use
            team.getForge().activate(20);
        });

        GameLoop.start(plugin);
        // This may cause some performance issues, keep an eye on it
        InventoryWatcher.start(plugin);

        BedwarsTeam.removeEmptyTeams();
    }
}
