package CalculRayonRadars;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class CrabeTracker2 {
    private static final int GRID_SIZE = 1000;
    private static final int CELL_SIZE = 5;
    private static List<CrabePosition> positions = new ArrayList<>();
    
    // Classe pour stocker la position et l'orientation d'un crabe
    private static class CrabePosition {
        Point position;
        int orientation; // 0: droite, 1: bas, 2: gauche, 3: haut
        
        CrabePosition(Point position, int orientation) {
            this.position = position;
            this.orientation = orientation;
        }
    }
    
    public static void main(String[] args) {
        lireDeplacements("mouvements.txt");
        SwingUtilities.invokeLater(() -> creerInterface());
    }
    
    private static void lireDeplacements(String fichier) {
        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                if (!ligne.trim().isEmpty()) {
                    CrabePosition position = calculerPosition(ligne);
                    positions.add(position);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static CrabePosition calculerPosition(String mouvements) {
        int x = GRID_SIZE / 2;
        int y = GRID_SIZE / 2;
        int orientation = 0; // Commence orienté vers la droite
        
        for (char mouvement : mouvements.toCharArray()) {
            switch (mouvement) {
                case 'D':
                    orientation = (orientation + 1) % 4; // Tourne à droite
                    break;
                case 'G':
                    orientation = (orientation + 3) % 4; // Tourne à gauche
                    break;
                case 'H': // Avancer
                    switch (orientation) {
                        case 0: x++; break;   // Droite
                        case 1: y++; break;   // Bas
                        case 2: x--; break;   // Gauche
                        case 3: y--; break;   // Haut
                    }
                    break;
                case 'B': // Reculer
                    switch (orientation) {
                        case 0: x--; break;   // Droite -> recule à gauche
                        case 1: y--; break;   // Bas -> recule en haut
                        case 2: x++; break;   // Gauche -> recule à droite
                        case 3: y++; break;   // Haut -> recule en bas
                    }
                    break;
            }
        }
        
        return new CrabePosition(new Point(x, y), orientation);
    }
    
    private static void creerInterface() {
        JFrame frame = new JFrame("Positions des Crabes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dessinerGrille(g);
                dessinerCrabes(g);
            }
        };
        
        int panelSize = GRID_SIZE * CELL_SIZE;
        panel.setPreferredSize(new Dimension(panelSize, panelSize));
        
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(800, 600));
        
        frame.add(scrollPane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private static void dessinerGrille(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        
        for (int i = 0; i <= GRID_SIZE; i++) {
            int pos = i * CELL_SIZE;
            if (i % 100 == 0) {
                g.setColor(Color.GRAY);
            } else {
                g.setColor(Color.LIGHT_GRAY);
            }
            g.drawLine(0, pos, GRID_SIZE * CELL_SIZE, pos);
            g.drawLine(pos, 0, pos, GRID_SIZE * CELL_SIZE);
        }
        
        // Point de départ
        g.setColor(Color.RED);
        int centre = (GRID_SIZE / 2) * CELL_SIZE;
        g.fillOval(centre - 2, centre - 2, 4, 4);
    }
    
    private static void dessinerCrabes(Graphics g) {
        // Map pour compter les crabes par position
        Map<Point, List<Integer>> positionsEtOrientations = new HashMap<>();
        
        // Regrouper les crabes par position avec leurs orientations
        for (CrabePosition cp : positions) {
            positionsEtOrientations
                .computeIfAbsent(cp.position, k -> new ArrayList<>())
                .add(cp.orientation);
        }
        
        // Dessiner chaque position
        for (Map.Entry<Point, List<Integer>> entry : positionsEtOrientations.entrySet()) {
            Point p = entry.getKey();
            List<Integer> orientations = entry.getValue();
            int count = orientations.size();
            
            // Position en pixels
            int x = p.x * CELL_SIZE;
            int y = p.y * CELL_SIZE;
            
            // Couleur basée sur le nombre de crabes
            int intensity = Math.min(255, count * 10);
            g.setColor(new Color(0, 0, intensity));
            g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
            
            // Si peu de crabes à cette position, dessiner des flèches d'orientation
            if (count <= 5) {
                g.setColor(Color.RED);
                for (int orientation : orientations) {
                    dessinerFleche(g, x + CELL_SIZE/2, y + CELL_SIZE/2, orientation);
                }
            }
        }
    }
    
    private static void dessinerFleche(Graphics g, int x, int y, int orientation) {
        int size = CELL_SIZE - 2;
        int[] xPoints = new int[3];
        int[] yPoints = new int[3];
        
        switch (orientation) {
            case 0: // Droite
                xPoints = new int[]{x, x+size, x};
                yPoints = new int[]{y-size/2, y, y+size/2};
                break;
            case 1: // Bas
                xPoints = new int[]{x-size/2, x, x+size/2};
                yPoints = new int[]{y, y+size, y};
                break;
            case 2: // Gauche
                xPoints = new int[]{x, x-size, x};
                yPoints = new int[]{y-size/2, y, y+size/2};
                break;
            case 3: // Haut
                xPoints = new int[]{x-size/2, x, x+size/2};
                yPoints = new int[]{y, y-size, y};
                break;
        }
        
        g.fillPolygon(xPoints, yPoints, 3);
    }
}