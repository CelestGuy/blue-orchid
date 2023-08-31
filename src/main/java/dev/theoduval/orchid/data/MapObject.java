package dev.theoduval.orchid.data;

public class MapObject {
    private final int id;

    private double x;
    private double y;
    private double z;
    private double width;
    private double height;
    private double depth;

    private boolean movable;

    public MapObject(int id, double x, double y, double z, double width, double height, double depth, boolean movable) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.movable = movable;
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getDepth() {
        return depth;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    public boolean isMovable() {
        return movable;
    }

    @Override
    public String toString() {
        return id +
                " " + x +
                " " + y +
                " " + z +
                " " + width +
                " " + height +
                " " + depth +
                " " + movable;
    }
}
