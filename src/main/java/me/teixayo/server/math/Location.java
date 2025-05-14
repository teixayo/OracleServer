package me.teixayo.server.math;

import me.teixayo.server.chunk.Position;

public class Location {
    public double x;
    public double y;
    public double z;
    public float yaw;
    public float pitch;

    public Location() {
    }

    public Location(double x, double y, double z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Location clone() {
        return new Location(x, y, z, yaw, pitch);
    }

    public double distance(Location otherLocation) {
        double dx = this.x - otherLocation.x;
        double dy = this.y - otherLocation.y;
        double dz = this.z - otherLocation.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }


    public Position toBlockPosition() {
        return new Position((int) x, (int) y, (int) z);
    }
}
