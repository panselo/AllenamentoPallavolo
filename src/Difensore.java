//import java.util.concurrent.Semaphore;
//
//public class Difensore extends Thread {
//    private String nome;
//    private Semaphore postiAttaccanti;
//    private Semaphore postiDifensori;
//
//    public Difensore(String nome, Semaphore postiAttaccanti, Semaphore postiDifensori) {
//        this.nome = nome;
//        this.postiAttaccanti = postiAttaccanti;
//        this.postiDifensori = postiDifensori;
//    }
//
//    @Override
//    public void run() {
//        try {
//            while (true) {
//                postiDifensori.acquire(); // Prende un posto come difensore
//
//                System.out.println(nome + " sta ricevendo.");
//
//                Thread.sleep(2000); // Simula il tempo di ricezione
//
//                System.out.println(nome + " ha finito di ricevere.");
//
//                Thread.sleep(300); // Pausa in modo tale che lo scheduler abbia il tempo per stampare il System.out.println sopra
//
//                postiAttaccanti.release(); // Libera un posto per gli attaccanti
//
//                Thread.sleep(2000); // Pausa prima di riprendere
//            }
//        } catch (InterruptedException e) {
//            System.out.println(nome + " interrotto.");
//        }
//    }
//}


import java.util.concurrent.Semaphore;

class Difensore extends Thread {
    private String nome;
    private Semaphore postiAttaccanti;
    private Semaphore postiDifensori;
    private Semaphore prontiPerScambio;
    private Semaphore scambioConcluso;
    private Semaphore sincronizzazioneStampe;
    private Semaphore contrattaccoScambio;

    public Difensore(String nome, Semaphore postiAttaccanti, Semaphore postiDifensori, Semaphore prontiPerScambio, Semaphore scambioConcluso, Semaphore sincronizzazioneStampe, Semaphore contrattaccoScambio) {
        this.nome = nome;
        this.postiAttaccanti = postiAttaccanti;
        this.postiDifensori = postiDifensori;
        this.prontiPerScambio = prontiPerScambio;
        this.scambioConcluso = scambioConcluso;
        this.sincronizzazioneStampe = sincronizzazioneStampe;
        this.contrattaccoScambio = contrattaccoScambio;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Attende che l'attaccante abbia finito di attaccare
                prontiPerScambio.acquire();
                // Acquisisce un posto da difensore
                postiDifensori.acquire();

                // Stampa che il difensore sta ricevendo
                sincronizzazioneStampe.acquire();
                System.out.println(nome + " sta ricevendo.");
                sincronizzazioneStampe.release();

                // Simula la ricezione
                Thread.sleep(2000);

                // Stampa che il difensore ha finito
                sincronizzazioneStampe.acquire();
                System.out.println(nome + " ha finito di ricevere.");
                sincronizzazioneStampe.release();

                Thread.sleep(2000);

                // Stampa il cambio di ruolo
                sincronizzazioneStampe.acquire();
                System.out.println("--- Scambio ruolo: Difensore -> Attaccante ---");
                sincronizzazioneStampe.release();

                // Consente all'attaccante di proseguire
                scambioConcluso.release();

                Thread.sleep(500);

                contrattaccoScambio.acquire();

                sincronizzazioneStampe.acquire();
                System.out.println(nome + " sta contrattaccando.");
                sincronizzazioneStampe.release();

                // Simula la ricezione
                Thread.sleep(2000);

                // Stampa che il difensore ha finito
                sincronizzazioneStampe.acquire();
                System.out.println(nome + " ha finito di contrattaccare.");
                sincronizzazioneStampe.release();

                Thread.sleep(500);

                contrattaccoScambio.release();


                // Libera uno slot per un attaccante
                postiAttaccanti.release();

                // Pausa per il ciclo
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            System.out.println(nome + " interrotto.");
        }
    }
}
