package code.libMap;

import java.util.Arrays;
import java.util.Random;

public class Map {

    private Terrain[][] map;
    private Terrain blankTerrain;
    final int ENTRY_POINT = 10;
    public static int mapHeight;
    public static int mapWidth;

    public Map(int height, int width) {
        mapHeight = height;
        mapWidth = width;
        generateEmptyMap();
        placeRandomTerrainOnMap();
    }

    public void generateEmptyMap() {
        blankTerrain = new Terrain();
        blankTerrain.generateEmptyTerrain();
        map = new Terrain[mapHeight][mapWidth];
        Arrays.stream(map).forEach(cell -> Arrays.fill(cell, blankTerrain));
    }

    public void placeRandomTerrainOnMap() {
        Random random = new Random();
        map[random.nextInt(mapHeight)][random.nextInt(mapWidth)] = new Terrain();
    }

    public String drawMap() {
        String rand = "";

        // Nummer der Spalte markieren
        for (int r = 0; r < map[0].length; r++) {
            rand += "      " + r + "     ";
        }
        // der Rand oberhalb jedem Terrain Feld zeichnen
        rand += "\n" + "+" + "-----------+".repeat(mapWidth);

        // Der Rand rechts und links von jedem Terrain Feld zeichnen
        for (int i = 0; i < mapHeight; i++) {
            rand += "\n";
            for (int n = 0; n < Terrain.SIZE_TERRAIN; n++) {
                rand += "| ";
                for (int j = 0; j < mapWidth; j++) {
                    rand += map[i][j].getRow(n) + " | ";
                }
                // Nummer der Zeile markieren
                if (n == 2) {
                    rand += " " + i;
                }
                rand += "\n";
            }
            // der Rand unterhalb jedem Terrain Feld zeichnen
            rand += "+-----------".repeat(mapWidth) + "+";
        }
        return rand;
    }

    // Überprüfen ob das angegebene Feld leer ist.
    public boolean isEmpty(int Xpos, int Ypos) {
        return map[Xpos][Ypos].equals(blankTerrain);
    }

    /*
     * für angrenzende leere Felder: +2 Punkte
     * 
     * für jedes angrenzende Feld, wo die aneinanderliegenden Kanten dasselbe
     * Füllzeichen: +5 Punkte
     * 
     * für jede Kante mit unterschiedlichen Füllzeichen: -2
     */
    public int getPoints(Terrain neighbor, String neigborDirection, String chosenDirection) {
        return neighbor.equals(blankTerrain) ? 2 : (neigborDirection.equals(chosenDirection) ? 5 : -2);
    }

    public int pointsEachRound(Terrain chosen, int Xpos, int Ypos) {
        int points = ENTRY_POINT; // Punkte für jedes Feld
        map[Xpos][Ypos] = chosen;
        if (Ypos + 1 < map[0].length) {
            points += getPoints(map[Xpos][Ypos + 1], map[Xpos][Ypos + 1].getWest(), chosen.getEast());
        }
        if (Ypos - 1 >= 0) {
            points += getPoints(map[Xpos][Ypos - 1], map[Xpos][Ypos - 1].getEast(), chosen.getWest());
        }
        if (Xpos + 1 < map.length) {
            points += getPoints(map[Xpos + 1][Ypos], map[Xpos + 1][Ypos].getNorth(), chosen.getSouth());
        }
        if (Xpos - 1 >= 0) {
            points += getPoints(map[Xpos - 1][Ypos], map[Xpos - 1][Ypos].getSouth(), chosen.getNorth());
        }
        return points;
    }

    public boolean mapIsFull() {
        return Arrays.stream(map).noneMatch(row -> Arrays.asList(row).contains(blankTerrain));
    }
}