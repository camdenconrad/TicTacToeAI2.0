import java.util.ArrayList;

/***
 CARA - Connected Array-based Recursive Approach

 Description:

 CARA represents a Connected Array-based Recursive Approach that efficiently handles a network of interconnected elements. The acronym highlights the key features and characteristics of the code.

 C - Connected: The approach emphasizes the interconnected nature of the elements in the code. It recognizes the relationships and dependencies between nodes, enabling seamless navigation and traversal within the network. The code ensures that all nodes are connected and that the flow of information is maintained throughout the structure.

 A - Array-based: The code employs an array-based data structure to organize and manage the elements. By utilizing an ArrayList, the elements are efficiently stored, allowing for quick access and manipulation. The array-based approach provides a scalable and flexible foundation for handling the interconnected elements.

 R - Recursive: The code utilizes a recursive methodology to explore and process the interconnected elements. It employs recursive functions or methods to navigate through the network, diving deeper into substates or substructures. This recursive approach enables efficient traversal and analysis of the entire network, considering various paths and possibilities.

 A - Approach: CARA represents an approach or strategy to effectively handle the interconnected elements. It outlines a systematic and structured way of managing the nodes, ensuring comprehensive coverage and accurate results. The approach combines the array-based data structure and recursive methodology to create a cohesive and efficient solution.

 In summary, CARA (Connected Array-based Recursive Approach) encapsulates the code's essence by highlighting its connected nature, array-based data structure, recursive methodology, and strategic approach. Together, these aspects enable effective handling and exploration of the interconnected elements, providing a robust and reliable solution.
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
