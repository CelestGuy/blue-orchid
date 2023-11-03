package dev.theoduval.orchid.io;

import dev.theoduval.orchid.Map;
import dev.theoduval.orchid.data.MapObject;
import dev.theoduval.orchid.data.Point;
import dev.theoduval.orchid.data.Sector;
import dev.theoduval.orchid.data.Wall;
import dev.theoduval.orchid.data.Metadata;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Scanner;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;

public class MapIO {
    public static void writeMap(Map map, File outputFile) {
        if (map != null && outputFile != null) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

                writer.append("[POINTS]\n");
                for (Point point : map.getPoints()) {
                    writer.append(point.toString()).append("\n");
                }

                writer.append("\n[WALLS]\n");
                for (Wall wall : map.getWalls()) {
                    writer.append(wall.toString()).append("\n");
                }

                writer.append("\n[SECTORS]\n");
                for (Sector sector : map.getSectors()) {
                    writer.append(sector.toString()).append("\n");
                }

                writer.append("\n[SECTORS]\n");
                for (Sector sector : map.getSectors()) {
                    writer.append(sector.toString()).append("\n");
                }

                writer.append("\n[MAP_OBJECT]\n");
                for (MapObject mapObject : map.getMapObjects()) {
                    writer.append(mapObject.toString()).append("\n");
                }

                writer.append("\n[ADJACENCY_LIST]\n");
                int[] adjacencyList = map.getAdjacencyList();
                for (int wallId : adjacencyList) {
                    int sectorId = adjacencyList[wallId];
                    writer.append(valueOf(wallId)).append(";").append(valueOf(sectorId)).append("\n");
                }

                writer.append("\n");
                writer.append("\n[DATA]\n");
                Metadata[] data = map.getMetadata();

                for (Metadata d : data) {
                    if (d.value() != null)
                        writer.append(d.key())
                                .append(": ")
                                .append(d.value().toString())
                                .append('\n');
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @NotNull
    public static Map readMap(File file) throws FileNotFoundException {
        String mode = "";

        if (file.exists() && file.isFile()) {
            Scanner scanner = new Scanner(file);
            MapRepository map = new MapRepository();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("[") && line.contains("]")) {
                    mode = line.substring(0, line.lastIndexOf(']')).replace("[", "").replace("]", "").toLowerCase();
                } else {
                    switch (mode) {
                        case "sectors" -> {
                            Sector s = readSector(line);
                            if (s != null) {
                                map.addSector(s);
                            }
                        }
                        case "walls" -> {
                            Wall w = readWall(line);
                            if (w != null) {
                                map.addWall(w);
                            }
                        }
                        case "points" -> {
                            Point p = readPoint(line);
                            if (p != null) {
                                map.addPoint(p);
                            }
                        }
                        case "map_object" -> {
                            MapObject mp = readMapObject(line);
                            if (mp != null) {
                                map.addMapObject(mp);
                            }
                        }
                        case "adjacency_list" -> {
                            Peer peer = readPeer(line);
                            if (peer != null) {
                                map.addPeer(peer.a, peer.b);
                            }
                        }
                        case "data" -> {
                            String[] dataLine = line.split(":");
                            if (dataLine.length >= 2) {
                                String dataName = dataLine[0];
                                String data = line.substring(dataName.length() + 1).trim();

                                map.addMetadata(new Metadata(dataName, data));
                            }
                        }
                    }
                }
            }

            return map.getMap();
        }

        throw new FileNotFoundException();
    }

    private static MapObject readMapObject(String line) {
        String[] parts = line.split(" ");
        if (parts.length == 11) {
            int id = parseInt(parts[0]);
            int x = parseInt(parts[1]);
            int y = parseInt(parts[2]);
            int z = parseInt(parts[3]);
            int orientationX = parseInt(parts[4]);
            int orientationY = parseInt(parts[5]);
            int orientationZ = parseInt(parts[6]);
            int width = parseInt(parts[7]);
            int height = parseInt(parts[8]);
            int depth = parseInt(parts[9]);
            boolean movable = parseBoolean(parts[10]);

            return new MapObject(id, x, y, z, orientationX, orientationY, orientationZ, width, height, depth, movable);
        }
        return null;
    }

    private static Peer readPeer(String listString) {
        String[] parts = listString.split(";");

        if (parts.length == 2) {
            return new Peer(
                    parseInt(parts[0]),
                    parseInt(parts[1])
            );
        }

        return null;
    }

    private static Sector readSector(String sectorString) {
        String[] parts = sectorString.split(" ");
        if (parts.length == 3) {
            int id = parseInt(parts[0]);
            int ceilHeight = parseInt(parts[1]);
            int floorHeight = parseInt(parts[2]);

            return new Sector(id, ceilHeight, floorHeight);
        }
        return null;
    }

    private static Wall readWall(String wallString) {
        String[] parts = wallString.split(" ");

        if (parts.length == 3) {
            int id = parseInt(parts[0]);
            int aId = parseInt(parts[1]);
            int bId = parseInt(parts[2]);

            return new Wall(id, aId, bId);
        } else if (parts.length == 4) {
            int id = parseInt(parts[0]);
            int aId = parseInt(parts[1]);
            int bId = parseInt(parts[2]);
            int linkedPortalId = parseInt(parts[3]);

            return new Wall(id, aId, bId, linkedPortalId);
        }
        return null;
    }

    private static Point readPoint(String pointString) {
        String[] parts = pointString.split(" ");
        if (parts.length == 3) {
            int id = parseInt(parts[0]);
            int x = parseInt(parts[1]);
            int y = parseInt(parts[2]);

            return new Point(id, x, y);
        }
        return null;
    }

    private record Peer(int a, int b) {
    }
}
