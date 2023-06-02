import java.awt.*;
import java.awt.image.BufferedImage;

public class Graphic {

    private final BufferedImage i;
    Node n;
    private Point p;

    public Graphic(BufferedImage image, Point point) {
        this.i = image;
        this.p = point;
    }

    public Graphic(BufferedImage image, Node node) {
        this.i = image;
        this.n = node;
        this.p = n.state.getCartesian();
    }

    public BufferedImage getImage() {
        return i;
    }

    public Point getPoint() {

        return p;
    }

    public void setP(Point p) {
        this.p = p;
    }
}
