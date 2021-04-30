package me.pycrs.bedwarsrecoded.teamUpgrades;

import me.pycrs.bedwarsrecoded.traps.Trap;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class TeamUpgrades {
    private boolean sharpness, healPool, dragonBuff;
    private int protection;
    private Forge forge;
    private ManiacMiner maniacMiner;
    private Queue<Trap> traps;

    public TeamUpgrades() {
        this.traps = new ArrayBlockingQueue<>(3);
    }

    public TeamUpgrades(boolean sharpness, int protection, ManiacMiner maniacMiner, Forge forge, boolean healPool, boolean dragonBuff) {
        this.sharpness = sharpness;
        this.protection = protection;
        this.maniacMiner = maniacMiner;
        this.forge = forge;
        this.healPool = healPool;
        this.dragonBuff = dragonBuff;
        this.traps = new ArrayBlockingQueue<>(3);
    }

    public boolean hasSharpness() {
        return sharpness;
    }

    public boolean hasHealPool() {
        return healPool;
    }

    public boolean hasDragonBuff() {
        return dragonBuff;
    }

    public int getProtection() {
        return protection;
    }

    public ManiacMiner getManiacMiner() {
        return maniacMiner;
    }

    public Queue<Trap> getTraps() {
        return traps;
    }

    public Forge getForge() {
        return forge;
    }
}
