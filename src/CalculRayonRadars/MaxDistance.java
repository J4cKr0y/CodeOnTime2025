package CalculRayonRadars;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Point {
    int x, y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class MaxDistance {
    public static void main(String[] args) {
        String filePath = "cerclesBateau.txt";  
        List<Point> points = lirePoints(filePath);

        if (points != null && points.size() > 1) {
            double maxDistance = calculerDistanceMaximale(points);
            System.out.println("La distance maximale entre deux points est: " + maxDistance);
        } else {
            System.out.println("Pas assez de points pour calculer la distance.");
        }
    }

    // Lecture des points à partir d'un fichier
    public static List<Point> lirePoints(String filePath) {
        List<Point> points = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] coords = line.split(" ");
                int x = Integer.parseInt(coords[0]);
                int y = Integer.parseInt(coords[1]);
                points.add(new Point(x, y));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return points;
    }

    // Calcul de la distance euclidienne
    public static double distanceEuclidienne(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
    }

    // Calcul de la distance maximale
    public static double calculerDistanceMaximale(List<Point> points) {
        double maxDistance = 0;
        int n = points.size();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double distance = distanceEuclidienne(points.get(i), points.get(j));
                if (distance > maxDistance) {
                    maxDistance = distance;
                }
            }
        }
        return maxDistance;
    }
}

