public class TestMutex {

    private static void TestRaceCondition() {
        int nbTests = 50;
        int nbThreads = 9;
        int nbIncrements = 1111111;
        int attendu = nbThreads * nbIncrements; 
        int succes = 0;
        int minObtenu = Integer.MAX_VALUE;
        int maxObtenu = Integer.MIN_VALUE;

        System.out.println("Lancement de " + nbTests + " tests de concurrence...\n");

        for (int i = 0; i < nbTests; i++) {
            CompteurSecurise.resetCompteur();
            Thread[] threads = new Thread[nbThreads];
            for (int j = 0; j < nbThreads; j++) {
                threads[j] = new Thread(
                    new CompteurSecurise.Incrementeur("Thread-" + (j + 1), nbIncrements)
                );
            }

            for (Thread thread : threads) {
                thread.start();
            }

            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            int resultat = CompteurSecurise.getCompteur();
            if (resultat == attendu) {
                succes++;
            }

            minObtenu = Math.min(minObtenu, resultat);
            maxObtenu = Math.max(maxObtenu, resultat);
        }

        double tauxReussite = ((double) succes / nbTests) * 100;
        System.out.println("\nSTATISTIQUES");
        System.out.println("Tests réussis      : " + succes + " / " + nbTests);
        System.out.println("Taux de réussite   : " + tauxReussite + " %");
        System.out.println("Valeur minimale    : " + minObtenu);
        System.out.println("Valeur maximale    : " + maxObtenu);
        System.out.println("Valeur attendue    : " + attendu);
    }

    public static void main(String[] args){
        TestRaceCondition();
    }
}