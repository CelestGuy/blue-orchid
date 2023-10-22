//
// Created by Théo Duval on 22/10/2023.
//

#ifndef BLUE_ORCHID_IOBLUEORCHID_H
#define BLUE_ORCHID_IOBLUEORCHID_H

#include "blueorchid.h"
#include <unordered_map>

using namespace bocd;
using namespace std;

namespace iomap {
    void writeMap(Map map, const char *outputFile);

    Object *readObject(const char *line);

    double *readSpawnPos(const char *line);

    Sector *readSector(const char *sectorString);

    Wall *readWall(const char *wallString);

    Point *readPoint(const char pointString[]);

    Map readMap(const char *fileName);

    pair<int, int> *readWallSectorAdjacency(const char *listString);
};


#endif //BLUE_ORCHID_IOBLUEORCHID_H
