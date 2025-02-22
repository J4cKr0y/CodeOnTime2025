package CalculRayonRadars;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Planches {

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("planches.txt"));
            List<Long> planksList = new ArrayList<>();
            while (scanner.hasNextLong()) {
                planksList.add(scanner.nextLong());
            }
            scanner.close();

            long[] planks = new long[planksList.size()];
            for (int i = 0; i < planksList.size(); i++) {
                planks[i] = planksList.get(i);
            }

            System.out.println("Lecture des données terminée. Nombre de planches: " + planks.length);
            for (int i = 0; i < planks.length; i++) {
                System.out.println("Plank " + (i + 1) + ": " + planks[i]);
            }

            long minPlank = getMinPlankSize(planks);
            long maxCutSize = minPlank / 100;

            System.out.println("Taille de la plus petite planche: " + minPlank);
            System.out.println("Taille de coupe maximale possible: " + maxCutSize);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static long getMinPlankSize(long[] planks) {
        long min = Long.MAX_VALUE;
        for (long plank : planks) {
            if (plank < min) {
                min = plank;
            }
        }
        return min;
    }
}



/*package CalculRayonRadars;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Planches {

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("planches.txt"));
            long[] planks = new long[100000];
            int index = 0;
            while (scanner.hasNextLong()) {
                planks[index++] = scanner.nextLong();
            }
            scanner.close();

            System.out.println("Lecture des données terminée. Nombre de planches: " + index);
            for (int i = 0; i < index; i++) {
                System.out.println("Plank " + (i + 1) + ": " + planks[i]);
            }

            long left = 1, right = Long.MAX_VALUE;
            while (left < right) {
                long mid = (left + right + 1) / 2;
                System.out.println("Vérification de la taille de coupe: " + mid);
                if (canCut(planks, mid, 1000000)) {
                    left = mid;
                    System.out.println("Taille de coupe valide: " + mid);
                } else {
                    right = mid - 1;
                    System.out.println("Taille de coupe invalide: " + mid);
                }
            }
            System.out.println("Taille de coupe maximale possible: " + left);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static boolean canCut(long[] planks, long size, int required) {
        long count = 0;
        for (long plank : planks) {
            count += plank / size;
        }
        System.out.println("Nombre de planches obtenues avec la taille " + size + ": " + count);
        return count >= required;
    }
}
*/