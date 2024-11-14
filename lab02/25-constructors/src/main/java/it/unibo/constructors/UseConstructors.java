package it.unibo.constructors;

class UseConstructors {

    public static void main(final String[] args) {
        // NB: Per verificare la correttezza dei costruttori implementati
        // stampare a video le informazioni relative agli studenti (metodo
        // printStudentInfo) e ai treni (a tal fine implementare un metodo
        // printTrainInfo nella classe Train).

        // 1) Creare lo studente Mario Rossi, matricola 1014, anno
        // immatricolazione 2013
        final Student marioRossi = new Student(1014, "Mario", "Rossi", 2013);
        
        // 2) Creare lo studente Luca Bianchi, matricola 1018, anno
        // immatricolazione 2010
        final Student lucaBianchi = new Student(1018, "Luca", "Bianchi", 2010);

        // 3) Creare lo studente Peppino Vitiello, matricola 1019, anno
        // immatricolazione 2012
        final Student peppinoVitiello = new Student(1019, "Peppino", "Vitiello", 2012);
        
        // 4) Creare lo studente Luca Verdi, matricola 1020, anno
        // immatricolazione 2013
        final Student lucaVerdi = new Student(1020, "Luca", "Verdi", 2013);

        // 5) Creare un treno con 300 posti di cui 100 in prima classe 200 in
        // seconda classe
        final Train t1 = new Train(100, 200, 0, 0);

        // 6) Creare un treno con 1200 posti di cui 50 in prima classe 1050 in
        // seconda classe
        final Train t2 = new Train(50, 1150, 20, 200);

        // 7) Creare un treno con 500 posti, tutti di seconda classe
        final Train t3 = new Train(0, 500, 0, 0);
        
        // 8) Creare un treno con numero di posti di default
        final Train t4 = new Train(Train.DEFAULT_N_FC_SEATS, Train.DEFAULT_N_SC_SEATS, 20, 40);

        // 9) Verificare che il numero di posti
        // di default sia consistente (ossia che ci sia un numero positivo di
        // posti totali, e che la somma dei posti in prima e seconda classe dia
        // il totale dei posti sul treno).
    }
}
