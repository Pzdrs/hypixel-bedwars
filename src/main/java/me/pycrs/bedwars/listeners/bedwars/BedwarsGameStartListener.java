package me.pycrs.bedwars.listeners.bedwars;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.Settings;
import me.pycrs.bedwars.entities.team.BedwarsTeam;
import me.pycrs.bedwars.events.BedwarsGameStartEvent;
import me.pycrs.bedwars.generators.Generator;
import me.pycrs.bedwars.menu.shops.items.ShopItem;
import me.pycrs.bedwars.tasks.GameLoop;
import me.pycrs.bedwars.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataType;

public class BedwarsGameStartListener implements Listener {
    private Bedwars plugin;

    public BedwarsGameStartListener(Bedwars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onGameStart(BedwarsGameStartEvent event) {
        Bedwars.gameLoop = new GameLoop(plugin);
        Bedwars.setGameInProgress(true);
        BedwarsTeam.distributePlayers();

        // Initial setup
        plugin.getPlayers().forEach(bedwarsPlayer -> {
            Player player = bedwarsPlayer.getPlayer();
            player.sendMessage(Settings.WELCOME_MESSAGE);
            bedwarsPlayer.setSpectator(false);
            bedwarsPlayer.getEquipment().updateArmor();
            bedwarsPlayer.getEquipment().equip();
            bedwarsPlayer.teleportToBase();
        });

        // Initial generator activation
        plugin.getMap().getDiamondGenerators().forEach(generator -> generator.activate(Generator.getProperty("diamondI", true)));
        plugin.getMap().getEmeraldGenerators().forEach(generator -> generator.activate(Generator.getProperty("emeraldI", true)));
        plugin.getTeams().forEach(team -> {
            // These will depend on what map is in use
            team.getIronGenerator().activate(20);
        });

        Bedwars.gameLoop.runTaskTimer(plugin, 0, 20);

        BedwarsTeam.removeEmptyTeams();
    }
}
