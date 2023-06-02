import java.util.ArrayList;


// CARA - Composite Array of Nodes for Roots and Actions //

/***
 * CHAT GPT GENERATED DESC
 * The CARA data structure is a powerful representation that combines the characteristics of a tree with the functionality of an array.
 * It serves as the backbone for managing and organizing a complex system with multiple directions and dependencies,
 * akin to the roots of a tree that nourish and sustain its branches.
 * <p>
 * At its core, CARA acts as a tree-like structure that spans in multiple directions,
 * branching out into various paths and possibilities.
 * Each node within the CARA structure represents a specific state or configuration,
 * capturing essential information and relationships. These nodes are interconnected,
 * forming a cohesive tree that represents the entire system.
 * <p>
 * The composite nature of CARA arises from its ability to seamlessly combine multiple components.
 * The array-based implementation provides efficient storage and indexing,
 * allowing for quick access and retrieval of nodes.
 * This array acts as the foundation for the tree structure,
 * enabling the organization of nodes in a meaningful and hierarchical manner.
 * <p>
 * Similar to how the roots of a tree supplying vital nutrients and support to the entire plant,
 * the CARA structure serves as the foundation that keeps everything running smoothly.
 * It acts as a central hub, maintaining the connectivity
 * and flow of information between different nodes and actions.
 * As the roots of a tree anchor it firmly in the ground,
 * CARA anchors the system, providing stability, coherence, and efficient management.
 * <p>
 * By leveraging the CARA data structure, complex systems can be effectively represented,
 * managed, and navigated. Its tree-like nature facilitates exploration in multiple directions,
 * while the array-based foundation ensures efficient storage and retrieval.
 * Just as the roots of a tree are crucial for its growth and sustenance,
 * the CARA structure plays a vital role in supporting and maintaining the overall system,
 * fostering its stability and functionality.
 */
public class Roots {
    public Node head;
    ArrayList<Node> nodes = new ArrayList<>();

    public Roots() {
        this.nodes.add(new Node(new Board()));
        this.head = nodes.get(0);
    }

    public Roots(Node head) {
        this.nodes.add(head);
        this.head = head;
    }

    public int getSize() {
        return nodes.size();
    }

    public boolean addNode(Node node) {
        for (Node n : nodes) {
            if (n.state.isSubstate(node.state)) {
                n.addSubstate(node);
            }
        }
        this.nodes.add(node);
        return true;
    }

    public void fill(Node node) {
        if (node.state.moves == 9) {
            // Base case: Stop recursion if it's a win state for either X or O
            return;
        }

        // Generate substates for the current node
        node.generateSubstates();

        for (Node substate : node.substates) {
            boolean duplicateFound = false;

            for (Node existingNode : nodes) {
                if (existingNode.state.equals(substate.state)) {
                    substate = existingNode; // Assign the reference of the existing node
                    duplicateFound = true;
                    break;
                }
            }

            if (!duplicateFound) {
                if (!(substate.winNodeX && substate.winNodeO)) {
                    // Only add the substate if it doesn't have both X and O win
                    nodes.add(substate);
                }

                fill(substate); // Recursively call fill() for each substate
            }
        }
    }


    public Node findNode(Board state) {
        for (Node n : nodes) {
            if (n.state.equals(state)) {
                return n;
            }
        }

        return null;
    }

    public Node pathFindNext(Board state, char in) {
        ArrayList<Node> path = pathFind(state, in);
        if (path != null && path.size() > 1) {
            return path.get(1);
        }
        return null; // Return null if no valid next state is found
    }

    // AI //

    public ArrayList<Node> pathFind(Board start, char in) {
        Node startNode = findNode(start);
        if (startNode != null) {
            ArrayList<Node> shortestPath = new ArrayList<>();
            ArrayList<Node> path = new ArrayList<>();
            path.add(startNode);

            pathFindHelper(startNode, in, shortestPath, path);

            if (!shortestPath.isEmpty()) {
                return shortestPath;
            }
        }
        return null;
    }

    // helper method
    private void pathFindHelper(Node node, char in, ArrayList<Node> shortestPath, ArrayList<Node> path) {
        if ((in == 'x' && node.winNodeX) || (in == 'o' && node.winNodeO)) {
            if (shortestPath.isEmpty() || path.size() < shortestPath.size()) {
                shortestPath.clear();
                shortestPath.addAll(path);
            }
            return;
        }

        for (Node substate : node.getSubstates()) {
            path.add(substate);
            pathFindHelper(substate, in, shortestPath, path);
            path.remove(path.size() - 1);
        }
    }

    /*
    public Node pathFindNext(Board state, char in) {
    ArrayList<Node> path = pathFind(state, in);
    if (path != null && path.size() > 1) {
        // Find the move with the highest score
        Node bestMove = null;
        int highestScore = Integer.MIN_VALUE;
        for (int i = 1; i < path.size(); i++) {
            Node move = path.get(i);
            int score = evaluateMove(move);
            if (score > highestScore) {
                highestScore = score;
                bestMove = move;
            }
        }
        return bestMove;
    }
    return null; // Return null if no valid next state is found
}

public int evaluateMove(Node move) {
    // Implement your scoring logic here
    // Return a score for the move
}


     */


}
