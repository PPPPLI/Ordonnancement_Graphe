import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        int indice = 0;
        Scanner scanner = new Scanner(System.in);
        while(true){

            System.out.println("\n\nTapez 1 - 12 pour choisir un fichier de test, et tapez 0 pour quiter le programme");
            indice = scanner.nextInt();
            if (indice == 0){
                System.out.println("Au revoir");
                return;
            }else if(indice < 0 || indice > 12){

                System.out.println("Valeur insérée incorrecte");
                continue;
            }else{

                ImportationFichier fichier = new ImportationFichier("C:\\Users\\31799\\Desktop\\S5\\Graphe\\Projet_Ordonnancement\\Projet\\Ressources\\table_"+indice+".txt");
                fichier.SeparationInfo();


                MatrixGraphe graphe = new MatrixGraphe(fichier.getSommets().size());
                MatrixGraphe graphe2 = new MatrixGraphe(fichier.getSommets().size());
                MatrixGraphe graphe3 = new MatrixGraphe(fichier.getSommets().size());
                graphe.completerMatrix(fichier.getPredecesseurs());
                graphe2.completerMatrix(fichier.getPredecesseurs());
                graphe3.completerMatrix(fichier.getPredecesseurs());

                AfficherGraphe.afficherGraphe(fichier.getSommets(),fichier.getPredecesseurs(),fichier.getDuree());
                System.out.println("\nMatrice d’adjacence:");
                AfficherGraphe.affhicher(fichier.getSommets(),fichier.getPredecesseurs(),graphe.getMatrix());

                System.out.println("");

                System.out.println("\n* Détéction d'arc à valeur négative :");
                System.out.print("Durée des états: ");
                System.out.println(fichier.getDuree());
                Vérification.vérifierArcNégative(fichier.getDuree());

                System.out.println("\n* Détéction de circuit :");
                boolean resultat = Vérification.vérificationCircuit(graphe.getMatrix(),fichier.getSommets(),fichier.getPredecesseurs());
                if(resultat){
                    continue;
                }
                System.out.println("");
                int[][] res = Ordonnancement.ordreRange(graphe3.getMatrix());

                int[] duree = Ordonnancement.tempPlusTot(graphe2.getMatrix(), fichier.getDuree(), res);

                int[] plusTard = Ordonnancement.tempPlusTard(graphe2.getMatrix(),fichier.getDuree(),res,duree[duree.length-1]);

                ArrayList<Integer> etats_critiques = Ordonnancement.marge(duree,plusTard,res);

                System.out.println(" ");
                ArrayList<ArrayList<Integer>> cheminCritique = new ArrayList<>();
                ArrayList<Integer> chemin = new ArrayList<>();
                HashMap<Integer,Integer> dureeMap = new HashMap<>();
                Ordonnancement.dureeMap(duree,res,dureeMap);
                List<ArrayList<Integer>> precs = fichier.getPredecesseurs();
                precs.add(Ordonnancement.precDernierEtat(graphe2.getMatrix()));
                Ordonnancement.cheminCritique(cheminCritique,etats_critiques,precs,chemin,etats_critiques.size()-1,dureeMap,false);
                AfficherGraphe.afficherOrdonnancement(res,duree,plusTard,etats_critiques, cheminCritique);

            }
        }
    }
}