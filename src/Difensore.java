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

    public Difensore(String nome, Semaphore postiAttaccanti, Semaphore postiDifensori, Semaphore prontiPerScambio, Semaphore scambioConcluso, Semaphore sincronizzazioneStampe) {
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
                prontiPerScambio.acquire(); // attende che un attaccante abbia terminato prima di iniziare la ricezione
                postiDifensori.acquire(); // occupa un posto come difensore, bloccando se non ci sono posti liberi
                synchronized (System.out) {
                    System.out.println(nome + " sta ricevendo.");
                }
                Thread.sleep(2000);
                synchronized (System.out) { // Segnala all'attaccante che la ricezione è completata e può riprendere
                    System.out.println(nome + " ha finito di ricevere.");
                }

                scambioConcluso.release(); // permette all'attaccante di continuare

                sincronizzazioneStampe.acquire();
                System.out.println("--- Scambio ruolo: Difensore -> Attaccante ---");
                sincronizzazioneStampe.release();

                postiAttaccanti.release(); // libera un posto per gli attaccanti, permettendo a un nuovo attaccante di entrare
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            System.out.println(nome + " interrotto.");
        }
    }
}
