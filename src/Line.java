import java.awt.*;
import java.util.ArrayList;

public class Line {

    private final Graphic start;
    private final Graphic end;

    ArrayList<Point> linePts;
    private Color color;

    public Line(Graphic start, Graphic end) {
        this.start = start;
        this.end = end;
        this.linePts = new ArrayList<>();
        this.color = Color.BLACK;
    }

    public Color getColor() {
//        if (start.getColor().equals(end.getColor())) {
//            return start.getColor();
//        }

        return Color.BLACK;
    }

    public void setColor(Color color) {
        this.color = color;
    }

//    public LoCal getStart() {
//        return start;
//    }
//
//    public LoCal getEnd() {
//        return end;
//    }

    public ArrayList<Point> getLine() {
        if (start == null || end == null) {
            linePts.clear();
        }
        return linePts;
    }

    public void setLinePts(ArrayList<Point> line) {
        this.linePts = line;
    }

    public void appendPoint(Point point) {
        linePts.add(point);
    }

    public void appendPoint(int x, int y) {
        linePts.add(new Point(x, y));
        //this.end.setPoint(linePts.get(linePts.size() - 1));
    }

//    public boolean checkLine(ArrayList<LoCal> ends) {
//        return ends.contains(start) && ends.contains(end);
//    }


}
