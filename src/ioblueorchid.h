//
// Created by theom on 22/10/2023.
//

#ifndef BLUE_ORCHID_IOBLUEORCHID_H
#define BLUE_ORCHID_IOBLUEORCHID_H

#include "blueorchid.h"
#include <unordered_map>

using namespace bocd;
using namespace std;

namespace iomap {
    void writeMap(Map map, const char *&outputFile);

    Object *readObject(char* line);

    double * readSpawnPos(char *line);

    unordered_map<int, int> *readWallSectorAdjacencyList(string listString);

    Sector *readSector(char *sectorString);

    Wall *readWall(char *wallString);

    Point *readPoint(char pointString[]);

    Map readMap(const char *&fileName);
};


#endif //BLUE_ORCHID_IOBLUEORCHID_H
