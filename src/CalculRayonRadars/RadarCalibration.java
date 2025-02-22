package CalculRayonRadars;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Radar {
    int x, y;

    Radar(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class RadarCalibration {
    public static void main(String[] args) {
        String filePath = "cerclesBateau.txt";  // Assurez-vous que le fichier est dans le bon chemin
        List<Radar> radars = lireRadars(filePath);

        if (radars != null && radars.size() > 1) {
            double maxDistance = calculerDistanceMaximale(radars);
            double minRadius = maxDistance / 2;
            System.out.println("Le plus petit rayon de radar nécessaire est: " + Math.ceil(minRadius));
        } else {
            System.out.println("Pas assez de radars pour effectuer le calcul.");
        }
    }

    // Lecture des radars à partir d'un fichier
    private static List<Radar> lireRadars(String filePath) {
        List<Radar> radars = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] coords = line.split(" ");
                int x = Integer.parseInt(coords[0]);
                int y = Integer.parseInt(coords[1]);
                radars.add(new Radar(x, y));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return radars;
    }

    // Calcul de la distance euclidienne
    private static double distanceEuclidienne(Radar r1, Radar r2) {
        return Math.sqrt(Math.pow(r2.x - r1.x, 2) + Math.pow(r2.y - r1.y, 2));
    }

    // Calcul de la distance maximale
    private static double calculerDistanceMaximale(List<Radar> radars) {
        double maxDistance = 0;
        int n = radars.size();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double distance = distanceEuclidienne(radars.get(i), radars.get(j));
                if (distance > maxDistance) {
                    maxDistance = distance;
                }
            }
        }
        return maxDistance;
    }
}
