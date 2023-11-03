package dev.theoduval.orchid.data;

public class Sector {
    private final int id;
    private int ceilHeight;
    private int floorHeight;

    public Sector(int id, int ceilHeight, int floorHeight) {
        this.id = id;
        this.ceilHeight = ceilHeight;
        this.floorHeight = floorHeight;
    }

    public final int getId() {
        return id;
    }

    public int getCeilHeight() {
        return ceilHeight;
    }

    public void setCeilHeight(int ceilHeight) {
        this.ceilHeight = ceilHeight;
    }

    public int getFloorHeight() {
        return floorHeight;
    }

    public void setFloorHeight(int floorHeight) {
        this.floorHeight = floorHeight;
    }

    @Override
    public String toString() {
        return id + " " + ceilHeight + " " + floorHeight;
    }
}
