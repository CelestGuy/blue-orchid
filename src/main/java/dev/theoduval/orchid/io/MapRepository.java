package dev.theoduval.orchid.io;

import dev.theoduval.orchid.Map;
import dev.theoduval.orchid.data.*;
import dev.theoduval.orchid.exceptions.IllegalIdException;

import java.util.Arrays;
import java.util.HashSet;

public class MapRepository {
    private Sector[] sectors;
    private Wall[] walls;
    private Point[] points;
    private MapObject[] mapObjects;

    private HashSet<Metadata> metadata;

    private int[] adjacencyList;

    public MapRepository() {
        adjacencyList = new int[0];
        sectors = new Sector[0];
        walls = new Wall[0];
        points = new Point[0];
        mapObjects = new MapObject[0];
        metadata = new HashSet<>();
    }

    public void addSector(Sector sector) {
        int sectorId = sector.getId();

        if (sectorId >= sectors.length) {
            Sector[] tempArray = new Sector[sectorId + 1];
            System.arraycopy(sectors, 0, tempArray, 0, sectors.length);
            tempArray[sectorId] = sector;
            sectors = tempArray;
        } else if (sectors[sectorId] != null) {
            sectors[sectorId] = sector;
        } else {
            throw new IllegalIdException(sectorId, IllegalIdException.alreadySetID);
        }
    }

    public void removeSector(int sectorId) {
        if (sectorId < sectors.length) {
            sectors[sectorId] = null;
            return;
        }

        throw new IllegalIdException(sectorId, IllegalIdException.nonExistentID);
    }

    public void addWall(Wall wall) {
        int wallId = wall.getId();

        if (wallId >= walls.length) {
            Wall[] tempArray = new Wall[wallId + 1];
            System.arraycopy(walls, 0, tempArray, 0, walls.length);
            tempArray[wallId] = wall;
            walls = tempArray;

            int[] tempArray2 = new int[wallId + 1];
            System.arraycopy(adjacencyList, 0, tempArray2, 0, adjacencyList.length);
            tempArray2[wallId] = -1;
            adjacencyList = tempArray2;
        } else if (walls[wallId] != null) {
            walls[wallId] = wall;
        } else {
            throw new IllegalIdException(wallId, IllegalIdException.alreadySetID);
        }
    }

    public void removeWall(int wallId) {
        if (wallId < walls.length) {
            walls[wallId] = null;
            return;
        }

        throw new IllegalIdException(wallId, IllegalIdException.nonExistentID);
    }

    public void addPoint(Point point) {
        int pointId = point.getId();

        if (pointId >= points.length) {
            Point[] tempArray = new Point[pointId + 1];
            System.arraycopy(points, 0, tempArray, 0, points.length);
            tempArray[pointId] = point;
            points = tempArray;
        } else if (points[pointId] != null) {
            points[pointId] = point;
        } else {
            throw new IllegalIdException(pointId, IllegalIdException.alreadySetID);
        }
    }

    public void removePoint(int pointId) {
        if (pointId < points.length) {
            points[pointId] = null;
            return;
        }

        throw new IllegalIdException(pointId, IllegalIdException.nonExistentID);
    }

    public void addMapObject(MapObject mapObject) {
        int mapObjectId = mapObject.getId();

        if (mapObjectId >= mapObjects.length) {
            MapObject[] tempArray = new MapObject[mapObjectId + 1];
            System.arraycopy(mapObjects, 0, tempArray, 0, mapObjects.length);
            tempArray[mapObjectId] = mapObject;
            mapObjects = tempArray;
        } else if (mapObjects[mapObjectId] != null) {
            mapObjects[mapObjectId] = mapObject;
        } else {
            throw new IllegalIdException(mapObjectId, IllegalIdException.alreadySetID);
        }
    }

    public void removeMapObject(int mapObjectId) {
        if (mapObjectId < mapObjects.length) {
            mapObjects[mapObjectId] = null;
            return;
        }

        throw new IllegalIdException(mapObjectId, IllegalIdException.nonExistentID);
    }

    public void addPeer(int wallId, int sectorId) {
        if (wallId < 0 || wallId >= walls.length) {
            throw new IllegalIdException(wallId, IllegalIdException.nonExistentID);
        }
        if (sectorId < 0 || sectorId >= sectors.length) {
            throw new IllegalIdException(wallId, IllegalIdException.nonExistentID);
        }

        if (adjacencyList[wallId] >= 0) {
            throw new IllegalIdException(wallId, IllegalIdException.alreadySetID);
        }

        adjacencyList[wallId] = sectorId;
    }

    public void removePeer(int wallId) {
        if (wallId < 0 || wallId >= walls.length) {
            throw new IllegalIdException(wallId, IllegalIdException.nonExistentID);
        }

        if (adjacencyList[wallId] < 0) {
            throw new IllegalIdException(wallId, IllegalIdException.nonExistentID);
        }

        adjacencyList[wallId] = -1;
    }

    public void addMetadata(Metadata metadata) {
        for (Metadata m : this.metadata) {
            if (m.key().equals(metadata.key())) {
                throw new IllegalArgumentException("Metadata key already set");
            }
        }

        this.metadata.add(metadata);
    }

    public void removeMetadata(String key) {
        for (Metadata m : this.metadata) {
            if (m.key().equals(key)) {
                this.metadata.remove(m);
                return;
            }
        }

        throw new IllegalArgumentException("Metadata key doesn't exist");
    }

    public Map getMap() {
        return new Map(
            adjacencyList,
                mapObjects,
                sectors,
                points,
                walls,
                metadata.toArray(new Metadata[0])
        );
    }
}
