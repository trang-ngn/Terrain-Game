package code.libMap;

import java.util.Arrays;
import java.util.Random;

public class Terrain {
    private String[] symbol = { "\033[0;31m" + "o" + "\033[0m",
            "\033[0;32m" + "-" + "\033[0m",
            "\033[0;33m" + "*" + "\033[0m" };
    public final static int SIZE_TERRAIN = 5;
    String[] type = new String[4];
    private String[][] terrain = new String[][] {
            { "\\", "0", "0", "0", "/" },
            { "3", "\\", "0", "/", "1" },
            { "3", "3", "x", "1", "1" },
            { "3", "/", "2", "\\", "1" },
            { "/", "2", "2", "2", "\\" } };

    public Terrain() {
        generateTerrain(); // ausgefüllte Terrain
    }

    public void createRandomType(String[] type) {
        Random random = new Random();
        for (int i = 0; i < type.length; i++) {
            type[i] = symbol[random.nextInt(3)];
        }
    }

    public void fillTerrainWithRandomType(String[] type) {
        for (int i = 0; i < SIZE_TERRAIN; i++) {
            for (int j = 0; j < SIZE_TERRAIN; j++) {
                switch (terrain[i][j]) {
                    case "0":
                        terrain[i][j] = type[0];
                        break;
                    case "1":
                        terrain[i][j] = type[1];
                        break;
                    case "2":
                        terrain[i][j] = type[2];
                        break;
                    case "3":
                        terrain[i][j] = type[3];
                        break;
                }
            }
        }
    }

    public void deleteBoundary(String[] type) {
        if (type[0] == type[1]) {
            terrain[0][4] = terrain[1][3] = type[0]; // "/" oben rechts
        }
        if (type[1] == type[2]) {
            terrain[4][4] = terrain[3][3] = type[1]; // "\" unten rechts
        }
        if (type[2] == type[3]) {
            terrain[3][1] = terrain[4][0] = type[2]; // "/" unten links
        }
        if (type[3] == type[0]) {
            terrain[0][0] = terrain[1][1] = type[3];// "\" oben links
        }
    }

    public void generateTerrain() {
        createRandomType(type);
        fillTerrainWithRandomType(type);
        deleteBoundary(type);
    }

    public void generateEmptyTerrain() {
        Arrays.stream(terrain).forEach(a -> Arrays.fill(a, " "));
    }

    public void printTerrain() {
        for (String[] row : terrain) {
            for (String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    public String getNorth() {
        return type[0];
    }

    public String getEast() {
        return type[1];
    }

    public String getSouth() {
        return type[2];
    }

    public String getWest() {
        return type[3];
    }

    // eine der fünf auszugebenen Zeilen von Terrain wird als String zurückgibt.
    public String getRow(int n) {
        return String.join(" ", terrain[n]);
    }

    // Die 4 Zeichen (Informationen) von Terrain werden als String zurückgibt
    public String toString() {
        return getEast() + getNorth() + getSouth() + getWest();
    }
}
