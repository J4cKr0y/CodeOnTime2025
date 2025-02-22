package CalculRayonRadars;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class OptimisationMoteur {
    private static final int WINDOW_SIZE = 5;
    private static final int MAX_CHANGES = 2;
    private static final int REGIMES = 10;
    private static final int INSTANTS = 250;
    
    static class State {
        int position;
        int lastRegime;
        List<Integer> lastChoices;
        long score;
        List<Integer> path;
        
        public State(int position, int lastRegime, List<Integer> lastChoices, long score, List<Integer> path) {
            this.position = position;
            this.lastRegime = lastRegime;
            this.lastChoices = new ArrayList<>(lastChoices);
            this.score = score;
            this.path = new ArrayList<>(path);
        }
    }
    
    public static List<Integer> findOptimalPath(int[][] efficacites) {
        PriorityQueue<State> pq = new PriorityQueue<>((a, b) -> Long.compare(b.score, a.score));
        
        // État initial pour chaque régime possible au premier instant
        for (int regime = 0; regime < REGIMES; regime++) {
            List<Integer> initialChoices = new ArrayList<>();
            initialChoices.add(regime);
            List<Integer> initialPath = new ArrayList<>();
            initialPath.add(regime);
            pq.offer(new State(0, regime, initialChoices, efficacites[regime][0], initialPath));
        }
        
        State bestFinalState = null;
        
        while (!pq.isEmpty()) {
            State current = pq.poll();
            
            if (current.position == INSTANTS - 1) {
                bestFinalState = current;
                break;
            }
            
            for (int nextRegime = 0; nextRegime < REGIMES; nextRegime++) {
                List<Integer> newChoices = new ArrayList<>(current.lastChoices);
                newChoices.add(nextRegime);
                if (newChoices.size() > WINDOW_SIZE) {
                    newChoices.remove(0);
                }
                
                // Vérifier la contrainte des changements
                if (isValidTransition(newChoices)) {
                    List<Integer> newPath = new ArrayList<>(current.path);
                    newPath.add(nextRegime);
                    
                    State newState = new State(
                        current.position + 1,
                        nextRegime,
                        newChoices,
                        current.score + efficacites[nextRegime][current.position + 1],
                        newPath
                    );
                    
                    pq.offer(newState);
                }
            }
        }
        
        return bestFinalState != null ? bestFinalState.path : new ArrayList<>();
    }
    
    private static boolean isValidTransition(List<Integer> choices) {
        if (choices.size() <= WINDOW_SIZE) {
            int changes = countChanges(choices);
            return changes <= MAX_CHANGES;
        }
        return false;
    }
    
    private static int countChanges(List<Integer> choices) {
        int changes = 0;
        for (int i = 1; i < choices.size(); i++) {
            if (!choices.get(i).equals(choices.get(i-1))) {
                changes++;
            }
        }
        return changes;
    }
    
    public static int[][] readEfficacites(String filename) throws IOException {
        int[][] efficacites = new int[REGIMES][INSTANTS];
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            for (int i = 0; i < REGIMES; i++) {
                String[] values = br.readLine().trim().split("\\s+");
                for (int j = 0; j < INSTANTS; j++) {
                    efficacites[i][j] = Integer.parseInt(values[j]);
                }
            }
        }
        return efficacites;
    }
    
    public static void main(String[] args) {
        try {
            int[][] efficacites = readEfficacites("regimes.txt");
            List<Integer> optimalPath = findOptimalPath(efficacites);
            
            // Afficher le résultat
            System.out.println(String.join(" ", 
                optimalPath.stream()
                    .map(String::valueOf)
                    .toArray(String[]::new)));
            
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier: " + e.getMessage());
        }
    }
}