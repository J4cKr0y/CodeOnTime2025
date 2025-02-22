package CalculRayonRadars;

import java.util.*;

class Ocean {
    private final int M, N, K;
    private final char[][] carte;
    private final char[][] configuration;
    private int baseCount = 0;
    private final int[][] directions;
    
    public Ocean(int M, int N, int K, char[][] carte) {
        this.M = M;
        this.N = N;
        this.K = K;
        this.carte = carte;
        this.configuration = new char[M][N];
        this.directions = new int[][]{{-1, 0}, {0, -1}, {1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 0}, {0, 1}};
        initConfiguration();
        setupBases();
        simulate();
        printConfiguration();
    }

    private void initConfiguration() {
        for (int i = 0; i < M; i++) {
            Arrays.fill(configuration[i], ' ');
            for (int j = 0; j < N; j++) {
                if (carte[i][j] == '.') configuration[i][j] = '^';  // Par défaut, les bateaux vont vers le nord
                else configuration[i][j] = carte[i][j];
            }
        }
    }

    private void setupBases() {
        for (int i = 0; i < M && baseCount < K; i++) {
            for (int j = 0; j < N && baseCount < K; j++) {
                if (configuration[i][j] == '^') {
                    configuration[i][j] = 'N'; // Exemple : base navale avec direction nord
                    baseCount++;
                }
            }
        }
    }

    private void simulate() {
        int[][] boatPos = new int[K][2];  // Positions des bateaux
        int[] boatDir = new int[K];       // Directions préférées des bateaux
        for (int i = 0; i < K; i++) {
            boatPos[i][0] = i / N;
            boatPos[i][1] = i % N;
            switch (configuration[boatPos[i][0]][boatPos[i][1]]) {
                case 'N' -> boatDir[i] = 0;
                case 'O' -> boatDir[i] = 1;
                case 'S' -> boatDir[i] = 2;
                case 'E' -> boatDir[i] = 3;
            }
        }

        List<int[]> dechetsCollectes = new ArrayList<>();
        for (int day = 0; day < 1000; day++) {
            for (int i = 0; i < K; i++) {
                int x = boatPos[i][0], y = boatPos[i][1];
                if (carte[x][y] == 'X' && Collections.frequency(dechetsCollectes, new int[]{x, y}) < 2) {
                    dechetsCollectes.add(new int[]{x, y});
                } else {
                    boatPos[i][0] = (x + directions[boatDir[i]][0] + M) % M;
                    boatPos[i][1] = (y + directions[boatDir[i]][1] + N) % N;
                }
            }
        }
        System.out.println("Déchets ramassés en 1000 jours : " + dechetsCollectes.size());
    }

    private void printConfiguration() {
        for (char[] row : configuration) {
            System.out.println(row);
        }
    }
}

public class NettoyerOcean {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int M = scanner.nextInt();
        int N = scanner.nextInt();
        int K = scanner.nextInt();
        scanner.nextLine();
        
        char[][] carte = new char[M][N];
        for (int i = 0; i < M; i++) {
            String ligne = scanner.nextLine();
            carte[i] = ligne.toCharArray();
        }

        new Ocean(M, N, K, carte);
    }
}
