class Consommateur implements Runnable {

    private final ProducteurConsommateur pc;
    private final int nombreProduits;

    public Consommateur(ProducteurConsommateur pc, int nombreProduits) {
        this.pc = pc;
        this.nombreProduits = nombreProduits;
    }

    public void run() {
        for (int i = 0; i < nombreProduits; i++) {
            try {
                // Attendre qu'un élément soit disponible
                pc.elementsDisponibles.acquire();

                // Accéder au buffer
                pc.mutexBuffer.acquire();

                // Retirer réellement l'élément
                Integer produit = pc.buffer.poll();
                pc.mutexBuffer.release();

                // Signaler une place libre
                pc.placesLibres.release();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}