public class Calcule extends Thread {

    private int valeur;
    private int multiplicateur;
    private int index;
    private int resultat = 0;
    
    public Calcule(int valeur, int multiplicateur, int index){   
        this.valeur = valeur;
        this.multiplicateur = multiplicateur;
        this.index = index;
    }

    @Override
    public void run(){
        for (int j = 0; j < this.multiplicateur; j++) {
            this.resultat += valeur * valeur + valeur;
        }
    }

    public int getResultat(){
        return resultat;
    }

}