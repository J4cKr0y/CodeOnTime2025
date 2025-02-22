package CalculRayonRadars;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CSVAnalyzerReleves {
    public static void main(String[] args) {
        String filePath = "fichier_releves.csv";
        analyzeCSV(filePath);
    }

    public static void analyzeCSV(String filePath) {
        Map<String, Double> idToAverage = new HashMap<>();
        String line;
        String highestId = null;
        double highestAverage = Double.MIN_VALUE;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip header line
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                if (values.length >= 5) {
                    String id = values[0];
                    double taux1 = Double.parseDouble(values[2]);
                    double taux2 = Double.parseDouble(values[3]);
                    double taux3 = Double.parseDouble(values[4]);
                    
                    double average = (taux1 + taux2 + taux3) / 3.0;
                    idToAverage.put(id, average);

                    if (average > highestAverage) {
                        highestAverage = average;
                        highestId = id;
                    }
                }
            }

            if (highestId != null) {
                System.out.println("ID avec la moyenne la plus élevée : " + highestId);
                System.out.println("Moyenne : " + String.format("%.2f", highestAverage));
            } else {
                System.out.println("Aucune donnée trouvée dans le fichier.");
            }

        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Erreur de format de nombre : " + e.getMessage());
        }
    }
}