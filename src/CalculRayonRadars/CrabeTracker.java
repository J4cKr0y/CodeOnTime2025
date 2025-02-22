package CalculRayonRadars;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class CrabeTracker {
    private static final int GRID_SIZE = 1000; // Taille de la grille
    private static final int CELL_SIZE = 5;    // Taille d'une cellule en pixels
    private static List<Point> positions = new ArrayList<>();
    
    public static void main(String[] args) {
        // Lecture du fichier et calcul des positions
        lireDeplacements("mouvements.txt");
        
        // Création et affichage de l'interface graphique
        SwingUtilities.invokeLater(() -> creerInterface());
    }
    
    private static void lireDeplacements(String fichier) {
        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                if (!ligne.trim().isEmpty()) {
                    Point position = calculerPosition(ligne);
                    positions.add(position);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static Point calculerPosition(String mouvements) {
        int x = GRID_SIZE / 2; // Position initiale au centre
        int y = GRID_SIZE / 2;
        
        for (char mouvement : mouvements.toCharArray()) {
            switch (mouvement) {
                case 'D': x++; break;
                case 'G': x--; break;
                case 'H': y--; break;
                case 'B': y++; break;
            }
        }
        
        return new Point(x, y);
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
        
        // Calcul de la taille de la fenêtre
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
        
        // Dessin des lignes horizontales et verticales
        for (int i = 0; i <= GRID_SIZE; i++) {
            int pos = i * CELL_SIZE;
            if (i % 100 == 0) { // Lignes principales tous les 100 points
                g.setColor(Color.GRAY);
            } else {
                g.setColor(Color.LIGHT_GRAY);
            }
            g.drawLine(0, pos, GRID_SIZE * CELL_SIZE, pos);
            g.drawLine(pos, 0, pos, GRID_SIZE * CELL_SIZE);
        }
        
        // Marquer le centre
        g.setColor(Color.RED);
        int centre = (GRID_SIZE / 2) * CELL_SIZE;
        g.fillOval(centre - 2, centre - 2, 4, 4);
    }
    
    private static void dessinerCrabes(Graphics g) {
        g.setColor(Color.BLUE);
        
        // Création d'une map pour compter le nombre de crabes par position
        Map<Point, Integer> densite = new HashMap<>();
        for (Point p : positions) {
            densite.merge(p, 1, Integer::sum);
        }
        
        // Dessin des positions avec une couleur basée sur la densité
        for (Map.Entry<Point, Integer> entry : densite.entrySet()) {
            Point p = entry.getKey();
            int count = entry.getValue();
            
            // Calcul de l'intensité de la couleur basée sur le nombre de crabes
            int intensity = Math.min(255, count * 10);
            g.setColor(new Color(0, 0, intensity));
            
            int x = p.x * CELL_SIZE;
            int y = p.y * CELL_SIZE;
            g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
        }
    }
}