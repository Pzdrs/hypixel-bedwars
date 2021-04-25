package me.pycrs.bedwarsrecoded;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class TeamUpgrades {
    private boolean sharpness, healPool, dragonBuff;
    private int protection, maniacMiner, forge;
    private Queue<Trap> traps;

    public TeamUpgrades(boolean sharpness, int protection, int maniacMiner, int forge, boolean healPool, boolean dragonBuff) {
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

    public int getManiacMiner() {
        return maniacMiner;
    }

    public Queue<Trap> getTraps() {
        return traps;
    }

    public int getForge() {
        return forge;
    }
}