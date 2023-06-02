import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Image extends JPanel {
    protected final ArrayList<Line> lines = new ArrayList<>();
    protected final ArrayList<BufferedImage> bLines = new ArrayList<>();
    private final BufferedImage img;
    private final ArrayList<Graphic> graphics = new ArrayList<>();

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public Image(int width, int height) {

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                img.setRGB(x, y, Color.WHITE.getRGB());
            }
        }
    }

    private static double frac(double a, double b) {
        return a / b;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.drawImage(img, 0, 0, this);
        g2d.dispose();
    }

    public void drawImage(BufferedImage image, int x, int y) {

        graphics.add(new Graphic(image, new Point(x, y)));

    }

    public void update() {
        //redraw();
//        for (Line l : lines) {
//            for (Point p : l.getLine()) {
//                drawPx((int) p.getX(), (int) p.getY());
//            }
//        }
//
//        for (Graphic l : graphics) {
//            img.createGraphics().drawImage(l.getImage(), l.getPoint().x - (l.getImage().getWidth() / 2) + (screenSize.width / 2), l.getPoint().y - (l.getImage().getHeight() / 2) + (screenSize.height / 2), null);
//        }

        this.updateUI();
    }

    public void drawPx(int x, int y) {
        try {
            img.setRGB(x, y, Color.BLACK.getRGB());
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    public void redraw() {
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                img.setRGB(x, y, Color.WHITE.getRGB());
            }
        }
    }

    public void drawLine(Point p1, Point p2) {
        p1.x += (screenSize.width / 2);
        p1.y += (screenSize.height / 2);

        p2.x += (screenSize.width / 2);
        p2.y += (screenSize.height / 2);

        Graphics2D g = img.createGraphics();
        g.setColor(Color.BLACK);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawLine(p1.x, p1.y, p2.x, p2.y);


    }

    public void drawLine(Point p1, Point p2, Color c) {
        p1.x += (screenSize.width / 2);
        p1.y += (screenSize.height / 2);

        p2.x += (screenSize.width / 2);
        p2.y += (screenSize.height / 2);

        Graphics2D g = img.createGraphics();
        g.setColor(c);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawLine(p1.x, p1.y, p2.x, p2.y);

    }

    public void drawLine(Graphic lp1, Graphic lp2) {
        if (!lp1.equals(lp2)) {
            Point p1 = lp1.getPoint();
            p1.x += (screenSize.width / 2);
            p1.y += (screenSize.height / 2);

            Point p2 = lp2.getPoint();
            p2.x += (screenSize.width / 2);
            p2.y += (screenSize.height / 2);

            Line locLine = new Line(lp1, lp2);

            if (p2.getX() > p1.getX()) {
                doLine(p1, p2, locLine, p2.getX(), p1.getX(), p2.getY(), p1.getY());
            } else {
                doLine(p1, p2, locLine, p1.getX(), p2.getX(), p1.getY(), p2.getY());

            }
            if (p1.x == p2.x) {
                if (p1.y > p2.y) {
                    for (int y = 0; y < (p1.y - p2.y); y++) {
                        locLine.appendPoint(p1.x, p2.y + y);
                    }
                } else {
                    for (int y = 0; y < (p2.y - p1.y); y++) {
                        locLine.appendPoint(p1.x, p1.y + y);
                    }
                }
            }

            lines.add(locLine);
        }

    }

    private void doLine(Point p1, Point p2, Line locLine, double x2, double x3, double y2, double y3) {
        if (p2.getY() > p1.getY()) {
            line2(p1, p2, locLine, x2, x3, y2, y3);
        } else {
            line2(p2, p1, locLine, x2, x3, y2, y3);
        }
    }

    private void line2(Point p1, Point p2, Line locLine, double x2, double x3, double y2, double y3) {

        for (int x = 0; x <= x2 - x3; x++) {
            for (int y = 0; y <= p2.getY() - p1.getY(); y++) {
                locLine.appendPoint((int) (x + x3), (int) (x * frac(y2 - y3, x2 - x3) + y3));
            }
        }
    }

    public void drawImage(Graphic graphic) {

        //graphics.add(graphic);
        Graphic l = graphic;
        img.createGraphics().drawImage(l.getImage(), l.getPoint().x - (l.getImage().getWidth() / 2) + (screenSize.width / 2), l.getPoint().y - (l.getImage().getHeight() / 2) + (screenSize.height / 2), null);
    }
}
