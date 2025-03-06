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

    public Attaccante(String nome, Semaphore postiAttaccanti, Semaphore postiDifensori, Semaphore prontiPerScambio, Semaphore scambioConcluso, Semaphore sincronizzazioneStampe) {
        this.nome = nome;
        this.postiAttaccanti = postiAttaccanti;
        this.postiDifensori = postiDifensori;
        this.prontiPerScambio = prontiPerScambio;
        this.scambioConcluso = scambioConcluso;
        this.sincronizzazioneStampe = sincronizzazioneStampe;
    }

    @Override
    public void run() {
        try {
            while (true) {
                postiAttaccanti.acquire();

                synchronized (System.out) { // garantisce che le stampe dei thread non si sovrappongano
                    System.out.println(nome + " sta attaccando.");
                }
                Thread.sleep(2000);
                synchronized (System.out) { // garantisce che le stampe dei thread non si sovrappongano
                    System.out.println(nome + " ha finito di attaccare.");
                }

                prontiPerScambio.release(); // aspetta che un difensore abbia completato la ricezione prima di procedere
                scambioConcluso.acquire(); // aspetta il difensore

                sincronizzazioneStampe.acquire(); // garantisce che il messaggio di scambio venga stampato in ordine corretto
                System.out.println("--- Scambio ruolo: Attaccante -> Difensore ---");
                sincronizzazioneStampe.release(); // rilascia il controllo della stampa per permettere la prossima operazione

                postiDifensori.release(); // libera un posto per i difensori, permettendo a un nuovo difensore di entrare
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            System.out.println(nome + " interrotto.");
        }
    }
}