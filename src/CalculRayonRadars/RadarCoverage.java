package CalculRayonRadars;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class RadarCoverage {
    // Déclaration correcte de la liste radars comme variable de classe
    private static ArrayList<LongPoint> radars;
    private static final long GRID_SIZE = 2_000_000_000L;
    private static final int DISPLAY_SIZE = 800;
    
    // Classe pour stocker les coordonnées en long
    private static class LongPoint {
        final long x;
        final long y;
        
        LongPoint(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }
    
    public static void main(String[] args) {
        // Initialisation de la liste dans le main
        ArrayList radars = new ArrayList<>();
        lireRadars("cerclesBateau.txt");
        long rayon = calculerRayonMinimal();
        System.out.println("Rayon minimal requis : " + rayon);
        
        SwingUtilities.invokeLater(() -> creerInterface(rayon));
    }
    
    private static void lireRadars(String fichier) {
        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] coords = ligne.trim().split(" ");
                if (coords.length == 2) {
                    long x = Long.parseLong(coords[0]);
                    long y = Long.parseLong(coords[1]);
                    ArrayList radars;
					radars.add(new LongPoint(x, y));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static long calculerRayonMinimal() {
        long gauche = 0;
        long droite = GRID_SIZE;
        long resultat = droite;
        
        while (gauche <= droite) {
            long milieu = gauche + (droite - gauche) / 2;
            
            if (peutCouvrir(milieu)) {
                resultat = milieu;
                droite = milieu - 1;
            } else {
                gauche = milieu + 1;
            }
        }
        
        return resultat;
    }
    
    private static boolean peutCouvrir(long rayon) {
        ArrayList radars;
		// Tri des radars par x croissant
        radars.sort((a, b) -> Long.compare(a.x, b.x));
        
        // Pour chaque y possible, on maintient la couverture maximale en x
        TreeMap<Long, Long> couverture = new TreeMap<>();
        
        // Pour chaque radar
        for (LongPoint radar : radars) {
            long x = radar.x;
            long y = radar.y;
            
            // Calculer la zone de couverture pour chaque y
            for (long dy = -rayon; dy <= rayon; dy++) {
                long ny = y + dy;
                if (ny < 0 || ny >= GRID_SIZE) continue;
                
                // Calculer la largeur de la couverture à cette hauteur
                long dx = (long) Math.sqrt(rayon * rayon - dy * dy);
                long startX = Math.max(0, x - dx);
                long endX = Math.min(GRID_SIZE - 1, x + dx);
                
                // Mettre à jour la couverture
                Long existingEnd = couverture.get(ny);
                if (existingEnd == null || startX <= existingEnd + 1) {
                    couverture.put(ny, Math.max(endX, existingEnd != null ? existingEnd : 0));
                }
            }
        }
        
        // Vérifier s'il existe un chemin de gauche à droite non couvert
        return couverture.values().stream().min(Long::compare).orElse(0L) >= GRID_SIZE - 1;
    }
    
    private static void creerInterface(long rayon) {
        JFrame frame = new JFrame("Couverture des Radars");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dessinerRadars(g, rayon);
            }
        };
        
        panel.setPreferredSize(new Dimension(DISPLAY_SIZE, DISPLAY_SIZE));
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private static void dessinerRadars(Graphics g, long rayon) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Calculer l'échelle
        double echelle = (double) DISPLAY_SIZE / GRID_SIZE;
        
        // Dessiner les radars et leur zone de couverture
        g2.setColor(new Color(0, 0, 255, 30));
        LongPoint[] radars;
		for (LongPoint radar : radars) {
            int x = (int) (radar.x * echelle);
            int y = (int) (radar.y * echelle);
            int rayonAffiche = (int) (rayon * echelle);
            
            g2.fillOval(x - rayonAffiche, y - rayonAffiche, 
                       rayonAffiche * 2, rayonAffiche * 2);
        }
        
        // Dessiner les centres des radars
        g2.setColor(Color.RED);
        for (LongPoint radar : radars) {
            int x = (int) (radar.x * echelle);
            int y = (int) (radar.y * echelle);
            g2.fillOval(x - 2, y - 2, 4, 4);
        }
    }
}