import java.util.*;

public class Ordonnancement {

    /**
     * @param matrice la double matrice correspondant à la graphe
     * @apiNote
     * 1.La fonction de trouver l'ordre des ranges
     * 2.Nous allons d'abord trouver les états qui n'ont pas de prédécesseur, et les définir commme le range 1. Ensuite nous
     * allons chercher ses succèsseurs, et dans la colonne de chaque de ces succèsseurs, nous remettrons son prédécesseur
     * */
    public static int[][] ordreRange(int[][] matrice){

        int index = 1;
        int count_range = 1;
        //La taille de range est la taille de la matrice + 2(L'état alpha et celui w)
        int[][] ranges = new int[matrice.length+2][2];
        int count = 0;
        //La liste temporaire pour stocker les états qui partienne au 1er range
        ArrayList<Integer> buffer = new ArrayList<>();

        //Parcours en ligne
        for(int i = 0; i < matrice[1].length; i++){
            //Pour le 1er range sans compter l'état alpha
            //Parcours en colonne
            for(int k = 0; k < matrice.length; k++){
                //S'il y a la valeur 1, c'est-à-dire ce sommet a encore un prédécesseur
                if(matrice[k][i] == 1) {
                    break;
                }
                //S'il est arrivé à la fin, nous n'avons pas trouvé un prédécesseur pour ce sommet, nous pouvons l'ajouter dans la liste
                if(k == matrice.length-1 && matrice[k][i] == 0){
                    buffer.add(i);
                }
            }
        }

        int arret;
        //
        Map<Integer,Integer> map = new HashMap();
        Queue<Integer> queue = new LinkedList();

        while(count < matrice.length){

            arret = buffer.size();

            for (int x = 0; x < arret; x++) {

                if(buffer.get(x) == matrice.length){
                    continue;
                }
                for(int i = 0; i < matrice.length; i++) {

                    if (matrice[i][buffer.get(x)] == 1) {
                        break;
                    }
                    if (i == matrice.length-1 && matrice[i][buffer.get(x)] == 0) {

                        ranges[index][0] = count_range;
                        ranges[index++][1] = buffer.get(x)+1;
                        count++;
                        queue.add(x);
                    }
                }
            }

            Integer n;

            while (!queue.isEmpty()){

                n = queue.poll();

                for(int a = 0; a < matrice[1].length; a++){

                    if (matrice[buffer.get(n)][a] == 1) {

                        if(!map.containsKey(a)){
                            map.put(a,0);
                            buffer.add(a);
                        }
                        matrice[buffer.get(n)][a] = 0;
                    }
                }
                buffer.set(n,matrice.length);
            }
            count_range++;
        }
        ranges[ranges.length-1][0] = count_range;
        ranges[ranges.length-1][1] = matrice.length+1;
        return ranges;
    }

    public static int[] tempPlusTot(int[][] matrice, List<Integer> duree, int[][] ranges){

        int[] list_temp_plut_tot = new int[matrice.length+2];
        int max_sommet_w = 0;

        for(int i = 1; i < ranges.length; i++){

            if(ranges[i][0] == 1){

                list_temp_plut_tot[ranges[i][1]] = 0;
                continue;
            }

            if(i == ranges.length-1){

                list_temp_plut_tot[(list_temp_plut_tot.length)-1] = max_sommet_w;
                break;
            }

            for(int k = 0; k < matrice.length; k++){

                if(matrice[k][ranges[i][1]-1] == 1){

                    if(list_temp_plut_tot[ranges[i][1]] < list_temp_plut_tot[k+1] + duree.get(k)){

                        list_temp_plut_tot[ranges[i][1]] = list_temp_plut_tot[k+1] + duree.get(k);
                    }
                }
            }

            if(ranges[i][0] == ranges[(ranges.length)-1][0]-1){

                if(max_sommet_w < list_temp_plut_tot[ranges[i][1]] + duree.get(ranges[i][1]-1)){

                    max_sommet_w = list_temp_plut_tot[ranges[i][1]] + duree.get(ranges[i][1]-1);
                }
            }
        }

        //la remise en ordre de date en fonction des ranges
        return remiseEnOrdre(list_temp_plut_tot,matrice,ranges);
    }

    public static int[] tempPlusTard(int[][] matrice, List<Integer> duree, int[][] ranges, int dureeTotal){

        int[] list_temp_plut_tard = new int[matrice.length+2];
        Arrays.fill(list_temp_plut_tard, dureeTotal);
        list_temp_plut_tard[0] = 0;
        int min_sommet_a = 0;
        boolean mark = false;

        for(int i = ranges.length-1; i > 0 ; i--){

            if(ranges[i][0] == ranges[ranges.length-1][0]-1){

                list_temp_plut_tard[ranges[i][1]] = dureeTotal - duree.get(ranges[i][1]-1);

            }

            if (i == ranges.length-1){

                list_temp_plut_tard[i] = dureeTotal;
                continue;
            }

            for(int k = 0; k < matrice[1].length; k++){

                if(matrice[ranges[i][1]-1][k] == 1){

                    if(list_temp_plut_tard[ranges[i][1]] > list_temp_plut_tard[k+1] - duree.get(ranges[i][1]-1)){

                        list_temp_plut_tard[ranges[i][1]] = list_temp_plut_tard[k+1] - duree.get(ranges[i][1]-1);
                        mark = true;
                    }
                }
            }
            if(!mark){
                list_temp_plut_tard[ranges[i][1]] = dureeTotal - duree.get(ranges[i][1]-1);
            }

            mark = false;
        }

        //La remise en ordre de date en fonction des ranges
        return remiseEnOrdre(list_temp_plut_tard,matrice,ranges);
    }

    public static void dureeMap(int[] arr, int[][] ranges, HashMap<Integer,Integer> res){

        for(int i = 0; i < arr.length; i++){

            res.put(ranges[i][1],arr[i]);
        }
    }

    public static ArrayList<Integer> precDernierEtat(int[][] matrice){

        boolean mark = false;
        ArrayList<Integer> precs = new ArrayList<>();

        for(int i = 0; i < matrice.length; i++){

            for(int k = 0; k < matrice[0].length; k++){

                if(matrice[i][k] == 1){
                    mark = true;
                }
            }

            if(!mark){
                precs.add(i+1);
            }

            mark = false;
        }

        return  precs;
    }

    public static int[] remiseEnOrdre(int[] arr, int[][] matrice, int[][] ranges){

        int[] res = new int[matrice.length+2];
        for(int i = 0; i < arr.length; i++){
            res[i] = arr[ranges[i][1]];
        }
        return res;
    }


    public static ArrayList<Integer> marge(int[] listPlusTard, int[] listPlusTot, int[][] ranges){

        int[] marges = new int[listPlusTard.length];
        ArrayList<Integer> etats_critiques = new ArrayList();

        //Calculer les marges
        for(int i = 0; i < listPlusTard.length; i++){

            marges[i] = listPlusTard[i] - listPlusTot[i];
        }

        for(int k = 0; k < marges.length; k++){

            if(marges[k] == 0){

                etats_critiques.add(ranges[k][1]);

            }
        }

        return etats_critiques;
    }


    public static void cheminCritique(ArrayList<ArrayList<Integer>> res, ArrayList<Integer> etats_critiques,
                                                    List<ArrayList<Integer>> predecesseurs, ArrayList<Integer> chemin,
                                      int index, HashMap<Integer,Integer> dureeMap, boolean mark) {
        //Une fois qu'on arrive à l'état de départ, l'un des chemins critique est trouvé
        if(predecesseurs.get(etats_critiques.get(index)-1).size() == 0){

            chemin.add(etats_critiques.get(index));
            chemin.add(0);
            res.add(chemin);
            return;
        }

        ArrayList<Integer> etat_max = new ArrayList<>();
        ArrayList<Integer> prec = predecesseurs.get(etats_critiques.get(index)-1);
        int duree_max = 0;
        for(int k = 0; k < prec.size(); k++){

            if(!etats_critiques.subList(0,index).contains(prec.get(k))){

                continue;
            }

            if(duree_max < dureeMap.get(prec.get(k))){

                etat_max.clear();
                etat_max.add(prec.get(k));
                duree_max = dureeMap.get(prec.get(k));
            }else if(duree_max == dureeMap.get(prec.get(k))){
                etat_max.add(prec.get(k));
            }
        }
        ArrayList<Integer> temp = new ArrayList<>(Arrays.asList(new Integer[chemin.size()]));
        Collections.copy(temp,chemin);
        for(int o = 0; o < etat_max.size(); o++){

            if(etats_critiques.subList(0,index).contains(etat_max.get(o)) && !mark){
                chemin.add(etats_critiques.get(index));
                mark = true;
                cheminCritique(res,etats_critiques,predecesseurs,chemin,etats_critiques.indexOf(etat_max.get(o)),dureeMap,false);

            }else if(etats_critiques.subList(0,index).contains(etat_max.get(o)) && mark){
                ArrayList<Integer> chemin_diff = new ArrayList<>(Arrays.asList(new Integer[temp.size()]));
                Collections.copy(chemin_diff,temp);
                chemin_diff.add(etats_critiques.get(index));
                cheminCritique(res,etats_critiques,predecesseurs,chemin_diff,etats_critiques.indexOf(etat_max.get(o)),dureeMap,false);
            }
        }
    }

}
