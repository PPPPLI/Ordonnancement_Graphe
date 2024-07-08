import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @matrix La matrice qu'nous allons créer selon le tableau donné
 * @apiNote
 * La double matrice qui a une taille correspondant au nombre des états donnés,
 * A partie du tableau de la graphe, nous allons remplir cette double matrice
 * */
public class MatrixGraphe {

    private int[][] matrix;

    //Initialisation de la double matrice en fonction du nombre des états donnés
    public MatrixGraphe(int nombre){

        this.matrix = new int[nombre][nombre];
    }

    /**
     * @param predecesseurs La liste de prédécesseurs
     * @apiNote
     * Compléter la double matrice avec les états donnés
     */
    public void completerMatrix(List<ArrayList<Integer>> predecesseurs){

        //Parcourir la double matrice en colonne
        for(int i = 0 ; i < matrix.length; i++){
            //Parcours en ligne
            for(int k = 0; k < matrix[0].length; k++){
                /* Si les prédécesseurs correspondant à cet état est nul,
                  nous mettons toute la colonne de cet état à 0 */
                if(predecesseurs.get(k).size() == 0){
                    continue;

                }else{
                    //S'il existe des prédécesseurs à propos de l'état, nous mettons ces prédécesseurs de l'état à 1
                    for (Integer ele: predecesseurs.get(k)) {

                        if(ele == i+1){

                            matrix[i][k] = 1;
                        }
                    }
                }
            }
        }
    }


    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }


}
