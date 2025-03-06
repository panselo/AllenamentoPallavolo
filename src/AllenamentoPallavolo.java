//import java.util.concurrent.Semaphore;
//import java.util.ArrayList;
//import java.util.List;
//
//public class AllenamentoPallavolo {
//    public static void main(String[] args) {
//        Semaphore postiAttaccanti = new Semaphore(1);
//        Semaphore postiDifensori = new Semaphore(1);
//
//        List<Thread> giocatori = new ArrayList<>();
//
//        // Creazione iniziale di 3 attaccanti e di 3 difensori
//        for (int i = 1; i <= 3; i++) {
//            giocatori.add(new Attaccante("Attaccante" + i, postiAttaccanti, postiDifensori));
//            giocatori.add(new Difensore("Difensore" + i, postiAttaccanti, postiDifensori));
//        }
//
//        // Avvio dei thread (coppia attaccante-difensore)
//        for (int i = 0; i < giocatori.size(); i+=2) {
//            giocatori.get(i).start();
//            try {
//                Thread.sleep(300);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            giocatori.get(i+1).start();
//            try {
//                Thread.sleep(300);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//}


import java.util.concurrent.Semaphore;
import java.util.ArrayList;

public class AllenamentoPallavolo {
    public static void main(String[] args) {
        Semaphore postiAttaccanti = new Semaphore(2); //max 2 attaccanti attivi
        Semaphore postiDifensori = new Semaphore(2); // max 2 difensori attivi
        Semaphore prontiPerScambio = new Semaphore(0); // in modo tale che attaccante e difensore si scambiano di ruolo
        Semaphore scambioConcluso = new Semaphore(0); // cosi non ci sono errori nelle stampe ed è piu ordinato
        Semaphore sincronizzazioneStampe = new Semaphore(1); // sincronizza i messaggi in modo tale da evitare ulteriori errori nelle stampe

        ArrayList<Thread> giocatori = new ArrayList<>();

        // 4 attaccanti e 4 difensori
        for (int i = 1; i <= 4; i++) {
            giocatori.add(new Attaccante("Attaccante" + i, postiAttaccanti, postiDifensori, prontiPerScambio, scambioConcluso, sincronizzazioneStampe));
            giocatori.add(new Difensore("Difensore" + i, postiAttaccanti, postiDifensori, prontiPerScambio, scambioConcluso, sincronizzazioneStampe));
        }

        // avvia tutti i thread dei giocatori con una breve pausa tra ciascuno
        for (Thread giocatore : giocatori) {
            giocatore.start();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
