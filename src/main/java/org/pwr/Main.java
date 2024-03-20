package org.pwr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<Float, ArrayList<Double>> simulationData = runSimulations();
        HashMap<Float, Double> processedData = processData(simulationData);
        // TODO: Save to txt
    }



    private static HashMap<Float, ArrayList<Double>> runSimulations() {
        HashMap<Float, ArrayList<Double>> data = new HashMap<>();

        for (int forestation = 5; forestation <= 100; forestation += 5) {
            ArrayList<Double> burntTreesPercentages = new ArrayList<>();

            float simForestation = (float) forestation / 100.0f;

            for (int i = 0; i < 10; i++) {
                BurningForestSimulation burningForestSimulation = new BurningForestSimulation(100, simForestation);
                double burntTreesPercentage = burningForestSimulation.runSim();
                burntTreesPercentages.add(burntTreesPercentage);
            }

            data.put(simForestation, burntTreesPercentages);
        }

        return data;
    }

    private static HashMap<Float, Double> processData(HashMap<Float, ArrayList<Double>> unprocessedData) {
        HashMap<Float, Double> processedData = new HashMap<>();

        for (float key : unprocessedData.keySet()) {
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
}