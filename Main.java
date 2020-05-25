import java.util.*;

class Main {
    public static void main(String[] args) {
        TreeRB<Integer> tree = new TreeRB<Integer>();
        tree.insert(80);
        tree.insert(40);
        tree.insert(90);
        tree.insert(85);
        tree.insert(95);
        tree.insert(88);
        tree.insert(81);
        tree.insert(80);
        tree.inOrderPrint();
        tree.PrintTree();
    }

    private static void Esempi() {

        /* Primo esempio: Albero di caratteri */
        TreeRB<Character> tree = new TreeRB<Character>();
        // In questo caso in insert non specifico nulla
        // quindi si utilizza il metodo compareTo della classe Character
        tree.insert('A');
        tree.insert('B');
        tree.insert('C');
        tree.insert('D');
        tree.insert('E');
        tree.insert('F');
        System.out.println("\nPrimo esempio:\n");
        tree.inOrderPrint();
        tree.PrintTree();

        /* Secondo esempio: Albero di oggetti Persona ordinato alfabeticamente */
        TreeRB<Persona> treePersona = new TreeRB<Persona>();
        treePersona.insert(new Persona("Rossi", "Mario"));
        treePersona.insert(new Persona("Stefanoni", "Gianluca"));
        treePersona.insert(new Persona("Sturaro", "Lorenzo"));
        treePersona.insert(new Persona("Tornaghi", "Omar"));
        System.out.println("\nSecondo esempio:\n");
        treePersona.inOrderPrint();
        treePersona.PrintTree();

        /* Terzo esempio: Albero di oggetti Persona ordinato per età */
        treePersona = new TreeRB<Persona>();
        treePersona.insert(new Persona("Rossi", "Mario", 10), (a, b) -> Integer.compare(a.eta, b.eta));
        treePersona.insert(new Persona("Stefanoni", "Gianluca", 2), (a, b) -> Integer.compare(a.eta, b.eta));
        treePersona.insert(new Persona("Sturaro", "Lorenzo", 3), (a, b) -> Integer.compare(a.eta, b.eta));
        treePersona.insert(new Persona("Tornaghi", "Omar", 7), (a, b) -> Integer.compare(a.eta, b.eta));
        System.out.println("\nTerzo esempio:\n");
        treePersona.inOrderPrint();
        treePersona.PrintTree();
    }

}

class Persona implements Comparable<Persona> {
    public String cognome;
    public String nome;
    public int eta;

    public Persona(String cognome, String nome) {
        this.cognome = cognome;
        this.nome = nome;
    }

    public Persona(String cognome, String nome, int eta) {
        this.cognome = cognome;
        this.nome = nome;
        this.eta = eta;
    }

    @Override
    public int compareTo(Persona altra) {
        // Ordine alfabetico di default
        if (this.cognome.compareTo(altra.cognome) > 0) {
            // Vuol dire che il cognome corrente viene prima di
            // quello nel parametro
            return 1;
        }
        if (this.cognome.compareTo(altra.cognome) < 0) {
            // L'altro cognome è più piccolo di quello corrente
            return -1;
        }
        // Se sono arrivato a questo punto vuol dire che i cognomi
        // sono uguali, quindi controllo il nome
        if (this.nome.compareTo(altra.nome) > 0) {
            return 1;
        }
        if (this.nome.compareTo(altra.nome) < 0) {
            return -1;
        }
        // Cognome e nome sono uguali
        return 0;
    }

    @Override
    public String toString() {
        return cognome + " " + nome;
    }
}
