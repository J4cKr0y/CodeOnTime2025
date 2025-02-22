package CalculRayonRadars;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class RadarCalibrationVerticale {
    static class Radar {
        long x;
        long y;

        Radar(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        String filePath = "cerclesBateau.txt";
        List<Radar> radars = lireRadars(filePath);

        if (radars != null && !radars.isEmpty()) {
            System.out.println("Nombre total de radars lus : " + radars.size());
            
            // Grouper les radars par coordonnée X
            Map<Long, List<Long>> radarsParX = new HashMap<>();
            
            for (Radar radar : radars) {
                radarsParX.computeIfAbsent(radar.x, k -> new ArrayList<>()).add(radar.y);
            }
            
            System.out.println("Nombre de coordonnées X différentes : " + radarsParX.size());
            
            // Pour chaque groupe, calculer la distance Y maximale
            long maxDistanceY = 0;
            long xCoordMaxDistance = 0;
            long yMin = 0, yMax = 0;
            int maxRadarsOnSameX = 0;
            
            for (Map.Entry<Long, List<Long>> entry : radarsParX.entrySet()) {
                List<Long> yCoords = entry.getValue();
                maxRadarsOnSameX = Math.max(maxRadarsOnSameX, yCoords.size());
                
                if (yCoords.size() > 1) {
                    long minY = Collections.min(yCoords);
                    long maxY = Collections.max(yCoords);
                    long distance = maxY - minY;
                    
                    if (distance > maxDistanceY) {
                        maxDistanceY = distance;
                        xCoordMaxDistance = entry.getKey();
                        yMin = minY;
                        yMax = maxY;
                        System.out.println("\nNouvelle distance maximale trouvée :");
                        System.out.println("Coordonnée X : " + xCoordMaxDistance);
                        System.out.println("Nombre de radars à cette coordonnée X : " + yCoords.size());
                        System.out.println("Liste des coordonnées Y : " + yCoords);
                    }
                }
            }
            
            long rayon = maxDistanceY / 2;
            
            System.out.println("\nRésultats finaux :");
            System.out.println("Nombre maximum de radars sur une même coordonnée X : " + maxRadarsOnSameX);
            System.out.println("Le plus petit rayon de radar nécessaire est : " + rayon);
            System.out.println("Distance Y maximale trouvée : " + maxDistanceY);
            System.out.println("À la coordonnée X : " + xCoordMaxDistance);
            System.out.println("Entre les points Y : " + yMin + " et " + yMax);
        } else {
            System.out.println("Pas assez de radars pour effectuer le calcul.");
        }
    }

    private static List<Radar> lireRadars(String filePath) {
        List<Radar> radars = new ArrayList<>();
        int lineCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lineCount++;
                String[] coords = line.split(" ");
                if (coords.length == 2) {  // Vérification de la validité de la ligne
                    try {
                        long x = Long.parseLong(coords[0]);
                        long y = Long.parseLong(coords[1]);
                        radars.add(new Radar(x, y));
                    } catch (NumberFormatException e) {
                        System.out.println("Erreur de parsing à la ligne " + lineCount + ": " + line);
                    }
                } else {
                    System.out.println("Format incorrect à la ligne " + lineCount + ": " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return radars;
    }
}


/*
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RadarCalibrationVerticale {
    static class Radar {
        long x;
        long y;

        Radar(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        String filePath = "cerclesBateau.txt";
        List<Radar> radars = lireRadars(filePath);

        if (radars != null && radars.size() > 1) {
            // Ajout de traces de débogage
            System.out.println("Nombre total de radars : " + radars.size());
            
            // Statistiques préliminaires
            long minX = Long.MAX_VALUE, maxX = Long.MIN_VALUE;
            long minY = Long.MAX_VALUE, maxY = Long.MIN_VALUE;
            
            for (Radar radar : radars) {
                minX = Math.min(minX, radar.x);
                maxX = Math.max(maxX, radar.x);
                minY = Math.min(minY, radar.y);
                maxY = Math.max(maxY, radar.y);
            }
            
            System.out.println("Plage des coordonnées :");
            System.out.println("X : [" + minX + ", " + maxX + "]");
            System.out.println("Y : [" + minY + ", " + maxY + "]");

            // Calcul du rayon minimum avec débogage
            long rayonMinimum = calculerRayonMinimum(radars);
            System.out.println("Le plus petit rayon de radar nécessaire est : " + rayonMinimum);
        } else {
            System.out.println("Pas assez de radars pour effectuer le calcul.");
        }
    }

    private static List<Radar> lireRadars(String filePath) {
        List<Radar> radars = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lignesLues = 0;
            
            while ((line = br.readLine()) != null) {
                String[] coords = line.split(" ");
                long x = Long.parseLong(coords[0]);
                long y = Long.parseLong(coords[1]);
                radars.add(new Radar(x, y));
                
                lignesLues++;
                // Log tous les 10 000 radars pour éviter de surcharger la console
                if (lignesLues % 10000 == 0) {
                    System.out.println("Radars lus : " + lignesLues);
                }
            }
            
            System.out.println("Total de radars lus : " + lignesLues);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return radars;
    }

    private static long calculerRayonMinimum(List<Radar> radars) {
        // Trier les radars par coordonnée x pour identifier la progression Ouest-Est
        radars.sort(Comparator.comparingLong(r -> r.x));
        
        long rayonMinimum = 0;
        
        // Considérer les points extrêmes et la distance maximale
      //  long minX = radars.get(0).x;
      //  long maxX = radars.get(radars.size() - 1).x;
        
        // Calculer la distance totale en x
      //  long distanceX = maxX - minX;
        
        // Pour chaque point, calculer le rayon nécessaire
        for (Radar radar : radars) {
            // Distance maximale aux autres points dans la même direction
            long rayonCourant = 0;
            
            for (Radar autreRadar : radars) {
                // Calculer la distance en considérant les variations du courant
                long distanceHorizontale = Math.abs(radar.x - autreRadar.x);
                long distanceVerticale = Math.abs(radar.y - autreRadar.y);
                
                long distanceRadar = (long) Math.ceil(Math.sqrt(
                    distanceHorizontale * distanceHorizontale + 
                    distanceVerticale * distanceVerticale
                ));
                
                rayonCourant = Math.max(rayonCourant, distanceRadar);
            }
            
            // Mettre à jour le rayon minimum global
            if (rayonMinimum == 0 || rayonCourant < rayonMinimum) {
                rayonMinimum = rayonCourant;
            }
        }
        
        return rayonMinimum;
    }
}*/