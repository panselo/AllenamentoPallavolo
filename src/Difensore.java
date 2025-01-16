import java.util.concurrent.Semaphore;

public class Difensore extends Thread {
    private String nome;
    private Semaphore postiAttaccanti;
    private Semaphore postiDifensori;

    public Difensore(String nome, Semaphore postiAttaccanti, Semaphore postiDifensori) {
        this.nome = nome;
        this.postiAttaccanti = postiAttaccanti;
        this.postiDifensori = postiDifensori;
    }

    @Override
    public void run() {
        try {
            while (true) {
                postiDifensori.acquire(); // Prende un posto come difensore

                System.out.println(nome + " sta ricevendo.");

                Thread.sleep(2000); // Simula il tempo di ricezione

                System.out.println(nome + " ha finito di ricevere.");

                Thread.sleep(300); // Pausa in modo tale che lo scheduler abbia il tempo per stampare il System.out.println sopra

                postiAttaccanti.release(); // Libera un posto per gli attaccanti

                Thread.sleep(2000); // Pausa prima di riprendere
            }
        } catch (InterruptedException e) {
            System.out.println(nome + " interrotto.");
        }
    }
}

