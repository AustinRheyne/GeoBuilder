import javax.sound.midi.Soundbank;
import java.util.ArrayList;

public class Shape {
    ArrayList<Point> points = new ArrayList<Point>();
    ArrayList<Line> lines = new ArrayList<Line>();

    public Shape(){
        newPoint(25, 50);
        newPoint(75, 50);
        newLine(points.get(0), points.get(1));
    }

    public Point newPoint(double x, double y) {
        Point point = new Point(x, y);
        points.add(point);
        return point;
    }
    public Line newLine(Point start, Point end) {
        Line line = new Line(start, end);
        lines.add(line);
        start.addLineConnection(line);
        end.addLineConnection(line);
        return line;
    }
    public ArrayList<Point> getPoints() {return points;}
    public Point getPoint(int index) {
        return points.get(index);
    }
    public void draw(){
        for (int i = 0; i < points.size(); i++){
            points.get(i).draw();
        }
        for (int i = 0; i < lines.size(); i++) {
            lines.get(i).draw();
        }
    }

    /**
     * Finds the nearest point on the perimeter of the shape by drawing a line perpendicular to each existing line that passes through the given
     * point, then finds the intersection, and measures distance. The point with the smallest distance is then returned.
     *
     * For n lines, there are n points checked.
     *
     * @param shadow takes a point that should be adjusted to the nearest point on the nearest line
     * @return A point object with the coordinates of the nearest point, or, a point with X: -100, Y: -100 if no such point exists.
     */
    public Point nearestPoint(Point shadow) {
        double distance = 10000; // Begin with a huge number so that a line is always found to be the closest
        Point closestPoint = new Point(0,0);
        for (int i = 0; i < lines.size(); i++) {
            Line line = lines.get(i);
            double dx = line.getStart().getX() - line.getEnd().getX();
            double dy = line.getStart().getY() - line.getEnd().getY();
            double slope = dy/dx;
            double x = 0;
            double y = 0;

            if (slope == 0) {
                y = line.start.getY();
                x = shadow.getX();
            } else {
                double perp = -1.0 / slope;
                double b1 = -(perp * shadow.getX()) + shadow.getY();
                double b2 = -(slope * line.getStart().getX()) + line.getStart().getY();

                /* The intersection of a line can be found by setting the equation
                   of the first line equal to the equation of the second line.
                   By knowing that x is the end result we can ignore x for now so,
                   we get y = m + b. We then set m1(x)+b1 = m2+b2.
                   So, we can combine like terms by moving things to the same side.
                   As such, our equation becomes m1 - m2 = b2 - b1. Finally, get x by itself by
                   changing our equation to (b2-b2)/(m1-m2). Then solve for y by plugging into
                   either side of the equation (y = mx+b)
                */

                x = (b1 - b2) / (slope - perp);
                y = (perp * x) + b1;
            }
            if (x > line.getDomain()[1]) {x = line.getDomain()[1];}
            if (x < line.getDomain()[0]) {x = line.getDomain()[0];}
            if (y > line.getRange()[1]) {y = line.getRange()[1];}
            if (y < line.getRange()[0]) {y = line.getRange()[0];}


            double dist = Math.sqrt((shadow.getX() - x)*(shadow.getX() - x) + (shadow.getY() - y)*(shadow.getY() - y));
            if (dist < distance) {
                distance = dist;
                closestPoint = new Point(x, y);
                closestPoint.addLineConnection(line);
            }

        }
        return closestPoint;
    }
    public Point findClickedPoint(double x, double y) {
        for (int i=0; i < points.size(); i++) {
            if (points.get(i).pointWithin(x, y)) {
                return points.get(i);
            }
        }
        return new Point(-100, -100);
    }
    public void removeLine(Line line) {
        line.getStart().removeLineConnection(line);
        line.getEnd().removeLineConnection(line);
        lines.remove(line);
    }
}
