//
// Created by Théo Duval on 21/10/2023.
//

#ifndef BLUE_ORCHID_BLUEORCHID_H
#define BLUE_ORCHID_BLUEORCHID_H

#pragma once

#include <ostream>
#include <unordered_map>
#include <set>

using namespace std;

namespace bocd {
    class Point {
    private:
        const int id;
        double x;
        double y;
    public:
        explicit Point(int id, double x, double y);

        [[maybe_unused]] [[nodiscard]] int getId() const;

        [[nodiscard]] double getX() const;

        void setX(double newX);

        [[nodiscard]] double getY() const;

        void setY(double newY);

        bool operator==(const Point &rhs) const;

        bool operator!=(const Point &rhs) const;

        friend std::ostream &operator<<(std::ostream &os, const Point &point);
    };

    class Object {
    private:
        const int id;
        double x;
        double y;
        double z;
        double width;
        double height;
        double depth;
        bool movable;
    public:
        Object(int id, double x, double y, double z, double width, double height, double depth, bool movable);

        [[nodiscard]] int getId() const;

        [[nodiscard]] double getX() const;

        void setX(double d);

        [[nodiscard]] double getY() const;

        void setY(double d);

        [[nodiscard]] double getZ() const;

        void setZ(double d);

        [[nodiscard]] double getWidth() const;

        void setWidth(double d);

        [[nodiscard]] double getHeight() const;

        void setHeight(double d);

        [[nodiscard]] double getDepth() const;

        void setDepth(double d);

        [[nodiscard]] bool isMovable() const;

        void setMovable(bool val);

        bool operator==(const Object &rhs) const;

        bool operator!=(const Object &rhs) const;
    };

    class Sector {
    private:
        const int id;
        double ceilHeight;
        double floorHeight;
    public:
        Sector(int id, double ceilHeight, double floorHeight);

        [[nodiscard]] int getId2() const;

        [[nodiscard]] double getCeilHeight() const;

        void setCeilHeight(double height);

        [[nodiscard]] double getFloorHeight() const;

        void setFloorHeight(double height);

        bool operator==(const Sector &rhs) const;

        bool operator!=(const Sector &rhs) const;
    };

    class Wall {
    private:
        const int id;
        int aPointId;
        int bPointId;

        const bool portal;
        const int linkedPortalId;
    public:
        Wall(int id, int aPointId, int bPointId);

        Wall(int id, int aPointId, int bPointId, int linkedPortalId);

        [[nodiscard]] int getId() const;

        [[nodiscard]] int getAPointId() const;

        void setAPointId(int val);

        [[nodiscard]] int getBPointId() const;

        void setBPointId(int val);

        [[nodiscard]] bool isPortal() const;

        [[nodiscard]] int getLinkedPortalId() const;
    };

/**
 * A Map contains all the information that a ray-caster needs.
 * There a list for each :
 * <ul>
 *     <li>Points of the map, that are used to connect walls</li>
 *     <li>Walls</li>
 *     <li>Sectors</li>
 * </ul>
 */
    class Map {
    private:
        std::unordered_map<int, int> adjacencyList;
        std::unordered_map<int, Point> points;
        std::unordered_map<int, Wall> walls;
        std::unordered_map<int, Object> objects;
        std::unordered_map<int, Sector> sectors;
        double spawnX;
        double spawnY;
    public:
        Map();

        Sector getSector(int sectorId);

        Wall getWall(int wallId);

        Point getPoint(int pointId);

        Object getObject(int mapObjectId);

        Sector getWallSector(int wallId);

        std::unordered_map<int, Wall> getWallsInSector(int sectorId);

        void addWallInSector(int wallId, int sectorId);

        void removeWallFromSector(int wallId);

        double getSpawnX() const;

        void setSpawnX(double x);

        double getSpawnY() const;

        void setSpawnY(double y);

        std::unordered_map<int, Sector> getSectors();

        std::unordered_map<int, Object> getObjects();

        std::unordered_map<int, Point> getPoints();

        std::unordered_map<int, Wall> getWalls();

        std::unordered_map<int, int> getAdjacencyList();

        void addSector(Sector sector);

        void removeSector(Sector sector);

        void addObject(Object mapObject);

        void removeObject(Object mapObject);

        void addWall(Wall wall);

        void removeWall(Wall wall);

        void addPoint(Point point);

        void removePoint(Point point);
    };
}

#endif //BLUE_ORCHID_BLUEORCHID_H