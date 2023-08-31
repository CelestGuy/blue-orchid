package dev.theoduval.orchid.data;

public class Portal extends Wall {
    public final int linkedPortalId;

    public Portal(int id, int aId, int bId, int linkedPortalId) {
        super(id, aId, bId);
        this.linkedPortalId = linkedPortalId;
    }

    @Override
    public String toString() {
        return id + " " +
                aPointId + " " +
                bPointId + " " +
                linkedPortalId;
    }
}
