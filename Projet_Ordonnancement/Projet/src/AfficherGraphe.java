import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class AfficherGraphe {

    public static void affhicher(List<Integer> sommet, List<ArrayList<Integer>> predecesseurs, int[][] matrice){

        int index = 0;
        int count = sommet.size() >= 10? (sommet.size()+1)%10 : 0;
        for(int i = 0; i < sommet.size()*2+count+4+3; i++){
            System.out.print("-");
        }
        System.out.println("");

        //Imprimer les sommets sur la première ligne
        System.out.print("|  | ");
        sommet.forEach(x -> {
            System.out.print(x);
            System.out.print(" ");
        });

        System.out.print(" |");
        //La ligne de séparation
        System.out.println("");
        for(int i = 0; i < sommet.size()*2+count+4+3; i++){
            System.out.print("-");
        }

        //La matrix à afficher
        System.out.println("");
        int[][] res = matrice;

        for (int[] ele: res) {

            System.out.print("|");
            if(sommet.get(index) < 10){

                System.out.print(" ");
            }
            System.out.print(sommet.get(index++));
            System.out.print("| ");
            for (int i = 0; i < ele.length; i++) {

                if(i >= 9){

                    System.out.print(" ");
                }
                System.out.print(ele[i]);
                System.out.print(" ");

            }
            System.out.print(" |");
            System.out.println("");
        }

        for(int i = 0; i < sommet.size()*2+count+4+3; i++){
            System.out.print("-");
        }
    }

    public static void afficherGraphe(List<Integer> sommet, List<ArrayList<Integer>> predecesseurs, List<Integer> duree){

        int count = 0;
        int nombre_arc = 0;
        int[] mark = new int[sommet.size()+1];
        Arrays.fill(mark,0);

        System.out.println("* Création du graphe d’ordonnancement :");

        while(count < sommet.size()){

            if(predecesseurs.get(count).size() == 0){

                System.out.println("0 -> " + sommet.get(count) + " = 0");
                nombre_arc++;
                count++;
                mark[0] = 1;
            }else{

                for(int i = 0; i< predecesseurs.get(count).size(); i++){

                    System.out.println(predecesseurs.get(count).get(i) + " -> " +  sommet.get(count) + " = " + duree.get(predecesseurs.get(count).get(i)-1));
                    mark[predecesseurs.get(count).get(i)] = 1;
                    nombre_arc++;
                }

                count++;
            }
        }

        for(int k = 0; k < mark.length; k++){

            if(mark[k] == 0){

                System.out.println(k + " -> " + (sommet.size()+1) + " = " + duree.get(k-1));
                nombre_arc++;
            }
        }

        System.out.println("Total: ["+(sommet.size()+2) + " sommets et " +nombre_arc+ " arcs]");
    }

    public static void afficherOrdonnancement(int[][] ranges, int[] plus_tot, int[] plus_tard, ArrayList<Integer> etat_critique, ArrayList<ArrayList<Integer>> chemins){

        etat_critique.sort(Comparator.naturalOrder());

        System.out.print(String.format("%-29s","Ranges:"));
        for (int[] range : ranges) {
            System.out.print(String.format("%3d",range[0]));
        }
        System.out.println("");
        System.out.print(String.format("%-29s","Etats:"));
        for (int[] range : ranges) {
            System.out.print(String.format("%3d",range[1]));
        }
        System.out.println("");
        System.out.print(String.format("%-29s","Les dates les plus tôts:"));
        for (int val : plus_tot) {
            System.out.print(String.format("%3d",val));
        }
        System.out.println("");
        System.out.print(String.format("%-29s","Les dates les plus tard:"));
        for (int val : plus_tard) {
            System.out.print(String.format("%3d",val));
        }
        System.out.println("");
        System.out.print(String.format("%-29s","Marge totale:"));
        for(int i = 0; i < plus_tot.length;i++){
            System.out.print(String.format("%3d",plus_tard[i]-plus_tot[i]));
        }
        System.out.println("");
        System.out.print(String.format("%-29s","Les états du chemin critique:"));
        for (Integer val : etat_critique) {
            System.out.print(String.format("%3d",val));
        }
        System.out.println("");
        for(int k = 1; k <= chemins.size();k++){
            System.out.print(String.format("%-29s","Chemin "+ k + ":"));
            System.out.print("  ");
            chemins.get(k-1).sort(Comparator.naturalOrder());
            System.out.println(chemins.get(k-1));
        }

    }
}
