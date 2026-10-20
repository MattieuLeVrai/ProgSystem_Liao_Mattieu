import java.util.concurrent.Semaphore;
import java.util.LinkedList;
import java.util.Queue;

public class ProducteurConsommateur {
    private static final int TAILLE_BUFFER = 5;
    private final Integer[] buffer = new Integer[TAILLE_BUFFER];

    // TODO: Déclarer 3 c:\Users\mattieu.liao\AppData\Local\Packages\MicrosoftWindows.Client.Core_cw5n1h2txyewy\TempState\ScreenClip\{49D3BDFE-C3C9-4DBF-AE5C-A848F487347B}.pngsémaphores avec les bonnes valeurs initiales
    private final Semaphore placesLibres = new Semaphore(TAILLE_BUFFER);
    private final Semaphore elementsDisponibles = new Semaphore(0);  
    private final Semaphore mutexBuffer = new Semaphore(1); // quel erreur ici ? vous pouvez le résoudre plus tard

    public Semaphore getPlacesLibres(){ 
        return placesLibres;
    }
    public Semaphore getElementsDisponibles() {
        return elementsDisponibles; 
    }
    public Semaphore getMutexBuffer() { 
        return mutexBuffer; 
    }
}