import java.io.*;
import java.util.*;

/**
 * @sommets La liste des états
 * @duree La liste des durées
 * @prédécesseurs La liste des prédecesseurs de chaque état
 * @cheminFichier Le chimin relatif du fichier teste
 *
 * @apiNote
 * Importer le fichier du teste
 * */
public class ImportationFichier {

    private List<Integer> sommets;
    private List<Integer> duree;
    private List<ArrayList<Integer>> predecesseurs;
    private ArrayList<Integer> element;
    private String cheminFichier;

    //Initialisation des variables
    public ImportationFichier(String cheminFichier) {
        this.sommets = new ArrayList<>();
        this.duree = new ArrayList<>();
        this.predecesseurs = new ArrayList<>();
        this.cheminFichier = cheminFichier;
    }

    /**
     * @line recevoir le texte de chaque ligne dans le fichier
     * @BufferedReader I/O class
     * @localisation Examiner si la condition est pour le nom d'état ou d'autres types
     * @index_pre localiser le premier caractère de chaque type(etat,durée,prédécesseurs).
     * ex. line.subString(index_pre,localisation) = le nom d'etat
     * @trigger Examiner s'il y a plusieurs prédécesseurs.
     *
     * @apiNote
     * * La méthode "separationInfo" sert à trier toutes les informations du fichier teste
     * * en les mettant dans chaque liste correspondante tels que l'état, la durée ainsi que le prédécesseur
     * * Pour importer le fichier, nous avons la stream I/O en choissant la classe bufferedReader qui est en thread sécurisé.
     * */
    public void SeparationInfo(){

        String line;
        BufferedReader reader = null;
        int localisation = 0;
        int index_pre = 0;
        boolean trigger = false;

        try {

            //Créer le stream I/O
            reader = new BufferedReader(new FileReader(cheminFichier));

            //Après être arrivé à la derniere line, il sort de la boucle
            while ((line = reader.readLine()) != null){

                //Parcourir chaque line, autrement dit le nom de chaque état, sa durée, et ses prédécesseurs
                for(int i = 0; i < line.length(); i++){

                    //La condition pour le nom d'état(index: 0 -> 1e espace par exemple "2 2 1")
                    if(localisation == 0 && line.charAt(i) == ' '){
                        //Ajouter le nom de chaque état dans la liste d'état
                        sommets.add(Integer.valueOf(line.substring(index_pre,i)));
                        //mettre à jour l'index qui pointe le premier caractère du prochain type(durée)
                        index_pre = i +1;
                        //Changer l'état d'examination pour la condtion de durée
                        localisation++;
                        continue;
                    //La condition pour la durée d'état et cet état posséde un ou plusieurs prédécesseurs, lors que localisation == 1
                    }else if(localisation == 1 && line.charAt(i) == ' '){
                        //Ajouter la durée de chaque état dans la liste de durée
                        duree.add(Integer.valueOf(line.substring(index_pre,i)));
                        //mettre à jour l'index qui pointe le premier caractère du prochain type(prédécesseur)
                        index_pre = i+1;
                        //Changer l'état d'examination pour la condtion de prédécesseur
                        localisation++;
                        continue;
                    //La condition pour la durée d'état et cet état ne posséde pas de prédécesseurs, lors que localisation == 1
                    }else if(localisation == 1 && i == line.length()-1){
                        //Ajouter la durée de chaque état dans la liste de durée
                        duree.add(Integer.valueOf(line.substring(index_pre)));
                        //Remettre à l'index 0, passer à la prochaine ligne(prochain état)
                        index_pre = 0;
                        //Remettre l'état d'examination à 0 pour la condition du nom d'état
                        localisation = 0;
                        //Créer une liste vide pour cet état qui posséde aucun prédécesseur
                        predecesseurs.add(new ArrayList<>());
                    }

                    //La condition pour les prédécesseurs d'état, lors que localisation > 1
                    if(localisation > 1){
                        //Si trigger == false, c'est-à_dire que l'on va stocker le 1e prédécesseur
                        if(trigger != true){
                            trigger = true;
                            //Dans un premier temps, créer une liste(un container) pour prendre tous les prédécesseur correspondants
                            element = new ArrayList<>();
                        }

                        //Si à la fin de chaque prédécesseur, il paraît un espace, c'est-à-dire qu'il y a encore d'autres prédécesseurs
                        if(line.charAt(i) == ' '){
                            //Ajouter le prédécesseur précedent dans la liste créée
                            element.add(Integer.valueOf(line.substring(index_pre,i)));
                            //mettre à jour l'index pour pointer le 1e caractère du prochain prédécesseur
                            index_pre = i+1;

                        //Si à la fin du prédécesseur, il est également la fin de la ligne, ainsi c'est le dernier prédécesseur
                        }else if(i == line.length()-1){
                            //Ajouter le prédécesseur précedent dans la liste créée
                            element.add(Integer.valueOf(line.substring(index_pre)));
                            //Ajouter la liste qui contient tous les prédécesseur de l'état dans la liste de prédécesseur
                            predecesseurs.add(element);
                            //Remettre l'état d'examination à 0
                            localisation = 0;
                            //Remettre l'index du premier caractère à 0
                            index_pre = 0;
                            //Remettre l'état qui signifie si c'est la première stockage à false
                            trigger = false;
                        }

                    }
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);

        } catch (IOException e) {
            throw new RuntimeException(e);

        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<Integer> getSommets() {
        return sommets;
    }

    public void setSommets(List<Integer> sommets) {
        this.sommets = sommets;
    }

    public List<Integer> getDuree() {
        return duree;
    }

    public void setDuree(List<Integer> duree) {
        this.duree = duree;
    }

    public List<ArrayList<Integer>> getPredecesseurs() {
        return predecesseurs;
    }

    public void setPredecesseurs(List<ArrayList<Integer>> predecesseurs) {
        this.predecesseurs = predecesseurs;
    }
}
