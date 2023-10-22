//
// Created by theom on 22/10/2023.
//

#include "ioblueorchid.h"
#include "blueorchid.h"
#include <iostream>

using namespace bocd;
using namespace iomap;

void iomap::writeMap(Map map, const char *&outputFile) {
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
        fprintf(file, "%d;%d", alitr->first, alitr->second);
    }

    fprintf(file, "\n\n[SPAWN]\n");
    fprintf(file, "%f %f", map.getSpawnX(), map.getSpawnY());

    fclose(file);
}

Object *iomap::readObject(char* line) {
    int id;
    double x, y, z, width, height, depth;
    bool movable;

    sscanf(line, "%d %lf %lf %lf %lf %lf %lf %c", &id, &x, &y, &z, &width, &height, &depth, &movable);

    return new Object(id, x, y, z, width, height, depth, movable);
}

double * iomap::readSpawnPos(char *line) {
    double x, y;
    sscanf(line, "%lf %lf", &x, &y);
    double e[] {
            x, y
    };

    return e;
}

unordered_map<int, int> *iomap::readWallSectorAdjacencyList(string listString) {
    unordered_map<int, int> *adjacencyList = new unordered_map<int, int>();

    return adjacencyList;
}

Sector *iomap::readSector(char *sectorString) {
    int id;
    double ceilHeight, floorHeight;

    sscanf(sectorString, "%d %lf %lf", &id, &ceilHeight, &floorHeight);

    return new
            Sector(id, ceilHeight, floorHeight
    );
}

Wall *iomap::readWall(char *wallString) {
    int id, aId, bId;
    int linkedPortalId = -1;

    sscanf(wallString, "%d %d %d %d", &id, &aId, &bId, &linkedPortalId);

    if (linkedPortalId >= 0) {
        return new Wall(id, aId, bId, linkedPortalId);
    }

    return new Wall(id, aId, bId);
}

Point *iomap::readPoint(char pointString[]) {
    int id;
    double x, y;

    sscanf(pointString, "%d %lf %lf", &id, &x, &y);

    return new
            Point(id, x, y
    );
}

Map iomap::readMap(const char *&fileName) {
    FILE *file = fopen(fileName, "r");
    string mode;

    Map *map = new Map();

    int c;
    do {
        int pos = 0;
        char buffer[32];
        // gets line
        do {
            c = fgetc(file);
            if (c != EOF && c != '\n')
                buffer[pos++] = (char) c;
        } while (c != EOF && c != '\n' && pos < 31);

        buffer[pos] = '\0';
        char *line = const_cast<char *>(buffer);
        string strline = (string) line;

        if (strline.find('[') == 0 && strline.find(']') != string::npos) {
            mode = strline.substr(1, strline.find(']') - 1);
        } else {
            if (mode == "SECTORS") {
                map->addSector(*readSector(line));
            } else if (mode == "WALLS") {
                map->addWall(*readWall(line));
            } else if (mode == "POINTS") {
                map->addPoint(*readPoint(line));
            } else if (mode == "MAP_OBJECT") {
                map->addObject(*readObject(line));
            } else if (mode == "ADJACENCY_LIST") {
                unordered_map<int, int> *adjacencyList = readWallSectorAdjacencyList(line);
                unordered_map<int, int>::iterator itr;
                for (itr = adjacencyList->begin(); itr != adjacencyList->end(); itr++) {
                    map->addWallInSector(itr->first, itr->second);
                }
            } else if (mode == "spawn") {
                double *p = readSpawnPos(line);
                map->setSpawnX(p[0]);
                map->setSpawnY(p[1]);
            }
        }
    } while (c != EOF);

    return *map;
}