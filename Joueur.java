public class Joueur {

    private String nom;
    private MEE chevalet;
    private int score;

    public Joueur(String unNom) {
        nom = unNom;
        chevalet = new MEE(26);
        score = 0;
    }

    public String toString() {
        return nom + " !\n -> Votre chevalet : "+chevalet;
    }

    public int getScore() {
        return score;
    }

    public String getNom(){
        return nom;
    }

    public void ajouteScore(int nb) {
        score += nb;
    }

    /*
     * pré-requis : nbPointsJet indique le nombre de points rapportés par chaque
     * jeton/lettre
     */

    public int nbPointsChevalet(int[] nbPointsJet) {
        return chevalet.sommeValeurs(nbPointsJet);
    }

    /* pré-requis : les éléments de s sont inférieurs à 26 */

    public void prendJetons(MEE sac, int nbJetons) {
        sac.transfereAleat(chevalet, nbJetons);
    }

    /* pré-requis : les éléments de s sont inférieurs à 26
                    nbPointsJet.length >= 26 */

    public int joue(Plateau plateau, MEE sac, int[] nbPointsJet) {
        int res = 0;
        boolean verit;
        System.out.println("Que voulez-vous jouer ? (Saisir la premiere lettre (en maj)-> Joue/Echange/Passe)");
        char tour = Ut.saisirCaractere();                        // on demande au joueur ce qu'il veut jouer
        if (tour == 'J' ){                                      // le joueur veut placer un mot
            verit = this.joueMot(plateau, sac, nbPointsJet);
            while (!verit){
                verit = this.joueMot(plateau, sac, nbPointsJet);
            }
            if (chevalet.estVide()){
                res = 1;
            }
        } else if (tour == 'E' ){                           // le joueur veut echanger des lettres
            this.echangeJetons(sac);
        } else {                                                // le joueur tape autre chose que 'joue' ou 'echange' donc il passe
            res = -1;
        }
        return res;
    }

    /** pré-requis : les éléments de s sont inférieurs à 26*et nbPointsJet.length >= 26*  
     * action : simule le placement d’un mot de this :*a) le mot, sa position sur le plateau et sa direction, sont saisis*au clavier*b) vérifie si le mot est valide*c) si le coup est valide, le mot est placé sur le plateau*  
     * résultat : vrai ssi ce coup est valide, c’est-à-dire accepté par*CapeloDico et satisfaisant les règles détaillées plus haut*  stratégie : utilise la méthode joueMotAux
     * */
    
    public boolean joueMot(Plateau plateau, MEE sac, int[] nbPointsJet) {
        boolean capeloV;
        System.out.println("Quel mot voulez-vous jouer ?");
        String mot = Ut.saisirChaine();
        System.out.println("Dans quel sens voulez-vous le jouer ? (h/v)");
        char sens = Ut.saisirCaractere();
        System.out.println("A quelle coordonnée ?");
        System.out.print("Indice de ligne : ");
        int numLig = Ut.saisirEntier();
        System.out.print("Indice de colonne : ");
        int numCol = Ut.saisirEntier();
        System.out.println("Le mot est validé par CapeloDico ? (O/N) ");
        char capelo = Ut.saisirCaractere();
        if (capelo == 'N'){
            System.out.println("Le mot n'est pas valide");
            capeloV = false;
        } else {
            capeloV = true;
        }
        boolean placementValide = plateau.placementValide(mot, numLig, numCol, sens, chevalet);
        if (capeloV && placementValide){
            joueMotAux(plateau, sac, nbPointsJet, mot, numLig, numCol, sens);

        }
        return capeloV && placementValide;
    }

    /** 
     * pré-requis : cf. joueMot et le placement de mot à partir de la case*(numLig, numCol) dans le sens donné par sens est valide*  
     * action : simule le placement d’un mot de this
     * */
        
        
    public void joueMotAux(Plateau plateau, MEE sac, int[] nbPointsJet, String mot,int numLig, int numCol, char sens) {
        int nbLettres = plateau.place(mot, numLig, numCol, sens, chevalet);
        if (chevalet.estVide()){
            System.out.println("Bien joue vous avez fait un scrabble !! +50points");
            this.ajouteScore(50);
        }
        int nbTransfere = sac.transfereAleat(chevalet, nbLettres);
        if (!sac.estVide() && nbTransfere<nbLettres){
            System.out.println("Il n'y a plus de jetons dans le sac");
        }
        this.ajouteScore(plateau.nbPointsPlacement(mot, numLig, numCol, sens, nbPointsJet));
        System.out.println("Bien joue vous marquez "+plateau.nbPointsPlacement(mot, numLig, numCol, sens, nbPointsJet)+" points");
            
    }


    /* pré-requis : sac peut contenir des entiers de 0 à 25  */


    public void echangeJetons(MEE sac) {
        System.out.println("Quelles lettres voulez vous echanger ? (tout colle en maj)");
        String chaine = Ut.saisirChaine();
        while (!estCorrectPourEchange(chaine)){
            System.out.println("Veuillez retaper les lettres que vous voulez echanger (tout colle en maj)");
            chaine = Ut.saisirChaine();
        }
        echangeJetonsAux(sac, chaine);
        System.out.println("L'echange a bien ete effectue");
    }

    public boolean estCorrectPourEchange (String mot) {
        int[] tabchaine = new int[chevalet.getTabFreq().length];
        boolean lettreEnMaj = true;
        boolean lettreEnMain = true;
        int i=0;
        char lettre;

        while (lettreEnMaj && i<mot.length()){                                    
            if (!(65 <= mot.charAt(i) && mot.charAt(i) <= 90)){                             // on test si les lettres ne sont pas en majuscule
                System.out.println(mot.charAt(i)+" n'est pas une majuscule");
                lettreEnMaj = false;
            }
            i++;
        }
        if (lettreEnMaj){                                                                                         // si elles sont en majuscule lettreEnMaj est vrai
            for (int k=0;k<mot.length();k++){                                                  // on remplit un tableau avec les occurances des lettres que l'on veut échanegr
                tabchaine[mot.charAt(k)-65]++;
            }
            i=0;
            while (lettreEnMain && i<tabchaine.length){
                if (chevalet.getTabFreq()[i]<tabchaine[i]){ 
                    lettre = (char) (i+65);                                      // si l'occurance d'une lettre est plus petite dans notre main alors ou souhaite echanger plus de lettres que possible
                    System.out.println("Vous n'avez pas la lettre " + lettre + " en main");
                    lettreEnMain = false;
                }
                i++;
            }
        }
        return (lettreEnMaj && lettreEnMain);

    }
        /** pré-requis : sac peut contenir des entiers de 0 à 25 et ensJetons
        * est un ensemble de jetons correct pour l’échange
        * action : simule l’échange de jetons de ensJetons avec des
        * jetons du sac tirés aléatoirement.
        */

    public void echangeJetonsAux(MEE sac, String ensJetons) {
        int[] tabChaine = new int[chevalet.getTabFreq().length];
        for (int i=0;i<ensJetons.length();i++){
            tabChaine[ensJetons.charAt(i)-65]++;
        }
        int nbJetonTransfere=sac.transfereAleat(chevalet, ensJetons.length());
        if (nbJetonTransfere==ensJetons.length()){                                         // Si on a pu retirer le nombre de jeton voulu
            for (int i=0;i<tabChaine.length;i++){
                for (int j=0;j<tabChaine[i];j++){
                    chevalet.transfere(sac, i);                                                 // on supprime les jetons de la main
                
                }
            }
        } else {                                                                        // sinon (le sac est vide)
            int[] tabChaineRacc = new int[tabChaine.length];                                 // on creer un nouveaux tableau qui va contenir les occurances des premiers jetons que l'on voualait echanger
            for (int i=0;i<nbJetonTransfere;i++){                                       // ce tableau aura autant de lettre que le nombre de jetons que l'on a réussi a piocher
                tabChaineRacc[ensJetons.charAt(i)-65]++;
            }
            for (int i = 0;i<tabChaine.length;i++){                                      // et on remet les jetons retirés dans le sac
                for (int j=0;j<tabChaineRacc[i];j++){
                    chevalet.transfere(sac, i);                                                 // on supprime les jetons de la main
                }
            }
        }



        

    }
    
}
