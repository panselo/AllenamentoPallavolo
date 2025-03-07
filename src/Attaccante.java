//import java.util.concurrent.Semaphore;
//
//public class Attaccante extends Thread {
//    private String nome;
//    private Semaphore postiAttaccanti;
//    private Semaphore postiDifensori;
//
//    public Attaccante(String nome, Semaphore postiAttaccanti, Semaphore postiDifensori) {
//        this.nome = nome;
//        this.postiAttaccanti = postiAttaccanti;
//        this.postiDifensori = postiDifensori;
//    }
//
//    @Override
//    public void run() {
//        try {
//            while (true) {
//                postiAttaccanti.acquire(); // Prende un posto come attaccante
//
//                System.out.println(nome + " sta attaccando.");
//
//                Thread.sleep(2000); // Simula il tempo di attacco
//
//                System.out.println(nome + " ha finito di attaccare.");
//
//                Thread.sleep(300); // Pausa in modo tale che lo scheduler abbia il tempo per stampare il System.out.println sopra
//
//                postiDifensori.release(); // Libera un posto per i difensori
//
//                Thread.sleep(2000); // Pausa prima di riprendere
//            }
//        } catch (InterruptedException e) {
//            System.out.println(nome + " interrotto.");
//        }
//    }
//}


import java.util.concurrent.Semaphore;

class Attaccante extends Thread {
    private String nome;
    private Semaphore postiAttaccanti;
    private Semaphore postiDifensori;
    private Semaphore prontiPerScambio;
    private Semaphore scambioConcluso;
    private Semaphore sincronizzazioneStampe;
    private Semaphore contrattaccoScambio;

    public Attaccante(String nome, Semaphore postiAttaccanti, Semaphore postiDifensori, Semaphore prontiPerScambio, Semaphore scambioConcluso, Semaphore sincronizzazioneStampe, Semaphore contrattaccoScambio) {
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
                // Aspetta che ci sia spazio per un attaccante
                postiAttaccanti.acquire();

                // Stampa che l'attaccante sta attaccando
                sincronizzazioneStampe.acquire();
                System.out.println(nome + " sta attaccando.");
                sincronizzazioneStampe.release();

                // Simula l'attacco
                Thread.sleep(2000);

                // Stampa che l'attaccante ha finito
                sincronizzazioneStampe.acquire();
                System.out.println(nome + " ha finito di attaccare.");
                sincronizzazioneStampe.release();

                Thread.sleep(500);

                // Permette al difensore di iniziare il ricevimento
                prontiPerScambio.release();
                // Aspetta che il difensore abbia finito prima di fare il cambio
                scambioConcluso.acquire();

                // Stampa il cambio di ruolo
                sincronizzazioneStampe.acquire();
                System.out.println("--- Scambio ruolo: Attaccante -> Difensore ---");
                sincronizzazioneStampe.release();

                contrattaccoScambio.acquire();

                Thread.sleep(2000);

                // Stampa che l'attaccante sta attaccando
                sincronizzazioneStampe.acquire();
                System.out.println(nome + " sta difendendo.");
                sincronizzazioneStampe.release();

                // Simula l'attacco
                Thread.sleep(500);

                // Stampa che l'attaccante ha finito
                sincronizzazioneStampe.acquire();
                System.out.println(nome + " ha finito di difendere.");
                sincronizzazioneStampe.release();

                Thread.sleep(500);


                // Libera uno slot per un difensore
                postiDifensori.release();

                // Pausa per il ciclo
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            System.out.println(nome + " interrotto.");
        }
    }
}