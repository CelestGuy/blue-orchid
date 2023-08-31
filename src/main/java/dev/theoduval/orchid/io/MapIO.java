package dev.theoduval.orchid.io;

import dev.theoduval.orchid.Map;
import dev.theoduval.orchid.data.*;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.parseDouble;
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
                HashMap<Integer, Integer> adjacencyList = map.getAdjacencyList();
                for (Integer wallId : adjacencyList.keySet()) {
                    int sectorId = adjacencyList.get(wallId);
                    writer.append(valueOf(wallId)).append(";").append(valueOf(sectorId)).append(" ");
                }

                writer.append("\n");
                writer.append("\n[SPAWN]\n");
                writer.append(valueOf(map.getSpawnX())).append(" ").append(valueOf(map.getSpawnY()));

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
            Map map = new Map();

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
                            HashMap<Integer, Integer> adjacencyList = readWallSectorAdjacencyList(line);
                            if (adjacencyList != null) {
                                for (Integer wallId : adjacencyList.keySet()) {
                                    int sectorId = adjacencyList.get(wallId);

                                    map.addWallInSector(wallId, sectorId);
                                }
                            }
                        }
                        case "spawn" -> {
                            double[] pos = readSpawnPos(line);
                            if (pos != null && pos.length == 2) {
                                map.setSpawnX(pos[0]);
                                map.setSpawnY(pos[1]);
                            }
                        }
                    }
                }
            }

            return map;
        }

        throw new FileNotFoundException();
    }

    private static MapObject readMapObject(String line) {
        String[] parts = line.split(" ");
        if (parts.length == 8) {
            int id = parseInt(parts[0]);
            double x = parseDouble(parts[1]);
            double y = parseDouble(parts[2]);
            double z = parseDouble(parts[3]);
            double width = parseDouble(parts[4]);
            double height = parseDouble(parts[5]);
            double depth = parseDouble(parts[6]);
            boolean movable = parseBoolean(parts[7]);

            return new MapObject(id, x, y, z, width, height, depth, movable);
        }
        return null;
    }

    private static double[] readSpawnPos(String line) {
        String[] parts = line.split(" ");

        if (parts.length == 2) {
            return new double[]{
                    Double.parseDouble(parts[0]),
                    Double.parseDouble(parts[1])
            };
        }

        return null;
    }

    public static HashMap<Integer, Integer> readWallSectorAdjacencyList(String listString) {
        String[] parts = listString.split(" ");

        if (parts.length >= 1) {
            HashMap<Integer, Integer> adjacencyList = new HashMap<>();
            for (String part : parts) {
                String[] list = part.split(";");
                if (list.length == 2) {
                    adjacencyList.put(Integer.parseInt(list[0]), Integer.parseInt(list[1]));
                }
            }
            return adjacencyList;
        }

        return null;
    }

    public static Sector readSector(String sectorString) {
        String[] parts = sectorString.split(" ");
        if (parts.length == 3) {
            int id = parseInt(parts[0]);
            double ceilHeight = parseDouble(parts[1]);
            double floorHeight = parseDouble(parts[2]);

            return new Sector(id, ceilHeight, floorHeight);
        }
        return null;
    }

    public static Wall readWall(String wallString) {
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

            return new Portal(id, aId, bId, linkedPortalId);
        }
        return null;
    }

    public static Point readPoint(String pointString) {
        String[] parts = pointString.split(" ");
        if (parts.length == 3) {
            int id = parseInt(parts[0]);
            double x = parseDouble(parts[1]);
            double y = parseDouble(parts[2]);

            return new Point(id, x, y);
        }
        return null;
    }
}
