package org.pwr;

import javax.swing.*;
import java.util.*;

public class BurningForestSimulation {
    String[][] map;
    private final float forestiation;
    private final int size;
    private boolean fireSpread;
    private Set<Integer> burningRows;

    BurningForestSimulation(int size, float forestiation) {
        this.size = size;
        this.forestiation = forestiation;
        this.fireSpread = true;
        this.burningRows = new HashSet<>();
        for (int i = 0; i < size; i++) {
            this.burningRows.add(i);
        }
    }

    public void runSim() {
        this.initializeMap();
        this.startFire();
        this.printMap();

        int i = 0;
        while (this.fireSpread) {
            runEpoque();
            System.out.println(i);
            this.printMap();
            i++;
        }

        this.printMap();
    }

    private void initializeMap() {
        this.map = new String[this.size][this.size];

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.map[i][j] = generateTile();
            }
        }
    }

    private void startFire() {
        for (int i = 0; i < this.size; i++) {
            if (Objects.equals(this.map[0][i], "T")) {
                this.map[0][i] = "B";
            }
        }
    }


    private void printMap() {
        for (int i = 0; i < this.size; i++) {
            StringBuilder row = new StringBuilder();

            for (int j = 0; j < this.size; j++) {
                row.append(map[i][j]);
            }

            System.out.println(row.toString());
        }
        System.out.println("\n");
    }


    private void runEpoque() {
        this.fireSpread = false;
        Set<Integer> staticRows = new HashSet<>();
        String[][] tempMap = new String[this.size][this.size];
        System.arraycopy(this.map, 0, tempMap, 0, this.map.length);

        for (int i : burningRows) {
            for (int j = 0; j < this.size; j++) {
                if (Objects.equals(this.map[i][j], "B")) {
                    spreadFire(tempMap, i, j);
                }
            }

            if (!this.fireSpread) {
                staticRows.add(i);
            }
        }

        for (int rowNum : staticRows) {
            this.burningRows.remove(rowNum);
        }

        System.arraycopy(tempMap, 0, this.map, 0, this.map.length);
    }

    private void spreadFire(String[][] tempMap, int x, int y) {

        for (int i = x - 1; i <= x + 1; i++) {
            if (i >= 0 && i < this.size) {
                for (int j = y - 1; j <= y + 1; j++) {
                    if (j >= 0 && j < this.size) {
                        if (Objects.equals(this.map[i][j], "T")) {
                            tempMap[i][j] = "B";
                            this.fireSpread = true;
                        }
                    }
                }
            }
        }
    }


    private String generateTile() {
        if (Math.random() < this.forestiation) {
            return "T";
        }
        return "X";
    }
}

