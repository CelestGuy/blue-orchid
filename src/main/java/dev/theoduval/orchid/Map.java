package dev.theoduval.orchid;

import dev.theoduval.orchid.data.MapObject;
import dev.theoduval.orchid.data.Point;
import dev.theoduval.orchid.data.Sector;
import dev.theoduval.orchid.data.Wall;
import dev.theoduval.orchid.data.Metadata;
import dev.theoduval.orchid.exceptions.IllegalIdException;
import org.jetbrains.annotations.NotNull;

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
    private final int[] adjacencyList;

    private final MapObject[] mapObjects;
    private final Sector[] sectors;
    private final Point[] points;
    private final Wall[] walls;
    private final Metadata[] metadata;

    public Map(
            int[] adjacencyList,
            MapObject[] mapObjects,
            Sector[] sectors,
            Point[] points,
            Wall[] walls,
            Metadata[] metadata
    ) {
        this.adjacencyList = adjacencyList;
        this.mapObjects = mapObjects;
        this.sectors = sectors;
        this.points = points;
        this.walls = walls;
        this.metadata = metadata;

    }

    /**
     * Gets the sector with the given ID.
     *
     * @return Sector
     * @throws IllegalIdException if the ID is negative or if it doesn't exist
     */
    @NotNull
    public Sector getSector(int sectorId) {
        if (sectorId >= 0 && sectorId < sectors.length) {
            return sectors[sectorId];
        } else if (sectorId < 0) {
            throw new IllegalIdException(sectorId, IllegalIdException.negativeID);
        }

        throw new IllegalIdException(sectorId, IllegalIdException.nonExistentID);
    }

    /**
     * Gets the wall with the given ID.
     *
     * @return Wall
     * @throws IllegalIdException if the ID is negative or if it doesn't exist
     */
    @NotNull
    public Wall getWall(int wallId) {
        if (wallId >= 0 && wallId < walls.length) {
            return walls[wallId];
        } else if (wallId < 0) {
            throw new IllegalIdException(wallId, IllegalIdException.nonExistentID);
        }

        throw new IllegalIdException(wallId, IllegalIdException.nonExistentID);
    }

    /**
     * Gets the wall with the given ID.
     *
     * @return Wall
     * @throws IllegalIdException if the ID is negative or if it doesn't exist
     */
    @NotNull
    public Point getPoint(int pointId) {
        if (pointId >= 0 && pointId < points.length) {
            return points[pointId];
        } else if (pointId < 0) {
            throw new IllegalIdException(pointId, IllegalIdException.nonExistentID);
        }

        throw new IllegalIdException(pointId, IllegalIdException.nonExistentID);
    }

    /**
     * Gets the mapObject with the given ID.
     *
     * @return MapObject
     * @throws IllegalIdException if the ID is negative or if it doesn't exist
     */
    @NotNull
    public MapObject getMapObject(int mapObjectId) {
        if (mapObjectId >= 0 && mapObjectId < mapObjects.length) {
            return mapObjects[mapObjectId];
        } else if (mapObjectId < 0) {
            throw new IllegalIdException(mapObjectId, IllegalIdException.nonExistentID);
        }

        throw new IllegalIdException(mapObjectId, IllegalIdException.nonExistentID);
    }

    /**
     *
     * @param wallId The ID of the wall
     * @return the Sector which contains the wall
     *
     * @throws IllegalIdException if the ID doesn't exist
     */
    @NotNull
    public Sector getWallSector(int wallId) {
        if (wallId >= 0 && wallId < adjacencyList.length) {
            int sectorId = adjacencyList[wallId];

            if (sectorId < 0) {
                throw new IllegalIdException(wallId, IllegalIdException.notSetID);
            }

            return getSector(sectorId);
        } else if (wallId < 0) {
            throw new IllegalIdException(wallId, IllegalIdException.nonExistentID);
        }

        throw new IllegalIdException(wallId, IllegalIdException.nonExistentID);
    }

    /**
     * Gets the walls in a given sector
     *
     * @param sectorId the sector ID to check
     * @return The set of Walls in the given sector
     */
    @NotNull
    public Wall[] getWallsInSector(int sectorId) {
        Sector sector = getSector(sectorId);

        HashSet<Wall> walls = new HashSet<>();

        for (int i = 0; i < adjacencyList.length; i++) {
            int value = adjacencyList[i];
            if (value == sector.getId()) {
                walls.add(this.walls[i]);
            }
        }

        return walls.toArray(new Wall[0]);
    }

    @NotNull
    public Sector[] getSectors() {
        return sectors.clone();
    }

    @NotNull
    public MapObject[] getMapObjects() {
        return mapObjects.clone();
    }

    @NotNull
    public Point[] getPoints() {
        return points.clone();
    }

    @NotNull
    public Wall[] getWalls() {
        return walls.clone();
    }

    @NotNull
    public int[] getAdjacencyList() {
        return adjacencyList.clone();
    }

    public Metadata[] getMetadata() {
        return metadata;
    }

    public Metadata getMetadata(String value) {
        for (Metadata data : metadata) {
            if (data.key().equals(value)) {
                return data;
            }
        }

        return null;
    }
}
