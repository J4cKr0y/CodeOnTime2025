package CalculRayonRadars;
import java.util.*;
import java.io.*;

class Position {
    int x, y;
    
    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class MaritimeCleanup {
    public static char[][] generateSolution(int M, int N, int K) {
        char[][] solution = new char[M][N];
        
        // Remplir la première ligne selon le pattern donné
        solution[0] = "S<<<<O<<<<".toCharArray();
        
        // Remplir les lignes du milieu avec le pattern alterné
        for (int i = 1; i < M-2; i++) {
            if (i % 2 == 1) {
                solution[i] = "v>^><v>^><".toCharArray();
            } else {
                solution[i] = "v^v^<v^v^<".toCharArray();
            }
        }
        
        // L'avant-dernière ligne a un pattern spécial
        solution[M-2] = ">^>^<>^>^<".toCharArray();
        
        // La dernière ligne est remplie de flèches vers la gauche
        Arrays.fill(solution[M-1], '<');
        
        return solution;
    }
    
    public static void processInputFile(String inputFilePath, String outputFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             PrintWriter writer = new PrintWriter(new FileWriter(outputFilePath))) {
            
            // Lecture de la première ligne
            String[] firstLine = reader.readLine().split(" ");
            int M = Integer.parseInt(firstLine[0]);
            int N = Integer.parseInt(firstLine[1]);
            int K = Integer.parseInt(firstLine[2]);
            
            // Lecture de la carte (même si on ne l'utilise pas directement pour le pattern)
            for(int i = 0; i < M; i++) {
                reader.readLine();
            }
            
            // Génération de la solution
            char[][] solution = generateSolution(M, N, K);
            
            // Écriture de la solution dans le fichier de sortie
            for(int i = 0; i < M; i++) {
                writer.println(new String(solution[i]));
            }
            
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture/écriture des fichiers: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Erreur de format dans le fichier d'entrée: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        String inputFilePath = "input.txt";
        String outputFilePath = "output.txt";
        
        processInputFile(inputFilePath, outputFilePath);
    }
}