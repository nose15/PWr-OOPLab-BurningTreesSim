package org.pwr;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        HashMap<Double, ArrayList<Double>> simulationData = runSimulations();
        HashMap<Double, Double> processedData = processData(simulationData);
        saveDataToFile(processedData, "data/data.txt");
    }

    private static HashMap<Double, ArrayList<Double>> runSimulations() {
        HashMap<Double, ArrayList<Double>> data = new HashMap<>();

        for (int forestation = 5; forestation <= 100; forestation += 5) {
            ArrayList<Double> burntTreesPercentages = new ArrayList<>();

            double simForestation = (double) forestation / 100.0;

            for (int i = 0; i < 10; i++) {
                BurningForestSimulation burningForestSimulation = new BurningForestSimulation(100, simForestation);
                double burntTreesPercentage = burningForestSimulation.runSim();
                burntTreesPercentages.add(burntTreesPercentage);
            }

            data.put(simForestation, burntTreesPercentages);
        }

        return data;
    }

    private static HashMap<Double, Double> processData(HashMap<Double, ArrayList<Double>> unprocessedData) {
        HashMap<Double, Double> processedData = new HashMap<>();

        for (double key : unprocessedData.keySet()) {
            processedData.put(key, avg(unprocessedData.get(key)));
        }
        return processedData;
    }

    private static double avg(ArrayList<Double> numbers) {
        double sum = 0.0;
        for (double num : numbers) {
            sum += num;
        }

        return sum / numbers.size();
    }

    private static void saveDataToFile(HashMap<Double, Double> processedData, String filePath) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write("forestation;burntTrees\n");
            for (Map.Entry<Double, Double> entry : processedData.entrySet()) {
                fileWriter.write(entry.getKey() + ";" + entry.getValue().toString().replace('.', ',') + "\n");
            }

            fileWriter.close();

        } catch (IOException e) {
            System.out.println("Błąd przy wpisywaniu do pliku: " + e.getMessage());
        }
    }
}