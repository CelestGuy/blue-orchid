//
// Created by Théo Duval on 22/10/2023.
//

#include "ioblueorchid.h"
#include "blueorchid.h"
#include <iostream>

using namespace bocd;
using namespace iomap;

void iomap::writeMap(Map map, const char *outputFile) {
    FILE *file = fopen(outputFile, "rw");

    fprintf(file, "[POINTS]\n");
    auto pmap = map.getPoints();
    unordered_map<int, Point>::iterator pitr;
    for (pitr = pmap.begin(); pitr != pmap.end(); pitr++) {
        Point point = pitr->second;
        fprintf(file, "%d %f %f\n", point.getId(), point.getX(), point.getY());
    }

    fprintf(file, "\n[WALLS]\n");
    auto wmap = map.getWalls();
    unordered_map<int, Wall>::iterator witr;
    for (witr = wmap.begin(); witr != wmap.end(); witr++) {
        Wall wall = witr->second;
        fprintf(file, "%d %d %d", wall.getId(), wall.getAPointId(), wall.getBPointId());

        if (wall.isPortal()) {
            fprintf(file, " %d", wall.getLinkedPortalId());
        }

        fprintf(file, "\n");
    }

    fprintf(file, "\n[SECTORS]\n");
    auto smap = map.getSectors();
    unordered_map<int, Sector>::iterator sitr;
    for (sitr = smap.begin(); sitr != smap.end(); sitr++) {
        Sector sector = sitr->second;
        fprintf(file, "%d %f %f\n", sector.getId2(), sector.getCeilHeight(), sector.getFloorHeight());
    }

    fprintf(file, "\n[MAP_OBJECT]\n");
    auto momap = map.getObjects();
    unordered_map<int, Object>::iterator moitr;
    for (moitr = momap.begin(); moitr != momap.end(); moitr++) {
        Object object = moitr->second;
        fprintf(file, "%d \n",
                object.getId());
    }

    fprintf(file, "\n[ADJACENCY_LIST]\n");
    auto adjacencyList = map.getAdjacencyList();
    unordered_map<int, int>::iterator alitr;
    for (alitr = adjacencyList.begin(); alitr != adjacencyList.end(); alitr++) {
        fprintf(file, "%d %d\n", alitr->first, alitr->second);
    }

    fprintf(file, "\n\n[SPAWN]\n");
    fprintf(file, "%f %f", map.getSpawnX(), map.getSpawnY());

    fclose(file);
}

Object *iomap::readObject(const char *line) {
    int id;
    double x, y, z, width, height, depth;
    bool movable;

    int t = sscanf(line, "%d %lf %lf %lf %lf %lf %lf %c", &id, &x, &y, &z, &width, &height, &depth, &movable);

    if (t == 8) {
        return new Object(id, x, y, z, width, height, depth, movable);
    } else {
        return nullptr;
    }
}

double *iomap::readSpawnPos(const char *line) {
    double x, y;
    int t = sscanf(line, "%lf %lf", &x, &y);

    if (t == 2) {
        auto *e = (double *) malloc(sizeof(double) * 2);
        e[0] = x;
        e[1] = y;

        return e;
    } else {
        return nullptr;
    }
}

pair<int, int> *iomap::readWallSectorAdjacency(const char *listString) {
    int wallId = -1;
    int sectorId = -1;
    if (sscanf(listString, "%d %d", &wallId, &sectorId) == 2) {
        auto *p = new pair<int, int>();
        p->first = wallId;
        p->second = sectorId;
        return p;
    }

    return nullptr;
}

Sector *iomap::readSector(const char *sectorString) {
    int id;
    double ceilHeight, floorHeight;

    int t = sscanf(sectorString, "%d %lf %lf", &id, &ceilHeight, &floorHeight);

    if (t == 3) {
        return new Sector(id, ceilHeight, floorHeight);
    }

    return nullptr;
}

Wall *iomap::readWall(const char *wallString) {
    int id, aId, bId;
    int linkedPortalId = -1;

    int t = sscanf(wallString, "%d %d %d %d", &id, &aId, &bId, &linkedPortalId);

    if (t == 4) {
        return new Wall(id, aId, bId, linkedPortalId);
    } else if (t == 3) {
        return new Wall(id, aId, bId);
    }

    return nullptr;
}

Point *iomap::readPoint(const char *pointString) {
    int id;
    double x, y;

    int t = sscanf(pointString, "%d %lf %lf", &id, &x, &y);

    if (t == 3) {
        return new Point(id, x, y);
    }

    return nullptr;
}

Map iomap::readMap(const char *fileName) {
    FILE *file = fopen(fileName, "r");
    string mode;

    Map *map = new Map();

    int c;
    do {
        int pos = 0;
        char buffer[16];
        // gets line
        do {
            c = fgetc(file);
            if (c != EOF && c != '\n')
                buffer[pos++] = (char) c;
        } while (c != EOF && c != '\n' && pos < 15);

        buffer[pos] = '\0';
        char *line = const_cast<char *>(buffer);
        string strline = (string) line;

        if (strline.find('[') == 0 && strline.find(']') != string::npos) {
            mode = strline.substr(1, strline.find(']') - 1);
        } else {
            if (!*line || *line == '#') {
                continue;
            } else if (mode == "SECTORS") {
                Sector *s = readSector(line);
                if (s != nullptr) {
                    map->addSector(*s);
                }
            } else if (mode == "WALLS") {
                Wall *w = readWall(line);
                if (w != nullptr) {
                    map->addWall(*w);
                }
            } else if (mode == "POINTS") {
                Point *p = readPoint(line);
                if (p != nullptr) {
                    map->addPoint(*p);
                }
            } else if (mode == "MAP_OBJECT") {
                Object *o = readObject(line);
                if (o != nullptr) {
                    map->addObject(*o);
                }
            } else if (mode == "ADJACENCY_LIST") {
                pair<int, int> *adjacency = readWallSectorAdjacency(line);
                if (adjacency != nullptr) {
                    map->addWallInSector(adjacency->first, adjacency->second);
                }
            } else if (mode == "SPAWN") {
                double *p = readSpawnPos(line);
                if (p != nullptr) {
                    map->setSpawnX(p[0]);
                    map->setSpawnY(p[1]);
                }
            }
        }
    } while (c != EOF);

    return *map;
}