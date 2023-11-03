package dev.theoduval.orchid.data;

public class Wall {
    private final int id;
    private int aPointId, bPointId;
    private final boolean portal;
    public final int linkedPortalId;

    public Wall(int id, int aPointId, int bPointId) {
        this.id = id;
        this.aPointId = aPointId;
        this.bPointId = bPointId;
        this.portal = false;
        this.linkedPortalId = -1;
    }

    public Wall(int id, int aPointId, int bPointId, int linkedPortalId) {
        this.id = id;
        this.aPointId = aPointId;
        this.bPointId = bPointId;
        this.portal = true;
        this.linkedPortalId = linkedPortalId;
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

    public boolean isPortal() {
        return portal;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(" ")
                .append(aPointId)
                .append(" ")
                .append(bPointId);

        if (portal) {
            sb.append(" ").append(linkedPortalId);
        }

        return sb.toString();
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
