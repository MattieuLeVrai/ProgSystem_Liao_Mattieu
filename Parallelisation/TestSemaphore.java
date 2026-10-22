public class TestSemaphore {

    public static void main(String[] args) throws InterruptedException {
        ProducteurConsommateur pc = new ProducteurConsommateur();

        Thread producteur1 = new Thread(new Producteur(pc));
        Thread producteur2 = new Thread(new Producteur(pc));
        Thread consommateur1 = new Thread(new Consommateur(pc, 10));
        Thread consommateur2 = new Thread(new Consommateur(pc, 10));

        producteur1.start();
        producteur2.start(); 
        consommateur1.start(); 
        consommateur2.start();
        producteur1.join();  
        producteur2.join();  
        consommateur1.join();  
        consommateur2.join();

        System.out.println("Buffer vide : " + pc.buffer.isEmpty());
        System.out.println("Places libres : " + pc.placesLibres.availablePermits());
        System.out.println("Éléments disponibles : " + pc.elementsDisponibles.availablePermits());
    }
}