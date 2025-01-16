import java.util.concurrent.Semaphore;

public class Attaccante extends Thread {
    private String nome;
    private Semaphore postiAttaccanti;
    private Semaphore postiDifensori;

    public Attaccante(String nome, Semaphore postiAttaccanti, Semaphore postiDifensori) {
        this.nome = nome;
        this.postiAttaccanti = postiAttaccanti;
        this.postiDifensori = postiDifensori;
    }

    @Override
    public void run() {
        try {
            while (true) {
                postiAttaccanti.acquire(); // Prende un posto come attaccante

                System.out.println(nome + " sta attaccando.");

                Thread.sleep(2000); // Simula il tempo di attacco

                System.out.println(nome + " ha finito di attaccare.");

                Thread.sleep(300); // Pausa in modo tale che lo scheduler abbia il tempo per stampare il System.out.println sopra

                postiDifensori.release(); // Libera un posto per i difensori

                Thread.sleep(2000); // Pausa prima di riprendere
            }
        } catch (InterruptedException e) {
            System.out.println(nome + " interrotto.");
        }
    }
}
