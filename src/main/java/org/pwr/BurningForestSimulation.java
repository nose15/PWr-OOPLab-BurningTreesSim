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

        burningRows = new HashSet<>();
        for (int i = 0; i < this.size; i++) {
            burningRows.add(i);
        }
    }

    public double runSim() {
        this.initializeMap();
        int initialTrees = this.count("T");
        this.startFire();
        while (this.fireSpread) {
            runEpoque();
        }
        int burntTrees = this.count("B");
        return (double)burntTrees / (double)initialTrees;
    }

    private int count(String obj) {
        int counter = 0;

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (Objects.equals(this.map[i][j], obj))  {
                    counter++;
                }
            }
        }
        return counter;
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
        clearScreen();

        for (int i = 0; i < this.size; i++) {
            StringBuilder row = new StringBuilder();

            for (int j = 0; j < this.size; j++) {
                row.append(map[i][j]);
            }

            System.out.println(row.toString());
        }
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J"); // ANSI escape sequence to clear the screen
        System.out.flush();
    }


    private void runEpoque() {
        this.fireSpread = false;

        ArrayList<Point> burnt = new ArrayList<>();
        Set<Integer> staticRows = new HashSet<>();

        for (int i : burningRows) {
            for (int j = 0; j < this.size; j++) {
                if (Objects.equals(this.map[i][j], "B")) {
                    spreadFire(burnt, i, j);
                }
            }

            if (!this.fireSpread) {
                staticRows.add(i);
            }
        }

        for (Point point : burnt) {
            this.map[point.x][point.y] = "B";
            staticRows.remove(point.x);
            burningRows.add(point.x);
        }

        for (int staticRow : staticRows) {
            burningRows.remove(staticRow);
        }
    }

    private void spreadFire(ArrayList<Point> burntTiles, int x, int y) {

        for (int i = x - 1; i <= x + 1; i++) {
            if (i >= 0 && i < this.size) {
                for (int j = y - 1; j <= y + 1; j++) {
                    if (j >= 0 && j < this.size) {
                        if (Objects.equals(this.map[i][j], "T")) {
                            burntTiles.add(new Point(i, j));
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

