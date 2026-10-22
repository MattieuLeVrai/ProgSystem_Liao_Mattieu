class Producteur implements Runnable {

    private final ProducteurConsommateur pc;

    public Producteur(ProducteurConsommateur pc) {
        this.pc = pc;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            int produit = (int) (Math.random() * 100);
            try {
                // Attendre une place libre
                pc.placesLibres.acquire();

                // Acquérir l'accès exclusif au buffer  
                pc.mutexBuffer.acquire();

                // Ajouter l'élément au buffer
                pc.buffer.offer(produit);

                // Libérer l'accès au buffer
                pc.mutexBuffer.release();

                // Signaler qu'un élément est disponible
                pc.elementsDisponibles.release();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}