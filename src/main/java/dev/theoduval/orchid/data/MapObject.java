package dev.theoduval.orchid.data;

public class MapObject {
    private final int id;
    private final boolean movable;

    private int x;
    private int y;
    private int z;
    private int orientationX;
    private int orientationY;
    private int orientationZ;
    private int width;
    private int height;
    private int depth;

    public MapObject(int id, int x, int y, int z, int orientationX, int orientationY, int orientationZ, int width, int height, int depth, boolean movable) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.orientationX = orientationX;
        this.orientationY = orientationY;
        this.orientationZ = orientationZ;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.movable = movable;
    }

    public int getId() {
        return id;
    }

    public boolean isMovable() {
        return movable;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getOrientationX() {
        return orientationX;
    }

    public void setOrientationX(int orientationX) {
        this.orientationX = orientationX;
    }

    public int getOrientationY() {
        return orientationY;
    }

    public void setOrientationY(int orientationY) {
        this.orientationY = orientationY;
    }

    public int getOrientationZ() {
        return orientationZ;
    }

    public void setOrientationZ(int orientationZ) {
        this.orientationZ = orientationZ;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public String toString() {
        return id +
                " " + x +
                " " + y +
                " " + z +
                " " + orientationX +
                " " + orientationY +
                " " + orientationZ +
                " " + width +
                " " + height +
                " " + depth +
                " " + movable;
    }
}
