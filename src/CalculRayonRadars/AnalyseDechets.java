package CalculRayonRadars;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AnalyseDechets {
    public static void main(String[] args) {
        String filePath = "dechets.txt"; 
        Map<Integer, Integer> dechetCounts = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String ligne;
            if ((ligne = br.readLine()) != null) {
                String[] parts = ligne.split(" ");
                for (String part : parts) {
                    int dechet = Integer.parseInt(part);
                    dechetCounts.put(dechet, dechetCounts.getOrDefault(dechet, 0) + 1);
                }
            }
            
            // Trouver l'objet le plus présent
            int maxCount = 0;
            int mostCommonDechet = -1;
            for (Map.Entry<Integer, Integer> entry : dechetCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostCommonDechet = entry.getKey();
                }
            }
            
            System.out.println("L'objet le plus présent est : " + mostCommonDechet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
