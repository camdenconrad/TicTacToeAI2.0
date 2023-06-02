import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
public class Main {

    private static final JFrame frame = new JFrame();
    private static final JFrame game = new JFrame();
    private static final ArrayList<Graphic> gs = new ArrayList<>();
    private static Image graphics;
    private static Roots graph;
    private static NodeButton[][] buttons;

    public static void main(String[] args) throws IOException {

        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width, screenSize.height);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        graphics = new Image(screenSize.width, screenSize.height);
        frame.add(graphics);

        frame.setIconImage(ImageIO.read(new File("Images/diagram.png")));
        frame.setTitle("State Substate Visualizer");

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);


        game.setLayout(new GridLayout(3, 3));
        game.setSize(screenSize.height / 2, screenSize.height / 2);
        game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game.setName("game");
        //game.setExtendedState(JFrame.MAXIMIZED_BOTH);

        BufferedImage image = ImageIO.read(new File("Images/circle.png"));

        System.out.println("Fuck me");


        graph = new Roots();
        graph.fill(graph.head);


        for (Node n : graph.nodes) {
            //if(n.winNode) {
            gs.add(new Graphic(image, n));
            //}
        }

        for (Graphic g : gs) {
            graphics.drawImage(g);
            graphics.update();
        }

        drawLines(graph.head);
        graphics.update();

        System.out.println(graph.getSize());
        System.out.println("Finished.");

        Board board = new Board();

        buttons = new NodeButton[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new NodeButton(i, j, board);
            }
        }

        for (NodeButton[] button : buttons) {
            for (NodeButton b : button) {
                b.addActionListener();
                game.add(b);
            }
        }

        game.setIconImage(ImageIO.read(new File("Images/tic-tac-toe.png")));
        game.setTitle("Tic Tac Toe");
        game.setVisible(true);
        game.setResizable(false);

        //makeBotMove(board);

    }

    public static void drawLines(Node node) {
        if (node.getSubstates().isEmpty()) {
            // Base case: Stop recursion
            return;
        }

        for (Node substate : node.substates) {
            if (substate.winNodeX) {
                graphics.drawLine(node.state.getCartesian(), substate.state.getCartesian(), Color.RED);
                graphics.update();
            }
            if (substate.winNodeO) {
                graphics.drawLine(node.state.getCartesian(), substate.state.getCartesian(), Color.BLUE);
                graphics.update();
            } else if (!substate.winNode) {
                //Color lineColor = getColorBasedOnMoves(node.state.moves);
                graphics.drawLine(node.state.getCartesian(), substate.state.getCartesian(), Color.BLACK);
                graphics.update();
            }

            drawLines(substate);
            // Recursively call
        }

    }

//    private static Color getColorBasedOnMoves(int moves) {
//        // Define your own logic to determine the color based on the number of moves
//        if (moves < 1) {
//            return Color.BLUE;
//        } else if (moves > 10) {
//            return Color.RED;
//        } else {
//            // Calculate hue value for the gradient
//            float hue = (float) (moves - 1) / 9; // Normalize the moves to a range from 0 to 1
//
//            // Create a color using the hue value and adjust saturation and brightness
//            float saturation = 0.8f; // Adjust the saturation value to control the intensity of the green color
//            float brightness = 1.0f; // Adjust the brightness value to control the overall brightness of the color
//
//            // Create the color with adjusted saturation and brightness
//            return Color.getHSBColor(hue, saturation, brightness);
//        }
//    }

    public static void makeBotMove(Board board) {
        Point nextMove;
        if (board.moves < 9 && !board.winState()) {
            try {
                if (board.moves % 2 == 0) {
                    // defensive move
                    nextMove = Board.extractMove(board, graph.pathFindNext(board, 'o').state);
                } else {
                    // defensive move
                    nextMove = Board.extractMove(board, graph.pathFindNext(board, 'x').state);
                }

            } catch (NullPointerException ignored) {

                // if bot can no longer win, choose random node // doesn't try to tie yet
                nextMove = Board.extractMove(board, graph.findNode(board).substates.get(0).state);
            }

            if (nextMove != null) {
                buttons[nextMove.x][nextMove.y].doClick();
            }
        }
        else {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    buttons[i][j].reset();
                }
            }
            board.reset();
        }
    }


}