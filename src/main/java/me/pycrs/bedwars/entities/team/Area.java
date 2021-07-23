package me.pycrs.bedwars.entities.team;

import org.bukkit.Location;

public class Area {
    private Location center;
    private double xOffset, yOffset, zOffset;

    public Area(Location center, double xOffset, double yOffset, double zOffset) {
        this.center = center;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
    }

    /**
     * A function that checks whether a given point, a block, is inside of an area centered around a block, the area's size is dictated by an offset on each axis
     *
     * @param point The block that we are checking
     * @return The point being inside of the given area
     */
    public boolean isInArea(Location point) {
        return (point.getBlockX() > (center.getBlockX() - xOffset) && point.getBlockX() < (center.getBlockX() + xOffset)) &&
                (point.getBlockY() > (center.getBlockY() - yOffset) && point.getBlockY() < (center.getBlockY() + yOffset)) &&
                (point.getBlockZ() > (center.getBlockZ() - zOffset) && point.getBlockZ() < (center.getBlockZ() + zOffset));
    }

    public Location getCenter() {
        return center;
    }

    public double getxOffset() {
        return xOffset;
    }

    public double getyOffset() {
        return yOffset;
    }

    public double getzOffset() {
        return zOffset;
    }
}
