import javax.swing.*;
import java.awt.*;

public class NodeButton extends JButton {

    Point position;
    Board game;

    public NodeButton(int x, int y, Board board) {
        this.position = new Point(x, y);
        this.game = board;
        this.setBackground(new Color(255, 255, 255));

    }

    public NodeButton(Point position, Board board) {
        this.position = new Point(position.x, position.y);
        this.game = board;
        this.setBackground(new Color(255, 255, 255));

    }

    public void addActionListener() {
        this.addActionListener(e -> {
            game.doMove(position.x, position.y);
            if (game.moves % 2 == 0) {
                this.setBackground(new Color(189, 147, 226));
            } else {
                this.setBackground(new Color(227, 147, 74));
                Main.makeBotMove(game);
            }
        });
    }

    public void reset() {
        this.setBackground(new Color(255, 255, 255));
    }
}
