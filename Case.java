public class Case {

    private int couleur;
    private boolean liberte;
    private char lettre;

    /* Constructeurs */

    /* pré-requis : uneCouleur est un entier entre 1 et 5 */

    public Case (int uneCouleur){
        couleur = uneCouleur;
        liberte = true;
        lettre = ' ';
    }

    /* Methodes */

    public int getCouleur (){
        return couleur;    
    }

    public void setCouleur(int coul){
        couleur = coul;
    }

    /* pré-requis : cette case est recouverte */

    public char getLettre (){
        return lettre;    
    }

    /* pré-requis : let est une lettre majuscule */

    public void setLettre (char let){
        lettre = let;
        liberte = false;
    }
    
    /* résultat : vrai ssi la case est recouverte par une lettre */

    public boolean estRecouverte (){
        return !liberte;
    }

    public String toString(){
        String res;
        if (liberte && couleur>1){
            res=Integer.toString(couleur);
        } else {
            res=Character.toString(lettre);
        }
        return res;
    }
}