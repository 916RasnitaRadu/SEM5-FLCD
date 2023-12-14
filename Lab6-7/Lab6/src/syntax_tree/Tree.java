package syntax_tree;

import grammar.Grammar;
import utils.TableEntry;

import java.util.*;
public class Tree {
    private Node root;
    private Grammar grammar;
    private int crt;
    private List<String> ws;
    private int indexInTreeSequence;

    public Tree(Grammar grammar) {
        this.root = null;
        this.grammar = grammar;
        this.crt = 0;
        this.ws = new ArrayList<>();
        this.indexInTreeSequence = 1;
    }

    public Node build(List<String> ws) {
        this.ws = ws;
        return buildRecursive(grammar.getS());
    }

    private Node buildRecursive(String currentSymbol) {
        if (Objects.equals(currentSymbol, "E") || ws.isEmpty() || (ws.size() == 1 && ws.contains("E"))) {
            return null;
        }

        Node node = new Node(currentSymbol, null, null);
        Node lastSibling = null;
        if (this.root == null) {
            this.root = node;
        }

        if (grammar.isTerminal(currentSymbol)) {
            if (currentSymbol.equals(ws.get(0))) {
                return node;
            } else {
                return null;
            }
        } else {
            List<String> productions = grammar.getProductionsFor(currentSymbol);

            for (String production : productions) {
                List<String> productionSymbols = List.of(production.trim().split(" "));

                for (String symbol : productionSymbols) {
                    Node child = buildRecursive(symbol);
                    if (child == null) {
                        break;
                    }

                    if (lastSibling == null) {
                        node.setChild(child);
                    } else {
                        lastSibling.setRightSibling(child);
                    }

                    lastSibling = child;
                    if (grammar.isTerminal(child.getValue())) {
                        ws = ws.subList(1, ws.size());
                    }
                }

                if (lastSibling != null) {
                    break;  // Production matched, exit the loop
                }
            }
            return node;
        }
    }



    public void printTable() {
        bfs(root, -1, -1);
    }

    private List<TableEntry> bfs(Node node, Integer fatherCrt, Integer rightSiblingCrt) {
        if (node == null) {
            return Collections.emptyList();
        }
        System.out.printf("%d | %s | %d | %d%n", crt, node.getValue(), fatherCrt, rightSiblingCrt);

        int crt = this.crt;
        this.crt += 1;

        if (rightSiblingCrt != -1) {
            List<TableEntry> children = new ArrayList<>(List.of(new TableEntry(node.getChild(), crt, -1)));
            children.addAll(bfs(node.getRightSibling(), fatherCrt, crt));
            return children;
        } else {
            List<TableEntry> children = new ArrayList<>(List.of(new TableEntry(node.getChild(), crt, -1)));
            children.addAll(bfs(node.getRightSibling(), fatherCrt, crt));
            for (TableEntry child : children) {
                bfs((Node) child.getItem(), (Integer) child.getParentIndex(), (Integer) child.getRightSiblingIndex());
            }
            return Collections.emptyList();
        }
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        Node node = this.root;
        while (node != null) {
            result.append(node);
            node = node.getRightSibling();
        }
        return result.toString();
    }
}

