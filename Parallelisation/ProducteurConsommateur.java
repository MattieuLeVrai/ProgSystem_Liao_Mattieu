import java.util.Queue;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class ProducteurConsommateur {

    public final int TAILLE_BUFFER = 5;
    public final Queue<Integer> buffer = new LinkedList<>();

    public final Semaphore placesLibres = new Semaphore(TAILLE_BUFFER);
    public final Semaphore elementsDisponibles = new Semaphore(0);
    public final Semaphore mutexBuffer = new Semaphore(1);
}