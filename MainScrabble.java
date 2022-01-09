public class MainScrabble {
    public static void main(String[] args) {
        System.out.print("Bonjour vous voici dans une nouvelle partie de Scrabble ! \nCombien de joueurs etes-vous ? ");
        int nbJoueur = Ut.saisirEntier();
        String[] tabJoueurs = new String[nbJoueur];
        for (int i = 0; i < tabJoueurs.length; i++) {
            System.out.print("Nom du joueur " + (i+1) + " : ");
            tabJoueurs[i] = Ut.saisirChaine();
        }
        Scrabble partie = new Scrabble(tabJoueurs);
        partie.Partie();

    }

}

// — gris (pas de valorisation particulière) : 1
// — bleu clair (lettre compte double) : 2
// — bleu foncé (lettre compte triple) : 3
// — rose (mot compte double) : 4
// — rouge (mot compte triple) : 5


//val=[1,3,3,2,1,4,2,4,1,8,10,1,2,1,1,3,8,1,1,1,1,4,10,10,10,10]
//let=[a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z]
//occ=[9,2,2,3,15,2,2,2,8,1,1,5,3,6,6,2,1,6,6,6,6,2,1,1,1,1]