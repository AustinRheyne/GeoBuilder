import java.util.ArrayList;
import java.util.Comparator;

public class Line {
    Point start;
    Point end;

    double[] domain = new double[2];
    double[] range = new double[2];

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;

        setDomain();
        setRange();

    }
    public void setStart(Point start){this.start=start;}
    public void setEnd(Point end) {this.end = end;}
    public Point getStart() {return start;}
    public Point getEnd() {return end;}
    public double[] getDomain() {return domain;}
    public double[] getRange() {return range;}
    public void setDomain() {
        if(start.getX() > end.getX()) {
            domain[0] = end.getX();
            domain[1] = start.getX();
        }
        else {
            domain[0] = start.getX();
            domain[1] = end.getX();
        }

    }
    public void setRange() {
        if(start.getY() > end.getY()) {
            range[0] = end.getY();
            range[1] = start.getY();
        }
        else {
            range[0] = start.getY();
            range[1] = end.getY();
        }
    }
    public void draw(){
        StdDraw.setPenRadius(0.005);
        StdDraw.line(start.getX(), start.getY(), end.getX(), end.getY());
    }

}
