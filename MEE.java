public class MEE {

    private int[] tabFreq; // tabFreq[i] est le nombre d’exemplaires ex: [2, 0, 0, 1, 3, 0, 0, ..., 0]
                           // (fréquence) de l’élément i
    private int nbTotEx; // nombre total d’exemplaires               ex: 6

    /* Constructeurs */

    /* pré-requis : max >= 0 */

    public MEE(int max) {
        tabFreq = new int[max];
        nbTotEx = 0;
    }

    /* pré-requis : les éléments de tab sont positifs ou nuls */

    public MEE(int[] tab) {
        tabFreq = new int[tab.length];
        for (int i = 0; i < tab.length; i++) {
            tabFreq[i] = tab[i];
        }
        nbTotEx = 0;
        for (int i = 0; i < tab.length; i++) {
            nbTotEx += tab[i];
        }
    }

    public MEE(MEE e) {
        tabFreq = new int[e.tabFreq.length];
        nbTotEx = e.nbTotEx;
        for(int i=0;i<e.tabFreq.length;i++){
            tabFreq[i]=e.tabFreq[i]; 
        }
    }

    /* Methodes */

    public int getNbTotEx() {
        return nbTotEx;
    }

    public int[] getTabFreq(){
        return tabFreq;
    }


    public boolean estVide() {
        return nbTotEx == 0;
    }

    /* pré-requis : 0 <= i < tabFreq.length */

    public void ajoute(int i) {
        tabFreq[i]++;
        nbTotEx++;
    }

    /* pré-requis : 0 <= i < tabFreq.length */

    public boolean retire(int i) {
        boolean res = false;
        if (tabFreq[i] > 0) {
            tabFreq[i]--;
            nbTotEx--;
            res = true;
        }
        return res;

    }

    /* pré-requis : this est non vide */

    public int retireAleat () {
        int resultat=0;
        int k=0;
        int[] tabJetonExistant = new int[tabFreq.length];   // on cree un nouveau tableau de la taille de tabFreq
        for (int i=0;i<tabJetonExistant.length;i++){
            tabJetonExistant[i]=30;                         // on remplis ce tableau avec un nombre supérieur a 26
        }
        int j=0;
        for (int i=0;i<tabFreq.length;i++){                 // on parcours tabFreq
            if (tabFreq[i]>0){                              // on vérifie si la lettre est contenu dans tabFrequ
                tabJetonExistant[j]=i;                      // puis on ajoute l'indices de toutes les lettres encore présentes dans le nouveau tableau
                j++;
            }
        }                                                   // on a donc un tableau avec les indices des lettres présentes dans notre ensemble
        if (nbTotEx>0){
            int tailleNewTab = 0;
            while (k<tabFreq.length && tabJetonExistant[k]<30){      // on repere a partir d'ou les 30 commencent (a la fin du nouveau tableau)
                tailleNewTab++;
                k++;
            }
            int[] tabRes = new int[tailleNewTab];           // ensuite on creer un nouveau tableau     
            for (int i=0;i<tailleNewTab;i++){               // puis on tronque les 0 a la fin du tableau
                tabRes[i]=tabJetonExistant[i];
            }
            int res = Ut.randomMinMax(0,tabRes.length-1);   // ensuite on choisit notre lettres au hasard parmit celle restantes
            this.retire(tabRes[res]);                   // puis on la retire de this    
            resultat=tabRes[res];
        }
        return resultat;
    }

    /* pré-requis : 0 <= i < tabFreq.length */

    public boolean transfere(MEE e, int i) {
        boolean res = false;
        if (this.retire(i)) {
            e.ajoute(i);
            res = true;
        }
        return res;
    }

    /* pré-requis : k >= 0 */

    public int transfereAleat(MEE e, int k) {
        int nbTransfere = 0;
        int i;
        while (!this.estVide() && nbTransfere < k) {
            i = this.retireAleat();
            e.ajoute(i);
            nbTransfere++;
        }
        return nbTransfere;
    }

    /* pré-requis : tabFreq.length <= tabValeur.length */

    public int sommeValeurs(int[] tabValeur) {
        int res = 0;
        for (int i = 0; i < tabFreq.length; i++) {
            res += tabFreq[i] * tabValeur[i];
        }
        return res;

    }

    public String toString(){
        String res = "[ ";
        for (int i=0;i<tabFreq.length;i++){
            if (tabFreq[i]>0){
                for (int j=0;j<tabFreq[i];j++){
                    res += (char) (i+65)+" ";
                }
            }
        }
        res+="]";

        return res;
    }

}
