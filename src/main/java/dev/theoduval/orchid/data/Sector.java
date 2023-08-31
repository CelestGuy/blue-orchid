package dev.theoduval.orchid.data;

public class Sector {
    private final int id;
    private double ceilHeight;
    private double floorHeight;

    public Sector(int id, double ceilHeight, double floorHeight) {
        this.id = id;
        this.ceilHeight = ceilHeight;
        this.floorHeight = floorHeight;
    }

    public final int getId() {
        return id;
    }

    public double getCeilHeight() {
        return ceilHeight;
    }

    public void setCeilHeight(double ceilHeight) {
        this.ceilHeight = ceilHeight;
    }

    public double getFloorHeight() {
        return floorHeight;
    }

    public void setFloorHeight(double floorHeight) {
        this.floorHeight = floorHeight;
    }

    @Override
    public String toString() {
        return id + " " + ceilHeight + " " + floorHeight;
    }
}
