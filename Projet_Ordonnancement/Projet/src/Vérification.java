import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Vérification {

    /**
     * @param valeurArc Liste de durée
     *
     * @apiNote
     * Il suffit de parcourir la liste de durée pour voir s'il y a la valeur négative
     * */
    public static void vérifierArcNégative(List<Integer> valeurArc){


        for(int i = 0; i < valeurArc.size(); i++){

            if(valeurArc.get(i) < 0){

                System.out.println("Résultat: La graphe contient l'arc à valeur négative");
                return;
            }
        }
        System.out.println("Résultat: La graphe ne contient pas d'arc à valeur négative");
    }

    /**
     * @param predecesseurs Liste de prédécesseurs
     * @param matrice La double matrice qu'on crée selon le tableau donné
     * @param sommet Liste des états
     *
     * @apiNote
     * Nous avons utilisé la matrice d'adjacence pour la détection de circuit,
     * un graphe est sans circuit si la matrice d'adjacence M de sa fermeture
     * transitive ne possède aucun 1 sur la diagonale
     * */
    public static boolean vérificationCircuit(int[][] matrice, List<Integer> sommet, List<ArrayList<Integer>> predecesseurs){

        //l'entité pour compter le nombre des états parcourus
        int index = 0;

        //Condition d'arrêt: lorsque le nombre des états parcourus est supérieur au nombre des états
        while(index < matrice.length){

            for(int i = 0; i < matrice.length; i++){

                //Si l'état présent a les prédécesseurs
                if(matrice[i][index] == 1){
                    //Nous allons ensuite chercher les succèsseur de cet état
                    for(int k = 0; k < matrice[1].length; k++){
                        //Si l'état présent a égalment les succèsseurs
                        if(matrice[index][k] == 1){

                            //Nous allons rajouter un arc entre ses prédécesseurs et ses succèsseurs
                            matrice[i][k] = 1;
                        }
                    }
                }
            }

            index++;
        }

        AfficherGraphe.affhicher(sommet,predecesseurs,matrice);
        System.out.println("");

        //Parcourir la diagonale de la matrice
        for(int i = 0; i < matrice.length; i++){

            //la matrice d'adjacence M de sa fermeture transitive possède un 1 sur la diagonale, il y a le circuit
            if(matrice[i][i] == 1){
                int nombre = "Donc la graphe contient le circuit".length()+10;
                System.out.println("Résultat: la matrice d'adjacence M de sa fermeture transitive possède 1 sur la diagonale");
                String.format("%"+nombre+"s","donc la graphe contient le circuit");

                return true;
            }
        }
        //Sinon, il en y a pas
        System.out.println("Résultat: la matrice d'adjacence M de sa fermeture transitive ne possède aucun 1 sur la diagonale");
        int nombre = "Donc la graphe ne contient pas de circuit".length()+10;
        System.out.println(String.format("%"+nombre+"s","donc la graphe ne contient pas de circuit"));
        return false;

    }
}
