public class Plateau {

    private Case [][] grille;

    /* Constructeurs */

    public Plateau(){
        int[][] plateau = {{5, 1, 1, 2, 1, 1, 1, 5, 1, 1, 1, 2, 1, 1, 5},
                           {1, 4, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 4, 1},
                           {1, 1, 4, 1, 1, 1, 2, 1, 2, 1, 1, 1, 4, 1, 1},
                           {2, 1, 1, 4, 1, 1, 1, 2, 1, 1, 1, 4, 1, 1, 2},
                           {1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1},
                           {1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1},
                           {1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1},
                           {5, 1, 1, 2, 1, 1, 1, 4, 1, 1, 1, 2, 1, 1, 5},
                           {1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1},
                           {1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1},
                           {1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1},
                           {2, 1, 1, 4, 1, 1, 1, 2, 1, 1, 1, 4, 1, 1, 2},
                           {1, 1, 4, 1, 1, 1, 2, 1, 2, 1, 1, 1, 4, 1, 1},
                           {1, 4, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 4, 1},
                           {5, 1, 1, 2, 1, 1, 1, 5, 1, 1, 1, 2, 1, 1, 5}};
        Case[][] grille = new Case[15][15];
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[0].length; j++) {
                grille[i][j] = new Case(plateau[i][j]);
            }
        }
        this.grille = grille;
    }

    public Plateau(Plateau p){
        grille = new Case[p.grille.length][p.grille[0].length];
        for (int i=0;i<p.grille.length;i++){
            for (int j=0;j<p.grille[0].length;j++){
                grille[i][j]=p.grille[i][j];
            }
        }
    }

    /* Methodes */

    public String toString() {
        String res = "  ";
        char lettreV = 65;
        char lettreH = 65;
        for (int j=0;j<grille[0].length;j++){       // indices horizontaux
            res+= lettreH;
            lettreH++;
        }
        res+="\n  ";
        for (int j=0;j<grille[0].length;j++){       // delimitation horizontale haute
            res+="_";
        }
        res+="\n";
        for (int i=0;i<grille.length;i++){          // boucle pour les lignes
            res+=lettreV + "|";                     // indices verticaux et delimitation verticale gauche
            for (int j=0;j<grille[0].length;j++){   // boucle pour les colonnes 
                res+=grille[i][j];                  // affichage de la Case
            }
            res+="|\n";                             // delimitation verticale droite
            lettreV++;
        }
        res+="  ";
        for (int j=0;j<grille[0].length;j++){       // delimitation horizontale basse
            res+="_";
        }
        return res;
    }
    
    /* pré-requis : mot est un mot accepté par CapeloDico,
                    0 <= numLig <= 14,
                    0 <= numCol <= 14,
                    sens est un élément de {’h’,’v’}
                    l’entier maximum prévu pour e est au moins 25 */



    // renvoie true ssi toutes les lettres a placer existe dans le chevalet du
    // joueur
    
    
    public boolean placementValide(String mot, int numLig, int numCol,char sens, MEE e) {
        boolean res = true;
        boolean plateauVide = !grille[7][7].estRecouverte();
        boolean passeParCentre = false;
        boolean lettreEnMain;
        int k = 0;
        if (plateauVide){                                                        // si le plateau est vide
            if (mot.length()<2){                                                        // on test si le mot a au moins 2 lettres
                System.out.println("Le premier mot doit contenir au moin 2 lettres");
                res = false;                                                            // le mot n'est pas valide on renvoie false
            }
            while (!passeParCentre && k<mot.length()){                                  // ensuite on test si le mot passe par le centre du plateau
                if (sens == 'h'){                
                    if(numLig == 7 && numCol == 7){
                        passeParCentre = true;
                    }
                    numCol++;
                } else {
                    if (numLig == 7 && numCol == 7){
                        passeParCentre = true;
                    }
                    numLig++;
                }
                k++;
            }
            if (!passeParCentre){                                                       // si le premier mot ne passe pas par le centre
                System.out.println("Le premier mot doit passer par le centre");         // on envoie un message d'erreur 
                res = false;
            }
            lettreEnMain = lettreEnMain(e, mot);
            if (!lettreEnMain){
                System.out.println("Vous avez n'avez pas les bonnes lettres en main");
                res = false;
            }
        } else {
            boolean depasse = depassePas(mot, numLig, numCol, sens);
            if (!depasse){
                System.out.println("Votre mot depasse du plateau.");
                res = false;
            }
            boolean suiviOuPrecede = pasSuivOuPreced(mot, numLig, numCol, sens);
            if (!suiviOuPrecede){
                System.out.println("Votre mot ne doit pas etre suivi ou precedé d'une autre lettre");
                res = false;
            }
            boolean contientLettreSurPlat = contientLettreSurPlat(mot, numLig, numCol, sens);
            if (!contientLettreSurPlat){
                System.out.println("Votre mot doit contenir une lettre qui est deja sur le plateau");
                res = false;
            }
            boolean contientPasLettreSurPlat = contientPasLettreSurPlat(mot, numLig, numCol, sens);
            if (!contientPasLettreSurPlat){
                System.out.println("Votre mot doit contenir une lettre qui n'est pas encore  sur le plateau");
                res = false;
            }
            String chaine="";
            for (int i=0;i<mot.length();i++){
                if (grille[numLig][numCol].getLettre()!=mot.charAt(i)){
                    chaine += mot.charAt(i);
                }
                if (sens == 'h'){
                    numCol++;
                } else {
                    numLig++;
                }
            }
            System.out.println(chaine);
            lettreEnMain = lettreEnMain(e, chaine);
            if (!lettreEnMain){
                System.out.println("Vous n'avez pas les bonnes lettres en main");
                res = false;
            }
        }
        return res;
    }

    public boolean pasSuivOuPreced(String mot, int numLig, int numCol, char sens){
        boolean res = true;
        if (sens == 'h') {
            if (0<numCol && numCol+mot.length()<14){                                // si le mot n'est pas collé au bord du plateau
                if (grille[numLig][numCol-1].getLettre()!=' ' && grille[numLig][numCol+mot.length()].getLettre()!=' '){ // on verifie qu'il n'est pas precedé ou suivit d'une lettre
                    res = false;
                }
            }
        } else {
            if (0<numCol && numCol+mot.length()<14){                                // si le mot n'est pas collé au bord du plateau
                if (grille[numLig-1][numCol].getLettre()!=' ' && grille[numLig+mot.length()][numCol].getLettre()!=' '){ // on verifie qu'il n'est pas precedé ou suivit d'une lettre
                    res = false;
                }
            }
        }
        
        return res;
    }

    public boolean depassePas(String mot, int numLig, int numCol, char sens){
        boolean res = true;
        int i = 0;
        if (sens == 'h') {
            while (res && i < mot.length()){
                if (numCol > 14 || numLig > 14){
                    res = false;
                }
                i++;
                numCol++;
            }
        } else {
            while (res && i < mot.length()){
                if (numCol > 14 || numLig > 14){
                    res = false;
                }
                i++;
                numLig++;
            }
        }
        return res;
    }

    public boolean contientLettreSurPlat(String mot, int numLig, int numCol, char sens){
        boolean res = false;
        int i = 0;
        while (!res && i < mot.length()){
            res = grille[numLig][numCol].getLettre()==mot.charAt(i);                
            i++;
            if (sens == 'h'){
                numCol++;
            } else {
                numLig++;
            }
        }        
        return res;
    }

    public boolean contientPasLettreSurPlat(String mot, int numLig, int numCol, char sens){
        boolean res = false;
        int i = 0;
        if (sens == 'h') {
            while (!res && i < mot.length()){
                if (grille[numLig][numCol].getLettre()==' '){
                    res = true;
                }
                i++;
                numCol++;
            }
        } else {
            while (!res && i < mot.length()){
                if (grille[numLig][numCol].getLettre()==' '){
                    res = true;
                }
                i++;
                numLig++;
            }
        }
        return res;
    }

    public boolean lettreEnMain(MEE chevalet,String chaine){
        boolean res = true;
        int[] lettremot = new int[chevalet.getTabFreq().length];
        int j = 0;
        for (int i=0;i<chaine.length();i++){
            lettremot[chaine.charAt(i)-65]++;
        }
        while (res && j<lettremot.length){
            if (chevalet.getTabFreq()[j]<lettremot[j]){                                       // on test si les lettres du mot sont bien dans la main
                res = false;
            }
            j++;
        }
        
        return res;
    }

    /* pré-requis : le placement de mot sur this à partir de la case
    (numLig, numCol) dans le sens donné par sens est valide */

    public int nbPointsPlacement(String mot, int numLig, int numCol, char sens, int[] nbPointsJet) {
        int res = 0;
        int multiplicat = 1;
        for (int i = 0; i < mot.length(); i++) {
            if(grille[numLig][numCol].getCouleur() == 2){
                res += (nbPointsJet[mot.charAt(i) - 65]*2);
            } else if(grille[numLig][numCol].getCouleur() == 3){
                res += (nbPointsJet[mot.charAt(i) - 65]*3);
            } else if(grille[numLig][numCol].getCouleur() == 4){
                multiplicat*=2;
                grille[numLig][numCol].setCouleur(1);
            }else if(grille[numLig][numCol].getCouleur() == 5){
                multiplicat*=3;
                grille[numLig][numCol].setCouleur(1);
            } else {
                res += (nbPointsJet[mot.charAt(i) - 65]);
            }
            if (sens == 'h') {
                numCol++;
            } else {
                numLig++;
            }
            res *= multiplicat;
        }
        return res;
    }

    /* pré-requis : le placement de mot sur this à partir de la case
    (numLig, numCol) dans le sens donné par sens à l’aide des
    jetons de e est valide. */

    public int place(String mot, int numLig, int numCol, char sens, MEE e) {
        String lettres = "";
        for (int i = 0; i < mot.length(); i++) {
            if (grille[numLig][numCol].getLettre() == ' ') {
                grille[numLig][numCol].setLettre(mot.charAt(i));
                lettres += mot.charAt(i);
            }
            if (sens == 'h') {
                numCol++;
            } else {
                numLig++;
            }
        }
        for (int i = 0; i < lettres.length(); i++) {
            e.retire(lettres.charAt(i) - 65);
        }
        return lettres.length();
    }

}
