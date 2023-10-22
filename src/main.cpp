#include <iostream>
#include "blueorchid.h"
#include "ioblueorchid.h"

using namespace bocd;
using namespace iomap;
using namespace std;

int main() {
    Map map = readMap((const char *) "./test.bomap");
    cout << "Hello, World!" << endl;
    return 0;
}
