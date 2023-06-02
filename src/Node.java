import java.util.ArrayList;

public class Node {
    public boolean winNode = false;
    public boolean winNodeX = false;
    public boolean winNodeO = false;
    Board state;
    ArrayList<Node> substates = new ArrayList<>();

    public Node(Board state) {
        this.state = state;
        if (state.winState()) {
            winNode = true;
        }
        if (state.winState('x')) {
            winNodeX = true;
        }
        if (state.winState('o')) {
            winNodeO = true;
        }
    }

    public ArrayList<Node> getSubstates() {
        return substates;
    }

    public boolean addSubstate(Node node) {
        return substates.add(node);
    }

    public boolean generateSubstates() {
        for (int i = 0; i < 9; i++) {
            Node n = createSubstate(state, i);
            if (n != null) {
                addSubstate(n);
            }
        }
        return true;
    }

    private Node createSubstate(Board state, int stateNumber) {
        // Create a new Board object to represent the substate
        Board substate = new Board(state);

        // Determine the position to place 'x' based on the state number
        int row = stateNumber / 3;
        int col = stateNumber % 3;

        // Place 'x' at the determined position in the substate
        if (substate.doMove(row, col)) {
            return new Node(substate);
        }
        return null;
    }
}
