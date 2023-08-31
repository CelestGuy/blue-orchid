package dev.theoduval.orchid.data;

public class Wall {
    protected final int id;
    protected int aPointId, bPointId;

    public Wall(int id, int aPointId, int bPointId) {
        this.id = id;
        this.aPointId = aPointId;
        this.bPointId = bPointId;
    }

    public final int getId() {
        return id;
    }

    public int getAPointId() {
        return aPointId;
    }

    public void setAPointId(int aId) {
        this.aPointId = aId;
    }

    public int getBPointId() {
        return bPointId;
    }

    public void setBPointId(int bId) {
        this.bPointId = bId;
    }

    @Override
    public String toString() {
        return id + " " +
                aPointId + " " +
                bPointId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Wall wall)) return false;

        return id == wall.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
