package CalculRayonRadars;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class OrdreLectureManuel {
    public static void main(String[] args) {
        String filePath = "references.txt"; // Chemin vers le fichier texte
        Map<Integer, List<Integer>> referencesMap = new HashMap<>();
        Set<Integer> allPages = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] parts = ligne.split(" ");
                int page = Integer.parseInt(parts[0]);
                int link = Integer.parseInt(parts[1]);
                referencesMap
                    .computeIfAbsent(page, k -> new ArrayList<>())
                    .add(link);
                allPages.add(page); // Collecter toutes les pages mentionnées
                allPages.add(link); // Collecter toutes les pages mentionnées
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Integer> ordreLecture = topologicalSort(referencesMap, allPages);

        // Impression de l'ordre de lecture avec des espaces entre les nombres
        for (int i = 0; i < ordreLecture.size(); i++) {
            if (i > 0) {
                System.out.print(" ");
            }
            System.out.print(ordreLecture.get(i));
        }
    }

    private static List<Integer> topologicalSort(Map<Integer, List<Integer>> graph, Set<Integer> allPages) {
        Map<Integer, Integer> indegree = new HashMap<>();
        for (Integer node : allPages) {
            indegree.put(node, 0);
        }
        for (List<Integer> neighbors : graph.values()) {
            for (Integer neighbor : neighbors) {
                indegree.put(neighbor, indegree.get(neighbor) + 1);
            }
        }

        Queue<Integer> zeroIndegreeQueue = new LinkedList<>();
        for (Map.Entry<Integer, Integer> entry : indegree.entrySet()) {
            if (entry.getValue() == 0) {
                zeroIndegreeQueue.add(entry.getKey());
            }
        }

        List<Integer> sortedList = new ArrayList<>();
        while (!zeroIndegreeQueue.isEmpty()) {
            int current = zeroIndegreeQueue.poll();
            sortedList.add(current);
            if (graph.containsKey(current)) {
                for (Integer neighbor : graph.get(current)) {
                    indegree.put(neighbor, indegree.get(neighbor) - 1);
                    if (indegree.get(neighbor) == 0) {
                        zeroIndegreeQueue.add(neighbor);
                    }
                }
            }
        }
        return sortedList;
    }
}




/*        
     // Impression de l'ordre de lecture avec des espaces entre les nombres
        for (int i = 0; i < ordreLecture.size(); i++) {
            if (i > 0) {
                System.out.print(" ");
            }
            System.out.print(ordreLecture.get(i));
        }
    }

 */