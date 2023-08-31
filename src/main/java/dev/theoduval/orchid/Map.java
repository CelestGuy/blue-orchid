package dev.theoduval.orchid;

import dev.theoduval.orchid.data.MapObject;
import dev.theoduval.orchid.data.Point;
import dev.theoduval.orchid.data.Sector;
import dev.theoduval.orchid.data.Wall;
import dev.theoduval.orchid.exceptions.IDNotSet;
import dev.theoduval.orchid.exceptions.IllegalIdException;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;

/**
 * A Map contains all the information that a ray-caster needs.
 * There a list for each :
 * <ul>
 *     <li>Points of the map, that are used to connect walls</li>
 *     <li>Walls</li>
 *     <li>Sectors</li>
 * </ul>
 */
public final class Map {
    /**
     * This list of the size of the number of walls contains the id of the sector to which the wall is associated
     */
    private final HashMap<Integer, Integer> adjacencyList;

    private final HashMap<Integer, MapObject> mapObjects;
    private final HashMap<Integer, Sector> sectors;
    private final HashMap<Integer, Point> points;
    private final HashMap<Integer, Wall> walls;

    private double spawnX;
    private double spawnY;

    public Map() {
        adjacencyList = new HashMap<>();
        mapObjects = new HashMap<>();
        sectors = new HashMap<>();
        points = new HashMap<>();
        walls = new HashMap<>();
        spawnX = 0D;
        spawnY = 0D;
    }

    /**
     * Gets the sector with the given ID.
     *
     * @return Sector
     * @throws IllegalIdException if the ID is negative or if it doesn't exist
     */
    @NotNull
    public Sector getSector(int sectorId) {
        if (sectors.containsKey(sectorId)) {
            return sectors.get(sectorId);
        } else if (sectorId < 0) {
            throw new IllegalIdException(sectorId, IllegalIdException.negativeID);
        }

        throw new IllegalIdException(sectorId, IllegalIdException.nonExistantID);
    }

    /**
     * Gets the wall with the given ID.
     *
     * @return Wall
     * @throws IllegalIdException if the ID is negative or if it doesn't exist
     */
    @NotNull
    public Wall getWall(int wallId) {
        if (walls.containsKey(wallId)) {
            return walls.get(wallId);
        } else if (wallId < 0) {
            throw new IllegalIdException(wallId, IllegalIdException.nonExistantID);
        }

        throw new IllegalIdException(wallId, IllegalIdException.nonExistantID);
    }

    /**
     * Gets the wall with the given ID.
     *
     * @return Wall
     * @throws IllegalIdException if the ID is negative or if it doesn't exist
     */
    @NotNull
    public Point getPoint(int pointId) {
        if (points.containsKey(pointId)) {
            return points.get(pointId);
        } else if (pointId < 0) {
            throw new IllegalIdException(pointId, IllegalIdException.nonExistantID);
        }

        throw new IllegalIdException(pointId, IllegalIdException.nonExistantID);
    }

    /**
     * Gets the mapObject with the given ID.
     *
     * @return MapObject
     * @throws IllegalIdException if the ID is negative or if it doesn't exist
     */
    @NotNull
    public MapObject getMapObject(int mapObjectId) {
        if (mapObjects.containsKey(mapObjectId)) {
            return mapObjects.get(mapObjectId);
        } else if (mapObjectId < 0) {
            throw new IllegalIdException(mapObjectId, IllegalIdException.nonExistantID);
        }

        throw new IllegalIdException(mapObjectId, IllegalIdException.nonExistantID);
    }

    /**
     *
     * @param wallId The ID of the wall
     * @return the Sector which contains the wall
     *
     * @throws IDNotSet if the Wall isn't in a sector
     * @throws IllegalIdException if the ID doesn't exist
     */
    @NotNull
    public Sector getWallSector(int wallId) throws IDNotSet {
        if (adjacencyList.containsKey(wallId)) {
            int sectorId = adjacencyList.get(wallId);

            if (sectorId < 0) {
                throw new IDNotSet();
            } else if (sectors.containsKey(sectorId)) {
                return sectors.get(sectorId);
            }
        } else if (wallId < 0) {
            throw new IllegalIdException(wallId, IllegalIdException.nonExistantID);
        }

        throw new IllegalIdException(wallId, IllegalIdException.nonExistantID);
    }

    /**
     * Gets the walls in a given sector
     *
     * @param sectorId the sector ID to check
     * @return The set of Walls in the given sector
     * @throws IDNotSet if the sector doesn't have wall in it
     */
    @NotNull
    public HashSet<Wall> getWallsInSector(int sectorId) throws IDNotSet {
        if (sectorId < 0) {
            throw new IllegalIdException(sectorId, IllegalIdException.nonExistantID);
        } else if (!sectors.containsKey(sectorId)) {
            throw new IllegalIdException(sectorId, IllegalIdException.nonExistantID);
        } else if (adjacencyList.containsValue(sectorId)) {
            HashSet<Wall> walls = new HashSet<>();

            for (int key : adjacencyList.keySet()) {
                int value = adjacencyList.get(key);
                if (value == sectorId) {
                    walls.add(this.walls.get(key));
                }
            }

            return walls;
        } else {
            throw new IDNotSet();
        }
    }

    /**
     * Add a wall in a sector
     * @param wallId the wall ID to add
     * @param sectorId the sector ID to add the wall in
     */
    public void addWallInSector(int wallId, int sectorId) {
        adjacencyList.put(wallId, sectorId);
    }

    /**
     * Removes the given wall from his sector.
     * @param wallId
     */
    public void removeWallFromSector(int wallId) {
        adjacencyList.remove(wallId);
    }

    public double getSpawnX() {
        return spawnX;
    }

    public void setSpawnX(double spawnX) {
        this.spawnX = spawnX;
    }

    public double getSpawnY() {
        return spawnY;
    }

    public void setSpawnY(double spawnY) {
        this.spawnY = spawnY;
    }

    @NotNull
    public HashSet<Sector> getSectors() {
        return new HashSet<>(sectors.values());
    }

    @NotNull
    public HashSet<MapObject> getMapObjects() {
        return new HashSet<>(mapObjects.values());
    }

    @NotNull
    public HashSet<Point> getPoints() {
        return new HashSet<>(points.values());
    }

    @NotNull
    public HashSet<Wall> getWalls() {
        return new HashSet<>(walls.values());
    }

    @NotNull
    public HashMap<Integer, Integer> getAdjacencyList() {
        return new HashMap<>(adjacencyList);
    }

    public void addSector(@NotNull Sector sector) {
        sectors.put(sector.getId(), sector);
    }

    public void removeSector(@NotNull Sector sector) {
        sectors.remove(sector.getId());
    }

    public void addMapObject(@NotNull MapObject mapObject) {
        mapObjects.put(mapObject.getId(), mapObject);
    }

    public void removeMapObject(@NotNull MapObject mapObject) {
        mapObjects.remove(mapObject.getId());
    }

    public void addWall(@NotNull Wall wall) {
        walls.put(wall.getId(), wall);
    }

    public void removeWall(@NotNull Wall wall) {
        walls.remove(wall.getId());
    }

    public void addPoint(@NotNull Point point) {
        points.put(point.getId(), point);
    }

    public void removePoint(@NotNull Point point) {
        points.remove(point.getId());
    }
}
