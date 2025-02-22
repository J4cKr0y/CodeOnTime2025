package CalculRayonRadars;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Navire {
    public String id;
    public int[] characteristics;

    public Navire(String id, int[] characteristics) {
        this.id = id;
        this.characteristics = characteristics;
    }
}

public class TrouverNavire {
    public static void main(String[] args) {
        List<Navire> navires = new ArrayList<>();
        String filePath = "navires.txt"; 
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] parts = ligne.split(" ");
                String id = parts[0];
                int[] characteristics = new int[8];
                for (int i = 1; i < parts.length; i++) {
                    characteristics[i - 1] = Integer.parseInt(parts[i]);
                }
                navires.add(new Navire(id, characteristics));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Navire navire : navires) {
            int[] c = navire.characteristics;
            if (c[0] == 3 && c[1] != c[0] && c[2] == c[4] && c[3] == 2 && c[4] != c[7] && c[5] == c[1] + c[6]) {
                System.out.println("Navire trouvé : " + navire.id);
            }
        }
    }
}
