//
// Created by Théo Duval on 21/10/2023.
//

#include "blueorchid.h"

bocd::Point::Point(int id, double x, double y) : id(id), x(x), y(y) {
}

[[maybe_unused]] int bocd::Point::getId() const {
    return this->id;
}

double bocd::Point::getX() const {
    return x;
}

void bocd::Point::setX(double newX) {
    Point::x = newX;
}

double bocd::Point::getY() const {
    return y;
}

void bocd::Point::setY(double newY) {
    Point::y = newY;
}

bool bocd::Point::operator==(const bocd::Point &rhs) const {
    return id == rhs.id &&
           x == rhs.x &&
           y == rhs.y;
}

bool bocd::Point::operator!=(const bocd::Point &rhs) const {
    return !(rhs == *this);
}

std::ostream &bocd::operator<<(std::ostream &os, const bocd::Point &point) {
    os << "id: " << point.id << " x: " << point.x << " y: " << point.y;
    return os;
}

bocd::Object::Object(const int id, double x, double y, double z, double width, double height, double depth,
                     bool movable) : id(id), x(x), y(y), z(z), width(width), height(height), depth(depth),
                                     movable(movable) {}

int bocd::Object::getId() const {
    return id;
}

double bocd::Object::getX() const {
    return x;
}

void bocd::Object::setX(double d) {
    Object::x = d;
}

double bocd::Object::getY() const {
    return y;
}

void bocd::Object::setY(double d) {
    Object::y = d;
}

double bocd::Object::getZ() const {
    return z;
}

void bocd::Object::setZ(double d) {
    Object::z = d;
}

double bocd::Object::getWidth() const {
    return width;
}

void bocd::Object::setWidth(double d) {
    Object::width = d;
}

double bocd::Object::getHeight() const {
    return height;
}

void bocd::Object::setHeight(double d) {
    Object::height = d;
}

double bocd::Object::getDepth() const {
    return depth;
}

void bocd::Object::setDepth(double d) {
    Object::depth = d;
}

bool bocd::Object::isMovable() const {
    return movable;
}

void bocd::Object::setMovable(bool val) {
    Object::movable = val;
}

bool bocd::Object::operator==(const bocd::Object &rhs) const {
    return id == rhs.id &&
           x == rhs.x &&
           y == rhs.y &&
           z == rhs.z &&
           width == rhs.width &&
           height == rhs.height &&
           depth == rhs.depth &&
           movable == rhs.movable;
}

bool bocd::Object::operator!=(const bocd::Object &rhs) const {
    return !(rhs == *this);
}

bocd::Sector::Sector(const int id, double ceilHeight, double floorHeight) : id(id), ceilHeight(ceilHeight),
                                                                            floorHeight(floorHeight) {}

int bocd::Sector::getId2() const {
    return id;
}

double bocd::Sector::getCeilHeight() const {
    return ceilHeight;
}

void bocd::Sector::setCeilHeight(double height) {
    Sector::ceilHeight = height;
}

double bocd::Sector::getFloorHeight() const {
    return floorHeight;
}

void bocd::Sector::setFloorHeight(double height) {
    Sector::floorHeight = height;
}

bool bocd::Sector::operator==(const bocd::Sector &rhs) const {
    return id == rhs.id &&
           ceilHeight == rhs.ceilHeight &&
           floorHeight == rhs.floorHeight;
}

bool bocd::Sector::operator!=(const bocd::Sector &rhs) const {
    return !(rhs == *this);
}

bocd::Wall::Wall(const int id, int aPointId, int bPointId)
        : id(id), aPointId(aPointId), bPointId(bPointId), portal(false), linkedPortalId(-1) {}

bocd::Wall::Wall(const int id, int aPointId, int bPointId, int linkedPortalId)
        : id(id), aPointId(aPointId), bPointId(bPointId), portal(true), linkedPortalId(linkedPortalId) {}

int bocd::Wall::getId() const {
    return id;
}

int bocd::Wall::getAPointId() const {
    return aPointId;
}

void bocd::Wall::setAPointId(int val) {
    this->aPointId = val;
}

int bocd::Wall::getBPointId() const {
    return bPointId;
}

void bocd::Wall::setBPointId(int val) {
    this->bPointId = val;
}

bool bocd::Wall::isPortal() {
    return portal;
}

int bocd::Wall::getLinkedPortalId() {
    return linkedPortalId;
}

bocd::Map::Map() : spawnX(.0), spawnY(.0) {
    this->adjacencyList = unordered_map<int, int>();
    this->objects = unordered_map<int, Object>();
    this->points = unordered_map<int, Point>();
    this->walls = unordered_map<int, Wall>();
    this->sectors = unordered_map<int, Sector>();
}

bocd::Sector bocd::Map::getSector(int sectorId) {
    return sectors.at(sectorId);
}

bocd::Wall bocd::Map::getWall(int wallId) {
    return walls.at(wallId);
}

bocd::Point bocd::Map::getPoint(int pointId) {
    return points.at(pointId);
}

bocd::Object bocd::Map::getObject(int mapObjectId) {
    return objects.at(mapObjectId);
}

bocd::Sector bocd::Map::getWallSector(int wallId) {
    int sectorId = adjacencyList.at(wallId);
    return sectors.at(sectorId);
}

unordered_map<int, bocd::Wall> bocd::Map::getWallsInSector(int sectorId) {
    unordered_map umap = unordered_map<int, Wall>();

    unordered_map<int, Wall>::iterator itr;
    for (itr = walls.begin(); itr != walls.end(); itr++) {
        int value = adjacencyList.at(itr->first);
        if (value == sectorId) {
            Wall wall = itr->second;
            int id = wall.getId();
            umap.insert(make_pair(id, wall));
        }
    }

    return umap;
}

void bocd::Map::addWallInSector(int wallId, int sectorId) {
    adjacencyList.insert(make_pair(wallId, sectorId));
}

void bocd::Map::removeWallFromSector(int wallId) {
    adjacencyList.erase(wallId);
}


double bocd::Map::getSpawnX() const {
    return spawnX;
}


void bocd::Map::setSpawnX(double x) {
    this->spawnX = x;
}


double bocd::Map::getSpawnY() const {
    return spawnY;
}


void bocd::Map::setSpawnY(double y) {
    this->spawnY = y;
}


unordered_map<int, bocd::Sector> bocd::Map::getSectors() {
    return this->sectors;
}


unordered_map<int, bocd::Object> bocd::Map::getObjects() {
    return this->objects;
}


unordered_map<int, bocd::Point> bocd::Map::getPoints() {
    return this->points;
}


unordered_map<int, bocd::Wall> bocd::Map::getWalls() {
    return this->walls;
}


unordered_map<int, int> bocd::Map::getAdjacencyList() {
    return this->adjacencyList;
}


void bocd::Map::addSector(Sector sector) {
    sectors.insert(make_pair(sector.getId2(), sector));
}


void bocd::Map::removeSector(Sector sector) {
    sectors.erase(sector.getId2());
}


void bocd::Map::addObject(Object mapObject) {
    objects.insert(make_pair(mapObject.getId(), mapObject));
}


void bocd::Map::removeObject(Object mapObject) {
    objects.erase(mapObject.getId());
}


void bocd::Map::addWall(Wall wall) {
    walls.insert(make_pair(wall.getId(), wall));
}


void bocd::Map::removeWall(Wall wall) {
    walls.erase(wall.getId());
}


void bocd::Map::addPoint(Point point) {
    points.insert(make_pair(point.getId(), point));
}


void bocd::Map::removePoint(Point point) {
    points.erase(point.getId());
}
