import java.util.concurrent.Semaphore;
import java.util.ArrayList;

public class AllenamentoPallavolo {
    public static void main(String[] args) {
        Semaphore postiAttaccanti = new Semaphore(1);
        Semaphore postiDifensori = new Semaphore(1);

        ArrayList<Thread> giocatori = new ArrayList<>();

        // Creazione iniziale di 3 attaccanti e di 3 difensori
        for (int i = 1; i <= 3; i++) {
            giocatori.add(new Attaccante("Attaccante" + i, postiAttaccanti, postiDifensori));
            giocatori.add(new Difensore("Difensore" + i, postiAttaccanti, postiDifensori));
        }

        // Avvio dei thread (coppia attaccante-difensore)
        for (int i = 0; i < giocatori.size(); i+=2) {
            giocatori.get(i).start();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            giocatori.get(i+1).start();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
