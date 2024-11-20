URL https://github.com/Nickolausen/unibo-oop-lab

CLONE git@github.com:Nickolausen/unibo-oop-lab.git

BRANCH lab09

LAB lab09/91-bad-gui-io

CASA lab09/92-simple-mvc-io
Tralasciando piccole differenze, le implementazioni di `Controller`, `SimpleGUI` e `SimpleGUIWithFileChooser` sono praticamente uguali. Nelle GUI ho raggruppato le istruzioni di relative alle interazioni con gli elementi Swing in `setupView()`, cercando di rendere più leggibile il codice.

CASA lab09/93-mvc-io
In `SimpleController` ho rappresentato la history delle stringhe stampate come un ArrayList — e non una LinkedList. Il getter della history non l'ho incluso per errore nell'interfaccia `Controller`, ho utilizzato `List.copyOf()` per restituire una copia della history (non ho considerato `Collections.unmodifiableList()`). Gestito diversamente il display degli elementi della lista history, realizzato tramite metodi di manipolazione delle stringhe. 