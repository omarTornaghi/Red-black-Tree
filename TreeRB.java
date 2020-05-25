/* Author       :  Tornaghi Omar */
/* Description  :  Red black Tree */

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Comparator;

public class TreeRB<T extends Comparable<T>> {
    class Node {
        public ArrayList<T> value;
        public Node father, sx, dx;
        public boolean type; // true->rosso false->nero

        public Node() {
        }

        public Node(T value, boolean type) {
            father = null;
            sx = null;
            dx = null;
            this.value = new ArrayList<T>();
            this.value.add(value);
            this.type = type;
        }

        public boolean isLeaf() {
            return sx == null && dx == null;
        }

        @Override
        public String toString() {
            return value.get(0).toString();
        }
    }

    private Node root = null;

    public void insert(T item) {
        // Inserisco utilizzando comparable
        this.insert(item, (a, b) -> a.compareTo(b));
    }

    public void insert(T item, Comparator<T> c) {
        if (root == null) {
            root = makeNode(item, false);
            return;
        }
        Node currentNode = root;
        Node previousNode = null;
        // Cerco la posizione dove inserire
        while (currentNode != null) {
            previousNode = currentNode;
            // Controllo se il nodo ha tutti i figli rossi
            // Se si inverto il nodo
            if (checkFull(currentNode)) {
                invert(currentNode);
                // Controllo che non ci siano due nodi rossi consecutivi
                decompose(currentNode);
            }
            if (c.compare(currentNode.value.get(0), item) > 0) {
                currentNode = currentNode.sx;
            } else {
                if (c.compare(currentNode.value.get(0), item) == 0) {
                    currentNode.value.add(item);
                    return;
                } else
                    currentNode = currentNode.dx;
            }
        }
        // Creo il nodo e lo collego al padre
        currentNode = makeNode(item, true);
        currentNode.father = previousNode;
        if (c.compare(previousNode.value.get(0), item) > 0)
            previousNode.sx = currentNode;
        else
            previousNode.dx = currentNode;
        // Controllo che l'operazione di inserimento non
        // abbia modificato le proprietà RB
        decompose(currentNode);
    }

    public T find(T value) {
        return find(value, (a, b) -> a.compareTo(b));
    }

    public T find(T value, Comparator<T> c) {
        Node currentNode = root;
        while (currentNode != null) {
            if (c.compare(currentNode.value.get(0), value) == 0)
                return currentNode.value.get(0);
            if (c.compare(currentNode.value.get(0), value) > 0)
                currentNode = currentNode.sx;
            else
                currentNode = currentNode.dx;
        }
        return null;
    }

    public ArrayList<T> findAll(T value) {
        return findAll(value, (a, b) -> a.compareTo(b));
    }

    public ArrayList<T> findAll(T value, Comparator<T> c) {
        Node currentNode = root;
        while (currentNode != null) {
            if (c.compare(currentNode.value.get(0), value) == 0)
                return currentNode.value;
            if (c.compare(currentNode.value.get(0), value) > 0)
                currentNode = currentNode.sx;
            else
                currentNode = currentNode.dx;
        }
        return new ArrayList<T>();
    }

    private void invert(Node n) {
        if (n.sx != null)
            n.sx.type = !n.sx.type;
        if (n.dx != null)
            n.dx.type = !n.dx.type;
        if (n.father != null)
            n.type = !n.type;
    }

    private void decompose(Node n) {
        if (n == root)
            return;
        if (n.father.type) {
            // Due nodi rossi consecutivi
            // Sottoalbero orientato a sinistra
            if (n.father.sx == n && n.father.father.sx == n.father) {
                // Ruoto a destra
                if (n.father.father == root) {
                    rotationDX(n.father, n.father.father);
                    root = n.father;
                } else
                    rotationDX(n.father, n.father.father);
                return;
            }
            // Sottoalbero orientato a destra
            if (n.father.dx == n && n.father.father.dx == n.father) {
                // Ruoto a sinistra
                if (n.father.father == root) {
                    rotationSX(n.father, n.father.father);
                    root = n.father;
                } else
                    rotationSX(n.father, n.father.father);
                return;
            }
            // Caso "complesso": due orientazioni differenti
            // si aggiusta facendo due rotazioni diverse
            if (n.father.dx == n && n.father.father.sx == n.father) {
                rotationSX(n, n.father);
                if (n.father == root) {
                    rotationDX(n, n.father);
                    root = n;
                } else
                    rotationDX(n, n.father);
                return;
            }
            if (n.father.sx == n && n.father.father.dx == n.father) {
                rotationDX(n, n.father);
                if (n.father == root) {
                    rotationSX(n, n.father);
                    root = n;
                } else
                    rotationSX(n, n.father);
                return;
            }
        }
    }

    private void rotationDX(Node first, Node second) {
        Node dx = first.dx;
        first.dx = second;
        first.father = second.father;
        if (second.father != null) // Controllo dov'è il primo nodo
        {
            if (second.father.sx == second)
                second.father.sx = first;
            else
                second.father.dx = first;
        }
        second.father = first;
        second.sx = dx;
        if (dx != null)
            dx.father = second;
        boolean firstState = first.type;
        first.type = second.type;
        second.type = firstState;
    }

    private void rotationSX(Node first, Node second) {
        Node sx = first.sx;
        first.sx = second;
        first.father = second.father;
        if (second.father != null) {
            if (second.father.sx == second)
                second.father.sx = first;
            else
                second.father.dx = first;
        }
        second.father = first;
        second.dx = sx;
        if (sx != null)
            sx.father = second;
        boolean firstState = first.type;
        first.type = second.type;
        second.type = firstState;
    }

    private boolean checkFull(Node n) {
        return n.sx != null && n.sx.type && n.dx != null && n.dx.type;
    }

    private Node makeNode(T val, boolean type) {
        return new Node(val, type);
    }

    public void PrintTree() {
        RPrintTree(root, 0);
        System.out.println();
    }

    private void RPrintTree(Node current, int level) {
        if (current == null)
            return;
        else {
            RPrintTree(current.sx, level + 1);
            String printStr = "";
            for (int i = 0; i < current.value.size(); i++)
                printStr += current.value.get(i).toString() + " ";
            if (current.type)
                System.out.println(level + " : " + "\u001B[31m" + printStr + "\u001B[0m");
            else
                System.out.println(level + " : " + printStr);
            RPrintTree(current.dx, level + 1);
        }
    }

    public void inOrderPrint() {
        ArrayDeque<Node> queue = new ArrayDeque<Node>();
        int levelNodes = 1;
        int maxLevelNodes = 0;
        if (root != null)
            queue.add(root);
        while (!queue.isEmpty()) {
            Node current = queue.removeFirst();
            if (current == root || current.father != null) {
                String printStr = "";
                for (int i = 0; i < current.value.size(); i++)
                    printStr += current.value.get(i).toString() + " ";
                if (current.type)
                    System.out.print("\u001B[31m" + printStr + "\u001B[0m");
                else
                    System.out.print(printStr);
                if (current.sx != null)
                    queue.add(current.sx);
                else
                    queue.add(new Node());
                if (current.dx != null)
                    queue.add(current.dx);
                else
                    queue.add(new Node());
            } else
                System.out.print("NULL ");
            if (levelNodes == (int) Math.pow((int) 2, (int) maxLevelNodes)) {
                System.out.println();
                maxLevelNodes++;
                levelNodes = 1;
            } else
                levelNodes++;
        }
        System.out.println();
    }

}