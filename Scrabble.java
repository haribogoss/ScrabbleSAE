public class Scrabble {

    private Joueur[] joueurs;
    private int numJoueur; // joueur courant (entre 0 et joueurs.length-1)
    private Plateau plateau;
    private MEE sac;
    private static int [] nbPointsJeton = {1,3,3,2,1,4,2,4,1,8,10,1,2,1,1,3,8,1,1,1,1,4,10,10,10,10};

    public Scrabble(String[] tabJoueurs){
        joueurs = new Joueur[tabJoueurs.length];
        for (int i = 0; i < tabJoueurs.length; i++) {
            joueurs[i] = new Joueur(tabJoueurs[i]);
        }
        numJoueur = 0;
        plateau = new Plateau();
        int [] tabSac = {9,2,2,3,15,2,2,2,8,1,1,5,3,6,6,2,1,6,6,6,6,2,1,1,1,1};
        sac = new MEE(tabSac);
    }

    public String toString(){
        String res = "";
        for (int i=0;i<joueurs.length;i++){
            res+= "Le joueur : "+joueurs[i]+" a un score de "+joueurs[i].getScore()+"\n";
        }
        return res;
    }

    public void Partie(){
        String nomGagnant ="";
        Joueur gagnant = joueurs[0];
        boolean finPartie = false;
        int meilleurScore = -100000000;
        int tour;
        int dernierJoueur = 0;
        int sommeChevalet = 0;
        int passe=0;
        boolean finPartiePasse = false;
        for (int i = 0; i < joueurs.length; i++) {
            joueurs[i].prendJetons(sac, 7);
        }
        numJoueur = Ut.randomMinMax(0, joueurs.length-1);
        while (!finPartie){
            passe = 0;
            for (int i = numJoueur; i < joueurs.length; i++) {
                if (passe==joueurs.length){
                    System.out.println("Tous les joueurs ont passé leur tour la partie est finis.");
                    finPartiePasse = true;
                    finPartie = true;
                    
                }else {

                    
                    System.out.println("\n"+plateau);
                    System.out.println("C'est votre tour "+joueurs[i]);
                    tour = joueurs[i].joue(plateau, sac, nbPointsJeton);
                    if (tour == -1){
                        System.out.println("Vous passez votre tour !");
                        passe+=1;
                    } else if (tour == 1){
                        if (sac.estVide()){
                            finPartie = true;
                            dernierJoueur = i;
                            break;
                        }
                    }
                }    
            }
            numJoueur=0;
        }
        System.out.println("Bien joué vous avez finis une Partie de Scrabble !");
        if(finPartiePasse){
            for (int i = 0; i < joueurs.length; i++) {
                joueurs[i].ajouteScore(-(joueurs[i].nbPointsChevalet(nbPointsJeton)));                            
            }

        } else {
            
            for (int i = 0; i < joueurs.length; i++) {
                if (i!=dernierJoueur){
                    sommeChevalet+=joueurs[i].nbPointsChevalet(nbPointsJeton);
                    joueurs[i].ajouteScore(-(joueurs[i].nbPointsChevalet(nbPointsJeton)));                
                }
            }
            joueurs[dernierJoueur].ajouteScore(sommeChevalet);
        } 

        for (int i = 0; i < joueurs.length; i++) {
            if (meilleurScore<=joueurs[i].getScore()){
                nomGagnant = joueurs[i].getNom();
                gagnant = joueurs[i];
            }

        }

        
        System.out.println("Le gagnant est "+nomGagnant+"Avec un score de : "+gagnant.getScore());
        System.out.println("Et voici les scores :");
        for (int i = 0; i < joueurs.length; i++) {
            System.out.println(joueurs[i].getNom()+" score : "+joueurs[i].getScore());
        }
    }
                                          
}
